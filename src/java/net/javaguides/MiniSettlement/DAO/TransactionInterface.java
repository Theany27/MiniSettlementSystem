package net.javaguides.MiniSettlement.DAO;

import java.util.List;
import net.javaguides.MiniSettlement.Models.Transaction;


public interface TransactionInterface {
    void createTransaction(Transaction transaction);
    Transaction getById(int id);
    Transaction getTransacById(int transacId);
    List<Transaction> getAllTransaction(int limit, int offset);
    void updateTransaction(Transaction transaction);
    void deleteTransaction(int id);
    int count();
}
