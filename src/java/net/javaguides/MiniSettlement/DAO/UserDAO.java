package net.javaguides.MiniSettlement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.MiniSettlement.Models.User;

public class UserDAO implements UserInterface {

    public boolean register(String username, String password, String role) {
        try (Connection conn = dbContext.DBConnection.getConnection()) {

            // check existing user
            String check = "SELECT * FROM users WHERE username=?";
            PreparedStatement ps1 = conn.prepareStatement(check);
            ps1.setString(1, username);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                return false;
            }

            // insert user
            String insert = "INSERT INTO users(username,password,role,firstlog) VALUES(?,?,?,?)";
            PreparedStatement ps2 = conn.prepareStatement(insert);
            int firstlog = 1;
            ps2.setString(1, username);
            ps2.setString(2, password);
            ps2.setString(3, role);
            ps2.setInt(4, firstlog);
            ps2.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(int id, String username, String password, String role) {
        try (Connection conn = dbContext.DBConnection.getConnection()) {

            String sql = "UPDATE users SET username=?, password=?, role=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.setInt(4, id);
//            ps.setBoolean(5, false);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public boolean login(String username, String password) {
//        try (Connection conn =dbContext.DBConnection.getConnection()) {
//
//            String sql = "SELECT * FROM users WHERE username=? AND password=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, username);
//            ps.setString(2, password);
//
//            ResultSet rs = ps.executeQuery();
//
//            return rs.next();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public User login(String username, String password) {
        User user = null;

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role")); // IMPORTANT: from DB
                user.setFirstLog(rs.getInt("firstlog"));
                Timestamp timestamp = rs.getTimestamp("updated_at");
                if (timestamp != null) {
                    user.setUpdated_at(timestamp.toLocalDateTime());
                } else {
                    user.setUpdated_at(LocalDateTime.now());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user; // null if not found
    }

    @Override
    public List<User> getAllUser() {
        String sql = "SELECT * FROM users ORDER BY id ASC";
        List<User> users = new ArrayList<>();
        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));

                users.add(u);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;
        try (Connection conn = dbContext.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setFirstLog(rs.getInt("firstlog"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public void UpdateUser(User user) {
        String sql = "UPDATE users SET username=?, password=?, role=? WHERE id=?";
        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection con = dbContext.DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM users";

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

    @Override
    public void expiredPassword() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean UpdatePassword(String username, String userpass) {

        try (Connection con = dbContext.DBConnection.getConnection();) {
            String sql = "UPDATE users SET password=?, firstlog=0, updated_at=CURRENT_TIMESTAMP WHERE username=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userpass); // password
            ps.setString(2, username); // username

            int rows = ps.executeUpdate();

            System.out.println("Updated rows: " + rows);

            return rows > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
