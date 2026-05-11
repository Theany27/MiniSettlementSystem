
package net.javaguides.MiniSettlement.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private int transactionId;
    private int merchantId;
    private float amount;
    private String status;
    private LocalDateTime createdAt;
    private String settlementStatus;
    private String merchantName;
    private float TotalAmount;
    private int pendingCount;
    private int settlementCount;
    
    public Transaction(){}
    
    public Transaction(int transactionId,int merchantId,float amount,String status,LocalDateTime createdAt,String settlementStatus,String merchantName,float TotalAmount,int pendingCount, int settlementCount){
        this.transactionId = transactionId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.settlementStatus = settlementStatus;
        this.merchantName = merchantName;
        this.TotalAmount = TotalAmount;
        this.pendingCount = pendingCount;
        this.settlementCount = settlementCount;
    }
    
    public int getTransactionId(){return transactionId;}
    public void setTransactionId(int transactionId){this.transactionId = transactionId;}
    
    
    public int getMerchantId(){return merchantId;}
    public void setMerchantId(int merchantId){this.merchantId = merchantId;}
    
    public float getAmount(){return amount;}
    public void setAmount(float amount){this.amount = amount;}
    
    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}
    
    public String getSettlementStatus(){return settlementStatus;}
    public void setSettlementStatus(String settlementStatus){this.settlementStatus = settlementStatus;}
    
    public String getCreatedAt(){
        if (createdAt == null) return "";
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a"));
    }
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt = createdAt;}
    
    public int getPendingCount(){return pendingCount;}
    public void setPendingCount(int pendingCount){this.pendingCount = pendingCount;}
    
    public int getSettlementCount(){return settlementCount;}
    public void setSettlementCount(int settlementCount){this.settlementCount = settlementCount;}
    
    public String getMerchantName(){return merchantName;}
    public void setMerchantName(String merchantName){this.merchantName = merchantName;}
    
    public float getTotalAmount(){return TotalAmount;}
    public void setTotalAmount(float TotalAmount){this.TotalAmount = TotalAmount;}
}


