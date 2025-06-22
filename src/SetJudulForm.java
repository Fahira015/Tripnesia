/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Antarmuka (interface) untuk mengatur judul pada form.
 * Interface ini menyediakan metode abstrak untuk menetapkan judul
 * dan metode default untuk mencatat log judul yang diatur.
 * 
 * @author tim F.N.M Trip
 */

public interface SetJudulForm {
    
    /**
     * Mengatur judul form.
     *
     * @param judul Teks judul yang akan ditampilkan pada form.
     */
    void setJudul(String judul);
    
    /**
     * Mencetak log judul form yang telah diatur.
     * Ini adalah metode default yang bisa langsung digunakan
     * oleh class yang mengimplementasikan interface ini.
     *
     * @param judul Teks judul yang akan dicetak ke konsol.
     */
    default void logJudul(String judul) {
        System.out.println("Judul form di-set ke: " + judul);
    }
}
