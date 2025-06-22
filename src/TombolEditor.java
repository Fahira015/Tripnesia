import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Kelas TombolEditor merupakan editor khusus untuk sel tabel (JTable)
 * yang menampilkan dua tombol aksi ("Setujui" dan "Tolak") berdasarkan status pemesanan.
 * Tombol hanya muncul jika status pemesanan adalah "Tertunda".
 * Jika status sudah "Disetujui" atau "Ditolak", maka tombol akan disesuaikan
 * dan hanya menampilkan informasi bahwa aksi sudah dilakukan.
 * 
 * Kelas ini juga langsung mengupdate status ke database ketika tombol diklik.
 * 
 * 
 * @author tim F.N.M Trip
 */
public class TombolEditor extends AbstractCellEditor implements TableCellEditor {

    private final JButton btnSetujui = new JButton("Setujui");
    private final JButton btnTolak = new JButton("Tolak");
    private JTable table;
    private JPanel panel = new JPanel(); 
    private int row;
    
    /**
     * Constructor untuk membuat TombolEditor.
     * Menginisialisasi editor dengan menambahkan ActionListener ke tombol-tombol
     * untuk menangani aksi setujui dan tolak pemesanan.
     * 
     * @param checkBox JCheckBox (parameter ini tidak digunakan, bisa diabaikan)
     * @param table JTable yang akan menggunakan editor ini
     *
     */
    public TombolEditor(JCheckBox checkBox, JTable table) {
        this.table = table;

        // ActionListener untuk tombol Setujui
        btnSetujui.addActionListener(e -> {
            if (row != -1) {
                fireEditingStopped();
                updateStatusPemesanan(row, "Disetujui");
            }
        });

        // ActionListener untuk tombol Tolak
        btnTolak.addActionListener(e -> {
            if (row != -1) {
                fireEditingStopped();
                updateStatusPemesanan(row, "Ditolak");
            }
        });
    }

    /**
     * Mengupdate status pemesanan di database dan tabel.
     * Method ini akan mengeksekusi UPDATE query ke database untuk mengubah
     * status pemesanan, kemudian mengupdate tampilan tabel.
     * 
     * @param row index baris dalam tabel yang statusnya akan diubah
     * @param status status baru yang akan di-set ("Disetujui" atau "Ditolak")
     */
    private void updateStatusPemesanan(int row, String status) {
        try {
            int idPemesanan = Integer.parseInt(table.getValueAt(row, 0).toString());
            Connection conn = Koneksi.getConnection();
            String sql = "UPDATE pemesanan SET status = ? WHERE id_pemesanan = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, idPemesanan);
            ps.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(null, "Status pemesanan diubah menjadi " + status);

            table.setValueAt(status, row, 3); // Update status di tabel langsung

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal ubah status: " + ex.getMessage());
        }
    }
    
    /**
     * Menentukan komponen editor yang akan digunakan dalam sel tabel.
     * Menampilkan tombol berdasarkan status pemesanan.
     * 
     * @param table
     * @param value
     * @param isSelected
     * @param row
     * @param column
     * @return 
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {

        this.row = row;
        panel.removeAll();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        // Ambil status dari kolom ke-4 
        String status = table.getValueAt(row, 3).toString();

        if (status.equalsIgnoreCase("Tertunda")) {
            // Tampilkan dua tombol aktif untuk pemesanan tertunda
            panel.add(btnSetujui);
            panel.add(btnTolak);
        } else if (status.equalsIgnoreCase("Disetujui")) {
            // hanya tampilkan tombol Setujui
            JButton btnSetujuiOnly = new JButton("Setujui");
            btnSetujuiOnly.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Pesanan sudah disetujui.");
                fireEditingStopped();
            });
            panel.add(btnSetujuiOnly);
        } else if (status.equalsIgnoreCase("Ditolak")) {
            // hanya tampilkan tombol Tolak
            JButton btnTolakOnly = new JButton("Tolak");
            btnTolakOnly.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Pesanan sudah ditolak.");
                fireEditingStopped();
            });
            panel.add(btnTolakOnly);
        }

        return panel;
    }

    /**
     * Mengembalikan nilai editor.
     * @return 
     */
    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
