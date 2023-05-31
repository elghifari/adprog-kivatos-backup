package com.service.kivatos.utils.enumeration;

public enum TransactionRekeningType {
    TRANSFER("transfer"),
    DEPOSIT("deposit"),
    PAYMENT("payment"),
    PEMBAYARAN_TAGIHAN_LISTRIK("listrik_2304"),
    PEMBAYARAN_TAGIHAN_TRANSPORTASI("transportasi_2305"),
    PEMBAYARAN_TAGIHAN_EDUCATION("education_2306");

    private final String transactionRekeningType;

    TransactionRekeningType(String transactionRekeningType){
        this.transactionRekeningType = transactionRekeningType;
    }

    public static TransactionRekeningType fromString(String transactionRekeningType){
        for(TransactionRekeningType type : TransactionRekeningType.values()){
            if(type.transactionRekeningType.equalsIgnoreCase(transactionRekeningType)){
                return type;
            }
        }
        return null;
    }

    public String getTransactionRekeningType(){
        return transactionRekeningType;
    }
}
