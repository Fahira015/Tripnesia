
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * Form untuk mengelola data destinasi wisata dalam aplikasi Tripnesia.
 * Form ini menyediakan fitur CRUD (Create, Read, Update, Delete) untuk 
 * mengelola destinasi wisata yang tersedia dalam sistem.
 * 
 * Fitur yang tersedia:
 * - Menampilkan daftar destinasi dalam tabel
 * - Menambah destinasi baru
 * - Mengubah data destinasi yang sudah ada
 * - Menghapus destinasi
 * - Navigasi ke form admin lainnya
 * 
 * @author tim F.N.M Trip
 */
public class FormAdminKelolaDestinasi extends javax.swing.JFrame {

    /**
     * Konstruktor untuk membuat form baru FormAdminKelolaDestinasi.
     * Inisialisasi komponen GUI dan mengatur properti dasar form.
     */

    public FormAdminKelolaDestinasi() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        
        // Memuat data destinasi ke dalam tabel
        loadTableFormAdminDestinasi();
        tampilDataFormAdminDestinasi();
        
        tampilTableFormAdminDestinasi(); // Mengatur event listener untuk tabel
        
        // Mengatur tinggi baris tabel dan font
        tableKelolaDestinasiFormAdminKelolaDestinasi.setRowHeight(28);
        tableKelolaDestinasiFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));

        // Membuat renderer untuk memusatkan teks di dalam sel tabel
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        centerRenderer.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Menerapkan renderer ke semua kolom tabel
        for (int i = 0; i < tableKelolaDestinasiFormAdminKelolaDestinasi.getColumnCount(); i++) {
            tableKelolaDestinasiFormAdminKelolaDestinasi.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }        
        
        textFieldNamaFormAdminKelolaDestinasi.setText("");
        textFieldAlamatFormAdminKelolaDestinasi.setText("");
        textFieldDeskripsiFormAdminKelolaDestinasi.setText("");
        textFieldJamOperasionalFormAdminKelolaDestinasi.setText("");
        textFieldHargaFormAdminKelolaDestinasi.setText("");
        comboBoxKategoriFormAdminKelolaDestinasi.setSelectedIndex(0);
    }
    
    /**
     * Mengatur event listener untuk tabel destinasi.
     * Ketika baris tabel diklik, data dari baris tersebut akan ditampilkan
     * di field-field input untuk memudahkan proses edit.
     */
    private void tampilTableFormAdminDestinasi() {
        tableKelolaDestinasiFormAdminKelolaDestinasi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Mendapatkan baris yang dipilih
                int rowFormAdminDestinasi = tableKelolaDestinasiFormAdminKelolaDestinasi.getSelectedRow();
                
                // Jika ada baris yang dipilih, tampilkan data ke field input
                if (rowFormAdminDestinasi != -1) {
                    textFieldNamaFormAdminKelolaDestinasi.setText(tableKelolaDestinasiFormAdminKelolaDestinasi.getValueAt(rowFormAdminDestinasi, 1).toString());
                    textFieldAlamatFormAdminKelolaDestinasi.setText(tableKelolaDestinasiFormAdminKelolaDestinasi.getValueAt(rowFormAdminDestinasi, 2).toString());
                    textFieldDeskripsiFormAdminKelolaDestinasi.setText(tableKelolaDestinasiFormAdminKelolaDestinasi.getValueAt(rowFormAdminDestinasi, 3).toString());
                    textFieldHargaFormAdminKelolaDestinasi.setText(tableKelolaDestinasiFormAdminKelolaDestinasi.getValueAt(rowFormAdminDestinasi, 4).toString());
                    textFieldJamOperasionalFormAdminKelolaDestinasi.setText(tableKelolaDestinasiFormAdminKelolaDestinasi.getValueAt(rowFormAdminDestinasi, 5).toString());
                    comboBoxKategoriFormAdminKelolaDestinasi.setSelectedItem(tableKelolaDestinasiFormAdminKelolaDestinasi.getValueAt(rowFormAdminDestinasi, 6).toString());
                }
            }
        });
    }
    
    /**
     * Menampilkan data destinasi dari database ke dalam tabel.
     * Method ini membuat model tabel baru dan mengisi data dari database.
     */
    private void tampilDataFormAdminDestinasi() {
        DefaultTableModel modelFormAdminDestinasi = new DefaultTableModel();
        modelFormAdminDestinasi.addColumn("ID");
        modelFormAdminDestinasi.addColumn("Nama");
        modelFormAdminDestinasi.addColumn("Alamat");
        modelFormAdminDestinasi.addColumn("Deskripsi");
        modelFormAdminDestinasi.addColumn("Harga");
        modelFormAdminDestinasi.addColumn("Jam Buka");
        modelFormAdminDestinasi.addColumn("Kategori");


        try {
            Connection connFormAdminDestinasi = Koneksi.getConnection();
            Statement stmtFormAdminDestinasi = connFormAdminDestinasi.createStatement();
            
            // Menjalankan query untuk mengambil semua data destinasi
            ResultSet rsFormAdminDestinasi = stmtFormAdminDestinasi.executeQuery("SELECT * FROM destinasi");

            // Menambahkan setiap baris data ke model tabel
            while (rsFormAdminDestinasi.next()) {
                modelFormAdminDestinasi.addRow(new Object[]{
                    rsFormAdminDestinasi.getInt("id_destinasi"),
                    rsFormAdminDestinasi.getString("nama"),
                    rsFormAdminDestinasi.getString("alamat"),
                    rsFormAdminDestinasi.getString("deskripsi"),
                    rsFormAdminDestinasi.getString("harga"),
                    rsFormAdminDestinasi.getString("jambuka"),
                    rsFormAdminDestinasi.getString("kategori"),
                });
            }
            
            // Set model tabel dan sembunyikan kolom ID
            tableKelolaDestinasiFormAdminKelolaDestinasi.setModel(modelFormAdminDestinasi);
            tableKelolaDestinasiFormAdminKelolaDestinasi.getColumnModel().getColumn(0).setMinWidth(0);
            tableKelolaDestinasiFormAdminKelolaDestinasi.getColumnModel().getColumn(0).setMaxWidth(0);
            tableKelolaDestinasiFormAdminKelolaDestinasi.getColumnModel().getColumn(0).setWidth(0);
        } catch (SQLException e) {
            //menampilkan pesan error jika terjadi kesalahan database
            e.printStackTrace();
        }
    }
    
    /**
     * Memuat ulang data tabel dari database.
     * Method ini digunakan setelah operasi tambah, ubah, atau hapus data
     * untuk memperbarui tampilan tabel.
     */
    private void loadTableFormAdminDestinasi() {
        DefaultTableModel modelFormAdminDestinasi = (DefaultTableModel) tableKelolaDestinasiFormAdminKelolaDestinasi.getModel();
        modelFormAdminDestinasi.setRowCount(0); // Menghapus semua baris yang ada

        try {
            Connection connFormAdminDestinasi = Koneksi.getConnection();
            
            String sqlFormAdminDestinasi = "SELECT * FROM destinasi";
            PreparedStatement pstFormAdminDestinasi = connFormAdminDestinasi.prepareStatement(sqlFormAdminDestinasi);
            ResultSet rsFormAdminDestinasi = pstFormAdminDestinasi.executeQuery();

            // Menambahkan setiap baris data ke model tabel
            while (rsFormAdminDestinasi.next()) {
                Object[] rowFormAdminDestinasi = {
                    rsFormAdminDestinasi.getInt("id_destinasi"),
                    rsFormAdminDestinasi.getString("nama"),
                    rsFormAdminDestinasi.getString("alamat"),
                    rsFormAdminDestinasi.getString("deskripsi"),
                    rsFormAdminDestinasi.getString("harga"),
                    rsFormAdminDestinasi.getString("jambuka"),
                    rsFormAdminDestinasi.getString("kategori"),
                };
                modelFormAdminDestinasi.addRow(rowFormAdminDestinasi);
            }

            rsFormAdminDestinasi.close();
            pstFormAdminDestinasi.close();
            connFormAdminDestinasi.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
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

        btnProfilFormAdminKelolaDestinasi = new javax.swing.JButton();
        btnBerandaFormAdminKelolaDestinasi = new javax.swing.JButton();
        btnKelolaDataFormAdminKelolaDestinasi = new javax.swing.JButton();
        btnKelolaDestinasiFormAdminKelolaDestinasi = new javax.swing.JButton();
        btnLaporanFormAdminKelolaDestinasi = new javax.swing.JButton();
        btnKeluarFormAdminKelolaDestinasi = new javax.swing.JButton();
        labelNamaFormAdminKelolaDestinasi = new javax.swing.JLabel();
        textFieldNamaFormAdminKelolaDestinasi = new javax.swing.JTextField();
        labelDeskripsiFormAdminKelolaDestinasi = new javax.swing.JLabel();
        textFieldDeskripsiFormAdminKelolaDestinasi = new javax.swing.JTextField();
        labelAlamatFormAdminKelolaDestinasi = new javax.swing.JLabel();
        textFieldAlamatFormAdminKelolaDestinasi = new javax.swing.JTextField();
        labelJamOperasionalFormAdminKelolaDestinasi = new javax.swing.JLabel();
        textFieldJamOperasionalFormAdminKelolaDestinasi = new javax.swing.JTextField();
        labelHargaFormAdminKelolaDestinasi = new javax.swing.JLabel();
        textFieldHargaFormAdminKelolaDestinasi = new javax.swing.JTextField();
        labelKategoriFormAdminKelolaDestinasi = new javax.swing.JLabel();
        comboBoxKategoriFormAdminKelolaDestinasi = new javax.swing.JComboBox<>();
        btnTambahDestinasiFormAdminKelolaDestinasi = new javax.swing.JButton();
        btnUbahFormAdminKelolaDestinasi = new javax.swing.JButton();
        btnHapusFormAdminKelolaDestinasi = new javax.swing.JButton();
        scrollPaneTableKelolaDestinasiFormAdminKelolaDestinasi = new javax.swing.JScrollPane();
        tableKelolaDestinasiFormAdminKelolaDestinasi = new javax.swing.JTable();
        bgFormAdminKelolaDestinasi = new javax.swing.JLabel();

        setTitle("Tripnesia - Dashboard Admin - Kelola Destinasi");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnProfilFormAdminKelolaDestinasi.setForeground(java.awt.Color.white);
        btnProfilFormAdminKelolaDestinasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/profil icon.png"))); // NOI18N
        btnProfilFormAdminKelolaDestinasi.setBorderPainted(false);
        btnProfilFormAdminKelolaDestinasi.setContentAreaFilled(false);
        btnProfilFormAdminKelolaDestinasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProfilFormAdminKelolaDestinasi.setFocusPainted(false);
        btnProfilFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfilFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnProfilFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, -1, 40));

        btnBerandaFormAdminKelolaDestinasi.setForeground(java.awt.Color.white);
        btnBerandaFormAdminKelolaDestinasi.setText("Beranda");
        btnBerandaFormAdminKelolaDestinasi.setBorderPainted(false);
        btnBerandaFormAdminKelolaDestinasi.setContentAreaFilled(false);
        btnBerandaFormAdminKelolaDestinasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBerandaFormAdminKelolaDestinasi.setFocusPainted(false);
        btnBerandaFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBerandaFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnBerandaFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 47, -1, -1));

        btnKelolaDataFormAdminKelolaDestinasi.setForeground(java.awt.Color.white);
        btnKelolaDataFormAdminKelolaDestinasi.setText("Kelola Data");
        btnKelolaDataFormAdminKelolaDestinasi.setBorderPainted(false);
        btnKelolaDataFormAdminKelolaDestinasi.setContentAreaFilled(false);
        btnKelolaDataFormAdminKelolaDestinasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKelolaDataFormAdminKelolaDestinasi.setFocusPainted(false);
        btnKelolaDataFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelolaDataFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnKelolaDataFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 80, -1, -1));

        btnKelolaDestinasiFormAdminKelolaDestinasi.setForeground(java.awt.Color.white);
        btnKelolaDestinasiFormAdminKelolaDestinasi.setText("Kelola Destinasi");
        btnKelolaDestinasiFormAdminKelolaDestinasi.setBorderPainted(false);
        btnKelolaDestinasiFormAdminKelolaDestinasi.setContentAreaFilled(false);
        btnKelolaDestinasiFormAdminKelolaDestinasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKelolaDestinasiFormAdminKelolaDestinasi.setFocusPainted(false);
        btnKelolaDestinasiFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelolaDestinasiFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnKelolaDestinasiFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 112, -1, -1));

        btnLaporanFormAdminKelolaDestinasi.setForeground(java.awt.Color.white);
        btnLaporanFormAdminKelolaDestinasi.setText("Laporan");
        btnLaporanFormAdminKelolaDestinasi.setBorderPainted(false);
        btnLaporanFormAdminKelolaDestinasi.setContentAreaFilled(false);
        btnLaporanFormAdminKelolaDestinasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLaporanFormAdminKelolaDestinasi.setFocusPainted(false);
        btnLaporanFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnLaporanFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 146, -1, -1));

        btnKeluarFormAdminKelolaDestinasi.setForeground(java.awt.Color.white);
        btnKeluarFormAdminKelolaDestinasi.setText("Keluar");
        btnKeluarFormAdminKelolaDestinasi.setBorderPainted(false);
        btnKeluarFormAdminKelolaDestinasi.setContentAreaFilled(false);
        btnKeluarFormAdminKelolaDestinasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKeluarFormAdminKelolaDestinasi.setFocusPainted(false);
        btnKeluarFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnKeluarFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 360, -1, -1));

        labelNamaFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelNamaFormAdminKelolaDestinasi.setForeground(new java.awt.Color(0, 51, 153));
        labelNamaFormAdminKelolaDestinasi.setText("Nama");
        getContentPane().add(labelNamaFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, -1, -1));

        textFieldNamaFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNamaFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldNamaFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 110, -1));

        labelDeskripsiFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelDeskripsiFormAdminKelolaDestinasi.setForeground(new java.awt.Color(0, 51, 153));
        labelDeskripsiFormAdminKelolaDestinasi.setText("Deskripsi");
        getContentPane().add(labelDeskripsiFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, -1, -1));

        textFieldDeskripsiFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldDeskripsiFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldDeskripsiFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 110, -1));

        labelAlamatFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelAlamatFormAdminKelolaDestinasi.setForeground(new java.awt.Color(0, 51, 153));
        labelAlamatFormAdminKelolaDestinasi.setText("Alamat");
        getContentPane().add(labelAlamatFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

        textFieldAlamatFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldAlamatFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldAlamatFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 110, -1));

        labelJamOperasionalFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelJamOperasionalFormAdminKelolaDestinasi.setForeground(new java.awt.Color(0, 51, 153));
        labelJamOperasionalFormAdminKelolaDestinasi.setText("Jam Operasional");
        getContentPane().add(labelJamOperasionalFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, -1, -1));

        textFieldJamOperasionalFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldJamOperasionalFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldJamOperasionalFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 110, -1));

        labelHargaFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelHargaFormAdminKelolaDestinasi.setForeground(new java.awt.Color(0, 51, 153));
        labelHargaFormAdminKelolaDestinasi.setText("Harga");
        getContentPane().add(labelHargaFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, -1, -1));

        textFieldHargaFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldHargaFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldHargaFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 110, -1));

        labelKategoriFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelKategoriFormAdminKelolaDestinasi.setForeground(new java.awt.Color(0, 51, 153));
        labelKategoriFormAdminKelolaDestinasi.setText("Kategori");
        getContentPane().add(labelKategoriFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, -1, -1));

        comboBoxKategoriFormAdminKelolaDestinasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kategori", "Destinasi Candi & Pura", "Destinasi Danau & Kepulauan", "Destinasi Gunung", "Destinasi Kawah", "Destinasi Pantai", "Destinasi Museum", "Destinasi Wisata Kota & Budaya", "Destinasi Wisata Alam & Satwa", "Destinasi Wisata Buatan (Taman Hiburan)" }));
        comboBoxKategoriFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxKategoriFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(comboBoxKategoriFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 110, -1));

        btnTambahDestinasiFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTambahDestinasiFormAdminKelolaDestinasi.setForeground(new java.awt.Color(0, 51, 153));
        btnTambahDestinasiFormAdminKelolaDestinasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/tambah icon.png"))); // NOI18N
        btnTambahDestinasiFormAdminKelolaDestinasi.setText("Tambah Destinasi");
        btnTambahDestinasiFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahDestinasiFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnTambahDestinasiFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 180, -1));

        btnUbahFormAdminKelolaDestinasi.setBackground(new java.awt.Color(0, 51, 153));
        btnUbahFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUbahFormAdminKelolaDestinasi.setForeground(java.awt.Color.white);
        btnUbahFormAdminKelolaDestinasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/edit icon.png"))); // NOI18N
        btnUbahFormAdminKelolaDestinasi.setText("Ubah");
        btnUbahFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnUbahFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 80, -1));

        btnHapusFormAdminKelolaDestinasi.setBackground(new java.awt.Color(255, 0, 0));
        btnHapusFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHapusFormAdminKelolaDestinasi.setForeground(java.awt.Color.white);
        btnHapusFormAdminKelolaDestinasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/delete icon.png"))); // NOI18N
        btnHapusFormAdminKelolaDestinasi.setText("Hapus");
        btnHapusFormAdminKelolaDestinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusFormAdminKelolaDestinasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnHapusFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 90, -1));

        tableKelolaDestinasiFormAdminKelolaDestinasi.setBackground(new java.awt.Color(102, 102, 255));
        tableKelolaDestinasiFormAdminKelolaDestinasi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableKelolaDestinasiFormAdminKelolaDestinasi.setForeground(new java.awt.Color(255, 255, 255));
        tableKelolaDestinasiFormAdminKelolaDestinasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nama", "Deskripsi", "Lokasi", "Jam", "Kategori", "Harga"
            }
        ));
        scrollPaneTableKelolaDestinasiFormAdminKelolaDestinasi.setViewportView(tableKelolaDestinasiFormAdminKelolaDestinasi);

        getContentPane().add(scrollPaneTableKelolaDestinasiFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, -1, 220));

        bgFormAdminKelolaDestinasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Tampilan Dashboard Admin Kelola Destinasi.png"))); // NOI18N
        getContentPane().add(bgFormAdminKelolaDestinasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLaporanFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanFormAdminKelolaDestinasiActionPerformed
        new FormAdminLaporan().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLaporanFormAdminKelolaDestinasiActionPerformed

    private void btnBerandaFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBerandaFormAdminKelolaDestinasiActionPerformed
        new FormAdminBeranda().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBerandaFormAdminKelolaDestinasiActionPerformed

    private void btnKelolaDataFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelolaDataFormAdminKelolaDestinasiActionPerformed
        new FormAdminKelolaData().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKelolaDataFormAdminKelolaDestinasiActionPerformed

    private void btnKelolaDestinasiFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelolaDestinasiFormAdminKelolaDestinasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKelolaDestinasiFormAdminKelolaDestinasiActionPerformed

    private void textFieldJamOperasionalFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldJamOperasionalFormAdminKelolaDestinasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldJamOperasionalFormAdminKelolaDestinasiActionPerformed

    private void textFieldHargaFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldHargaFormAdminKelolaDestinasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldHargaFormAdminKelolaDestinasiActionPerformed

    private void textFieldDeskripsiFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldDeskripsiFormAdminKelolaDestinasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldDeskripsiFormAdminKelolaDestinasiActionPerformed

    private void textFieldAlamatFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldAlamatFormAdminKelolaDestinasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldAlamatFormAdminKelolaDestinasiActionPerformed

    private void textFieldNamaFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldNamaFormAdminKelolaDestinasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldNamaFormAdminKelolaDestinasiActionPerformed

    /**
     * Event handler untuk tombol Ubah.
     * Mengubah data destinasi yang sudah ada di database.
     * 
     * @param evt Event yang dipicu saat tombol diklik
     */
    private void btnUbahFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahFormAdminKelolaDestinasiActionPerformed
        
        // Memeriksa apakah ada baris yang dipilih
        int selectedRow = tableKelolaDestinasiFormAdminKelolaDestinasi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diubah!");
            return;
        }

        // Ambil ID dari baris tersembunyi (kolom 0)
        int id = Integer.parseInt(tableKelolaDestinasiFormAdminKelolaDestinasi.getValueAt(selectedRow, 0).toString());

        // Mengambil data dari field input
        String nama = textFieldNamaFormAdminKelolaDestinasi.getText();
        String alamat = textFieldDeskripsiFormAdminKelolaDestinasi.getText();
        String deskripsi = textFieldAlamatFormAdminKelolaDestinasi.getText();
        String harga = textFieldHargaFormAdminKelolaDestinasi.getText();
        String jamBuka = textFieldJamOperasionalFormAdminKelolaDestinasi.getText();
        String kategori = comboBoxKategoriFormAdminKelolaDestinasi.getSelectedItem().toString();

        if (jamBuka.isEmpty() || kategori.equals("Pilih Kategori") || nama.isEmpty() || alamat.isEmpty() || deskripsi.isEmpty() ||
                harga.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }
        
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "UPDATE destinasi SET nama=?, alamat=?, deskripsi=?, harga=?, jambuka=?, kategori=? WHERE id_destinasi=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, alamat);
            ps.setString(3, deskripsi);
            ps.setString(4, harga);
            ps.setString(5, jamBuka);
            ps.setString(6, kategori);
            ps.setInt(7, id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
            
            // Memuat ulang tabel dan mengosongkan field
            loadTableFormAdminDestinasi();
            textFieldNamaFormAdminKelolaDestinasi.setText("");
            textFieldAlamatFormAdminKelolaDestinasi.setText("");
            textFieldDeskripsiFormAdminKelolaDestinasi.setText("");
            textFieldHargaFormAdminKelolaDestinasi.setText("");
            textFieldJamOperasionalFormAdminKelolaDestinasi.setText("");
            comboBoxKategoriFormAdminKelolaDestinasi.setSelectedIndex(0);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUbahFormAdminKelolaDestinasiActionPerformed

    private void btnTambahDestinasiFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDestinasiFormAdminKelolaDestinasiActionPerformed
        new FormAdminTambahDestinasi().setVisible(true);
        dispose();    }//GEN-LAST:event_btnTambahDestinasiFormAdminKelolaDestinasiActionPerformed

    private void btnKeluarFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarFormAdminKelolaDestinasiActionPerformed
        new FormLogin().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKeluarFormAdminKelolaDestinasiActionPerformed

    private void btnProfilFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfilFormAdminKelolaDestinasiActionPerformed
        new FormAdminAkun().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnProfilFormAdminKelolaDestinasiActionPerformed

    private void comboBoxKategoriFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxKategoriFormAdminKelolaDestinasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxKategoriFormAdminKelolaDestinasiActionPerformed

    /**
     * Event handler untuk tombol Hapus.
     * Menghapus data destinasi yang dipilih dari database.
     * 
     * @param evt Event yang dipicu saat tombol diklik
     */
    private void btnHapusFormAdminKelolaDestinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusFormAdminKelolaDestinasiActionPerformed
        
        // Memeriksa apakah ada baris yang dipilih
        int selectedRow = tableKelolaDestinasiFormAdminKelolaDestinasi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus!");
            return;
        }

        // Konfirmasi penghapusan
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus destinasi ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) {
            return; // Batal menghapus jika user memilih No
        }

        int id = Integer.parseInt(tableKelolaDestinasiFormAdminKelolaDestinasi.getValueAt(selectedRow, 0).toString());

        try {
            Connection conn = Koneksi.getConnection();
            String sql = "DELETE FROM destinasi WHERE id_destinasi=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            
            // Memuat ulang tabel dan mengosongkan field 
            loadTableFormAdminDestinasi();           
            textFieldNamaFormAdminKelolaDestinasi.setText("");
            textFieldAlamatFormAdminKelolaDestinasi.setText("");
            textFieldDeskripsiFormAdminKelolaDestinasi.setText("");
            textFieldHargaFormAdminKelolaDestinasi.setText("");
            textFieldJamOperasionalFormAdminKelolaDestinasi.setText("");
            comboBoxKategoriFormAdminKelolaDestinasi.setSelectedIndex(0);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }//GEN-LAST:event_btnHapusFormAdminKelolaDestinasiActionPerformed

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
            java.util.logging.Logger.getLogger(FormAdminKelolaDestinasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAdminKelolaDestinasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAdminKelolaDestinasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAdminKelolaDestinasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FormAdminKelolaDestinasi().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bgFormAdminKelolaDestinasi;
    private javax.swing.JButton btnBerandaFormAdminKelolaDestinasi;
    private javax.swing.JButton btnHapusFormAdminKelolaDestinasi;
    private javax.swing.JButton btnKelolaDataFormAdminKelolaDestinasi;
    private javax.swing.JButton btnKelolaDestinasiFormAdminKelolaDestinasi;
    private javax.swing.JButton btnKeluarFormAdminKelolaDestinasi;
    private javax.swing.JButton btnLaporanFormAdminKelolaDestinasi;
    private javax.swing.JButton btnProfilFormAdminKelolaDestinasi;
    private javax.swing.JButton btnTambahDestinasiFormAdminKelolaDestinasi;
    private javax.swing.JButton btnUbahFormAdminKelolaDestinasi;
    private javax.swing.JComboBox<String> comboBoxKategoriFormAdminKelolaDestinasi;
    private javax.swing.JLabel labelAlamatFormAdminKelolaDestinasi;
    private javax.swing.JLabel labelDeskripsiFormAdminKelolaDestinasi;
    private javax.swing.JLabel labelHargaFormAdminKelolaDestinasi;
    private javax.swing.JLabel labelJamOperasionalFormAdminKelolaDestinasi;
    private javax.swing.JLabel labelKategoriFormAdminKelolaDestinasi;
    private javax.swing.JLabel labelNamaFormAdminKelolaDestinasi;
    private javax.swing.JScrollPane scrollPaneTableKelolaDestinasiFormAdminKelolaDestinasi;
    private javax.swing.JTable tableKelolaDestinasiFormAdminKelolaDestinasi;
    private javax.swing.JTextField textFieldAlamatFormAdminKelolaDestinasi;
    private javax.swing.JTextField textFieldDeskripsiFormAdminKelolaDestinasi;
    private javax.swing.JTextField textFieldHargaFormAdminKelolaDestinasi;
    private javax.swing.JTextField textFieldJamOperasionalFormAdminKelolaDestinasi;
    private javax.swing.JTextField textFieldNamaFormAdminKelolaDestinasi;
    // End of variables declaration//GEN-END:variables
}
