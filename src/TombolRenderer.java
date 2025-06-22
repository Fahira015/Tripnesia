import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Kelas TombolRenderer digunakan untuk merender sel tabel
 * yang berisi tombol aksi berdasarkan status pemesanan.
 * 
 * Kelas ini hanya bertanggung jawab untuk menampilkan tombol di sel tabel.
 * Aksi dilakukan oleh kelas TombolEditor.
 * 
 * Jika status adalah:
 * - "Tertunda" → tampilkan tombol "Setujui" dan "Tolak"
 * - "Disetujui" → tampilkan hanya tombol "Setujui"
 * - "Ditolak" → tampilkan hanya tombol "Tolak"
 * 
 * @author tim F.N.M Trip
 */
public class TombolRenderer extends JPanel implements TableCellRenderer {

    /**
     * Konstruktor yang mengatur panel renderer.
     */
    public TombolRenderer() {
       setOpaque(true); // Agar warna latar belakang ditampilkan
       setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Tata letak horizontal dengan jarak antar tombol
   
    }

    /**
     * Metode ini dipanggil setiap kali sel tabel perlu dirender.
     * Komponen tombol akan ditambahkan sesuai dengan status pada baris tersebut.
     * 
     * @param table Tabel yang sedang dirender
     * @param value Nilai dari sel (tidak digunakan di sini)
     * @param isSelected Apakah sel sedang dipilih
     * @param hasFocus Apakah sel memiliki fokus
     * @param row Baris dari sel
     * @param column Kolom dari sel
     * @return Komponen yang akan ditampilkan di panel(panel dengan tombol)
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        // Clear panel
        this.removeAll();
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        // Ambil status
        String status = table.getValueAt(row, 3).toString();

        // Tentukan tombol yang akan ditampilkan berdasarkan status
        if (status.equalsIgnoreCase("Tertunda")) {
            // Pemesanan masih tertunda, tampilkan kedua opsi
            this.add(new JButton("Setujui"));
            this.add(new JButton("Tolak"));
        } else if (status.equalsIgnoreCase("Disetujui")) {
            // Pemesanan sudah disetujui, tampilkan tombol Setujui saja
            this.add(new JButton("Setujui"));
        } else if (status.equalsIgnoreCase("Ditolak")) {
            // Pemesanan sudah ditolak, tampilkan tombol Tolak saja
            this.add(new JButton("Tolak"));
        }

        return this;
    }
}