package org.einar.regnskap.rema1000;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.einar.regnskap.rema1000.api.Transaction;
import org.einar.regnskap.rema1000.api.TransactionRow;
import org.einar.regnskap.rema1000.auth.GetAccessTokenLogic;
import org.einar.regnskap.rema1000.auth.MediaStoreToken;
import org.einar.regnskap.rema1000.auth.RemaAccessToken;
import org.einar.regnskap.rema1000.auth.TokenStore;
import org.einar.regnskap.rema1000.client.GetHeader;
import org.einar.regnskap.rema1000.client.GetHeaderLogic;
import org.einar.regnskap.rema1000.client.MediaStoreAuthClient;
import org.einar.regnskap.rema1000.client.RemaAuthClient;
import org.einar.regnskap.rema1000.client.RemaClient;
import org.einar.regnskap.rema1000.model.ProductItem;
import org.einar.regnskap.rema1000.model.Receipt;
import org.einar.regnskap.rema1000.model.ReceiptLogic;

import static org.einar.regnskap.rema1000.BuildConfig.IDP_APP_CLIENT_TOKEN;


public class App2 {

    public static void main(String... args) {
        org.apache.log4j.BasicConfigurator.configure();
        if (!refreshTokenValid()) {
            newLoginFlow();
        }
        TokenStore tokenVault = TokenStore.getInstance();

        RemaClient client = RemaClient.getInstance(BuildConfig.BASE_URL);
        List<Transaction> transactions = client.getTransactionHeads().getTransactions();

        Transaction transaction = transactions.get(new Random().nextInt(transactions.size()));
        //Transaction transaction = transactions.get(transactions.size() - 1);
        long id = transaction.getId();
        List<TransactionRow> rows = client.getTransactionRows(id);
        Receipt receipt = ReceiptLogic.createRecieptFromTransactionRows(rows, id);

        receipt.getProducts().forEach(item -> {
            loadAndDisplayImage(tokenVault, item);
        });
    }

    private static void loadAndDisplayImage(TokenStore tokenVault, ProductItem item) {
        MediaStoreToken mediaStoreToken = tokenVault.getMediaStoreToken()
                                                    .filter(t -> !t.isExpired())
                                                    .orElseGet(() -> {
                                                        MediaStoreToken token = MediaStoreAuthClient.getInstance().getToken();
                                                        tokenVault.saveMediaStoreToken(token);
                                                        return token;
                                                    });
        String uri = BuildConfig.IMAGE_URL + mediaStoreToken.getUrlAuthToken() + "/"
            + item.getGtin() + "/main/image?productgroupid="
            + item.getProductGroupCode()
            + "&internalId=" + item.getProductCode();

        System.out.println(uri);
        System.out.println(mediaStoreToken.getToken());


        loadAndDisplayImage(mediaStoreToken, uri);
    }

    private static void loadAndDisplayImage(MediaStoreToken mediaStoreToken, String uri) {
        try {
            URLConnection urlConn = new URL(uri).openConnection();
            urlConn.setRequestProperty("__token__", mediaStoreToken.getToken());
            urlConn.connect();
            InputStream urlStream = urlConn.getInputStream();
            Image image = ImageIO.read(urlStream);
            System.out.println("Load image into frame...");
            JLabel label = new JLabel(new ImageIcon(image));
            JFrame f = new JFrame();
            //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.getContentPane().add(label);
            f.pack();
            f.setLocation(100, 100);
            f.setVisible(true);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private static boolean refreshTokenValid() {
        try {
            RemaAccessToken accessToken = GetAccessTokenLogic.getInstance(
                RemaAuthClient.getInstance(BuildConfig.BASE_IDP_URL, IDP_APP_CLIENT_TOKEN, GetHeaderLogic.getInstance())
            ).getAccessToken(TokenStore.getInstance());
            return accessToken.getRefreshToken() != null;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static void deprecatedLoginWithOtp() {
        GetHeader getHeader = GetHeaderLogic.getInstance();
        RemaAuthClient authClient = RemaAuthClient.getInstance(BuildConfig.BASE_IDP_URL,
                                                               IDP_APP_CLIENT_TOKEN,
                                                               getHeader);
        String token = authClient.getToken();
        authClient.postPhoneNumber("90948784", token);
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter one time password: ");
        String otp = scanner.next();
        String tokenId = authClient.postOtp(otp, token);
        String code = authClient.getOauth2Token(tokenId);
        RemaAccessToken accessToken = authClient.getAccessToken(code);
        TokenStore.getInstance().saveAccessToken(accessToken);
    }

    public static void newLoginFlow() {
        TokenStore.getInstance().saveAccessToken(NewRemaAuthFlow.accessToken());
    }

}
