package net.javaguides.MiniSettlement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import net.javaguides.MiniSettlement.Models.Settlement;

public class SettlementDAO implements SettlementInterface {

    @Override
    public void createSettlement(Settlement settlement) {
        String sql = "INSERT INTO settlement( merchant_id, total_amount, fee, net_amount ,created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, settlement.getMerchantId());
            ps.setFloat(2, settlement.getTotalAmount());
            ps.setFloat(3, settlement.getFee());
            ps.setFloat(4, settlement.getNetAmount());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Settlement getById(int id) {
        String Sql = "SELECT * FROM settlement WHERE settlement_id = ?";
        Settlement settlement = null;
        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(Sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                settlement = new Settlement();
                settlement.setSettlementId(rs.getInt("settlement_id"));
                settlement.setMerchantId(rs.getInt("merchant_id"));
                settlement.setTotalAmount(rs.getFloat("total_amount"));
                settlement.setFee(rs.getFloat("fee"));
                settlement.setNetAmount(rs.getFloat("net_amount"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    settlement.setCreatedAt(timestamp.toLocalDateTime());
                } else {
                    settlement.setCreatedAt(LocalDateTime.now());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return settlement;
    }

    @Override
    public List<Settlement> getAllSettlement() {
        String sql = "SELECT * FROM settlement ORDER BY created_at DESC";
        List<Settlement> list = new ArrayList<>();

        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Settlement set = new Settlement();

                set.setSettlementId(rs.getInt("settlement_id"));
                set.setMerchantId(rs.getInt("merchant_id"));
                set.setTotalAmount(rs.getFloat("total_amount"));
                set.setFee(rs.getFloat("fee"));
                set.setNetAmount(rs.getFloat("net_amount"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    set.setCreatedAt(timestamp.toLocalDateTime());
                } else {
                    set.setCreatedAt(LocalDateTime.now());
                }

                System.out.println("Settlement ID: " + set.getSettlementId());
                System.out.println("Merchant Id: " + set.getMerchantId());
                System.out.println("Total Amount: " + set.getTotalAmount());
                System.out.println("Fee: " + set.getFee());
                System.out.println("Net Amount: " + set.getNetAmount());
                System.out.println("Created at: " + set.getCreatedAt());
                System.out.println("-------------------");
                list.add(set);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateSettlement(Settlement settlement) {
        String sql = "UPDATE settlement SET merchant_id=?, total_amount=?, fee=?, net_amount=? WHERE settlement_id=?";
        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, settlement.getMerchantId());
            ps.setFloat(2, settlement.getTotalAmount());
            ps.setFloat(3, settlement.getFee());
            ps.setFloat(4, settlement.getNetAmount());

            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteSettlement(int id) {
        String sql = "DELETE FROM settlement WHERE settlement_id=?";
        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public float getPendingByMerchant(int merchantId) {
        float total = 0;

        String sql = "SELECT SUM(amount) FROM transaction WHERE merchant_id = ? AND settlement_status = 'PENDING'";

        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchantId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    @Override
    public boolean markAsSettled(int merchantId) {
        String sql = "UPDATE transaction SET settlement_status = 'SETTLED' WHERE merchant_id = ?";

        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, merchantId);
              int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
