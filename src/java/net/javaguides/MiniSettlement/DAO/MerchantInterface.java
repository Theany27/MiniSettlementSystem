package net.javaguides.MiniSettlement.DAO;

import java.util.List;
import net.javaguides.MiniSettlement.Models.Merchant;
import net.javaguides.MiniSettlement.Models.Transaction;

public interface MerchantInterface {
    void insertMerchant(Merchant merchant);
    Merchant getById(int id);
    List<Merchant> getAllMerchants();
    List<Transaction> getTransactionsByMerchantId(int merchantId);
    void updateMerchant(Merchant merchant);
    void deleteMerchant(int id);
}
