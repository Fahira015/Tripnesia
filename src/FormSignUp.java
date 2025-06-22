import javax.swing.*;
import java.sql.*;


/**
 * Kelas FormSignUp merupakan form pendaftaran untuk aplikasi Tripnesia.
 * Form ini menyediakan interface untuk user mendaftar akun baru dengan
 * mengisi data nama, email, nomor ponsel, jenis kelamin, dan password.
 * 
 * @author tim F.N.M Trip
 */
public class FormSignUp extends javax.swing.JFrame {
    
    /**
     * Konstruktor untuk membuat instance FormSignUp.
     * Menginisialisasi komponen form, mengatur pengaturan window,
     * dan mengelompokkan radio button untuk jenis kelamin.
     */
    public FormSignUp() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        
        //Mengelompokkan radio button jenis kelamin agar hanya satu yang bisa dipilih
        ButtonGroup groupGender = new ButtonGroup();
        groupGender.add(radioBtnLakilakiSignUp);
        groupGender.add(radioBtnPerempuanSignUp);

        //Mengatur tampilan radio button agar transparan dan tidak ada border
        radioBtnLakilakiSignUp.setOpaque(false);
        radioBtnLakilakiSignUp.setContentAreaFilled(false);
        radioBtnLakilakiSignUp.setBorderPainted(false);

