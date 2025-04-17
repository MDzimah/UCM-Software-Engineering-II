package presentacion;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import eventos.Evento;
import misc.Pair;
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
        
		ArrayList<Pair<JComponent, JComponent>> buttonPairs = new ArrayList<>();
		buttonPairs.add(new Pair<>(subsFactura, subsCliente));
		buttonPairs.add(new Pair<>(subsPase, subsTaquillero));
		buttonPairs.add(new Pair<>(subsObra, subsCompTea));
		buttonPairs.add(new Pair<>(subsMiemCompTea, null));
		JPanel mainPanel = super.initComps(buttonPairs, null, null, true);
		mainPanel.add(new JLabel(new ImageIcon("resources/theaterMainWindow.png")), BorderLayout.NORTH);
		this.setVisible(true);		
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
