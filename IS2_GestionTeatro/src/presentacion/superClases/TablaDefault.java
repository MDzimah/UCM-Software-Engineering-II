package presentacion.superClases;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import misc.Constants;
import negocio.cliente.TCliente;
import negocio.compTea.TCompTea;
import negocio.factura.TFactura;
import negocio.obra.TObra;
import negocio.pase.TPase;
import negocio.taquillero.TTaquillero;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
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

    //MultiLineTableCellRenderer inspirado por Channa Jayamuni en Stack Overflow
    //https://stackoverflow.com/questions/9955595/how-to-display-multiple-lines-in-a-jtable-cell
    private class MultiLineTableCellRenderer extends JList<String> implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
        	//Solo se va a hacer un renderizado multilínea si el valor del objeto es una Collection o un Array
        	//En caso contrario renderizamos la casilla con el toString() del Objeto
            if (value == null) this.setListData(new String[] {""});
            else {
                if (value instanceof Collection) {
                	Collection<?> list = (Collection<?>) value;
                    this.setListData(list.toArray(new String[0]));
                } 
                else if (value.getClass().isArray()) this.setListData((String[]) value);
                else setListData(new String[] { value.toString() });
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
    
    //Para construir la matriz de información a partir de un vector de TFacturas, TClientes, etc.
    private List<Object[]> convert(Collection<Object> data, int numCols){
    	List<Object[]> matInfo = new ArrayList<Object[]>();
    	if (data.isEmpty()) return matInfo;
    	else {
    		
    		Object[] aux = data.toArray();
    		
    		//Vemos de que tipo es la colección de objetos
    		if (aux[0] instanceof TFactura) {
    			for (TFactura tFac : (TFactura[])aux) {
    				Object[] fila = new TFactura[numCols];
    				
    				fila[0] = tFac.getIdFactura();
    				fila[1] = tFac.getIdCliente();
    				fila[2] = tFac.getIdTaquillero();
    				fila[3] = tFac.getFecha();
    				fila[4] = tFac.getCarrito();
    				fila[5] = tFac.getImporte();
    				
    				matInfo.add(fila);
    			}
    		}
    		else if (aux[0] instanceof TCliente) {
    			
    		}
    		/* Luis tendrá método "mostrar clientes VIP/Normales"???
			else if (aux[0] instanceof TClienteVIP) {
			    			
			    		}
			else if (aux[0] instanceof TClienteNormal) {
				
			}
			*/
			else if (aux[0] instanceof TPase) {
			    			
			    		}
			else if (aux[0] instanceof TTaquillero) {
				
			}
			else if (aux[0] instanceof TObra) {
				
			}
			else if (aux[0] instanceof TCompTea) {
				
			}
			else { //Instancia de TMiemCompTea
				
			}
    		
    		return matInfo;
    	}
    }

    /**
     * Constructs a new {@code TablaDefault} frame displaying a table with the specified column names and data.
     * <p>
     * The table is created using a custom model based on {@code AbstractTableModel}, and it is displayed
     * within a scrollable pane. The frame is automatically sized relative to the screen dimensions, with a margin.
     * The constructor also configures a custom {@code TableCellRenderer} that supports multiline display for cells
     * containing {@code Collection} instances or arrays, rendering each element on a separate line.
     * </p>
     * <p>
     * The {@code data} collection is internally converted into a list of {@code Object[]} rows using the
     * {@code convert} method, which checks the type of elements and extracts relevant fields to populate the table.
     * </p>
     *
     * @param columnNames an array of {@code String} representing the column headers of the table
     * @param data a {@code Collection<Object>} containing business objects such as {@code TFactura}, {@code TCliente}, etc.
     *             which will be processed and displayed in the table
     * @param nombreTabla the title to be displayed in the window frame
     */
    public TablaDefault(String[] columnNames, Collection<Object> data, String nombreTabla) {
        this.setTitle(nombreTabla);
        this.setLayout(new BorderLayout());
        Dimension sd = Constants.screenDimension();
        int width = sd.width - sd.width / 10;
        int height = sd.height - sd.height / 12;
        this.setSize(new Dimension(width, height));
        
        
        DefaultTableModel model = new DefaultTableModel(columnNames, this.convert(data, columnNames.length));
        JTable table = new JTable(model);

        //Las celdas de la tabla pueden contener listas, luego para mostrar los elementos uno debajo del otro, hace falta
        //cambiar la forma de renderizarlas
        for (int colIndex = 0; colIndex < columnNames.length; ++colIndex) {
            table.getColumnModel().getColumn(colIndex).setCellRenderer(new MultiLineTableCellRenderer());
        }

        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
