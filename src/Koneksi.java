/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Mendapatkan koneksi ke database MySQL.
 * Method ini akan membuat dan mengembalikan objek Connection yang dapat
 * digunakan untuk mengeksekusi query SQL ke database tripnesia.
 * 
 * @author tim F.N.M Trip
 */
public class Koneksi {
    private static final String url = "jdbc:mysql://localhost:3306/tripnesia";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

}
