package org.einar.regnskap.rema1000;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.Gson;
import org.einar.regnskap.rema1000.auth.RemaAccessToken;

import static java.nio.charset.StandardCharsets.UTF_8;

public class NewRemaAuthFlow {

    private static String AUTHORIZATION_ENDPOINT_URI = "https://id.rema.no/authorization";
    private static String AUTHORIZATION_REDIRECT_URI = "https://ae-appen.appspot.com";
    private static URI TOKEN_ENDPOINT_URI = URI.create("https://id.rema.no/token");
    private static String IDP_CLIENT_ID = "708554a0-29ec-11e9-b210-d663bd873d93";

    private static String AUTH_SCOPE = "all";


    public static RemaAccessToken accessToken() {

        String codeVerifier = generateRandomCodeVerifier();

        String authUrl = AUTHORIZATION_ENDPOINT_URI
            + "?redirect_uri=" + AUTHORIZATION_REDIRECT_URI
            + ";client_id=" + IDP_CLIENT_ID
            + ";response_type=code"
            + ";state=" + UUID.randomUUID()
            + ";scope=" + AUTH_SCOPE
            + ";code_challenge=" + codeChallenge(codeVerifier)
            + ";code_challenge_method=S256";


        System.out.println("AuthUrl: " + authUrl);

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter code: ");
        String code = scanner.next();


        String body = "grant_type=authorization_code"
            + "&code=" + URLEncoder.encode(code, UTF_8)
            + "&code_verifier=" + URLEncoder.encode(codeVerifier, UTF_8)
            + "&client_id=" + URLEncoder.encode(IDP_CLIENT_ID, UTF_8)
            + "&redirect_uri=" + URLEncoder.encode(AUTHORIZATION_REDIRECT_URI, UTF_8);


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(TOKEN_ENDPOINT_URI)
                                         .POST(BodyPublishers.ofString(body))
                                         .header("Content-Type", "application/x-www-form-urlencoded")
                                         //.header("Accept", "application/json")
                                         .build();
        System.out.println("Request: " + request);
        System.out.println("Body: " + body);
        System.out.println("Headers: " + request.headers());

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println("URI: " + response.uri());
            System.out.println("Status: " + response.statusCode());
            System.out.println("\nHeaders: " + response.headers());
            System.out.println("\n" + response.body());

            return new Gson().fromJson(response.body(), RemaAccessToken.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateRandomCodeVerifier() {
        return generateRandomCodeVerifier(new SecureRandom(), 64);
    }

    private static String generateRandomCodeVerifier(SecureRandom secureRandom, int i) {
        byte[] bytes = new byte[i];
        secureRandom.nextBytes(bytes);
        return new String(Base64.getUrlEncoder().withoutPadding().encode(bytes)); //11
    }

    private static String codeChallenge(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes("ISO_8859_1"));
            return new String(Base64.getUrlEncoder().withoutPadding().encode(instance.digest())); ///, 11);;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
