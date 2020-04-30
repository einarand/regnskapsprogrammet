package org.einar.regnskap.rema1000.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.einar.regnskap.rema1000.api.Transaction;
import org.einar.regnskap.rema1000.api.TransactionRow;
import org.einar.regnskap.rema1000.api.UsedOffer;

public class ReceiptLogic {
    public static final int APP_START_YEAR = 2017;

    private static String getMonth(int monthNumber) {
        switch (monthNumber) {
            case 0:
                return "januar";
            case 1:
                return "februar";
            case 2:
                return "mars";
            case 3:
                return "april";
            case 4:
                return "mai";
            case 5:
                return "juni";
            case 6:
                return "juli";
            case 7:
                return "august";
            case 8:
                return "september";
            case 9:
                return "oktober";
            case 10:
                return "november";
            case 11:
                return "desember";
            default:
                return "";
        }
    }

    private static List<ReceiptSummary> createReceiptSummariesFromReceiptMonths(List<ReceiptMonth> receiptMonths) {
        List<ReceiptSummary> receiptSummaries = new ArrayList<>();
        for (ReceiptMonth rm : receiptMonths) {
            if (!rm.getStripeLabel().startsWith("20")) {
                receiptSummaries.addAll(rm.getReceiptSummaries());
            }
        }
        return receiptSummaries;
    }

    public static Receipt createRecieptFromTransactionRows(List<TransactionRow> transactionRows, long transactionId) {
        Receipt receipt = new Receipt();
        receipt.setId(transactionId);
        List<ProductItem> productItems = new ArrayList<>();
        for (TransactionRow row : transactionRows) {
            ProductItem productItem = new ProductItem();
            productItem.setAmount(row.getAmount());
            productItem.setVolume(row.getVolume());
            productItem.setDiscount(row.getDiscount());
            productItem.setDeposit(row.getDeposit());
            productItem.setGtin(row.getProdtxt3());
            productItem.setProductName(row.getProdtxt1());
            productItem.setProductDescription(row.getProdtxt2());
            productItems.add(productItem);
            productItem.setPieces(row.getPieces());
            productItem.setProductCode(row.getProductCode());
            productItem.setProductGroupCode(row.getProductGroupCode());
            productItem.setProductUnit(ProductUnit.KG);
            if (row.getUsedOffers() != null) {
                List<Offer> offers = new ArrayList<>();
                Iterator it = row.getUsedOffers().iterator();
                while (it.hasNext()) {
                    UsedOffer usedOffer = (UsedOffer) it.next();
                    offers.add(new Offer(usedOffer.getOfferCode(), usedOffer.getOfferDesc(), usedOffer.getDiscount(), usedOffer.getDiscountPercent()));
                }
                productItem.setOffers(offers);
            }
        }
        receipt.setProducts(combineIdenticalProductItems(filterEmptyProductItems(productItems)));
        return receipt;
    }

    public static List<ProductItem> filterEmptyProductItems(List<ProductItem> productItems) {
        List<ProductItem> filteredList = new ArrayList<>();
        Iterator<ProductItem> it = productItems.iterator();
        while (it.hasNext()) {
            ProductItem productItem = it.next();
            if (productItem.getProductCode() != null) {
                filteredList.add(productItem);
            }
        }
        return filteredList;
    }

    public static List<ProductItem> combineIdenticalProductItems(List<ProductItem> productItems) {
        HashMap<String, ProductItem> productItemHashMap = new HashMap<>();
        for (ProductItem productItem : productItems) {
            if (productItemHashMap.containsKey(productItem.getProductCode())) {
                ProductItem item = productItemHashMap.get(productItem.getProductCode());
                item.setAmount(item.getAmount() + productItem.getAmount());
                item.setPieces(item.getPieces() + productItem.getPieces());
                item.setDiscount(item.getDiscount() + productItem.getDiscount());
                item.setVolume(item.getVolume() + productItem.getVolume());
                if (productItem.getOffers() != null) {
                    item.getOffers().addAll(productItem.getOffers());
                }
                productItemHashMap.put(item.getProductCode(), item);
            } else {
                if (productItem.getOffers() == null) {
                    productItem.setOffers(new ArrayList<>());
                }
                productItemHashMap.put(productItem.getProductCode(), productItem);
            }
        }
        List<ProductItem> filteredProductItems = new ArrayList<>();
        for (Entry<String, ProductItem> entry : productItemHashMap.entrySet()) {
            filteredProductItems.add(productItemHashMap.get(entry.getKey()));
        }
        combineIdenticalOffers(filteredProductItems);
        return filteredProductItems;
    }

    private static void combineIdenticalOffers(List<ProductItem> productItems) {
        Iterator it = productItems.iterator();
        while (it.hasNext()) {
            ProductItem productItem = (ProductItem) it.next();
            HashMap<String, Offer> offerHashMap = new HashMap<>();
            for (Offer o : productItem.getOffers()) {
                if (offerHashMap.containsKey(o.getOfferCode())) {
                    Offer offer = offerHashMap.get(o.getOfferCode());
                    offer.setDiscount(offer.getDiscount() + o.getDiscount());
                    offerHashMap.put(offer.getOfferCode(), offer);
                } else {
                    offerHashMap.put(o.getOfferCode(), o);
                }
            }
            List<Offer> combinedOffers = new ArrayList<>();
            combinedOffers.addAll(offerHashMap.values());
            productItem.setOffers(combinedOffers);
        }
    }
}
