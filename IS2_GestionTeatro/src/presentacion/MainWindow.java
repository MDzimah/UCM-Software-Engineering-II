package presentacion;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import misc.Constants;
import misc.JSwingUtils;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    private JButton subsFactura;
    private JButton subsCliente;
    private JButton subsPase;
    private JButton subsTaquillero;
    private JButton subsObra;
    private JButton subsCompTea;
    private JButton subsMiemCompTea;

    public MainWindow() {
    	JSwingUtils.setAppIcon(this);
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
                Image image = Constants.logoTeatret();
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
                g.drawImage(Constants.marcoOro(), 0, 0, getWidth(), getHeight(), this);
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
	    button.setFont(new Font("Georgia", Font.BOLD, 30));
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
            	dispose();
            	//Controlador.getInstance().accion(Evento.MAINWINDOW, Evento.SUBS_FACTURA);
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
            	JDialog menuObra= new JDialog(MainWindow.this,"Menú obras", true);
            	
            	JButton actualizar, agregar, borrar, buscar, consultar, mostrar;

            	actualizar = new JButton("Actualizar obra");
        		agregar = new JButton("Agregar obra");
        		borrar = new JButton("Borrar obra");
        		buscar = new JButton("Buscar obras por campos");
        		consultar = new JButton("Consultar obra por id");
        		mostrar = new JButton("Mostrar obras en cartelera");
        		
        		actualizar.addActionListener((ev)->{
        			FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_OBRA_0);
        		});
        		agregar.addActionListener((ev)->{
        			FactoriaAbstractaPresentacion.getInstance().createVista(Evento.CREAR_OBRA);
        		});
        		borrar.addActionListener((ev)->{
        			FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ELIMINAR_OBRA);
        		});		
        		buscar.addActionListener((ev)->{
        			FactoriaAbstractaPresentacion.getInstance().createVista(Evento.BUSCAR_OBRA);
        		});		
        		consultar.addActionListener((ev)->{
        			FactoriaAbstractaPresentacion.getInstance().createVista(Evento.CONSULTAR_OBRA);
        		});		
        		mostrar.addActionListener((ev)->{
        			FactoriaAbstractaPresentacion.getInstance().createVista(Evento.LISTAR_OBRAS);
        		});
        		
        		menuObra.add(actualizar);        		
        		menuObra.add(agregar);
        		menuObra.add(borrar);
        		menuObra.add(buscar);
        		menuObra.add(consultar);
        		menuObra.add(mostrar);
        		menuObra.setVisible(true);
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