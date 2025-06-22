
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * Frame untuk menampilkan kategori destinasi Pantai
 * Kelas ini mengextend Jframe dan menampilkan katalog destinasi wisata
 * berupa Gunung dalam bentuk grid dengan gambar dan nama destinasi.
 * 
 * fitur Utama:
 * -Menampilkan daftar destinasi pantai dari database
 * -Layout grid dengan 3 kolom per baris
 * -Scroll panel untuk navigasi vertikal
 * -click handler untuk membuka detail destinasi
 * -navigasi ke halaman homepage dan kategori selanjutnya
 * 
 * @author tim F.N.M trip
 */
public class FrameKategoriPantai extends javax.swing.JFrame {

    /**
     * Konstruktor untuk membuat frame kategori pantai
     * Menginisialisasi komponen GUI, mengatur window,
     * dan memuat data katalog destinasi dari database.
     */
    public FrameKategoriPantai() {
        initComponents();
        setResizable(false); //mengatur window agar tidak bisa diubah
        setLocationRelativeTo(null); //mengatur window ditengah layar
        
        //scrollpane agar selalu vertikal, dan menyembunyikan scroll horizontal
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //flowlayout untuk susunan grid
        panelPantai.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        loadKatalog("Destinasi Pantai");
        
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(panelPantai);
        jScrollPane1.setViewportView(wrapperPanel);
    }

