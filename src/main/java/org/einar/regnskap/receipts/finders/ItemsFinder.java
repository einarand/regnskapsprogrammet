package org.einar.regnskap.receipts.finders;

import org.einar.regnskap.receipts.LineItem;
import org.einar.regnskap.receipts.Price;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by
 * User: einahage
 * Date: 2/18/14
 * Time: 11:37 PM
 */
public class ItemsFinder {

    private static final Pattern pattern = Pattern.compile(
                    "^(\\+ Pant\t.{1,2}[%*xX]\t(.+))$|" +               //+ Pant-->0%-->2,50
                    "^(KORR.+:(.+)\t.{1,2}[%*xX/]{1,3}.*\t(.+))$|" +    //KORR. SIST:VARE_NAVN-->15%-->-21,00
                    "^((.+)\t.{1,2}[%*xX/]{1,3}.*\t(.+))$|" +           //VARE_NAVN-->15%-->21,00
                    "^((\\d+) [xX] kr (.+))$|" +                        //2 x kr 10,50
                    "^(Su[mn] (\\d+) varer\\s+(.+))",                   //Sum 25 varer-->-->156,50
                    Pattern.MULTILINE);

    public static ItemsFinderResult find(String receiptData) {
        LinkedList<LineItem> lineItems = new LinkedList<LineItem>();
        Matcher matcher = pattern.matcher(receiptData);

        final int pantGroup = 1;
        final int correctionGroup = 3;
        final int itemGroup = 6;
        final int multiplierGroup = 9;
        final int sumGroup = 12;

        int index = 0;

        while (matcher.find()) {
            if (matcher.group(pantGroup) != null) {
                try {
                    LineItem lineItem = lineItems.removeLast();
                    Price newPrice = lineItem.getItemPrice().add(Price.parsePriceFromReceiptData(matcher.group(pantGroup + 1)));
                    lineItems.add(new LineItem(lineItem.getQuantity(), lineItem.getDescription() + " (inkl. pant)", newPrice, lineItem.getIndex()));
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
            } else if (matcher.group(itemGroup) != null) {
                index += 1;
                lineItems.add(new LineItem(matcher.group(itemGroup+1).toUpperCase(), Price.parsePriceFromReceiptData(matcher.group(itemGroup+2)), index));
            } else if (matcher.group(multiplierGroup) != null) {
                //What if previous was not found? Wrong one?
                try {
                    LineItem lineItem = lineItems.removeLast();
                    int multiplier = Integer.parseInt(matcher.group(multiplierGroup + 1));
                    lineItems.add(new LineItem(multiplier, lineItem.getDescription(), lineItem.getTotal(), lineItem.getIndex()));
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
            } else if (matcher.group(sumGroup) != null) {
                return new ItemsFinderResult(lineItems, Integer.parseInt(matcher.group(sumGroup+1)),
                        Price.parsePriceFromReceiptData(matcher.group(sumGroup + 2)));
            } else if (matcher.group(correctionGroup) != null) {
                String description = matcher.group(correctionGroup+1);
                Price price = Price.parsePriceFromReceiptData(matcher.group(correctionGroup+2));
                System.out.println("CORRECTION! " + description + ": " + matcher.group(correctionGroup+2));

                Candidate mostLikelyHit = new Candidate(null, 0);
                for (LineItem i : new LinkedList<LineItem>(lineItems)) {
                    double similarity = LevenshteinDistance.similarity(description, i.getDescription());
                    System.out.println(i.getDescription() + ": " + similarity);
                    if (similarity > mostLikelyHit.similarity) {
                        mostLikelyHit = new Candidate(i, similarity);
                    }
                }
                System.out.println("Corrected lineItem " + mostLikelyHit.lineItem);
                lineItems.remove(mostLikelyHit.lineItem);
            }
        }
        return new ItemsFinderResult(lineItems, 0, null);
    }


}
