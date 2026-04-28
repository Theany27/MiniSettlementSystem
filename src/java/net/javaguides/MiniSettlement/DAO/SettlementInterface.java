
package net.javaguides.MiniSettlement.DAO;

import java.util.List;
import net.javaguides.MiniSettlement.Models.Settlement;
import net.javaguides.MiniSettlement.Models.Transaction;

public interface SettlementInterface {
    void createSettlement(Settlement settlement);
    Settlement getById(int id);
    List<Settlement> getAllSettlement();
    void updateSettlement(Settlement settlement);
    void deleteSettlement(int id);
    float getPendingByMerchant(int merchantId);
    boolean markAsSettled(int merchantId); 
}
