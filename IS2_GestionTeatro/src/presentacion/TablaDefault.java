package presentacion;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import misc.*;

@SuppressWarnings("serial")
public class TablaDefault<T extends Convertable<T>> extends JFrame {
	private JTable table;
	private JButton aceptar;
	private ArrayList<T> edicionDeCadaTabla;
	private boolean actualizar;

	private int currentCardIndex = 0;

	private class DefaultTableModel extends AbstractTableModel {
		private final HashMap<Integer, Boolean> editableCols;
		private final String[] nomCols;
		private final ArrayList<T> datos;

		public DefaultTableModel(String[] nomCols, ArrayList<T> datos, HashMap<Integer, Boolean> editableCols) {
			this.nomCols = nomCols;
			this.datos = datos;
			this.editableCols = editableCols;
		}

		@Override
		public int getRowCount() { return this.datos.size(); }

		@Override
		public int getColumnCount() { return nomCols.length; }

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) { return this.datos.get(rowIndex).getColumnValue(columnIndex); }

		@Override
		public String getColumnName(int columnIndex) { return nomCols[columnIndex]; }

		@Override
		public void setValueAt(Object value, int row, int col) {
			try {
				this.datos.get(row).setColumnValue(col, (String) value);
			} catch (Exception e) {
				ViewUtils.createErrorDialogMessage(Messages.ERROR_EDICION_TABLA);
			}
			fireTableCellUpdated(row, col);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			if (this.editableCols == null) return column != 0 && actualizar;
			else {
				if (this.editableCols.containsKey(column)) return this.editableCols.get(column) && actualizar;
				else return actualizar;
			}
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
	//Para tablas con edición por defecto (solo la columna 0 no es editable)
	public TablaDefault(String nombreVista, ArrayList<String[]> columnNamesDeCadaTabla, ArrayList<ArrayList<T>> datosDeCadaTabla, boolean CUActualizar) {
		this(nombreVista, columnNamesDeCadaTabla, datosDeCadaTabla, null, CUActualizar);
	}

	public TablaDefault(String nombreVista, ArrayList<String[]> columnNamesDeCadaTabla, ArrayList<ArrayList<T>> datosDeCadaTabla,
						ArrayList<HashMap<Integer, Boolean>> editableColsDeCadaTabla, boolean CUActualizar) {
		this.setTitle(nombreVista);
		this.setLayout(new BorderLayout());
		ViewUtils.setAppIcon(this);

		if (columnNamesDeCadaTabla.size() != datosDeCadaTabla.size()) {
			throw new IllegalArgumentException(Messages.ERROR_MISMATCH_COLUMNAS_DATOS);
		}
		if (editableColsDeCadaTabla != null && editableColsDeCadaTabla.size() != datosDeCadaTabla.size()) {
			throw new IllegalArgumentException(Messages.ERROR_MISMATCH_EDITABLES_DATOS);
		}

		this.actualizar = CUActualizar;

		if (datosDeCadaTabla != null && !datosDeCadaTabla.isEmpty()) {
			this.edicionDeCadaTabla = new ArrayList<>();

			CardLayout cardLayout = new CardLayout();
			JPanel tablesPanel = new JPanel(cardLayout);

			int tableInd = 0;
			for (ArrayList<T> datosTabla : datosDeCadaTabla) {
				if (datosTabla != null && !datosTabla.isEmpty()) {

					DefaultTableModel model = new DefaultTableModel(columnNamesDeCadaTabla.get(tableInd), datosTabla,
							editableColsDeCadaTabla == null ? null : editableColsDeCadaTabla.get(tableInd));
					table = new JTable(model);
					table.setFont(ViewUtils.fontTablaDefaultCuerpo());
					table.setIntercellSpacing(new Dimension(10,0));

					JTableHeader header = table.getTableHeader();
					header.setFont(ViewUtils.fontTablaDefaultCabecera());
					header.setReorderingAllowed(false);

					TableColumnModel cm = table.getColumnModel();
					Graphics2D temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
					temp.setFont(ViewUtils.fontTablaDefaultCabecera());

					FontMetrics metrics = temp.getFontMetrics();
					for (int i = 0; i < columnNamesDeCadaTabla.get(tableInd).length; ++i) {
						int headerWidth = metrics.stringWidth(columnNamesDeCadaTabla.get(tableInd)[i]) + 120;
						cm.getColumn(i).setPreferredWidth(headerWidth);
					}
					temp.dispose();

					if (actualizar) {
						if (datosTabla.size() > 1) {
							throw new IllegalArgumentException(Messages.ERROR_SOLO_UNA_FILA_PARA_EDICION);
						}
						this.edicionDeCadaTabla.add(datosTabla.get(0));

						table.getModel().addTableModelListener(e -> {
							if (e.getType() == TableModelEvent.UPDATE) {
								edicionDeCadaTabla.set(edicionDeCadaTabla.size() - 1, datosTabla.get(0));
							}
						});
					}

					JScrollPane scrollPane = new JScrollPane(table);
					Dimension preferredSize = table.getPreferredSize();
					preferredSize.height += table.getTableHeader().getPreferredSize().height + 20;
					table.setPreferredScrollableViewportSize(preferredSize);
					scrollPane.setPreferredSize(preferredSize);
					tablesPanel.add(scrollPane, "card" + tableInd);
				}
				tableInd++;
			}
			
			this.add(tablesPanel, BorderLayout.CENTER);
			cardLayout.show(tablesPanel, "card0");

			if (datosDeCadaTabla.size() > 1) {
				JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				JButton prev = new JButton("Anterior");
				JButton next = new JButton("Siguiente");

				prev.addActionListener(e -> {
					if (currentCardIndex > 0) {
						currentCardIndex--;
						cardLayout.show(tablesPanel, "card" + currentCardIndex);
					}
				});

				next.addActionListener(e -> {
					if (currentCardIndex < datosDeCadaTabla.size() - 1) {
						currentCardIndex++;
						cardLayout.show(tablesPanel, "card" + currentCardIndex);
					}
				});

				navPanel.add(prev);
				navPanel.add(next);
				this.add(navPanel, BorderLayout.NORTH);
			}
		} else {
			ViewUtils.createErrorDialogMessage(Messages.ERROR_DATOS_INVALIDOS);
		}

		if (actualizar) {
			JPanel res = new JPanel(new FlowLayout(FlowLayout.CENTER));
			this.aceptar = new JButton("Aceptar");
			res.setBorder(BorderFactory.createEmptyBorder(10, -1, 10, -1));
			res.add(this.aceptar);
			this.add(res, BorderLayout.SOUTH);
		}

		this.pack();
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public ArrayList<T> getEdiciones() {
		if (this.actualizar) {
			//Similar al commitEdit de un JSpinner
			if (table.isEditing()) {
	            table.getCellEditor().stopCellEditing();
	        }
			return this.edicionDeCadaTabla;
		}
		else throw new IllegalArgumentException(Messages.ERROR_ACCION_NO_PERMITIDA);
	}

	public JButton getOkButton() {
		if (this.actualizar) return this.aceptar;
		else throw new IllegalArgumentException(Messages.ERROR_ACCION_NO_PERMITIDA);
	}
}
