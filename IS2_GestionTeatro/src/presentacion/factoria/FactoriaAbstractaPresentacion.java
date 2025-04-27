package presentacion.factoria;

import java.util.Collection;

import javax.swing.JOptionPane;
import presentacion.IGUI;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import misc.Constants;
import misc.Evento;
import negocio.cliente.TCliente;
import negocio.compTea.TCompTea;
import negocio.factura.TFactura;
import negocio.obra.TObra;
import negocio.pase.TPase;
import negocio.taquillero.TTaquillero;

public abstract class FactoriaAbstractaPresentacion {
	private static FactoriaAbstractaPresentacion instancia = null;
		
	public static FactoriaAbstractaPresentacion getInstance() {
		if (instancia == null) instancia = new FactoriaPresentacion();
		return instancia;
	}
	
	public abstract IGUI createVista(Evento evento);

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
	public void createTabla(String tituloTabla, String[] nomCols, Collection<Object> datos, boolean consultar) {
		new TablaDefault(tituloTabla, nomCols, datos, consultar).setVisible(true);
	}
	
	//Diálogos
	public void createErrorDialogMessage(String message) {
		JOptionPane.showMessageDialog(null,
				message,
	            "Error",
	            JOptionPane.ERROR_MESSAGE);
	}
	
	public void createDialogMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@SuppressWarnings("serial")
	private class TablaDefault extends JFrame {

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

		public TablaDefault(String nombreTabla,  String[] columnNames, Collection<Object> data, boolean consultar) {
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
	        
	        DefaultTableModel model = new DefaultTableModel(columnNames, this.convert(data, columnNames.length));
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
            FactoriaAbstractaPresentacion.getInstance().createTabla("Tabla de Facturas", columnNames, facturas, true);

        });
    }
    //*/
}


