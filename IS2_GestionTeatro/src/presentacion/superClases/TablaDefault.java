package presentacion.superClases;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
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
    	
    	//Para una apariencia más vistosa de la tabla
    	public MultiLineTableCellRenderer() {
    	    setOpaque(true);
    	    setFont(Constants.FontTablaDefaultCuerpo());
    	    setFixedCellHeight(-1); //Ajuste dinámico del tamaño de las células
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
    
    //Se asume una colección no vacía
    private boolean containsType(Collection<?> data, Class<?> clazz) {
        return clazz.isInstance(data.iterator().next());
    }
    
    //Convierte colección de TFacturas, TClientes, etc. a una matriz de información para la tabla
    private List<Object[]> convert(Collection<Object> data, int numCols){
    	List<Object[]> matInfo = new ArrayList<Object[]>();
    	if (data.isEmpty()) return matInfo;
    	else {
    		//Vemos de que tipo es la colección de objetos
    		if (this.containsType(data, TFactura.class)) {
    			for (TFactura tFac : data.toArray(new TFactura[0])) {
    				Object[] fila = new Object[numCols];
    				
    				fila[0] = tFac.getIdFactura();
    				fila[1] = tFac.getIdCliente();
    				fila[2] = tFac.getIdTaquillero();
    				fila[3] = tFac.getFecha();
    				fila[4] = tFac.getCarrito();
    				fila[5] = tFac.getImporte();
    				
    				matInfo.add(fila);
    			}
    		}
    		else if (this.containsType(data, TCliente.class)) {
    			
    		}
    		/* Luis tendrá método "mostrar clientes VIP/Normales"???
			else if (aux[0] instanceof TClienteVIP) {
			    			
			    		}
			else if (aux[0] instanceof TClienteNormal) {
				
			}
			*/
			else if (this.containsType(data, TPase.class)) {
			    			
			    		}
			else if (this.containsType(data, TTaquillero.class)) {
				
			}
			else if (this.containsType(data, TObra.class)) {
				
			}
			else if (this.containsType(data, TCompTea.class)) {
				
			}
			else { //Instancia de TMiemCompTea
				
			}
    		
    		return matInfo;
    	}
    }

    /**
     * Constructs a new {@code TablaDefault} window displaying a dynamically sized table with custom rendering.
     * <p>
     * This table is designed to display various business objects (e.g., {@code TFactura}, {@code TCliente}, etc.)
     * by converting them into rows of data. It uses a custom {@code AbstractTableModel} for data binding and a
     * specialized {@code TableCellRenderer} that supports multiline rendering of {@code Collection} or array elements
     * within individual cells.
     * </p>
     *
     * <p>
     * Column headers are automatically converted to uppercase and styled with a custom font. Cells are rendered
     * with padding, custom font, and support for vertical resizing based on content height. If the cell contains
     * a list or array, its elements are displayed on separate lines for clarity.
     * </p>
     *
     * @param columnNames an array of {@code String} representing the column headers; headers are converted to uppercase
     * @param data a {@code Collection<Object>} containing entities to be rendered, such as {@code TFactura}, {@code TCliente}, etc.
     * @param nombreTabla the title of the window displayed in the frame’s title bar
     */
    public TablaDefault(String[] columnNames, Collection<Object> data, String nombreTabla) {
        this.setTitle(nombreTabla);
        this.setLayout(new BorderLayout());
        Dimension sd = Constants.screenDimension();
        int width = sd.width - sd.width / 10;
        int height = sd.height - sd.height / 12;
        this.setSize(new Dimension(width, height));
        
        for (int i = 0; i < columnNames.length; ++i) {
        	columnNames[i] = columnNames[i].toUpperCase();
        }
        
        DefaultTableModel model = new DefaultTableModel(columnNames, this.convert(data, columnNames.length));
        JTable table = new JTable(model);
        
        //Cambiar apariencia del header de la tabla
        JTableHeader header = table.getTableHeader();
        header.setFont(Constants.FontTablaDefaultCabecera());

        //Las celdas de la tabla pueden contener listas, luego para mostrar los elementos uno debajo del otro, hace falta
        //cambiar la forma de renderizarlas
        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer(); 
        table.setDefaultRenderer(String[].class, renderer);
        for (int i = 0; i < columnNames.length; ++i) {
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

        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    /* PRUEBA DE LA TABLA
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] columnNames = {
                "ID Factura", "ID Cliente", "ID Taquillero", "Fecha", "Carrito", "Importe"
            };

            Collection<Object> facturas = new ArrayList<>();

            for (int i = 1; i <= 3; i++) {
                Collection<TLineaFactura> carrito = new ArrayList<>();
                carrito.add(new TLineaFactura(100 + i, 2));
                carrito.add(new TLineaFactura(200 + i, 1));
                carrito.add(new TLineaFactura(300 + i, 20));
                for (TLineaFactura lf : carrito) {
                    lf.setPrecioVenta(9.99f + i);
                }

                TFactura factura = new TFactura(
                        1000 + i, 2000 + i, true,
                        LocalDateTime.now().minusDays(i),
                        carrito,
                        50.0f + i,
                        60.0f + i
                );
                factura.setIdFactura(i);
                factura.setIdTaquillero(3000 + i);

                facturas.add(factura);
            }

            new TablaDefault(columnNames, facturas, "Tabla de Facturas").setVisible(true);
        });
    }
    */
}
