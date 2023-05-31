package com.service.kivatos.utils.enumeration;

public enum TransactionEwalletType {
    TRANSFER("transfer"),
    TOPUP("topup"),
    PAYMENT("payment");

    private final String transactionEwalletType;

    TransactionEwalletType(String transactionEwalletType){
        this.transactionEwalletType = transactionEwalletType;
    }

    public static TransactionEwalletType fromString(String transactionEwalletType){
        for(TransactionEwalletType type : TransactionEwalletType.values()){
            if(type.transactionEwalletType.equalsIgnoreCase(transactionEwalletType)){
                return type;
            }
        }
        return null;
    }

    public String getTransactionEwalletType(){
        return transactionEwalletType;
    }
}
