package net.javaguides.MiniSettlement.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Merchant {
    private int merchant_id;
    private String name;
    private String email;
    private String phone;
    private String status;
    private LocalDateTime created_at;
        
//    public Merchant(){};
//    
//    
//    public Merchant(int merchant_id,String name,String email,String phone, String status,LocalDateTime created_at){
//            this.merchant_id = merchant_id;
//            this.name = name;
//            this.email = email;
//            this.phone = phone;
//            this.status = status;
//            this.created_at = created_at;
//    }
//    
//    
//    public int getId(){return merchant_id;}
//    public void setId(int merchant_id){this.merchant_id = merchant_id;}
//    
//    public String getName(){return name;}
//    public void setName(String name){this.name = name;}
//    
//    public String getEmail(){return email;}
//    public void setEmail(String email){this.email = email;}
//    
//    public String getPhone(){return phone;}
//    public void setPhone(String phone){this.phone = phone;}
//    
//    public String getStatus(){return status;}
//    public void setStatus(String status){this.status = status;}
//    
    public String getCreated_at(){
        if (created_at == null) return "";
        return created_at.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a"));
    }
//    public void setCreated_at(LocalDateTime created_at){this.created_at = created_at;}

//    public void setCreated_at(Timestamp timestamp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    
}
