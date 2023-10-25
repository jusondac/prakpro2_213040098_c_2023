import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

public class TugasFormTable extends JFrame {
    private DefaultTableModel tablemodel;
    private boolean checboxselected;
    private JLabel labelnama, labelnomor, labelradio, labelalamat;
    private JTextField textnama, textnomor;
    private JTextArea textalamat;
    private JButton buttonsimpan, buttonfile, buttonhapus, buttonedit;

    public TugasFormTable() {
        JFrame jframe = this;
        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(jframe, "Do you want to Exit ?", "Exit Confirmation : ", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if (result == JOptionPane.NO_OPTION) jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

        labelnama = new JLabel("Nama :");labelnama.setBounds(15,40,350,15);
        labelnomor = new JLabel("Nomor :");labelnomor.setBounds(15,100,350,15);
        labelradio = new JLabel("Jenis Kelamin :");labelradio.setBounds(15,160,350,15);
        labelalamat = new JLabel("Alamat :");labelalamat.setBounds(15,230,350,15);

        textnama = new JTextField();textnama.setBounds(15,60,200,30);
        textnomor = new JTextField();textnomor.setBounds(15,120,200,30);
        textalamat = new JTextArea();textalamat.setBounds(15,250,400,100);

        buttonsimpan = new JButton("Simpan");buttonsimpan.setBounds(15,360,100,40);
        buttonedit = new JButton("Edit");buttonedit.setBounds(115,360,80,40);
        buttonhapus = new JButton("Hapus");buttonhapus.setBounds(195,360,80,40);
        buttonfile = new JButton("Simpan ke File");buttonfile.setBounds(275,360,150,40);


        JRadioButton radioButton_1 = new JRadioButton("Laki-laki", true);radioButton_1.setBounds(15,180,200,30);
        JRadioButton radioButton_2 = new JRadioButton("Perempuan");radioButton_2.setBounds(15,200,200,30);

        tablemodel = new DefaultTableModel();
        JTable table = new JTable(tablemodel);
        tablemodel.addColumn("Nama");
        tablemodel.addColumn("Nomor");
        tablemodel.addColumn("Jenis Kelamin");
        tablemodel.addColumn("Alamat");

        JScrollPane scrollabletable = new JScrollPane(table);
        scrollabletable.setBounds(15, 420, 500, 200);

        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButton_1);
        bg.add(radioButton_2);

        buttonhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int barisTerpilih = table.getSelectedRow();
                if (barisTerpilih != -1) {
                    // Menghapus data dari tabel
                    tablemodel.removeRow(barisTerpilih);

                    // Menyimpan data kembali ke file setelah menghapus data
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("/home/rejka/Desktop/data.txt"))) {
                        for (int i = 0; i < tablemodel.getRowCount(); i++) {
                            writer.write((String) tablemodel.getValueAt(i, 0));
                            writer.newLine();
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(jframe, "Silakan pilih data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        buttonsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("/home/rejka/Desktop/data.txt", true))) {
                    // Menyimpan data baru ke file
                    String jeniskelamin = "", nama = textnama.getText(), nomor = textnomor.getText(), alamat = textalamat.getText();
                    if(radioButton_1.isSelected()) jeniskelamin = radioButton_1.getText();
                    if(radioButton_2.isSelected()) jeniskelamin = radioButton_2.getText();
                    writer.write(nama+","+nomor+","+jeniskelamin+","+alamat+",");
                    writer.newLine();
                    writer.close();

                    // Menambah data baru ke tabel
                    tablemodel.addRow(new Object[]{nama, nomor, jeniskelamin, alamat});
                    textalamat.setText("");
                    textnomor.setText("");
                    textnama.setText("");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        buttonedit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = table.getSelectedRow();
                String jeniskelamin = "", nama = textnama.getText(), nomor = textnomor.getText(), alamat = textalamat.getText();
                if(radioButton_1.isSelected()) jeniskelamin = radioButton_1.getText();
                if(radioButton_2.isSelected()) jeniskelamin = radioButton_2.getText();

                table.setValueAt(nama, selected, 0);
                table.setValueAt(nomor, selected, 1);
                table.setValueAt(alamat, selected, 3);
                table.setValueAt(jeniskelamin, selected, 2);
            }
        });

        try (BufferedReader reader = new BufferedReader(new FileReader("/home/rejka/Desktop/data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Menambahkan data dari file ke tabel
                String[] arr = line.split(",");
                String nama = arr[0], nomor = arr[1], alamat = arr[2], jeniskelamin = arr[3];
                tablemodel.addRow(new Object[]{nama, nomor, jeniskelamin, alamat});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(labelnama);
        this.add(labelnomor);
        this.add(labelradio);
        this.add(labelalamat);
        this.add(textalamat);
        this.add(radioButton_1);
        this.add(radioButton_2);
        this.add(textnama);
        this.add(textnomor);
        this.add(buttonsimpan);
        this.add(buttonedit);
        this.add(buttonhapus);
        this.add(buttonfile);
        this.add(scrollabletable);
        this.setSize(550,700);
        this.setLayout(null);
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TugasFormTable().setVisible(true);
            }
        });
    }
}
