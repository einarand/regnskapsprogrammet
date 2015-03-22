package org.einar.regnskap.receipts.ocr;

public class ProcessingSettings {

    public enum OutputFormat {
        txt, rtf, docx, xlsx, pptx, pdfSearchable, pdfTextAndImages, xml
    }

    private String language = "English";
    private OutputFormat outputFormat = OutputFormat.pdfSearchable;

    public String asUrlParams() {
        return String.format("language=%s&exportFormat=%s&textType=%s&imageSource=%s&profile=%s", language,
                outputFormat, "normal", "photo", "textExtraction");
    }

    public void setOutputFormat(OutputFormat format) {
        outputFormat = format;
    }

    public OutputFormat getOutputFormat() {
        return outputFormat;
    }

    /*
     * Set recognition language. You can set any language listed at
     * http://ocrsdk.com/documentation/specifications/recognition-languages/ or
     * set comma-separated combination of them.
     *
     * Examples: English English,ChinesePRC English,French,German
     */
    public void setLanguage(String newLanguage) {
        language = newLanguage;
    }

    public String getLanguage() {
        return language;
    }

}
