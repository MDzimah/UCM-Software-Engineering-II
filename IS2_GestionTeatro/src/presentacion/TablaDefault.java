package presentacion;


import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import misc.Constants;

/**
 * {@code TablaDefault} is a generic Swing-based window for displaying and optionally editing a list of
 * domain objects in a tabular format.
 *
 * <p>This class extends {@code JFrame} and embeds a {@code JTable} whose contents are automatically derived
 * from objects implementing the {@link Convertable} interface. It supports both read-only ("consultar") and editable modes.</p>
 *
 * <p>Key features include:</p>
 * <ul>
 *   <li>Automatic mapping of domain object fields to table cells via {@code getColumnValue} and {@code setColumnValue} methods.</li>
 *   <li>Support for multiple domain types such as {@code TFactura}, {@code TObra}, {@code TCliente}, etc.</li>
 *   <li>Editable mode with a built-in "Aceptar" button to confirm changes made to table cells.</li>
 *   <li>Consultation mode with a more compact, non-resizable layout for viewing single-record tables.</li>
 *   <li>Optional support (currently commented out) for multi-line cell rendering to display arrays or collections inside cells.</li>
 *   <li>Consistent UI styling using centralized font and layout constants from the {@code Constants} class.</li>
 * </ul>
 *
 * <p>Note: This class uses an internal {@code DefaultTableModel} implementation to bind data and column headers
 * to the {@code JTable}, and relies on the domain objects conforming to the {@code Convertable<T>} interface.</p>
 *
 * @param <T> the type of domain objects to display in the table; must implement {@link Convertable}
 */
public class TablaDefault<T extends Convertable<T>> extends JFrame {
	//Solo para los modos de actualizacion
	private JButton aceptar; 
	private boolean editable;
	private T edicion;
	
    private class DefaultTableModel extends AbstractTableModel {
        private final String[] nomCols;
        private ArrayList<T> datos;

        public DefaultTableModel(String[] nomCols, ArrayList<T> datos) {
            this.nomCols = nomCols;
            this.datos = datos;
        }

        @Override
        public int getRowCount() { return datos.size(); }

        @Override
        public int getColumnCount() { return nomCols.length; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) { 
        	 return datos.get(rowIndex).getColumnValue(columnIndex);
        }

        @Override
        public String getColumnName(int columnIndex) { return nomCols[columnIndex]; }
        
