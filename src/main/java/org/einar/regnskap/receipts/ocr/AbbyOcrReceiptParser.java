package org.einar.regnskap.receipts.ocr;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import org.einar.regnskap.receipts.LineItem;
import org.einar.regnskap.receipts.Receipt;
import org.einar.regnskap.receipts.ReceiptParser;
import org.einar.regnskap.receipts.Store;
import org.einar.regnskap.receipts.finders.DateTimeFinder;
import org.einar.regnskap.receipts.finders.ItemsFinder;
import org.einar.regnskap.receipts.finders.ItemsFinderResult;
import org.einar.regnskap.receipts.gui.ReceiptFrame;
import org.einar.regnskap.receipts.finders.StoreFinder;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by
 * User: einahage
 * Date: 2/16/14
 * Time: 11:54 PM
 */
public class AbbyOcrReceiptParser implements ReceiptParser {

    private final ProcessingSettings settings;

    public AbbyOcrReceiptParser() {
        settings = new ProcessingSettings();
        settings.setLanguage("Norwegian");
        settings.setOutputFormat(ProcessingSettings.OutputFormat.xml);
    }

    public Receipt parseFromFile(String filename) {
        String receiptData = "";

        try {
            receiptData = readFromFile(filename + ".txt");
        } catch (Exception e) {
            receiptData = uploadWaitAndDownload(filename + ".jpg");
            saveToFile(filename+".xml", receiptData);
        }

        Store store = StoreFinder.find(receiptData);
        DateTime dateTime = DateTimeFinder.find(receiptData);

        ItemsFinderResult itemsFinderResult = ItemsFinder.find(receiptData);

        Receipt r = new Receipt(store, dateTime, itemsFinderResult.getSubTotal(), itemsFinderResult.getNrOfItems());
        r.setLineItems(itemsFinderResult.getLineItems());

        new ReceiptFrame(r);


        for (LineItem i : itemsFinderResult.getLineItems()) {
            System.out.println(i);
        }

        System.out.println(r);
        return null;

    }

    private void saveToFile(String filename, String data) {
        try {
            File file = new File(filename);
            Files.append(data, file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException("Could not save file " + filename, e);
        }
    }

    private String readFromFile(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            return CharStreams.toString(new InputStreamReader(fis, "UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("Could not read file " + filename, e);
        }
    }

    private String uploadWaitAndDownload(String filename) {
        try {
            Client c = new Client("minedingser", "9EFZr1ZGUKaBAZrWu/a1ottk");
            System.out.println("Uploading file");
            Task task = c.processImage(filename, settings);
            for (int i = 0; i < 30; i++) {
                task = c.getTaskStatus(task);
                if (!task.isTaskActive()) {
                    System.out.println("Task status: " + task.Status);
                    break;
                }
                System.out.println("Waiting");
                Thread.sleep(1000);
            }

            InputStream is = c.downloadResult(task);
            return CharStreams.toString(new InputStreamReader(is, "UTF-8"));

        } catch (Exception e) {
            throw new RuntimeException("Could not parse " + filename, e);
        }
    }

    public static void main(String[] args) {
        //new AbbyOcrReceiptParser().parseFromFile("kvitteringJoker_1");
        //new AbbyOcrReceiptParser().parseFromFile("resultat3");
        //new AbbyOcrReceiptParser().parseFromFile("kvitteringKiwi_5");
        new AbbyOcrReceiptParser().parseFromFile("receipts/kvitteringREMA1000_2_test");
    }

}
