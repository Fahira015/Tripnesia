import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * Kelas FrameStatusPemesanan adalah GUI untuk menampilkan status pemesanan tiket wisata.
 * Frame ini menampilkan daftar pemesanan user dengan status yang berbeda-beda seperti
 * disetujui, ditolak, atau masih dalam proses.
 * 
 * Fitur utama:
 * - Menampilkan daftar pemesanan dengan gambar destinasi
 * - Menampilkan informasi tiket (nama destinasi, PAX, harga)
 * - Tombol status yang berbeda warna sesuai kondisi
 * - Tombol unduh tiket untuk pemesanan yang disetujui
 * - Tombol pesan lagi untuk pemesanan yang ditolak
 * 
 * @author tim F.N.M Trip
 */
public class FrameStatusPemesanan extends javax.swing.JFrame {
    
    /**
     * Panel kontainer untuk menampung komponen-komponen pemesanan
     */
    private JPanel panelKontainer;

    /**
     * Konstruktor untuk membuat instance FrameStatusPemesanan baru.
     * Menginisialisasi komponen GUI dan memuat data pemesanan user.
     */
    public FrameStatusPemesanan() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        loadPemesananUser(); // Memuat data pemesanan dari database
    }
    
    /**
     * Metode untuk memuat dan menampilkan data pemesanan user dari database.
     * Membuat card dinamis untuk setiap pemesanan dengan informasi lengkap
     * termasuk gambar, nama destinasi, PAX, harga, dan tombol aksi.
     */
    private void loadPemesananUser() {
        // Inisialisasi panel utama dengan layout vertikal
        jPanel1 = new JPanel();
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));

        
        try (Connection conn = Koneksi.getConnection()) {
        // Query SQL untuk mengambil data pemesanan dengan join ke tabel terkait
        String sql = "SELECT p.*, d.nama AS nama_destinasi, d.gambar, " +
                     "       COALESCE(b.namalengkap, u.nama) AS namalengkap " +
                     "FROM pemesanan p " +
                     "JOIN destinasi d ON p.id_destinasi = d.id_destinasi " +
                     "LEFT JOIN pembayaran b ON p.id_pemesanan = b.id_pemesanan " +
                     "JOIN users u ON p.id_user = u.id_user";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Loop untuk setiap hasil pemesanan
            while (rs.next()) {
                
                // Membuat card untuk setiap pemesanan
                JPanel card = new JPanel(null);
                card.setPreferredSize(new Dimension(540, 120));
                card.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                // Komponen gambar destinasi
                JLabel labelGambar = new JLabel();
                labelGambar.setBounds(10, 10, 100, 100);
                ImageIcon icon = new ImageIcon("images/" + rs.getString("gambar"));
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                labelGambar.setIcon(new ImageIcon(img));
                card.add(labelGambar);

                // Judul Tiket
                JLabel labelTiket = new JLabel("Tiket " + rs.getString("nama_destinasi"));
                labelTiket.setFont(new Font("Segoe UI", Font.BOLD, 16));
                labelTiket.setBounds(120, 10, 300, 20);
                card.add(labelTiket);

                // Informasi Pax
                int pax = rs.getInt("paxPemesanan");
                JLabel labelPAX = new JLabel("PAX:         " + pax);
                labelPAX.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                labelPAX.setBounds(120, 35, 100, 20);
                card.add(labelPAX);
                
                // Harga
                JLabel labelHarga = new JLabel("Rp " + rs.getInt("hargaPemesanan") + ",00");
                labelHarga.setForeground(new Color(255, 102, 0));
                labelHarga.setFont(new Font("Segoe UI", Font.BOLD, 14));
                labelHarga.setBounds(120, 55, 300, 20);
                card.add(labelHarga);

                // Tombol Status
                String status = rs.getString("status");
                JButton btnStatus = new JButton(status);
                btnStatus.setBounds(400, 75, 110, 25);
                btnStatus.setEnabled(false);

                // Mengatur warna tombol berdasarkan status
                switch (status) {
                    case "Disetujui":
                        btnStatus.setBackground(new Color(0, 153, 0)); // Hijau
                        btnStatus.setForeground(Color.WHITE);
                        break;
                    case "Ditolak":
                        btnStatus.setBackground(Color.RED); // Merah
                        btnStatus.setForeground(Color.WHITE);
                        break;
                    default:
                        btnStatus.setBackground(Color.ORANGE); // Orange
                        btnStatus.setForeground(Color.BLACK);
                        break;
                }
                card.add(btnStatus);

                //tombol aksi berdasarkan status
                int idPemesanan = rs.getInt("id_pemesanan");

                if (status.equals("Disetujui")) {
                    // Jika disetujui, tampilkan tombol unduh tiket
                    final String namaDestinasi = rs.getString("nama_destinasi");
                    final String namaPemesan = rs.getString("namalengkap");
                    final String tanggal = rs.getString("tanggalPemesanan");
                    final int jumlahTiket = rs.getInt("paxPemesanan");

                    JButton btnUnduh = new JButton("Unduh Tiket");
                    btnUnduh.setBounds(400, 40, 120, 25);
                    btnUnduh.addActionListener(e -> {
                        // Membuka frame tiket dengan data pemesanan
                        new FrameTiket(namaDestinasi, namaPemesan, tanggal, jumlahTiket).setVisible(true);
                        dispose();
                    });
                    card.add(btnUnduh);
                } else if (status.equals("Ditolak")) {                    
                    // Jika ditolak, tampilkan tombol pesan lagi
                    JButton btnPesanLagi = new JButton("Pesan Lagi");
                    btnPesanLagi.setBounds(400, 40, 120, 25);
                    btnPesanLagi.setBackground(new Color(0, 51, 102));
                    btnPesanLagi.setForeground(Color.WHITE);
                    btnPesanLagi.addActionListener(e -> {
                        try (Connection conn2 = Koneksi.getConnection()) {
                            // Menghapus data pemesanan yang ditolak
                            PreparedStatement psDel = conn2.prepareStatement(
                                "DELETE FROM pemesanan WHERE id_pemesanan = ?");
                            psDel.setInt(1, idPemesanan);
                            psDel.executeUpdate();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage());
                        }

                        // Kembali ke halaman beranda untuk pemesanan baru
                        new FrameBeranda().setVisible(true);
                        dispose();
                    });
                    card.add(btnPesanLagi);
                }

                // Tambahkan ke panel utama
                jPanel1.add(Box.createVerticalStrut(10));
                jPanel1.add(card);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data pemesanan: " + e.getMessage());
        }

        jScrollPane1.setViewportView(jPanel1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        akunProfilKeluar = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tripnesia - Status Pemesanan");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        akunProfilKeluar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Akun", "Profil", "Keluar" }));
        akunProfilKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                akunProfilKeluarActionPerformed(evt);
            }
        });
        getContentPane().add(akunProfilKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 22, -1, 30));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/currency_exchange_27dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setMaximumSize(new java.awt.Dimension(27, 27));
        jButton5.setMinimumSize(new java.awt.Dimension(27, 27));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 23, 40, 30));

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Beranda");
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 73, 100, 30));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kategori", "Destinasi Candi & Pura", "Destinasi Danau & Kepulauan", "Destinasi Gunung", "Destinasi Kawah", "Destinasi Pantai", "Destinasi Museum", "Destinasi Wisata Kota & Budaya", "Destinasi Wisata Alam & Satwa", "Destinasi Wisata Buatan (Taman Hiburan)" }));
        jComboBox2.setMinimumSize(new java.awt.Dimension(72, 24));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 73, 167, 28));

        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 560, 230));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Tampilan Status Pemesanan User FINAL BANGET.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Event handler untuk dropdown menu akun (Profil/Keluar).
     * Navigasi ke frame yang sesuai berdasarkan pilihan user.
     * 
     * @param evt Event yang dipicu saat user memilih item dari dropdown
     */
    private void akunProfilKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_akunProfilKeluarActionPerformed
        // TODO add your handling code here:
        String pilihan = akunProfilKeluar.getSelectedItem().toString();

        if (pilihan.equals("Profil")) {
            new FrameProfilUser().setVisible(true);
            dispose();
        } else if (pilihan.equals("Keluar")) {
            new FormLogin().setVisible(true);
            dispose();
        }

        

    }//GEN-LAST:event_akunProfilKeluarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * Event handler untuk tombol navigasi ke Beranda.
     * Menutup frame saat ini dan membuka frame beranda.
     * 
     * @param evt Event yang dipicu saat tombol Beranda diklik
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new FrameBeranda().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * Event handler untuk dropdown kategori destinasi.
     * Navigasi ke frame kategori yang sesuai berdasarkan pilihan user.
     * 
     * @param evt Event yang dipicu saat user memilih kategori dari dropdown
     */
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        String kategoriDipilih = jComboBox2.getSelectedItem().toString();
        if(kategoriDipilih.equals("Destinasi Candi & Pura")){
            new FrameKategoriCandiPura().setVisible(true);
            dispose();
        }
        if(kategoriDipilih.equals("Destinasi Danau & Kepulauan")){
            new FrameKategoriDanauPulau().setVisible(true);
            dispose();
        }
        if(kategoriDipilih.equals("Destinasi Gunung")){
            new FrameKategoriGunung().setVisible(true);
            dispose();
        }
        if(kategoriDipilih.equals("Destinasi Kawah")){
            new FrameKategoriKawah().setVisible(true);
            dispose();
        }
        if(kategoriDipilih.equals("Destinasi Pantai")){
            new FrameKategoriPantai().setVisible(true);
            dispose();
        }
        if(kategoriDipilih.equals("Destinasi Museum")){
            new FrameKategoriMuseum().setVisible(true);
            dispose();
        }
        if(kategoriDipilih.equals("Destinasi Wisata Kota & Budaya")){
            new FrameKategoriWisataKotaBudaya().setVisible(true);
            dispose();
        }
        if(kategoriDipilih.equals("Destinasi Wisata Alam & Satwa")){
            new FrameKategoriWisataAlamSatwa().setVisible(true);
            dispose();
        }
        if(kategoriDipilih.equals("Destinasi Wisata Buatan (Taman Hiburan)")){
            new FrameKategoriWisataBuatan().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

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
            java.util.logging.Logger.getLogger(FrameStatusPemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameStatusPemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameStatusPemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameStatusPemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FrameStatusPemesanan().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> akunProfilKeluar;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
