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
		public Object getValueAt(int rowIndex, int columnIndex) {
			return this.datos.get(rowIndex).getColumnValue(columnIndex);
		}

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

	//Para tablas con edición por defecto (solo la columna 0 no es editable)
	public TablaDefault(String nombreVista, ArrayList<String[]> columnNamesDeCadaTabla, ArrayList<ArrayList<T>> datosDeCadaTabla, boolean CUActualizar) {
		new TablaDefault<T>(nombreVista, columnNamesDeCadaTabla, datosDeCadaTabla, null, CUActualizar);
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
					JTable table = new JTable(model);
					table.setFont(ViewUtils.fontTablaDefaultCuerpo());

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
					tablesPanel.add(scrollPane, "card" + tableInd);
				}
				tableInd++;
			}

			this.add(tablesPanel, BorderLayout.CENTER);

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
			this.aceptar = new JButton("Aceptar");
			this.add(this.aceptar, BorderLayout.SOUTH);
		}

		this.pack();
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public ArrayList<T> getEdiciones() {
		if (this.actualizar) return this.edicionDeCadaTabla;
		else throw new IllegalArgumentException(Messages.ERROR_ACCION_NO_PERMITIDA);
	}

	public JButton getOkButton() {
		if (this.actualizar) return this.aceptar;
		else throw new IllegalArgumentException(Messages.ERROR_ACCION_NO_PERMITIDA);
	}
}
