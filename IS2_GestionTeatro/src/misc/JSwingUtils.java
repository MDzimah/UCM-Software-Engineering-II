package misc;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import negocio.cliente.TCliente;
import negocio.compTea.TCompTea;
import negocio.factura.TFactura;
import negocio.miemCompTea.TMiemCompTea;
import negocio.obra.TObra;
import negocio.pase.TPase;
import negocio.taquillero.TTaquillero;

public class JSwingUtils {
	
	 public static void setAppIcon(JFrame window) {
		 window.setIconImage(Constants.logoTeatret());
	 } 
	
	/**
	 * Creates a horizontally aligned {@code JPanel} containing two {@code JComponent}s with adjustable spacing between them.
	 * <p>
	 * The panel uses a horizontal {@code BoxLayout} and includes a fixed horizontal strut for consistent spacing between the
	 * two components. The entire panel is center-aligned and includes vertical padding, making it ideal for use in vertically
	 * stacked layouts such as {@code BoxLayout.Y_AXIS}, where each row should be centered and evenly spaced. The spacing
	 * between the components is only applied when both components are present.
	 * </p>
	 *
	 * @param component1 the first {@code JComponent} to be added (e.g., {@code JLabel}, {@code JTextField}, etc.)
	 * @param component2 the second {@code JComponent} to be added (e.g., {@code JTextField}, {@code JButton}, etc.)
	 * @return a {@code JPanel} containing the two components arranged horizontally with adjustable spacing and vertical padding.
	 *         If either component is {@code null}, only the non-null component will be added, with spacing adjusted accordingly.
	 */
	public static JPanel createComponentPair(JComponent component1, JComponent component2) {
	    JPanel pair = new JPanel();
	    pair.setLayout(new BoxLayout(pair, BoxLayout.X_AXIS));
	    pair.setAlignmentX(Component.CENTER_ALIGNMENT);
	    pair.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

	    if (component1 != null) pair.add(component1);
	  	if (component1 != null && component2 != null) pair.add(Box.createHorizontalStrut(30));
	  	if (component2 != null) pair.add(component2);
	    
	    return pair;
	}
	
	/**
	 * Creates a JPanel containing two JButton components arranged horizontally at the center. Only the non-null are added.
	 *  
	 * @param button1 the first JButton to be added to the panel
	 * @param button2 the second JButton to be added to the panel
	 * @return JPanel containing the two buttons arranged in the center
	 */
	public static JPanel createResponsePair(JButton button1, JButton button2) {
        JPanel pair = new JPanel();
        pair.setLayout(new FlowLayout(FlowLayout.CENTER));
        if (button1 != null) pair.add(button1);
        if (button2 != null) pair.add(button2);
        return pair;
    }
	
	//Tabla
   /**
     * Creates and displays a table window populated with business entity data.
     * <p>
     * The table dynamically renders collections or arrays with a multi-line layout
     * inside cells, automatically adjusting row heights for better readability.
     * It supports dynamic types like {@link TFactura}, {@link TCliente},
     * {@link TPase}, {@link TTaquillero}, {@link TObra}, {@link TCompTea}, etc.
     * </p>
     *
     * @param tituloTabla the title of the window and the table view
     * @param nomCols an array of column names (headers) to be displayed
     * @param datos a collection of business objects to be shown in the table
     * @param consultar if {@code true}, the table will open in a compact size suitable for consulting
     */
	public static JFrame createTabla(String tituloTabla, String[] nomCols, Collection<Object> datos, boolean consultar, boolean edicion) {
		JFrame jframe = new TablaDefault(tituloTabla, nomCols, datos, consultar, edicion);
		jframe.setVisible(true);
		return jframe;
	}
	
	//Diálogos
	public static void createErrorDialogMessage(String message) {
		JOptionPane.showMessageDialog(null,
				message,
	            "Error",
	            JOptionPane.ERROR_MESSAGE);
	}
	
	public static void createDialogMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@SuppressWarnings("serial")
	private static class TablaDefault extends JFrame {

	    private class DefaultTableModel extends AbstractTableModel {
	        private final String[] columnNames;
	        private final List<Object[]> datos;
	        private final boolean editable;

	        public DefaultTableModel(String[] nomCols, List<Object[]> datos, boolean editable) {
	            this.columnNames = nomCols;
	            this.datos = datos;
				this.editable = editable;
	        }

	        @Override
	        public int getRowCount() { return datos.size(); }

	        @Override
	        public int getColumnCount() { return columnNames.length; }

	        @Override
	        public Object getValueAt(int rowIndex, int columnIndex) { return datos.get(rowIndex)[columnIndex]; }

	        @Override
	        public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }
	        
	        @Override
            public boolean isCellEditable(int row, int column) {
                return editable;
            }
	    }

	    //MultiLineTableCellRenderer inspirado por Channa Jayamuni en Stack Overflow
	    //https://stackoverflow.com/questions/9955595/how-to-display-multiple-lines-in-a-jtable-cell
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
	    
	    //Se asume una colección no vacía
	    private boolean containsType(Collection<?> data, Class<?> clase) {
	        return clase.isInstance(data.iterator().next());
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
	    				fila[4] = tFac.getImporte();
	    				fila[5] = tFac.getSubtotal();
	    				
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
					for (TMiemCompTea tMiemComp : data.toArray(new TMiemCompTea[0])) {
	    				Object[] fila = new Object[numCols];
	    				
	    				fila[0] = tMiemComp.getIdMiembComp();
	    				fila[1] = tMiemComp.getNombre();
	    				fila[2] = tMiemComp.getApellido();
	    				fila[3] = tMiemComp.getEdad();
	    				fila[4] = tMiemComp.getDNI();
	    				fila[5] = tMiemComp.getEmail();
	    				fila[6] = tMiemComp.getGenero().toString();
	    				
	    				matInfo.add(fila);
	    			}
				}
				else { //Instancia de TMiemCompTea
					
				}
	    		
	    		return matInfo;
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

		public TablaDefault(String nombreTabla,  String[] columnNames, Collection<Object> data, boolean consultar, boolean edicion) {
	        this.setTitle(nombreTabla);
	        this.setLayout(new BorderLayout());
	        
	        Dimension sd = Constants.screenDimension();
	        int width, height;
	        if (consultar) {
	        	width = sd.width/2;
	        	height = sd.height/10;
	        }
	        else {	     
		        width = sd.width - sd.width / 10;
		        height = sd.height - sd.height / 12;   
	        }
	        this.setSize(new Dimension(width, height));
	        
	        for (int i = 0; i < columnNames.length; ++i) {
	        	columnNames[i] = columnNames[i].toUpperCase();
	        }
	        
	        DefaultTableModel model = new DefaultTableModel(columnNames, this.convert(data, columnNames.length), edicion);
	        JTable table = new JTable(model);
	        
	        //Cambiar apariencia del header de la tabla
	        JTableHeader header = table.getTableHeader();
	        header.setFont(Constants.FontTablaDefaultCabecera());

	        
	        this.setRender(table, columnNames.length);
	        this.add(new JScrollPane(table), BorderLayout.CENTER);
	        this.setLocationRelativeTo(null);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setVisible(true);
	    }
	}

	
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
            JSwingUtils.createTabla("Tabla de Facturas", columnNames, facturas, true);
        });
    }
    //*/
}
