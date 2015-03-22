package org.einar.regnskap.receipts.finders;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeFinder {

    private static final Pattern pattern = Pattern.compile("(\\d{2}[.,]\\d{2}[.,]\\d{2}) (\\d{2}[ :]\\d{2})");
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyHH:mm");

    public static DateTime find(String data) {
        Matcher matcher = pattern.matcher(data);

        while(matcher.find()) {
            String date = matcher.group(1).replaceAll("[,/]",".");
            String time = matcher.group(2).replace(' ',':');
            try {
                return formatter.parseDateTime(date+time);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                //TODO log
            }
        }
        return null;
    };

}
