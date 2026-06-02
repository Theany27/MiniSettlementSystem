/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.javaguides.MiniSettlement.DAO;

import java.util.List;
import net.javaguides.MiniSettlement.Models.User;

/**
 *
 * @author ROG G17
 */
public interface UserInterface {
    List<User> getAllUser();
    User getUserById(int id);
    void UpdateUser(User user);
    void deleteUser(int id);
    boolean UpdatePassword(String username,String userpass);
    void expiredPassword();
    int count();
}
