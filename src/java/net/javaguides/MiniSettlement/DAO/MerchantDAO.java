package net.javaguides.MiniSettlement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import net.javaguides.MiniSettlement.Models.Merchant;
import net.javaguides.MiniSettlement.Models.Transaction;

public class MerchantDAO implements MerchantInterface {

    @Override
    public void insertMerchant(Merchant merchant) {
        String sql = "INSERT INTO merchant(name, email, phone, status,created_at) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, merchant.getName());
            ps.setString(2, merchant.getEmail());
            ps.setString(3, merchant.getPhone());
            ps.setString(4, merchant.getStatus());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Merchant getById(int id) {
        String sql = "SELECT * FROM merchant WHERE merchant_id = ?";
        Merchant merchant = null;
        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                merchant = new Merchant();
                merchant.setMerchant_id(rs.getInt("merchant_id"));
                merchant.setName(rs.getString("name"));
                merchant.setEmail(rs.getString("email"));
                merchant.setPhone(rs.getString("phone"));
                merchant.setStatus(rs.getString("status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return merchant;
    }

    @Override
    public List<Merchant> getAllMerchants() {
        String sql = "SELECT * FROM merchant ORDER BY created_at ASC";
        List<Merchant> list = new ArrayList<>();

        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Merchant m = new Merchant();

                m.setMerchant_id(rs.getInt("merchant_id"));
                m.setName(rs.getString("name"));
                m.setEmail(rs.getString("email"));
                m.setPhone(rs.getString("phone"));
                m.setStatus(rs.getString("status"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    m.setCreated_at(timestamp.toLocalDateTime());
                } else {
                    m.setCreated_at(LocalDateTime.now());
                }
                System.out.println("Merchant ID: " + m.getMerchant_id());
                System.out.println("Name: " + m.getName());
                System.out.println("Email: " + m.getEmail());
                System.out.println("Status: " + m.getStatus());
                System.out.println("Created at: " + m.getCreated_at());
                System.out.println("ALL : " + m.toString());
                System.out.println("-------------------");

                list.add(m);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateMerchant(Merchant merchant) {
        String sql = "UPDATE merchant SET name=?, email=?, phone=?, status=? WHERE merchant_id=?";
        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, merchant.getName());
            ps.setString(2, merchant.getEmail());
            ps.setString(3, merchant.getPhone());
            ps.setString(4, merchant.getStatus());
            ps.setInt(5, merchant.getMerchant_id());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteMerchant(int id) {
        String sql = "DELETE FROM merchant WHERE merchant_id=?";

        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getTransactionsByMerchantId(int merchantId) {
        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT t.transaction_id, m.name AS merchant_name,\n"
                + "t.amount, t.status, t.created_at, t.settlement_status,\n"
                + "SUM(t.amount) OVER (PARTITION BY t.merchant_id) AS total_amount\n"
                + "FROM transaction t\n"
                + "LEFT JOIN merchant m ON t.merchant_id = m.merchant_id\n"
                + "WHERE t.merchant_id = ?\n"
                + " AND t.settlement_status = 'PENDING'\n"
                + "ORDER BY t.created_at DESC;";

        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, merchantId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransactionId(rs.getInt("transaction_id"));
                t.setAmount(rs.getFloat("amount"));
                t.setStatus(rs.getString("status"));
                t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                t.setSettlementStatus(rs.getString("settlement_status"));
                t.setMerchantName(rs.getString("merchant_name"));
                t.setTotalAmount(rs.getFloat("total_amount"));

                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Transaction> CountPendingNSettle() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "SUM(CASE WHEN settlement_status = 'PENDING' THEN 1 ELSE 0 END) AS pending_count,\n"
                + "SUM(CASE WHEN settlement_status = 'SETTLED' THEN 1 ELSE 0 END) AS settled_count\n"
                + "FROM transaction";
        
        try(Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Transaction tran = new Transaction();
                tran.setPendingCount(rs.getInt("pending_count"));
                tran.setSettlementCount(rs.getInt("settled_count"));
                list.add(tran);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

}
