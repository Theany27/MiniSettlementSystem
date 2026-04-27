
package net.javaguides.MiniSettlement.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Settlement {
    private int settlementId;
    private int merchantId;
    private float totalAmount;
    private float fee;
    private float netAmount;
    private LocalDateTime createdAt;
    
    public Settlement(){}
    
    public Settlement(int settlementId, int merchantId, float totalAmount, float fee, float netAmount,LocalDateTime createdAt){
           this.settlementId = settlementId;
           this.merchantId = merchantId;
           this.totalAmount = totalAmount;
           this.fee = fee;
           this.netAmount = netAmount;
           this.createdAt = createdAt;
    }
    
    public int getSettlementId(){return settlementId;}
    public void setSettlementId(int settlementId){this.settlementId = settlementId;}
    
    public int getMerchantId(){return merchantId;}
    public void setMerchantId(int merchantId){this.merchantId = merchantId;}
    
    public float getTotalAmount(){return totalAmount;}
    public void setTotalAmount(float totalAmount){this.totalAmount = totalAmount;}
    
    public float getFee(){return fee;}
    public void setFee(float fee){this.fee = fee;}
    
    public float getNetAmount(){return netAmount;}
    public void setNetAmount(float netAmount){this.netAmount = netAmount;}
    
     public String getCreatedAt(){
        if (createdAt == null) return "";
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a"));
    }
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt = createdAt;}
}
