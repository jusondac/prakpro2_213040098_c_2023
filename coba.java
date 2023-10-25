import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class coba extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField textFieldNama;

    public coba() {
        // Set layout manager
        setLayout(new BorderLayout());

        // Buat table model dengan kolom-kolom yang sesuai
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nama");
        loadDataFromFile(); // Load data dari file saat aplikasi dimulai

        // Inisialisasi JTable dengan table model
        table = new JTable(tableModel);

        // Tambahkan JTable ke JScrollPane agar bisa di-scroll jika data terlalu banyak
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel untuk input data
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel labelNama = new JLabel("Nama:");
        textFieldNama = new JTextField(20);
        JButton buttonSimpan = new JButton("Simpan");
        JButton buttonHapus = new JButton("Hapus");
        JButton buttonEdit = new JButton("Edit");

        // Action listener untuk tombol Simpan
        buttonSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = textFieldNama.getText();
                if (!nama.isEmpty()) {
                    tambahData(nama); // Menambahkan data baru
                    textFieldNama.setText(""); // Mengosongkan input field setelah simpan
                }
            }
        });

        // Action listener untuk tombol Hapus
        buttonHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    hapusData(selectedRow); // Menghapus data dari tabel dan file
                }
            }
        });

        // Action listener untuk tombol Edit
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Mengambil data dari user
                    String namaBaru = JOptionPane.showInputDialog(coba.this, "Masukkan nama baru:", "Edit Nama", JOptionPane.PLAIN_MESSAGE);
                    if (namaBaru != null && !namaBaru.isEmpty()) {
                        editData(selectedRow, namaBaru); // Mengedit data di tabel dan file
                    }
                }
            }
        });

        // Menambahkan komponen-komponen ke panel input
        inputPanel.add(labelNama);
        inputPanel.add(textFieldNama);
        inputPanel.add(buttonSimpan);
        inputPanel.add(buttonHapus);
        inputPanel.add(buttonEdit);

        // Menambahkan panel input ke frame
        add(inputPanel, BorderLayout.SOUTH);

        // Set properti frame
        setTitle("Aplikasi Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Tampilkan frame di tengah layar
        setVisible(true);
    }

    // Method untuk menambah data ke tabel dan file
    private void tambahData(String nama) {
        // Menambah data ke tabel
        tableModel.addRow(new Object[]{nama});
        // Menambah data ke file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", true))) {
            writer.write(nama);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method untuk menghapus data dari tabel dan file
    private void hapusData(int rowIndex) {
        // Menghapus data dari tabel
        tableModel.removeRow(rowIndex);
        // Menghapus data dari file
        try {
            // Membaca data dari file ke dalam ArrayList
            ArrayList<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            // Menulis ulang data ke file tanpa data yang dihapus
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            for (int i = 0; i < lines.size(); i++) {
                if (i != rowIndex) {
                    writer.write(lines.get(i));
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method untuk mengedit data di tabel dan file
    private void editData(int rowIndex, String namaBaru) {
        // Mengedit data di tabel
        tableModel.setValueAt(namaBaru, rowIndex, 0);
        // Mengedit data di file
        try {
            // Membaca data dari file ke dalam ArrayList
            ArrayList<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            // Menulis ulang data ke file dengan data yang diedit
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            for (int i = 0; i < lines.size(); i++) {
                if (i == rowIndex) {
                    writer.write(namaBaru);
                } else {
                    writer.write(lines.get(i));
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method untuk memuat data dari file saat aplikasi dimulai
    private void loadDataFromFile() {
        try {
            // Membaca data dari file ke dalam ArrayList
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                // Menambahkan data ke tabel
                tableModel.addRow(new Object[]{line});
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new coba();
            }
        });
    }
}
