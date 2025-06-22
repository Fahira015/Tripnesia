
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
 * Frame untuk menampilkan kategori destinasi wisata Candi & Pura
 * Kelas ini mengextend JFrame dan menampilkan katalog destinasi wisata
 * yaitu candi dan pura dalam bentuk grid dengan gambar dan nama destinasi.
 * 
 * 
 * Fitur Utama:
 * -Menampilkan daftar destinasi candi & pura dari database
 * -Layout grid dengan 3 kolom per baris
 * -Scroll panel untuk navigasi vertikal
 * -click handler untuk membuka detail destinasi
 * -navigasi ke halaman homepage dan kategori selanjutnya
 * 
 * 
 * @author tim F.N.M Trip
 */
public class FrameKategoriCandiPura extends javax.swing.JFrame {

    /**
     * Konstruktor untuk membuat frame kategori Candi & Pura
     * Menginisialisasi komponen GUI, mengatur window,
     * dan memuat data katalog destinasi dari database.
     */
    public FrameKategoriCandiPura() {
        initComponents();
        setResizable(false); //mengatur window agar tidak bisa diubah
        setLocationRelativeTo(null); //mengatur window ditengah layar
        
        //scrollpane agar selalu vertikal, dan menyembunyikan scroll horizontal
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //flowlayout untuk susunan grid
        panelCandiPura.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        loadKatalog("Destinasi Candi & Pura");
        
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(panelCandiPura);
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
            panelCandiPura.removeAll();
            panelCandiPura.setLayout(new BoxLayout(panelCandiPura, BoxLayout.Y_AXIS));

            int kolom = 3; //jumlah kolom perbaris
            int lebarItem = 160; //lebar setiap item destinasi
            int tinggiItem = 160; //tinggi setiap item destinasi
            int spasi = 20;  // jarak antar item

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

                //menambahkan mouse click listener untuk ke detail
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
                    panelCandiPura.add(barisPanel);
                    //panel baris baru
                    barisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, spasi, spasi));
                    barisPanel.setOpaque(false);
                }
            }

            //menambahkan baris terakhir jikma masih ada item yang belum ditambahkan
            if (i % kolom != 0) {
                panelCandiPura.add(barisPanel);
            }
 
            //merefresh tampilan panel
            panelCandiPura.revalidate();
            panelCandiPura.repaint();

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
        panelCandiPura = new javax.swing.JPanel();
        btnNext = new javax.swing.JButton();
        btnHomepg = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tripnesia - Kategori Candi & Pura");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCandiPura.setLayout(new javax.swing.BoxLayout(panelCandiPura, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane1.setViewportView(panelCandiPura);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 580, 250));

        btnNext.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        btnNext.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/kanan icon.png"))); // NOI18N
        btnNext.setBorderPainted(false);
        btnNext.setContentAreaFilled(false);
        btnNext.setFocusable(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        getContentPane().add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 40, 20));

        btnHomepg.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        btnHomepg.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnHomepg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/home.png"))); // NOI18N
        btnHomepg.setBorderPainted(false);
        btnHomepg.setContentAreaFilled(false);
        btnHomepg.setFocusable(false);
        btnHomepg.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnHomepg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomepgActionPerformed(evt);
            }
        });
        getContentPane().add(btnHomepg, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 40, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Bg Kategori Candi & Pura.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setPreferredSize(new java.awt.Dimension(600, 400));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Event handler untuk tombol next
     * untuk berpindah ke frame kategori danau & pulau dan menutuo frame saat ini
     * 
     * @param evt event saat tombol diklik
     */
    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        new FrameKategoriDanauPulau().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNextActionPerformed

    
    /**
     * Event handler untuk tombol home
     * untuk kembali ke halaman beranda dan menutup frame saat ini
     * 
     * @param evt
     */
    private void btnHomepgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomepgActionPerformed
        // TODO add your handling code here:
        new FrameBeranda().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnHomepgActionPerformed

    /**
     * method main untuk menjalankan aplikasi
     * 
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
            java.util.logging.Logger.getLogger(FrameKategoriCandiPura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameKategoriCandiPura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameKategoriCandiPura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameKategoriCandiPura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameKategoriCandiPura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHomepg;
    private javax.swing.JButton btnNext;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelCandiPura;
    // End of variables declaration//GEN-END:variables
}
