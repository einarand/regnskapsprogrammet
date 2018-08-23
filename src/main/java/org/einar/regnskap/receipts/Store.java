package org.einar.regnskap.receipts;

public class Store {
    
    private final String chainName;
    private final String storeName;

    public Store(String chainName) {
        this(chainName, null);
    }

    public Store(String chainName, String storeName) {
        this.chainName = chainName;
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "Store{" +
                "chainName='" + chainName + '\'' +
                "storeName='" + storeName + '\'' +
                '}';
    }

    public String getChainName() {
        return chainName;
    }

    public String getStoreName() {
        return storeName;
    }
}
