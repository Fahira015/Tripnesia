import javax.swing.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * Kelas dasar untuk form admin.
 * Merupakan turunan dari JFrame dan menyediakan pengaturan dasar
 * seperti posisi form, ukuran yang tidak bisa diubah, serta pengaturan judul form.
 * 
 * @author tim F.N.M Trip
 */

public class FormBaseAdmin extends javax.swing.JFrame {
    
    /**
     * Konstruktor untuk FormBaseAdmin.
     * Mengatur agar jendela tidak dapat diubah ukurannya dan muncul di tengah layar.
     */
    public FormBaseAdmin() {
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * Mengatur judul jendela form.
     *
     * @param judul     Teks yang akan digunakan sebagai judul jendela.
     * @param uppercase Jika true, judul akan diubah menjadi huruf kapital semua.
     */
    public void setJudul(String judul, boolean uppercase) {
        
        if (uppercase) {
            // Jika parameter uppercase bernilai true, ubah judul menjadi huruf kapital
            setTitle(judul.toUpperCase());
        } else {
            // Jika tidak, gunakan judul sesuai input asli (tanpa diubah)
            setTitle(judul);
        }
    }
}
