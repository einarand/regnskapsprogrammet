package org.einar.regnskap.receipts.finders;

import org.einar.regnskap.receipts.LineItem;

public class Candidate {
    public final LineItem lineItem;
    public final double similarity;

    public Candidate(LineItem lineItem, double similarity) {
        this.lineItem = lineItem;
        this.similarity = similarity;
    }
}