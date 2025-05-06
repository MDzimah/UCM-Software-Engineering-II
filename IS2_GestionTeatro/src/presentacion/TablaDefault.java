package presentacion;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import misc.*;


@SuppressWarnings("serial")
public class TablaDefault<T extends Convertable<T>> extends JFrame {
	//Solo para los modos de actualizacion
	private JButton aceptar; 
	private ArrayList<T> edicionDeCadaTabla;
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
            return column != 0 && editable;
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

	public TablaDefault(String nombreVista,  ArrayList<String[]> columnNamesDeCadaTabla, ArrayList<ArrayList<T>> datosDeCadaTabla, boolean CUActualizar) {
		this.setTitle(nombreVista);
        this.setLayout(new BorderLayout());
        ViewUtils.setAppIcon(this);
        
        this.editable = CUActualizar;

        if (datosDeCadaTabla != null && !datosDeCadaTabla.isEmpty()) {
        	this.edicionDeCadaTabla = new ArrayList<T>();
        	JPanel tablesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	
        	int tableInd = 0;
        	for (ArrayList<T> datosTabla : datosDeCadaTabla) {
        		if (datosTabla != null && !datosTabla.isEmpty()) {
		        	DefaultTableModel model = new DefaultTableModel(columnNamesDeCadaTabla.get(tableInd), datosTabla);
		        	JTable table = new JTable(model);
			        table.setFont(ViewUtils.fontTablaDefaultCuerpo());
			        
			        //Cambiar apariencia del header de la tabla
			        	//Font
			        JTableHeader header = table.getTableHeader();
			        header.setFont(ViewUtils.fontTablaDefaultCabecera());
			        header.setReorderingAllowed(false);
			        
			        	//Header
			        TableColumnModel cm = table.getColumnModel();
			        
			        Graphics2D temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
			        temp.setFont(ViewUtils.fontTablaDefaultCabecera());
			        
			        FontMetrics metrics = temp.getFontMetrics();
			        for (int i = 0; i < columnNamesDeCadaTabla.get(tableInd).length; ++i) {
			            int headerWidth = metrics.stringWidth(columnNamesDeCadaTabla.get(tableInd)[i]) + 120;
			            cm.getColumn(i).setPreferredWidth(headerWidth);
			        }
			        temp.dispose();
		
			        tablesPanel.add(new JScrollPane(table));
			        
			        //Solo se edita la tabla en los CU de actualizar, luego consultar tiene que ser true también
			        if (this.editable) {
			        	//En el modo consultar la tabla tiene una única fila
			        	if (datosTabla.size() > 1) throw new IllegalArgumentException(Messages.EXC_EVENTO_TABLA);
			        	
			        	//Lo inicializo a lo que vale el array de datos en su única posición no nula
			        	this.edicionDeCadaTabla.add(datosTabla.get(0));
			        	
				        table.getModel().addTableModelListener(new TableModelListener() {
				            @Override
				            public void tableChanged(TableModelEvent e) {
				                if (e.getType() == TableModelEvent.UPDATE) edicionDeCadaTabla.set(edicionDeCadaTabla.size()-1, datosTabla.get(0));
				            }
				        });
			        }
			        table.setPreferredScrollableViewportSize(table.getPreferredSize());
		        }
        		++tableInd;
        	}
        	 this.add(tablesPanel, BorderLayout.CENTER);
        }
        
        if (this.editable) {
        	this.aceptar = new JButton("Aceptar");
        	this.add(this.aceptar, BorderLayout.SOUTH);
        }
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
	
	public ArrayList<T> getEdicion() { 
		if(this.editable) return this.edicionDeCadaTabla; 
		else throw new IllegalArgumentException(Messages.EXC_EVENTO_TABLA); 
	}
	
	public JButton getOkButton() { 
		if(this.editable) return this.aceptar;
		else throw new IllegalArgumentException(Messages.EXC_EVENTO_TABLA);
	}


  //PRUEBA DE LA TABLA
	
	/*
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
            TablaDefault<TFactura> tabla = new TablaDefault<>("Tabla de Facturas", Messages.colNomsFactura, , false);
            tabla.setVisible(true);
        });
    }
	*/

}