        radioBtnPerempuanSignUp.setOpaque(false);
        radioBtnPerempuanSignUp.setContentAreaFilled(false);
        radioBtnPerempuanSignUp.setBorderPainted(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        namaSignUp = new javax.swing.JLabel();
        textNamaSignUp = new javax.swing.JTextField();
        emailSignUp = new javax.swing.JLabel();
        textEmailSignUp = new javax.swing.JTextField();
        nomorPonselSignUp = new javax.swing.JLabel();
        textNomorPonselSignUp = new javax.swing.JTextField();
        jenisKelaminSignUp = new javax.swing.JLabel();
        radioBtnPerempuanSignUp = new javax.swing.JRadioButton();
        radioBtnLakilakiSignUp = new javax.swing.JRadioButton();
        passwordSignUp = new javax.swing.JLabel();
        textPasswordSignUp = new javax.swing.JPasswordField();
        konfirmasiPasswordSignUp = new javax.swing.JLabel();
        textKonfirmasiPasswordSignUp = new javax.swing.JPasswordField();
        btnSubmitSignUp = new javax.swing.JButton();
        textSudahPunyaAkunSignUp = new javax.swing.JLabel();
        btnLoginSignUp = new javax.swing.JButton();
        bgSignUp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tripnesia - Sign Up Page");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        namaSignUp.setText("Nama");
        getContentPane().add(namaSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        textNamaSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNamaSignUpActionPerformed(evt);
            }
        });
        getContentPane().add(textNamaSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 180, -1));

        emailSignUp.setText("Email");
        getContentPane().add(emailSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        textEmailSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textEmailSignUpActionPerformed(evt);
            }
        });
        getContentPane().add(textEmailSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 180, -1));

        nomorPonselSignUp.setText("Nomor Ponsel");
        getContentPane().add(nomorPonselSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        textNomorPonselSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNomorPonselSignUpActionPerformed(evt);
            }
        });
        getContentPane().add(textNomorPonselSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 180, -1));

        jenisKelaminSignUp.setText("Konfirmasi Kata Sandi");
        getContentPane().add(jenisKelaminSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        radioBtnPerempuanSignUp.setText("Perempuan");
        radioBtnPerempuanSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBtnPerempuanSignUpActionPerformed(evt);
            }
        });
        getContentPane().add(radioBtnPerempuanSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, -1, -1));

        radioBtnLakilakiSignUp.setText("Laki-laki");
        radioBtnLakilakiSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBtnLakilakiSignUpActionPerformed(evt);
            }
        });
        getContentPane().add(radioBtnLakilakiSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, -1, -1));

        passwordSignUp.setText("Kata Sandi");
        getContentPane().add(passwordSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        textPasswordSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPasswordSignUpActionPerformed(evt);
            }
        });
        getContentPane().add(textPasswordSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 180, -1));

        konfirmasiPasswordSignUp.setText("Jenis Kelamin");
        getContentPane().add(konfirmasiPasswordSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));
        getContentPane().add(textKonfirmasiPasswordSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 180, -1));

        btnSubmitSignUp.setText("Daftar");
        btnSubmitSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitSignUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnSubmitSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 310, -1));

        textSudahPunyaAkunSignUp.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        textSudahPunyaAkunSignUp.setText("Sudah punya akun?");
        getContentPane().add(textSudahPunyaAkunSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, -1, -1));

        btnLoginSignUp.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        btnLoginSignUp.setForeground(new java.awt.Color(51, 102, 255));
        btnLoginSignUp.setText("Masuk");
        btnLoginSignUp.setBorderPainted(false);
        btnLoginSignUp.setContentAreaFilled(false);
        btnLoginSignUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLoginSignUp.setFocusPainted(false);
        btnLoginSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginSignUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnLoginSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 356, -1, -1));

        bgSignUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Tampilan Sign Up v2.png"))); // NOI18N
        bgSignUp.setText("jLabel1");
        bgSignUp.setMinimumSize(new java.awt.Dimension(600, 400));
        getContentPane().add(bgSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textEmailSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textEmailSignUpActionPerformed
        
    }//GEN-LAST:event_textEmailSignUpActionPerformed

    private void textPasswordSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPasswordSignUpActionPerformed
        
    }//GEN-LAST:event_textPasswordSignUpActionPerformed

    private void textNomorPonselSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNomorPonselSignUpActionPerformed
        
    }//GEN-LAST:event_textNomorPonselSignUpActionPerformed

    private void textNamaSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNamaSignUpActionPerformed
        
    }//GEN-LAST:event_textNamaSignUpActionPerformed

    /**
     * Event handler untuk tombol submit pendaftaran.
     * Melakukan validasi input, menyimpan data ke database MySQL,
     * dan menampilkan pesan sukses atau error.
     * 
     * @param evt ActionEvent yang dipicu saat tombol "Daftar" diklik
     */
    private void btnSubmitSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitSignUpActionPerformed
        //Mengambil data dari form input
        String namaLengkap = textNamaSignUp.getText();
        String email = textEmailSignUp.getText();
        String noPonsel = textNomorPonselSignUp.getText();
        String password = new String(textPasswordSignUp.getPassword());
        String confirmPassword = new String(textKonfirmasiPasswordSignUp.getPassword());
        
        //Validasi input untuk memastikan semua field terisi dan jenis kelamin dipilih
        if (namaLengkap.isEmpty() || email.isEmpty() || noPonsel.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()
                || (!radioBtnLakilakiSignUp.isSelected() && !radioBtnPerempuanSignUp.isSelected())) {

            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Validasi konfirmasi password
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Password dan konfirmasi tidak cocok!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Menentukan jenis kelamin berdasarkan radio button yang dipilih
        String jenisKelamin = radioBtnLakilakiSignUp.isSelected() ? "Laki-laki" : "Perempuan";

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tripnesia", "root", "");
            
            //Query SQL untuk insert data user baru
            String sql = "INSERT INTO users (nama, email, nomor, jeniskelamin, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaLengkap);
            stmt.setString(2, email);
            stmt.setString(3, noPonsel);
            stmt.setString(4, jenisKelamin);
            stmt.setString(5, password);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan login.");
                new FormLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registrasi gagal, coba lagi.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSubmitSignUpActionPerformed

    /**
     * Event handler untuk tombol "Masuk" yang mengarahkan ke halaman login.
     * Menutup form sign up dan membuka form login.
     * 
     * @param evt ActionEvent yang dipicu saat tombol "Masuk" diklik
     */
    private void btnLoginSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginSignUpActionPerformed
        new FormLogin().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLoginSignUpActionPerformed

    private void radioBtnLakilakiSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBtnLakilakiSignUpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioBtnLakilakiSignUpActionPerformed

    private void radioBtnPerempuanSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBtnPerempuanSignUpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioBtnPerempuanSignUpActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormSignUp().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bgSignUp;
    private javax.swing.JButton btnLoginSignUp;
    private javax.swing.JButton btnSubmitSignUp;
    private javax.swing.JLabel emailSignUp;
    private javax.swing.JLabel jenisKelaminSignUp;
    private javax.swing.JLabel konfirmasiPasswordSignUp;
    private javax.swing.JLabel namaSignUp;
    private javax.swing.JLabel nomorPonselSignUp;
    private javax.swing.JLabel passwordSignUp;
    private javax.swing.JRadioButton radioBtnLakilakiSignUp;
    private javax.swing.JRadioButton radioBtnPerempuanSignUp;
    private javax.swing.JTextField textEmailSignUp;
    private javax.swing.JPasswordField textKonfirmasiPasswordSignUp;
    private javax.swing.JTextField textNamaSignUp;
    private javax.swing.JTextField textNomorPonselSignUp;
    private javax.swing.JPasswordField textPasswordSignUp;
    private javax.swing.JLabel textSudahPunyaAkunSignUp;
    // End of variables declaration//GEN-END:variables
}
