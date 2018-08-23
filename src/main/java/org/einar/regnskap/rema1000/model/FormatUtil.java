package org.einar.regnskap.rema1000.model;

import java.text.DecimalFormat;

public class FormatUtil {
    static String DECIMAL_PERCENT = "#0.0";
    static String DECIMAL_WHOLE = "#0";

    public static String formatPercent(double percent) {
        DecimalFormat df;
        if (percent % 1.0d == 0.0d) {
            df = new DecimalFormat(DECIMAL_WHOLE);
        } else {
            df = new DecimalFormat(DECIMAL_PERCENT);
        }
        String formattedString = df.format(percent).replace(".", ",");
        if (formattedString.endsWith("0,0")) {
            return formattedString.replace(",0", "");
        }
        return formattedString;
    }

    public static String formatKgDecimal(double percent) {
        DecimalFormat df;
        if (percent % 1.0d == 0.0d) {
            df = new DecimalFormat(DECIMAL_WHOLE);
        } else {
            df = new DecimalFormat("#0.00");
        }
        return df.format(percent).replace(".", ",");
    }

}
