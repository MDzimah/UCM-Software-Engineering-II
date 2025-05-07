package presentacion.GUICliente;

import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;

import exceptions.InvalidFields;
import misc.Pair;
import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;
import negocio.cliente.TipoCliente;
import negocio.cliente.VIPEnum;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaAltaCl extends VistaDefault implements IGUI {
	
	private JTextField DNI, nombre, apellido, cuentaBancaria;
	private JLabel DNIl, nombrel, apellidol, cuentaBancarial, tipol;
	private JSpinner tipo;
	private JButton alta, cancelar;
	
	public VistaAltaCl() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.setTitle("Dar de alta cliente");
		alta = new JButton("Alta");
		cancelar = new JButton("Cancelar");
		DNIl = new JLabel("DNI"); 
		nombrel = new JLabel("Nombre"); 
		apellidol = new JLabel("Apellido"); 
		cuentaBancarial = new JLabel("Cuenta Bancaria");
		tipol = new JLabel("Tipo de cliente");
		DNI = new JTextField(20); 
		nombre = new JTextField(20); 
		apellido = new JTextField(20); 
		cuentaBancaria = new JTextField(20);
		tipo = new JSpinner(new SpinnerListModel(valuesTipoCliente()));
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(DNIl, DNI));
		campos.add(new Pair<>(nombrel, nombre));
		campos.add(new Pair<>(apellidol, apellido));
		campos.add(new Pair<>(cuentaBancarial, cuentaBancaria));
		campos.add(new Pair<>(tipol, tipo));
		
		super.initComps(campos, alta, cancelar);
		
		alta.addActionListener(e -> {
			
		});
		
		cancelar.addActionListener(e -> {
			VistaAltaCl.this.dispose();
		});
		
		tipo.addChangeListener(e -> {
			String s = tipo.getValue().toString();
			if (s == "VIP") {
				camposVIP();
			}
			else if (s == "Normal") {
				camposNormal();
			}
		});
		
	}

	private String[] valuesTipoCliente() {
		String[] lista = new String[TipoCliente.values().length +1];
		lista[0] = "---";
		TipoCliente[] tipos = TipoCliente.values();
		String[] tiposstring = new String[tipos.length];
		for (int i = 0;i<tipos.length;++i) {
			tiposstring[i] = tipos[i].toString();
		}
		System.arraycopy(tiposstring, 0, lista, 1, TipoCliente.values().length);
		return lista;
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage("Se ha añadido correctamente el cliente: " + (int)datos);
		}
		else if (evento == Evento.RES_KO) {
			ViewUtils.createDialogMessage("No se ha podido añadir el cliente.\nError: " +((Exception) datos).getMessage());
		}
		
	}
	
	private void camposVIP() {
		JFormattedTextField costeMensual = new JFormattedTextField(NumberFormat.getIntegerInstance());
		JSpinner nivelVIP = new JSpinner(new SpinnerListModel(VIPEnum.values()));
		
		JLabel costeMensuall = new JLabel("Coste Mensual");
		JLabel nivelVIPl = new JLabel("Nivel VIP");
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(DNIl, DNI));
		campos.add(new Pair<>(nombrel, nombre));
		campos.add(new Pair<>(apellidol, apellido));
		campos.add(new Pair<>(cuentaBancarial, cuentaBancaria));
		campos.add(new Pair<>(tipol, tipo));
		campos.add(new Pair<>(costeMensuall, costeMensual));
		campos.add(new Pair<>(nivelVIPl, nivelVIP));
		super.initComps(campos, cancelar, alta);
		super.revalidate();
		super.repaint();
		
		alta.addActionListener(e -> {
			for (ActionListener al : alta.getActionListeners()) {
			    alta.removeActionListener(al);
			}
			try {
				String dni = DNI.getText(), nom = nombre.getText(), ap = apellido.getText(), cuenta = cuentaBancaria.getText(), nivel = nivelVIP.getValue().toString();
				Long cost = (Long) costeMensual.getValue();
				float coste = cost.floatValue();
				TClienteVIP tCliente = new TClienteVIP(-1,dni,nom,ap,true,cuenta,VIPEnum.valueOf(nivel),coste);
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ALTA_CLIENTE, tCliente);});
			}
			catch (Exception ex) {
				this.actualizar(Evento.RES_KO, new InvalidFields());
			}
			
			finally {
				VistaAltaCl.this.dispose();
			}
		});
		
	}
	
	private void camposNormal() {
		JTextField puntosAcum = new JTextField(20);
		JLabel puntosAcuml = new JLabel("Puntos acumulados");
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(DNIl, DNI));
		campos.add(new Pair<>(nombrel, nombre));
		campos.add(new Pair<>(apellidol, apellido));
		campos.add(new Pair<>(cuentaBancarial, cuentaBancaria));
		campos.add(new Pair<>(tipol, tipo));
		campos.add(new Pair<>(puntosAcuml, puntosAcum));
		super.initComps(campos, cancelar, alta);
		super.revalidate();
		super.repaint();
		
		alta.addActionListener(e -> {
			for (ActionListener al : alta.getActionListeners()) {
			    alta.removeActionListener(al);
			}
			try {
				String dni = DNI.getText(), nom = nombre.getText(), ap = apellido.getText(), cuenta = cuentaBancaria.getText();
				int ptos = Integer.parseInt(puntosAcum.getText());
				TClienteNormal tCliente = new TClienteNormal(-1,dni,nom,ap,true,cuenta, ptos);
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ALTA_CLIENTE, tCliente);});
			}
			catch (Exception ex) {
				this.actualizar(Evento.RES_KO, new InvalidFields());
			}
			
			finally {
				VistaAltaCl.this.dispose();
			}
		});
	}

}