    /**
     * memuat data katalog destinasi berdasarkan kategori tertentu dari database
     * method ini mengambil data dari tabel 'destinasi'
     * membuat panel untuk setiap item, dan menampilkan dalam layout grid 3 kolom
     * 
     * @param kategori nama kategori destinasi
     */
    private void loadKatalog(String kategori) {
        try {
            //membuka koneksi ke database
            Connection conn = Koneksi.getConnection();
            
            //query untuk mengambil data destinasi berdasarkan kategori
            String sql = "SELECT * FROM destinasi WHERE kategori = ?";
            PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, kategori);
            ResultSet rs = ps.executeQuery();

            //untuk menghitung jumlah total item untuk layout
            rs.last();
            int jumlahItem = rs.getRow();
            rs.beforeFirst();

            //reset panel dan atur layout baru
            panelPantai.removeAll();
            panelPantai.setLayout(new BoxLayout(panelPantai, BoxLayout.Y_AXIS));

            int kolom = 3; //jumlah kolom perbaris
            int lebarItem = 160; //lebar setiap item destinasi
            int tinggiItem = 160; //tinggi setiap item destinasi
            int spasi = 20; // jarak antar item

            int i = 0; //untuk tracking posisi dalam grid
            
            //panel untuk sebaris 3 kolom
            JPanel barisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, spasi, spasi));
            barisPanel.setOpaque(false);

            //loop untuk setiap destinasi dari database
            while (rs.next()) {
                //untuk ambil data dari resultset
                int id = rs.getInt("id_Destinasi");
                String nama = rs.getString("nama");
                String gambar = rs.getString("gambar");
                String path = "images/" + gambar; //path ke file gambar

                //buat panel untuk satu item destinasi
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
                itemPanel.setBackground(Color.WHITE);
                
                //border dengan outline abu-abu dan padding dalam
                itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                itemPanel.setPreferredSize(new Dimension(lebarItem, tinggiItem));

                //buat dan atur gambar destinasi
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(140, 73, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(img));
                imgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                imgLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

                //buat label nama destinasi dengan HTML untuk word wrapping
                JLabel lblNama = new JLabel("<html><div style='width: 140px;'><b>" + nama + "</b></div></html>");
                lblNama.setAlignmentX(Component.LEFT_ALIGNMENT);
                lblNama.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

                //tambah komponen ke panel item
                itemPanel.add(imgLabel);
                itemPanel.add(lblNama);
                
                //simpan referensi final untuk use dalam anonymous class
                int finalId = id;
                String finalNama = nama;
                
                itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        //buka frame pembelian tiket dengan ID destinasi
                        FramePembelianTiket frame = new FramePembelianTiket(finalId);
                        frame.setVisible(true);
                        dispose(); //menutup
                    }
                });

                //menambahkan itempanel ke barispanel
                barisPanel.add(itemPanel);
                i++;

                //jika sudah 3 item perbaris, membuat baris baru
                if (i % kolom == 0) {
                    panelPantai.add(barisPanel);
                    //panel baris baru
                    barisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, spasi, spasi));
                    barisPanel.setOpaque(false);
                }
            }

            //menambahkan baris terakhir jika masih ada item yang belum ditambahkan
            if (i % kolom != 0) {
                panelPantai.add(barisPanel);
            }

            //merefresh tampilan panel
            panelPantai.revalidate();
            panelPantai.repaint();

            //menutup koneksi database
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            //menampilkan pesan error jika gagal memuat data
            JOptionPane.showMessageDialog(this, "Gagal tampilkan katalog: " + e.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        panelPantai = new javax.swing.JPanel();
        btnNext4 = new javax.swing.JButton();
        btnBack4 = new javax.swing.JButton();
        btnHomepg4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tripnesia - Kategori Pantai");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPantai.setLayout(new javax.swing.BoxLayout(panelPantai, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane1.setViewportView(panelPantai);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 580, 250));

        btnNext4.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        btnNext4.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnNext4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/kanan icon.png"))); // NOI18N
        btnNext4.setBorderPainted(false);
        btnNext4.setContentAreaFilled(false);
        btnNext4.setFocusable(false);
        btnNext4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNext4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext4ActionPerformed(evt);
            }
        });
        getContentPane().add(btnNext4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 40, 20));

        btnBack4.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        btnBack4.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnBack4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/kiri icon.png"))); // NOI18N
        btnBack4.setBorderPainted(false);
        btnBack4.setContentAreaFilled(false);
        btnBack4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBack4.setFocusable(false);
        btnBack4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBack4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack4ActionPerformed(evt);
            }
        });
        getContentPane().add(btnBack4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 40, 20));

        btnHomepg4.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        btnHomepg4.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnHomepg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/home.png"))); // NOI18N
        btnHomepg4.setBorderPainted(false);
        btnHomepg4.setContentAreaFilled(false);
        btnHomepg4.setFocusable(false);
        btnHomepg4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomepg4ActionPerformed(evt);
            }
        });
        getContentPane().add(btnHomepg4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 40, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Bg Kategori Pantai.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setPreferredSize(new java.awt.Dimension(600, 400));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Event handler untuk tombol next
     * untuk berpindah ke frame kategori museum dan menutup frame saat ini
     * 
     * @param evt event saat tombol diklik
     */
    private void btnNext4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext4ActionPerformed
        // TODO add your handling code here:
        new FrameKategoriMuseum().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNext4ActionPerformed

    /**
     * Event handler untuk tombol kembali
     * untuk kembali ke halaman sebelumnya dan menutup frame saat ini
     * 
     * @param evt 
     */
    private void btnBack4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack4ActionPerformed
        // TODO add your handling code here:
        new FrameKategoriKawah().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBack4ActionPerformed

    /**
     * Event handler untuk tombol home
     * untuk kembali ke halaman beranda dan menutup frame saat ini
     * 
     * @param evt
     */
    private void btnHomepg4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomepg4ActionPerformed
        // TODO add your handling code here:
        new FrameBeranda().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnHomepg4ActionPerformed

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
            java.util.logging.Logger.getLogger(FrameKategoriPantai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameKategoriPantai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameKategoriPantai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameKategoriPantai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameKategoriPantai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack4;
    private javax.swing.JButton btnHomepg4;
    private javax.swing.JButton btnNext4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPantai;
    // End of variables declaration//GEN-END:variables
}
