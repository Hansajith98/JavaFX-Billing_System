package Billing_System;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Deshitha Hansajith
 */
import java.sql.*;
import javax.swing.*;

public class DB {
    public static Connection c;
    static Connection connecrDB;
    public static Connection connecrDB() {
    
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
           if(c==null)
           c = DriverManager.getConnection("jdbc:sqlite:aestimet.db");
           
         return c;  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
     
    }
}
