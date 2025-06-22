import java.sql.*;
import javax.swing.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * Form dashboard admin untuk halaman beranda aplikasi Tripnesia.
 * Menampilkan ringkasan data seperti total pengguna, destinasi, pemesanan,
 * pendapatan, dan notifikasi pesanan yang perlu dikonfirmasi.
 * 
 * Fitur navigasi ke:
 * - Kelola Data
 * - Kelola Destinasi
 * - Laporan
 * - Logout
 * 
 * @author tim F.N.M Trip
 */
public class FormAdminBeranda extends javax.swing.JFrame {

    /**
     * Konstruktor untuk membuat form admin beranda baru.
     * Menginisialisasi komponen GUI dan menampilkan data statistik dashboard.
     */
    public FormAdminBeranda() {
        initComponents();
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout()); 
        setResizable(false);
        setLocationRelativeTo(null);
        
        // Menampilkan data statistik dashboard
        tampilkanTotalPengguna();
        tampilkanTotalDestinasi();
        tampilkanTotalPemesanan();
        tampilkanTotalPendapatan();
        tampilkanNotifikasiPesananTertunda();
    }


    /**
     * Menampilkan total jumlah pengguna yang terdaftar di sistem.
     * Data diambil dari tabel 'users' di database dan ditampilkan pada label.
     */
    private void tampilkanTotalPengguna() {
        try {
            Connection conn = Koneksi.getConnection();
            
            // Query untuk menghitung total pengguna
            String sql = "SELECT COUNT(*) AS total_pengguna FROM users";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            // Jika ada hasil query
            if (rs.next()) {
                int total = rs.getInt("total_pengguna");
                labelTotalPenggunaFormAdminBeranda.setText("<html><center>" + total + "<br>Pengguna</center></html>");
            }

            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil data pengguna: " + e.getMessage());
        }
    }
    
    /**
     * Menampilkan total jumlah destinasi wisata yang tersedia di sistem.
     * Data diambil dari tabel 'destinasi' di database dan ditampilkan pada label.
     */
    private void tampilkanTotalDestinasi() {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT COUNT(*) AS total_destinasi FROM destinasi";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total_destinasi");
                labelTotalDestinasiFormAdminBeranda.setText("<html><center>" + total + "<br>Destinasi</center></html>");
            }

            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil data destinasi: " + e.getMessage());
        }
    }
    
    /**
     * Menampilkan total jumlah pemesanan yang telah dibuat oleh pengguna.
     * Data diambil dari tabel 'pemesanan' di database dan ditampilkan pada label.
     */
    private void tampilkanTotalPemesanan() {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT COUNT(*) AS total_pemesanan FROM pemesanan";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total_pemesanan");
                labelTotalPemesananFormAdminBeranda.setText("<html><center>" + total + "<br>Pemesanan</center></html>");
            }

            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil data pemesanan: " + e.getMessage());
        }
    }
    
    /**
     * Menghitung dan menampilkan total pendapatan dari semua pemesanan.
     * Melakukan JOIN antara tabel 'pemesanan' dan 'destinasi' untuk mendapatkan harga.
     * Harga dalam format "Rp X.XXX" dikonversi menjadi angka untuk perhitungan.
     */
    private void tampilkanTotalPendapatan() {
        try {
            Connection conn = Koneksi.getConnection();
            
            // Query untuk menghitung total pendapatan dengan JOIN table
            
            String sql = "SELECT SUM(CAST(REPLACE(REPLACE(d.harga, 'Rp', ''), '.', '') AS UNSIGNED)) AS total_pendapatan " +
                         "FROM pemesanan p JOIN destinasi d ON p.id_destinasi = d.id_destinasi";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total_pendapatan");
                String formatRupiah = "Rp " + String.format("%,d", total).replace(',', '.');
                labelPendapatanFormAdminBeranda.setText("<html><center><b>" + formatRupiah + "</b><br>Pendapatan</center></html>");
            }

            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil data pendapatan: " + e.getMessage());
        }
    }
    
    /**
     * Menampilkan notifikasi tentang status pesanan yang perlu dikonfirmasi admin.
     * Menampilkan peringatan jika ada pesanan dengan status 'Tertunda',
     * atau pesan konfirmasi jika semua pesanan sudah dikonfirmasi.
     */
    private void tampilkanNotifikasiPesananTertunda() {
        try {
            Connection conn = Koneksi.getConnection();

            // Cek total pesanan yang ada
            String sqlTotal = "SELECT COUNT(*) AS total_pesanan FROM pemesanan";
            PreparedStatement pstTotal = conn.prepareStatement(sqlTotal);
            ResultSet rsTotal = pstTotal.executeQuery();

            int totalPesanan = 0;
            if (rsTotal.next()) {
                totalPesanan = rsTotal.getInt("total_pesanan");
            }

            rsTotal.close();
            pstTotal.close();

            // Jika belum ada pesanan sama sekali
            if (totalPesanan == 0) {
                labelNotifikasiPesananFormAdminBeranda.setText("<html><div style='padding-left:10px; color:gray;'>Belum ada pesanan masuk</div></html>");
            } else {
                // Hitung pesanan yang masih tertunda
                String sqlTertunda = "SELECT COUNT(*) AS total_tertunda FROM pemesanan WHERE status = 'Tertunda'";
                PreparedStatement pst = conn.prepareStatement(sqlTertunda);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    int jumlahTertunda = rs.getInt("total_tertunda");

                    if (jumlahTertunda > 0) {
                        labelNotifikasiPesananFormAdminBeranda.setText("<html><font color='red'>⚠ Ada " + jumlahTertunda + " pesanan yang belum dikonfirmasi</font></html>");
                    } else {
                        labelNotifikasiPesananFormAdminBeranda.setText("<html><font color='green'>✔ Semua pesanan telah dikonfirmasi</font></html>");
                    }
                }

                rs.close();
                pst.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil data notifikasi: " + e.getMessage());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnProfilFormAdminBeranda = new javax.swing.JButton();
        btnBerandaFormAdminBeranda = new javax.swing.JButton();
        btnKelolaDataFormAdminBeranda = new javax.swing.JButton();
        btnKelolaDestinasiFormAdminBeranda = new javax.swing.JButton();
        btnKeluarFormAdminBeranda = new javax.swing.JButton();
        btnLaporanFormAdminBeranda = new javax.swing.JButton();
        labelTotalPenggunaFormAdminBeranda = new javax.swing.JLabel();
        labelTotalDestinasiFormAdminBeranda = new javax.swing.JLabel();
        labelTotalPemesananFormAdminBeranda = new javax.swing.JLabel();
        labelPendapatanFormAdminBeranda = new javax.swing.JLabel();
        labelNotifikasiPesananFormAdminBeranda = new javax.swing.JLabel();
        bgFormAdminBeranda = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setTitle("Tripnesia - Dashboard Admin - Beranda");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnProfilFormAdminBeranda.setForeground(java.awt.Color.white);
        btnProfilFormAdminBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/profil icon.png"))); // NOI18N
        btnProfilFormAdminBeranda.setBorderPainted(false);
        btnProfilFormAdminBeranda.setContentAreaFilled(false);
        btnProfilFormAdminBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProfilFormAdminBeranda.setFocusPainted(false);
        btnProfilFormAdminBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfilFormAdminBerandaActionPerformed(evt);
            }
        });
        getContentPane().add(btnProfilFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, -1, 40));

        btnBerandaFormAdminBeranda.setForeground(java.awt.Color.white);
        btnBerandaFormAdminBeranda.setText("Beranda");
        btnBerandaFormAdminBeranda.setBorderPainted(false);
        btnBerandaFormAdminBeranda.setContentAreaFilled(false);
        btnBerandaFormAdminBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBerandaFormAdminBeranda.setFocusPainted(false);
        btnBerandaFormAdminBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBerandaFormAdminBerandaActionPerformed(evt);
            }
        });
        getContentPane().add(btnBerandaFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 47, -1, -1));

        btnKelolaDataFormAdminBeranda.setForeground(java.awt.Color.white);
        btnKelolaDataFormAdminBeranda.setText("Kelola Data");
        btnKelolaDataFormAdminBeranda.setBorderPainted(false);
        btnKelolaDataFormAdminBeranda.setContentAreaFilled(false);
        btnKelolaDataFormAdminBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKelolaDataFormAdminBeranda.setFocusPainted(false);
        btnKelolaDataFormAdminBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelolaDataFormAdminBerandaActionPerformed(evt);
            }
        });
        getContentPane().add(btnKelolaDataFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 80, -1, -1));

        btnKelolaDestinasiFormAdminBeranda.setForeground(java.awt.Color.white);
        btnKelolaDestinasiFormAdminBeranda.setText("Kelola Destinasi");
        btnKelolaDestinasiFormAdminBeranda.setBorderPainted(false);
        btnKelolaDestinasiFormAdminBeranda.setContentAreaFilled(false);
        btnKelolaDestinasiFormAdminBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKelolaDestinasiFormAdminBeranda.setFocusPainted(false);
        btnKelolaDestinasiFormAdminBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelolaDestinasiFormAdminBerandaActionPerformed(evt);
            }
        });
        getContentPane().add(btnKelolaDestinasiFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 112, -1, -1));

        btnKeluarFormAdminBeranda.setForeground(java.awt.Color.white);
        btnKeluarFormAdminBeranda.setText("Laporan");
        btnKeluarFormAdminBeranda.setBorderPainted(false);
        btnKeluarFormAdminBeranda.setContentAreaFilled(false);
        btnKeluarFormAdminBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKeluarFormAdminBeranda.setFocusPainted(false);
        btnKeluarFormAdminBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarFormAdminBerandaActionPerformed(evt);
            }
        });
        getContentPane().add(btnKeluarFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 146, -1, -1));

        btnLaporanFormAdminBeranda.setForeground(java.awt.Color.white);
        btnLaporanFormAdminBeranda.setText("Keluar");
        btnLaporanFormAdminBeranda.setBorderPainted(false);
        btnLaporanFormAdminBeranda.setContentAreaFilled(false);
        btnLaporanFormAdminBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLaporanFormAdminBeranda.setFocusPainted(false);
        btnLaporanFormAdminBeranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanFormAdminBerandaActionPerformed(evt);
            }
        });
        getContentPane().add(btnLaporanFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 360, -1, -1));

        labelTotalPenggunaFormAdminBeranda.setBackground(new java.awt.Color(255, 255, 255));
        labelTotalPenggunaFormAdminBeranda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTotalPenggunaFormAdminBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/akun icon.png"))); // NOI18N
        labelTotalPenggunaFormAdminBeranda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelTotalPenggunaFormAdminBeranda.setOpaque(true);
        labelTotalPenggunaFormAdminBeranda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(labelTotalPenggunaFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 120, 80));

        labelTotalDestinasiFormAdminBeranda.setBackground(new java.awt.Color(255, 255, 255));
        labelTotalDestinasiFormAdminBeranda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTotalDestinasiFormAdminBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/location icon.png"))); // NOI18N
        labelTotalDestinasiFormAdminBeranda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelTotalDestinasiFormAdminBeranda.setOpaque(true);
        labelTotalDestinasiFormAdminBeranda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(labelTotalDestinasiFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 120, 80));

        labelTotalPemesananFormAdminBeranda.setBackground(new java.awt.Color(255, 255, 255));
        labelTotalPemesananFormAdminBeranda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTotalPemesananFormAdminBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/pesan icon.png"))); // NOI18N
        labelTotalPemesananFormAdminBeranda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelTotalPemesananFormAdminBeranda.setOpaque(true);
        labelTotalPemesananFormAdminBeranda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(labelTotalPemesananFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 120, 80));

        labelPendapatanFormAdminBeranda.setBackground(new java.awt.Color(255, 255, 255));
        labelPendapatanFormAdminBeranda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPendapatanFormAdminBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/money icon.png"))); // NOI18N
        labelPendapatanFormAdminBeranda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelPendapatanFormAdminBeranda.setOpaque(true);
        labelPendapatanFormAdminBeranda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(labelPendapatanFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 120, 80));

        labelNotifikasiPesananFormAdminBeranda.setBackground(java.awt.Color.white);
        labelNotifikasiPesananFormAdminBeranda.setOpaque(true);
        getContentPane().add(labelNotifikasiPesananFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 260, 80));

        bgFormAdminBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Tampilan Dashboard Admin Dashboard.png"))); // NOI18N
        getContentPane().add(bgFormAdminBeranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Tampilan Dashboard Admin Dashboard.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBerandaFormAdminBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBerandaFormAdminBerandaActionPerformed
        new FormAdminBeranda().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBerandaFormAdminBerandaActionPerformed

    private void btnKelolaDataFormAdminBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelolaDataFormAdminBerandaActionPerformed
        new FormAdminKelolaData().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKelolaDataFormAdminBerandaActionPerformed

    private void btnKelolaDestinasiFormAdminBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelolaDestinasiFormAdminBerandaActionPerformed
        new FormAdminKelolaDestinasi().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKelolaDestinasiFormAdminBerandaActionPerformed

    private void btnLaporanFormAdminBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanFormAdminBerandaActionPerformed
        new FormLogin().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLaporanFormAdminBerandaActionPerformed

    private void btnKeluarFormAdminBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarFormAdminBerandaActionPerformed
        new FormAdminLaporan().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKeluarFormAdminBerandaActionPerformed

    private void btnProfilFormAdminBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfilFormAdminBerandaActionPerformed
        new FormAdminAkun().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnProfilFormAdminBerandaActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormAdminBeranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAdminBeranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAdminBeranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAdminBeranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FormAdminBeranda().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bgFormAdminBeranda;
    private javax.swing.JButton btnBerandaFormAdminBeranda;
    private javax.swing.JButton btnKelolaDataFormAdminBeranda;
    private javax.swing.JButton btnKelolaDestinasiFormAdminBeranda;
    private javax.swing.JButton btnKeluarFormAdminBeranda;
    private javax.swing.JButton btnLaporanFormAdminBeranda;
    private javax.swing.JButton btnProfilFormAdminBeranda;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelNotifikasiPesananFormAdminBeranda;
    private javax.swing.JLabel labelPendapatanFormAdminBeranda;
    private javax.swing.JLabel labelTotalDestinasiFormAdminBeranda;
    private javax.swing.JLabel labelTotalPemesananFormAdminBeranda;
    private javax.swing.JLabel labelTotalPenggunaFormAdminBeranda;
    // End of variables declaration//GEN-END:variables
}
