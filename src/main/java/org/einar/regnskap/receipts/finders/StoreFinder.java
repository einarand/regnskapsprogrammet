package org.einar.regnskap.receipts.finders;

import org.einar.regnskap.receipts.Store;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by
 * User: einahage
 * Date: 2/19/14
 * Time: 8:40 PM
 */
public class StoreFinder {

    private static final Pattern pattern = Pattern.compile(
            "(RE[MH]A [1l][0O]{3} (.+))|" +
                    "(KI[WU]I (.+))|" +
                    "(JOKER (.+))",
            Pattern.MULTILINE);

    public static Store find(String receiptData) {
        Matcher matcher = pattern.matcher(receiptData);

        int remaGroup = 1;
        int kiwiGroup = 3;
        int jokerGroup = 5;

        while (matcher.find()) {
            if (matcher.group(remaGroup) != null) {
                return new Store("REMA 1000", matcher.group(remaGroup + 1));
            } else if (matcher.group(kiwiGroup) != null) {
                return new Store("KIWI", matcher.group(kiwiGroup + 1));
            } else if (matcher.group(jokerGroup) != null) {
                return new Store("Joker", matcher.group(jokerGroup + 1));
            }
        }
        return null;
    }

}
