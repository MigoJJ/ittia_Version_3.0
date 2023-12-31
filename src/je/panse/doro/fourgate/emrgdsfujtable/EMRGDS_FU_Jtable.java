package je.panse.doro.fourgate.emrgdsfujtable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.entry.EntryDir;

// Main Class for creating the GUI Table
public class EMRGDS_FU_Jtable {
    
    private static JFrame frame;
    public static JTable table;
    private static DefaultTableModel model;
    private JButton saveButton, loadButton;

    public EMRGDS_FU_Jtable() {
        initializeFrame();
        initializeTable();
        initializeButtons();
        layoutComponents();
        frame.setVisible(true);
    }

    // Initialize the main JFrame
    private void initializeFrame() {
        frame = new JFrame("EMRGDS_FU_Jtable");
        frame.setSize(1600, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Setup the table with the necessary properties and model
    private void initializeTable() {
        String[] columnNames = {"Category","Diabetes Mellitus", "Hypertension", 
        						"Hypercholesterolemia", "Thyroid", "Influenza",
        						"URI","Atypical chest pain", "Osteoporosis",
        						"10"};
        model = new DefaultTableModel(columnNames, 10);
        table = new JTable(model);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new MultiLineCellRenderer());
        }
        
        table.setDefaultEditor(Object.class, new MultiLineCellEditor());
        setRowAndColumnDimensions();
    }

    // Set specific row and column dimensions
    private static void setRowAndColumnDimensions() {
    	table.setRowHeight(80);
//        table.setRowHeight(0, 150);  
        table.setRowHeight(0, 50);
//        table.setRowHeight(1, 40);
//    	table.getColumnModel().getColumn(0).setPreferredWidth(5);
//        table.getColumnModel().getColumn(1).setPreferredWidth(50);
//        table.getColumnModel().getColumn(2).setPreferredWidth(75);
    }

    // Initialize the save and load buttons with actions
    private void initializeButtons() {
        saveButton = new JButton("Save Data");
        saveButton.addActionListener(e -> saveData());
        loadButton = new JButton("Load Data");
        loadButton.addActionListener(e -> loadData());
    }

    // Layout components on the frame
    private void layoutComponents() {
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.add(saveButton);
        panel.add(loadButton);
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
    }

    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(EntryDir.homeDir + "/fourgate/emrgdsfujtable/tabledata.ser"))) {
            
            // Save table data
            out.writeObject(model.getDataVector());
            
            // Save column widths
            int[] columnWidths = new int[table.getColumnCount()];
            for (int i = 0; i < table.getColumnCount(); i++) {
                columnWidths[i] = table.getColumnModel().getColumn(i).getWidth();
            }
            out.writeObject(columnWidths);
            
            JOptionPane.showMessageDialog(frame, "Data Saved Successfully!");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(EntryDir.homeDir + "/fourgate/emrgdsfujtable/tabledata.ser"))) {
            
            // Load table data
            Vector<? extends Vector> data = (Vector<? extends Vector>) in.readObject();
            Vector<String> columnNames = new Vector<>();
            for (int i = 0; i < model.getColumnCount(); i++) {
                columnNames.add(model.getColumnName(i));
            }
            model.setDataVector(data, columnNames);

            // Load and set column widths
            int[] columnWidths = (int[]) in.readObject();
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setWidth(columnWidths[i]);
                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]); // this line can be retained or removed based on your preference
            }

            JOptionPane.showMessageDialog(frame, "Data Loaded Successfully!");

            // Reapply the row dimensions after loading the data
            setRowAndColumnDimensions();
//            printTableData(); 
            
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void printTableData() {
        for (int col = 0; col < table.getColumnCount(); col++) {
            String columnName = table.getColumnName(col);
            System.out.println("Column " + (col + 1) + " (" + columnName + "):");
            for (int row = 0; row < table.getRowCount(); row++) {
                System.out.println("Row [" + (row + 1) + "]: " + table.getValueAt(row, col));
            }
            System.out.println(); // Add an empty line for separation
        }
    }

								    static void extractTableData(String coName) {
								        loadData();
								
								        if (isColumnNameInvalid(coName)) {
								            return;
								        }
								
								        int columnIndex = findColumnIndex(coName);
								        
								        if (columnIndex == -1) {
								            System.out.println("Column named '" + coName + "' not found.");
								            return;
								        }
								
								        printColumnData(columnIndex, coName);
								
								    }
								
								    private static boolean isColumnNameInvalid(String coName) {
								        if (coName == null || coName.trim().isEmpty()) {
								            System.out.println("Column name is either null or empty.");
								            return true;
								        }
								        return false;
								    }
								
								    private static int findColumnIndex(String coName) {
								        for (int col = 0; col < table.getColumnCount(); col++) {
								            if (table.getColumnName(col).equals(coName)) {
								                return col;
								            }
								        }
								        return -1;
								    }
								
								    private static void printColumnData(int columnIndex, String coName) {
								        System.out.println("Column: " + coName);
								
								        for (int row = 0; row < table.getRowCount(); row++) {
								            Object value = table.getValueAt(row, columnIndex);
								            
								            String jtableExtractString = "";
								            if (value != null) {
								                jtableExtractString = value.toString();
//								                String A00 = (String) model.getValueAt(row, columnIndex); 
//								                GDSEMR_frame.setTextAreaText(5, A00);
//
//								                
								            } else {
								                // handle non-string values appropriately
								            }
								
								            printRowData(row, jtableExtractString);


								        }
								
								        System.out.println(); // Add an empty line for separation
								    }
								
								    private static void printRowData(int row, String data) {
								        if (data != null) {
								            System.out.println("Row [" + (row + 1) + "]: " + data);
					
								        } else {
								            System.out.println("Row [" + (row + 1) + "]: Data is null");
								        }
								    }
								
								


    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EMRGDS_FU_Jtable());
    }
}

// Cell editor that supports multi-line input
class MultiLineCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JScrollPane scrollPane;
    private JTextArea textArea;

    public MultiLineCellEditor() {
        textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        scrollPane = new JScrollPane(textArea);
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int currentColumn = EMRGDS_FU_Jtable.table.getSelectedColumn();
                    if (currentColumn < EMRGDS_FU_Jtable.table.getColumnCount() - 1) {
                        EMRGDS_FU_Jtable.table.changeSelection(EMRGDS_FU_Jtable.table.getSelectedRow(), currentColumn + 1, false, false);
                        e.consume();
                    }
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textArea.setText((value != null) ? value.toString() : "");
        return scrollPane;
    }

    @Override
    public Object getCellEditorValue() {
        return textArea.getText();
    }
}

// Cell renderer that displays multi-line content
class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
    
    public MultiLineCellRenderer() {
        setWrapStyleWord(true);
        setLineWrap(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value != null) ? value.toString() : "");
        
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
        if (table.getRowHeight(row) != getPreferredSize().height) {
            table.setRowHeight(row, getPreferredSize().height);
        }
        
        return this;
    }
}
