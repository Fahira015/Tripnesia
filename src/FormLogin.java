import java.sql.*;
import javax.swing.*;

/**
 * Kelas FormLogin untuk menampilkan halaman login aplikasi Tripnesia.
 * Form ini memungkinkan user untuk masuk ke aplikasi dengan email dan password,
 * atau sebagai admin dengan kredensial khusus.
 * 
 * @author tim F.N.M Trip
 */
public class FormLogin extends javax.swing.JFrame {     
    
    /**
     * Constructor untuk membuat instance FormLogin baru.
     * Menginisialisasi komponen GUI dan mengatur properti window.
     */
    public FormLogin() {
        initComponents();
        setResizable(false); //Agar ukuran window tidak berubah
        setLocationRelativeTo(null); //Agar window ditengah layar
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gmailLogin = new javax.swing.JLabel();
        textGmailLogin = new javax.swing.JTextField();
        passwordLogin = new javax.swing.JLabel();
        textPasswordLogin = new javax.swing.JPasswordField();
        btnSubmitLogin = new javax.swing.JButton();
        textTidakPunyaAkunLogin = new javax.swing.JLabel();
        btnSignUpLogin = new javax.swing.JButton();
        bgLogin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tripnesia - Login Page");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        gmailLogin.setText("Gmail");
        getContentPane().add(gmailLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 250, -1, -1));

        textGmailLogin.setForeground(new java.awt.Color(102, 102, 102));
        textGmailLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textGmailLoginActionPerformed(evt);
            }
        });
        getContentPane().add(textGmailLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 180, -1));

        passwordLogin.setText("Kata Kunci");
        getContentPane().add(passwordLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 290, -1, -1));
        getContentPane().add(textPasswordLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 180, -1));

        btnSubmitLogin.setText("Masuk");
        btnSubmitLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnSubmitLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 270, -1));

        textTidakPunyaAkunLogin.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        textTidakPunyaAkunLogin.setText("Belum ada akun?");
        getContentPane().add(textTidakPunyaAkunLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 364, -1, 10));

        btnSignUpLogin.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        btnSignUpLogin.setForeground(new java.awt.Color(51, 102, 255));
        btnSignUpLogin.setText("Daftar");
        btnSignUpLogin.setBorderPainted(false);
        btnSignUpLogin.setContentAreaFilled(false);
        btnSignUpLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignUpLogin.setFocusPainted(false);
        btnSignUpLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnSignUpLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 360, -1, -1));

        bgLogin.setBackground(new java.awt.Color(255, 255, 255));
        bgLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Tampilan Login v3.png"))); // NOI18N
        getContentPane().add(bgLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textGmailLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textGmailLoginActionPerformed
        
    }//GEN-LAST:event_textGmailLoginActionPerformed

    
    /**
     * Event handler untuk tombol Submit/Masuk.
     * Melakukan validasi input dan proses autentikasi user.
     * Mendukung login sebagai admin atau user biasa melalui database.
     * 
     * @param evt Event yang dipicu saat tombol Masuk diklik
     */
    private void btnSubmitLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitLoginActionPerformed
        // Ambil input dari user
        String gmail = textGmailLogin.getText();
        String password = new String(textPasswordLogin.getPassword());

        // Validasi input tidak boleh kosong
        if (gmail.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Gmail dan Password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Cek login sebagai admin
        if (gmail.equals("admin") && password.equals("admin123")) {
            Session.idUser = -1; // ID khusus untuk admin
            JOptionPane.showMessageDialog(this, "Login Admin Berhasil!");
            new FormAdminBeranda().setVisible(true);
            dispose();
            return;
        }

        // Proses login user biasa melalui database
        try (
            Connection conn = Koneksi.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            
        ) {
            stmt.setString(1, gmail);
            stmt.setString(2, password);

            // Eksekusi query dan proses hasil
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Login berhasil, ambil data user
                    String nama = rs.getString("nama");
                    Session.idUser = rs.getInt("id_user"); // Simpan ID user ke session
                    Session.id_user = rs.getInt("id_user");
                    JOptionPane.showMessageDialog(this, "Selamat datang, " + nama);
                    new FrameBeranda().setVisible(true); // Buka halaman beranda
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gmail atau Password salah!");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSubmitLoginActionPerformed

    /**
     * Event handler untuk tombol Daftar.
     * Membuka form SignUp dan menutup form login.
     * 
     * @param evt Event yang dipicu saat tombol Daftar diklik
     */
    private void btnSignUpLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpLoginActionPerformed
        new FormSignUp().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnSignUpLoginActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormLogin().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bgLogin;
    private javax.swing.JButton btnSignUpLogin;
    private javax.swing.JButton btnSubmitLogin;
    private javax.swing.JLabel gmailLogin;
    private javax.swing.JLabel passwordLogin;
    private javax.swing.JTextField textGmailLogin;
    private javax.swing.JPasswordField textPasswordLogin;
    private javax.swing.JLabel textTidakPunyaAkunLogin;
    // End of variables declaration//GEN-END:variables
}
