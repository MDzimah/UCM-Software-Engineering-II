package presentacion.superClases;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import misc.Constants;

import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class TablaDefault extends JFrame {

    private class DefaultTableModel extends AbstractTableModel {
        private final String[] columnNames;
        private final List<Object[]> datos;

        public DefaultTableModel(String[] nomCols, List<Object[]> datos) {
            this.columnNames = nomCols;
            this.datos = datos;
        }

        @Override
        public int getRowCount() { return datos.size(); }

        @Override
        public int getColumnCount() { return columnNames.length; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) { return datos.get(rowIndex)[columnIndex]; }

        @Override
        public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }
    }
    
    /**
     * Constructs a new {@code TablaDefault} frame displaying a table with the specified column names and data.
     * <p>
     * The table is created using a custom model based on {@code AbstractTableModel}, and it is displayed
     * within a scrollable pane. The frame's size is calculated based on the screen dimensions, leaving margins.
     *
     * @param columnNames an array of {@code String} representing the column headers of the table
     * @param data a {@code List} of {@code Object[]} where each element represents a row of data in the table
     * @param nombreTabla the title to be displayed in the window frame
     */
    public TablaDefault(String[] columnNames, List<Object[]> data, String nombreTabla) {
		 this.setTitle(nombreTabla);
	     this.setLayout(new BorderLayout());
	     Dimension sd = Constants.screenDimension();
	     int width = sd.width-sd.width/10;
	     int height = sd.height - sd.height/12;
	     this.setSize(new Dimension(width, height));
	     
	     //Creación de la tabla a partir del modelo
		 DefaultTableModel model = new DefaultTableModel(columnNames, data);
		 JTable table = new JTable(model);
		 this.add(new JScrollPane(table), BorderLayout.CENTER);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
