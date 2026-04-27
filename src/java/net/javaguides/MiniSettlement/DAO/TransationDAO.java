package net.javaguides.MiniSettlement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import net.javaguides.MiniSettlement.Models.Transaction;

public class TransationDAO implements TransactionInterface {

    @Override
    public void createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction(merchant_id, amount, status, created_at, settlement_status) VALUES (?, ?, ?, ?,?)";
        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, transaction.getMerchantId());
            ps.setFloat(2, transaction.getAmount());
            ps.setString(3, transaction.getStatus());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
//            ps.setTimestamp(4, Timestamp.valueOf(transaction.getCreatedAt()));
            ps.setString(5, transaction.getSettlementStatus());

            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Transaction getById(int id) {
        String Sql = "SELECT * FROM transaction WHERE transaction_id = ?";
        Transaction transaction = null;
        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(Sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setMerchantId(rs.getInt("merchant_id"));
                transaction.setAmount(rs.getFloat("amount"));
                transaction.setStatus(rs.getString("status"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    transaction.setCreatedAt(timestamp.toLocalDateTime());
                } else {
                    transaction.setCreatedAt(LocalDateTime.now());
                }
                transaction.setSettlementStatus(rs.getString("settlement_status"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return transaction;
    }

    @Override
    public List<Transaction> getAllTransaction(int limit, int offset) {
        String sql = "SELECT * FROM transaction ORDER BY created_at DESC LIMIT ? OFFSET ?";
        List<Transaction> list = new ArrayList<>();

        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction tran = new Transaction();

                tran.setTransactionId(rs.getInt("transaction_id"));
                tran.setMerchantId(rs.getInt("merchant_id"));
                tran.setAmount(rs.getFloat("amount"));
                tran.setStatus(rs.getString("status"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    tran.setCreatedAt(timestamp.toLocalDateTime());
                } else {
                    tran.setCreatedAt(LocalDateTime.now());
                }
                tran.setSettlementStatus(rs.getString("settlement_status"));

                System.out.println("Transaction ID: " + tran.getTransactionId());
                System.out.println("Merchant Id: " + tran.getMerchantId());
                System.out.println("Amount: " + tran.getAmount());
                System.out.println("Status: " + tran.getStatus());
                System.out.println("Created at: " + tran.getCreatedAt());
                System.out.println("Settlement Status: " + tran.getSettlementStatus());

                System.out.println("-------------------");

                list.add(tran);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transaction SET merchant_id=?, amount=?, status=?, settlement_status=? WHERE transaction_id=?";
        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, transaction.getMerchantId());
            ps.setFloat(2, transaction.getAmount());
            ps.setString(3, transaction.getStatus());
            ps.setString(4, transaction.getSettlementStatus());
            ps.setInt(5, transaction.getTransactionId());

            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteTransaction(int id) {
        String sql = "DELETE FROM transaction WHERE transaction_id=?";
        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Transaction getTransacById(int transacId) {
        String Sql = "SELECT * FROM transaction WHERE transaction_id = ?";
        Transaction transaction = null;
        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(Sql)) {

            ps.setInt(1, transacId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setMerchantId(rs.getInt("merchant_id"));
                transaction.setAmount(rs.getFloat("amount"));
                transaction.setStatus(rs.getString("status"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    transaction.setCreatedAt(timestamp.toLocalDateTime());
                } else {
                    transaction.setCreatedAt(LocalDateTime.now());
                }
                transaction.setSettlementStatus(rs.getString("settlement_status"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return transaction;
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM transaction";

        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}
