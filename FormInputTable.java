import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FormInputTable extends AbstractTableModel {
    private String[] columnNames = {"Nama", "Nomor", "Jenis Kelamin", "Alamat"};
    private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

    public int getRowCount() {
        return data.size();
    }
    public int getColumnCount() {
        return columnNames.length;
    }
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        List<String> ri = data.get(rowIndex);
        return ri.get(columnIndex);
    }

    public boolean isCellEditable(int row, int col)  { return false; }
    public void add(ArrayList<String> val) {
        data.add(val);
        fireTableRowsInserted(data.size() - 1, data.size() -1);
    }
    public void remove(int rowIndex) {
        List<String> ri = data.get(rowIndex);
        data.remove(ri);
    }
}
