package presentacion.factoria;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import misc.*;
import negocio.cliente.TCliente;
import negocio.compTea.TCompTea;
import negocio.factura.TFactura;
import negocio.obra.TObra;
import negocio.pase.TPase;
import negocio.taquillero.TTaquillero;
import presentacion.*;
import presentacion.GUIfactura.*;

/* Hay que hacer alguna forma para que la FactoriaPresentación se encargue solamente de crear
vistas y que sea el controlador solamente el que se encargue de pedir que se creen. Si no, estamos
mezcando FactoriaPresentación con el Controlador y eso hace que la arquitectura sea más sucia. Lo de 
create "NonIGUIVistas" tiene que desaparecer completamente y solamente ha de haber createVista con IGUI
*/
public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {

	@Override
	public IGUI createVista(Evento e) {
		switch(e) {
		//Factura
		case ANYADIR_PASE_A_VENTA: return new VistaAddPaseVenta();
		case BUSCAR_FACTURA: return new VistaBuscarFac();
		case CERRAR_VENTA: return new VistaCerrarVenta();
		case MOSTRAR_FACTURAS: return new VistaMostrarFacs();
		case QUITAR_PASE_DE_VENTA: return new VistaQPDeVenta();
		
		
		//Cliente
		
		case ALTA_CLIENTE: return null;
		case BUSCAR_CLIENTE: return null;
		case ELIMINAR_CLIENTE: return null;
		case MOSTRAR_CLIENTE: return null;
		case ACTUALIZAR_CLIENTE:  return null;
		
		//Taquillero
		
		
		//Obra
		
		
		//CompTea
		
		
		//MiemCompTea
		case ACTUALIZAR_MIEMBRO_COMPANIA:
		case BUSCAR_MIEMBRO_COMPANIA:
		case CONTRATAR_MIEMBRO_COMPANIA:
		case DESPEDIR_MIEMBRO_COMPANIA:
		case LISTAR_MIEMBRO_COMPANIA:
		
		
		default: return null;
		
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void createNonIGUIVistas(Evento evento, Object datos) {
		switch(evento) {
		case MAINWINDOW: new MainWindow(); break;
		case FACTURA: //ventana de Jaime
			/*
			 * 
			 * 
			 * 
			 * 
			 * 
			 * */
		case X_CAMPOS_INCORRECTOS: {
			JOptionPane.showMessageDialog(null, 
		    		Messages.ERROR_CAMPOS_INCORRECTOS, 
		    		"Error", 
		    		JOptionPane.ERROR_MESSAGE);
			break;
		}
		case X_BBDD_READ: {
			JOptionPane.showMessageDialog(null,
					(String) datos,
	                "Error Lectura",
	                JOptionPane.ERROR_MESSAGE);
			break;
		}
		case X_BBDD_WRITE:{
			JOptionPane.showMessageDialog(null,
					(String) datos,
	                "Error Escritura",
	                JOptionPane.ERROR_MESSAGE);
			break;
		}
		case MESSAGE_DIALOG: JOptionPane.showMessageDialog(null, (String) datos); break;
		case TABLA_DEFAULT:{
			Object[] aux;
        	if (datos.getClass().isArray()) aux = (Object[]) datos;
        	else aux = ((Collection<?>) datos).toArray();
			
			new TablaDefault((String[])aux[0], (Collection<Object>) aux[1], (String)aux[2]);
			break;
		}
		}
	}

	@SuppressWarnings("serial")
	public class MainWindow extends JFrame implements IGUI {

	    private JButton subsFactura;
	    private JButton subsCliente;
	    private JButton subsPase;
	    private JButton subsTaquillero;
	    private JButton subsObra;
	    private JButton subsCompTea;
	    private JButton subsMiemCompTea;

	    public MainWindow() {
	        subsFactura = new JButton("FACTURA");
	        subsCliente = new JButton("CLIENTE");
	        subsPase = new JButton("PASE");
	        subsTaquillero = new JButton("TAQUILLERO");
	        subsObra = new JButton("OBRA");
	        subsCompTea = new JButton("<html><p style=\"text-align:center;\"> COMPAÑÍA TEATRAL </p></html>");
	        subsMiemCompTea = new JButton("<html><p style=\"text-align:center;\"> MIEMBROS DE LAS COMPAÑÍAS TEATRALES </p></html>");

	        this.estiloBoton(subsFactura);
	        this.estiloBoton(subsCliente);
	        this.estiloBoton(subsPase);
	        this.estiloBoton(subsTaquillero);
	        this.estiloBoton(subsObra);
	        this.estiloBoton(subsCompTea);
	        this.estiloBoton(subsMiemCompTea);

	        this.setLayout(new GridLayout(1, 2));
	        this.add(createPanelTeatro());
	        this.add(createPanelBotones());
	        
	        this.addActionListeners();

	        this.setTitle("TEATRET");
	        SwingUtils.setAppIcon(this);
	        this.setSize(1400, 800);
	        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        this.setVisible(true);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }

	    private JPanel createPanelTeatro() {
	        JPanel panel = new JPanel(new BorderLayout()) {
	            @Override
	            //Para que la imagen quepa en la parte izquierda del panel
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Image image = new ImageIcon("resources/imagenes/teatretLogo.png").getImage();
	                int x = (getWidth() - image.getWidth(null)) / 2;
	                int y = (getHeight() - image.getHeight(null)) / 2;
	                g.drawImage(image, x, y, this);
	            }
	        };

	        return panel;
	    }
	    
	   
	    private JPanel createPanelBotones() {
	    	JPanel panelBotones = new JPanel(new GridBagLayout()) {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Image image = new ImageIcon("resources/imagenes/marcoOro.png").getImage();
	                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	            }
	        };

	        panelBotones.setOpaque(false);
	        
	        GridBagConstraints gbc = new GridBagConstraints();
	        //Peso asignado para distribuirse en el espacio que haya disponible
	        gbc.weightx = 1;
	        gbc.weighty = 1;
	        //Qué hacer cuando el botón ocupe más de su espacio destinado
	        gbc.fill = GridBagConstraints.BOTH;
	        gbc.insets = new Insets(45, 45, 45, 45);
	        
	        JComponent[][] botonesGrid  = {
	            {subsFactura, subsCliente}, 
	            {subsPase, subsTaquillero}, 
	            {subsObra, subsCompTea}
	        };
	        
	        gbc.gridwidth = 1;
	        for (int f = 0; f < botonesGrid.length; ++f) {
	        	for (int c = 0; c < botonesGrid[0].length; ++c) {
	        		gbc.gridx = c;
	        		gbc.gridy = f;
	        		panelBotones.add(botonesGrid[f][c], gbc);
	        	}
	        }
	        
	        //Último botón, gbc.gridWidth = 2 para que ocupe 2 grids
	        gbc.gridx = 0;
	        gbc.gridy = botonesGrid.length + 1;
	        ++gbc.gridwidth;

	        panelBotones.add(subsMiemCompTea, gbc);

	        return panelBotones;
	    }

	    private void estiloBoton(JButton button) {
		    button.setBackground(new Color(160, 0, 0));
		    button.setForeground(new Color(255, 215, 0));
		    button.setFont(new Font("Georgia", Font.BOLD, 35));
		    button.setFocusPainted(false);
		
		    //Para la apariencia 3d del botón. Lo de "lowered" pone el efecto abajo a la derecha
		    Border outerBevel = BorderFactory.createBevelBorder(
		        BevelBorder.LOWERED, 
		        new Color(255, 100, 100),
		        new Color(111, 0, 0)
		    );
		
		    Border innerBevel = BorderFactory.createBevelBorder(
		        BevelBorder.LOWERED, 
		        new Color(200, 50, 50), 
		        new Color(80, 0, 0)
		    );
		
		    Border padding = BorderFactory.createEmptyBorder(18, 26, 18, 26);
		
		    button.setBorder(BorderFactory.createCompoundBorder(
		        outerBevel,
		        BorderFactory.createCompoundBorder(innerBevel, padding)
		    ));
		
		    //Efecto al entrar/salir del botón
		    button.addMouseListener(new MouseAdapter() {
		        public void mouseEntered(MouseEvent e) {
		            button.setBackground(new Color(95, 0, 0));
		            //Para que el cursor se convierta en la "mano" en lugar del puntero
		            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		        }
		        
		        public void mouseExited(MouseEvent e) {
		        	button.setBackground(new Color(160, 0, 0));
		        }
		    });
		}


		private void addActionListeners() {
	        subsFactura.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) { 
	            	FactoriaAbstractaPresentacion.getInstance().createNonIGUIVistas(Evento.FACTURA, null);
	            }
	        });

	        subsCliente.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                
	            }
	        });

	        subsPase.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               
	            }
	        });

	        subsTaquillero.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                
	            }
	        });

	        subsObra.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	           
	            }
	        });

	        subsCompTea.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                
	            }
	        });

	        subsMiemCompTea.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                
	            }
	        });
	    }
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
	        this.setVisible(true);
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
}	
