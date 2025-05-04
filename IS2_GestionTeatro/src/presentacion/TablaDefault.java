package presentacion;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import misc.*;
import negocio.factura.TFactura;

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

@SuppressWarnings("serial")
public class TablaDefault<T extends Convertable<T>> extends JFrame {
	//Solo para los modos de actualizacion
	private JButton aceptar; 
	private T edicion;
	private boolean editable;
	
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
        	try {
        		datos.get(row).setColumnValue(col, (String)value);
        	}
        	catch(Exception e) {
        		ViewUtils.createErrorDialogMessage(Messages.EDICION_INVALIDA_TABLA);
        	}
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
     * <p>In this constructor, the column header widths are dynamically calculated based on the width of each column name.
     * The width is determined by calculating the pixel width of the text in the header using {@code FontMetrics}, and a
     * buffer of 120 pixels is added to ensure proper display of the headers in the window.</p>
     *
     * <p>If the table is in editable mode, only a single row will be displayed for editing. If the table contains more than one row
     * and editable mode is selected, an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param nombreTabla the title to be shown on the window's title bar
     * @param columnNames an array of column header names (each corresponding to a property of the domain object)
     * @param data a list of domain objects (implementing {@link Convertable}) to be displayed in the table
     * @param CUActualizar if {@code true}, enables editable mode and shows an "Aceptar" button for confirming table edits
     * 
     * <p>Behavior notes:</p>
     * <ul>
     *   <li>If {@code data} is not {@code null}, each row in the table corresponds to an instance of the domain object.</li>
     *   <li>In editable mode, only one row will be available for editing, assuming the table's data size is 1.</li>
     *   <li>Column values are dynamically extracted and updated using the {@code getColumnValue} and {@code setColumnValue} methods from {@code Convertable}.</li>
     * </ul>
     */
	public TablaDefault(String nombreTabla,  String[] columnNames, ArrayList<T> data, boolean CUActualizar) {
		this.setTitle(nombreTabla);
        this.setLayout(new BorderLayout());
        
        this.editable = CUActualizar;

        if (data != null && !data.isEmpty()) {
        	DefaultTableModel model = new DefaultTableModel(columnNames, data);
        	JTable table = new JTable(model);
	        table.setFont(ViewUtils.FontTablaDefaultCuerpo());
	        
	        //Cambiar apariencia del header de la tabla
	        	//Font
	        JTableHeader header = table.getTableHeader();
	        header.setFont(ViewUtils.FontTablaDefaultCabecera());
	        header.setReorderingAllowed(false);
	        
	        	//Header
	        TableColumnModel cm = table.getColumnModel();
	        
	        Graphics2D temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
	        temp.setFont(ViewUtils.FontTablaDefaultCabecera());
	        
	        FontMetrics metrics = temp.getFontMetrics();
	        for (int i = 0; i < columnNames.length; ++i) {
	            int headerWidth = metrics.stringWidth(columnNames[i]) + 120;
	            cm.getColumn(i).setPreferredWidth(headerWidth);
	        }
	        temp.dispose();

	        this.add(new JScrollPane(table), BorderLayout.CENTER);
	        
	        //Solo se edita la tabla en los CU de actualizar, luego consultar tiene que ser true también
	        if (this.editable) {
	        	//En el modo consultar la tabla tiene una única fila
	        	if (data.size() > 1) throw new IllegalArgumentException(Messages.EXC_EVENTO_TABLA);  
	        	
	        	this.aceptar = new JButton("Aceptar");
	        	this.add(this.aceptar, BorderLayout.SOUTH);
	        	
	        	//Lo inicializo a lo que vale el array de datos en su única posición no nula
	        	this.edicion = data.get(0);
	        	
		        table.getModel().addTableModelListener(new TableModelListener() {
		            @Override
		            public void tableChanged(TableModelEvent e) {
		                if (e.getType() == TableModelEvent.UPDATE) edicion = data.get(0);
		            }
		        });
	        }
	        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        }
      
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
	
	
	public T getEdicion() { 
		if(this.editable) return this.edicion; 
		else throw new IllegalArgumentException(Messages.EXC_EVENTO_TABLA); 
	}
	
	public JButton getOkButton() { 
		if(this.editable) return this.aceptar;
		else throw new IllegalArgumentException(Messages.EXC_EVENTO_TABLA);
	}


  //PRUEBA DE LA TABLA
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	try {
				UIManager.setLookAndFeel(new FlatMacLightLaf());
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ArrayList<TFactura> facturas = new ArrayList<>();
            facturas.add(new TFactura(101, 201, true, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), 120.5f, 100.0f));
            facturas.add(new TFactura(102, 202, true, LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.SECONDS), 95.0f, 80.0f));

            // If you want to test update mode on a single row, set consultar=true, editable=true
            TablaDefault<TFactura> tabla = new TablaDefault<>("Tabla de Facturas", Messages.colNomsFactura, facturas, false);
            tabla.setVisible(true);
        });
    }
	

}
