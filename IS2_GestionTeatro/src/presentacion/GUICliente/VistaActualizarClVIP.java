package presentacion.GUICliente;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;

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

public class VistaActualizarClVIP extends VistaDefault implements IGUI {
	private JTextField DNI, nombre, apellido, cuentaBancaria;
	private JFormattedTextField costeMensual;
	private JLabel DNIl, nombrel, apellidol, cuentaBancarial, nivelVIPl, costeMensuall;
	private JSpinner nivelVIP;
	private JButton alta, cancelar;
	private int id;
	
	public VistaActualizarClVIP() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.setTitle("Actualizar Cliente");
		alta = new JButton("Actualizar");
		cancelar = new JButton("Cancelar");
		DNIl = new JLabel("DNI"); 
		nombrel = new JLabel("Nombre"); 
		apellidol = new JLabel("Apellido"); 
		cuentaBancarial = new JLabel("Cuenta Bancaria");
		DNI = new JTextField(20); 
		nombre = new JTextField(20); 
		apellido = new JTextField(20); 
		cuentaBancaria = new JTextField(20);
		costeMensual = new JFormattedTextField(NumberFormat.getIntegerInstance());
		nivelVIP = new JSpinner(new SpinnerListModel(VIPEnum.values()));
		
		costeMensuall = new JLabel("Coste Mensual");
		nivelVIPl = new JLabel("Nivel VIP");
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(DNIl, DNI));
		campos.add(new Pair<>(nombrel, nombre));
		campos.add(new Pair<>(apellidol, apellido));
		campos.add(new Pair<>(cuentaBancarial, cuentaBancaria));
		campos.add(new Pair<>(costeMensuall, costeMensual));
		campos.add(new Pair<>(nivelVIPl, nivelVIP));
		super.initComps(campos, cancelar, alta);
		
		super.initComps(campos, alta, cancelar);
		
		alta.addActionListener(e -> {
			try {
				String dni = DNI.getText(), nom = nombre.getText(), ap = apellido.getText(), cuenta = cuentaBancaria.getText(), nivel = nivelVIP.getValue().toString();
				float coste = 0;
				try {
					Long cost = (Long) costeMensual.getValue();
					coste = cost.floatValue();
				}
				catch (ClassCastException ex) {}
				
				TClienteVIP tCliente = new TClienteVIP(id,dni,nom,ap,true,cuenta,VIPEnum.valueOf(nivel),coste);
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ACTUALIZAR_CLIENTE_VIP, tCliente);});
			}
			catch (Exception ex) {
				this.actualizar(Evento.RES_KO, new Exception("Campos incorrectos"));
			}
			finally {
				VistaActualizarClVIP.this.dispose();
			}
			
		});
		
		cancelar.addActionListener(e -> {
			VistaActualizarClVIP.this.dispose();
		});
		
		
	}
	
	public void cargarCliente(TClienteVIP tCliente, int idCl) {
		id = idCl;
		nombre.setText(tCliente.getNombre());
		apellido.setText(tCliente.getApellido());
		DNI.setText(tCliente.getDNI());
		cuentaBancaria.setText(tCliente.getCuentaBancaria());
		nivelVIP.setValue(tCliente.getNivelVIP());
		costeMensual.setValue(tCliente.getCosteMensual());
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage("Se ha actualizado correctamente el cliente: " + (int)datos);
		}
		else if (evento == Evento.RES_KO) {
			ViewUtils.createDialogMessage("No se ha podido actualizar el cliente.\nError: " +((Exception) datos).getMessage());
		}
		
	}
}