        @Override
        public void setValueAt(Object value, int row, int col) {
            datos.get(row).setColumnValue(col, value);
            fireTableCellUpdated(row, col);
        }
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return editable;
        }
    }

    //POR SI HUBIERA QUE MOSTRAR EN LA TABLA ARRAYS/COLLECTIONS EN CELDAS
    //MultiLineTableCellRenderer inspirado por Channa Jayamuni en Stack Overflow
    //https://stackoverflow.com/questions/9955595/how-to-display-multiple-lines-in-a-jtable-cell
    /*
    private class MultiLineTableCellRenderer extends JList<String> implements TableCellRenderer {
    	
    	//Para una apariencia más vistosa de la tabla
    	public MultiLineTableCellRenderer() {
    	    setOpaque(true);
    	    setFont(Constants.FontTablaDefaultCuerpo());
    	    setFixedCellHeight(-1); //Para ajuste dinámico del tamaño de las células
    	    setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    	}
    	
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
        	//Solo se va a hacer un renderizado multilínea si el valor del objeto es una Collection o un Array
        	//En caso contrario renderizamos la casilla con el toString() del Objeto
            if (value == null) this.setListData(new String[] {""});
            else {
                if (!(value instanceof Collection) && !value.getClass().isArray()) setListData(new String[] { value.toString() });
                else {
                	Object[] paramData;
                	if (value.getClass().isArray()) paramData = (Object[]) value;
                	else paramData = ((Collection<?>) value).toArray();
                	
                	String[] data = new String[paramData.length];
                	for (int i = 0; i < data.length; ++i) {
                		data[i] = paramData[i].toString();
                	}
                    this.setListData(data);
                }
            }

            //Para la interacción con las celdas
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            return this;
        }
    }
    
     private void setRender(JTable table, int numCols) {
        //Las celdas de la tabla pueden contener listas, luego para mostrar los elementos uno debajo del otro, hace falta
        //cambiar la forma de renderizarlas
        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer(); 
        table.setDefaultRenderer(String[].class, renderer);
        for (int i = 0; i < numCols; ++i) {
        	table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        for (int row = 0; row < table.getRowCount(); row++) {
            int maxHeight = table.getRowHeight();
            for (int col = 0; col < table.getColumnCount(); col++) {
                Component comp = table.prepareRenderer(renderer, row, col);
                maxHeight = Math.max(maxHeight, comp.getPreferredSize().height);
            }
            table.setRowHeight(row, maxHeight);
        }
    }
    */


    /**
     * Constructs a {@code TablaDefault} window configured to display a list of domain objects in a table format.
     *
     * <p>This constructor creates a Swing {@code JFrame} that displays the provided data using a {@code JTable}.
     * It supports both consultation (read-only) and editable modes. In editable mode, an "Aceptar" button is displayed
     * to confirm edits made to the table.</p>
     *
     * @param nombreTabla the title to be shown on the window's title bar
     * @param columnNames an array of column header names (each corresponding to a property of the domain object)
     * @param data a list of domain objects (implementing {@link Convertable}) to be displayed in the table
     * @param consultar if {@code true}, the window uses a compact, fixed-size layout intended for simple viewing or editing a single record
     * @param editable if {@code true}, enables editing of the table's cells and shows an "Aceptar" button at the bottom
     *
     * <p>Behavior notes:</p>
     * <ul>
     *   <li>If {@code data} is not {@code null}, each row in the table corresponds to an instance of the domain object.</li>
     *   <li>In editable + consultar mode, it assumes a single row and allows inline editing of that record.</li>
     *   <li>Column values are dynamically extracted and updated using the {@code getColumnValue} and {@code setColumnValue} methods from {@code Convertable}.</li>
     * </ul>
     */
	public TablaDefault(String nombreTabla,  String[] columnNames, ArrayList<T> data, boolean consultar, boolean editable) {
        this.setTitle(nombreTabla);
        this.setLayout(new BorderLayout());
        
        Dimension sd = Constants.screenDimension();
        int width, height;
        if (consultar) {
        	width = sd.width/2;
        	height = sd.height/10;
        	this.setResizable(false);
        }
        else {	     
	        width = sd.width - sd.width / 10;
	        height = sd.height - sd.height / 12;   
        }
        this.setSize(new Dimension(width, height));
        
        this.editable = editable;
        
        if (data != null && !data.isEmpty()) {
        	DefaultTableModel model = new DefaultTableModel(columnNames, data);
	        JTable table = new JTable(model);
	       
	        
	        //Cambiar apariencia del header de la tabla
	        JTableHeader header = table.getTableHeader();
	        header.setFont(Constants.FontTablaDefaultCabecera());

	        //this.setRender(table, columnNames.length);
	        
	        //Solo se edita la tabla en los CU de actualizar, luego consultar tiene que ser true también
	        if (editable && consultar) {
	        	this.aceptar = new JButton("Aceptar");
	        	this.add(new JScrollPane(table), BorderLayout.NORTH);
	        	this.add(this.aceptar, BorderLayout.SOUTH);
	        	
	        	//En el modo consultar la tabla tiene una única fila, luego inicializo la edicion a lo
	        	//que vale el array de datos en su única posición no nula
	        	this.edicion = data.get(0);
	        	
		        table.getModel().addTableModelListener(new TableModelListener() {
		            @Override
		            public void tableChanged(TableModelEvent e) {
		                if (e.getType() == TableModelEvent.UPDATE) {
		                	edicion = data.get(0);
		                }
		            }
		        });
	        }
	        else this.add(new JScrollPane(table), BorderLayout.CENTER);
        }
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
	
	public T getEdicion() { return this.edicion; }
	
	public JButton getOkButton() { return editable ? this.aceptar : null; }

  //PRUEBA DE LA TABLA
/*
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        String[] columnNames = {
            "ID Factura", "ID Cliente", "ID Taquillero", "Fecha", "Importe", "Subtotal"
        };

        Collection<Object> facturas = new ArrayList<>();

        for (int i = 1; i <= 1; i++) {

            TFactura factura = new TFactura(
                    1000 + i, 2000 + i, true,
                    LocalDateTime.now().minusDays(i).truncatedTo(ChronoUnit.SECONDS),
                    50.0f + i,
                    60.0f + i
            );
            factura.setIdFactura(i);
            factura.setIdTaquillero(3000 + i);

            facturas.add(factura);
        }
        TablaDefault v= new TablaDefault("Tabla de Facturas", columnNames, facturas, true, true);
    });
    
}*/
}
//*/