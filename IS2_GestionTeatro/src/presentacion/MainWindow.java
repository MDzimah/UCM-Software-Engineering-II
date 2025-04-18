package presentacion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import eventos.Evento;
import misc.Constants;
import presentacion.controlador.Controlador;
import presentacion.superClases.VistaDefault;

@SuppressWarnings("serial")
public class MainWindow extends VistaDefault {
	
	private JButton subsFactura;
	private JButton subsCliente;
	private JButton subsPase;
	private JButton subsTaquillero;
	private JButton subsObra;
	private JButton subsCompTea;
	private JButton subsMiemCompTea;
	
	
	public MainWindow() {
		subsFactura = new JButton("Factura");
        subsCliente = new JButton("Cliente");
        subsPase = new JButton("Pase");
        subsTaquillero = new JButton("Taquillero");
        subsObra = new JButton("Obra");
        subsCompTea = new JButton("Compañía teatral");
        subsMiemCompTea = new JButton("Miembros de las compañías teatrales");
        addActionListeners();
        
        this.setLayout(new GridLayout(1,2));
        this.add(new JLabel(new ImageIcon("resources/imagenes/theaterMainWindow.png")));
      
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4,2));
        buttonsPanel.add(subsFactura);
        buttonsPanel.add(subsCliente);
        buttonsPanel.add(subsPase);
        buttonsPanel.add(subsTaquillero);
        buttonsPanel.add(subsObra);
        buttonsPanel.add(subsCompTea);
        buttonsPanel.add(subsMiemCompTea);
        this.add(buttonsPanel);
		
        this.setSize(Constants.getScaledScreenDimension(2, 2));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void addActionListeners() {
        subsFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Controlador.getInstance().accion(null, Evento.FACTURA);
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
