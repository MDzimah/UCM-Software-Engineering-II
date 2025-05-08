package presentacion.GUICliente;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import exceptions.InvalidFields;
import misc.Pair;
import negocio.cliente.TClienteNormal;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaActualizarClNormal extends VistaDefault implements IGUI {
	private JTextField DNI, nombre, apellido, cuentaBancaria, puntosAcum;
	private JLabel DNIl, nombrel, apellidol, cuentaBancarial, puntosAcuml;
	private JButton alta, cancelar;
	private int id;
	
	
	public VistaActualizarClNormal() {
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
		puntosAcum = new JTextField(20);
		puntosAcuml = new JLabel("Puntos acumulados");
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(DNIl, DNI));
		campos.add(new Pair<>(nombrel, nombre));
		campos.add(new Pair<>(apellidol, apellido));
		campos.add(new Pair<>(cuentaBancarial, cuentaBancaria));
		campos.add(new Pair<>(puntosAcuml, puntosAcum));
		super.initComps(campos, cancelar, alta);
		
		super.initComps(campos, alta, cancelar);
		
		alta.addActionListener(e -> {
			try {
				String nom = nombre.getText();
				String ap = apellido.getText();
				String dni = DNI.getText();
				String cuenta = cuentaBancaria.getText();
				int ptos = Integer.parseInt(puntosAcum.getText());
				TClienteNormal tCliente = new TClienteNormal(id,dni,nom,ap,true,cuenta, ptos);
				
				
				SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.ACTUALIZAR_CLIENTE_NORMAL, tCliente); });
			}
			catch (Exception ex) {
				this.actualizar(Evento.RES_KO, new InvalidFields());
			}
			
			finally {
				VistaActualizarClNormal.this.dispose();
			}
			
			
		});
		
		cancelar.addActionListener(e -> {
			VistaActualizarClNormal.this.dispose();
		});
		
		
	}
	
	public void cargarCliente(TClienteNormal tCliente, int idCl) {
		id = idCl;
		nombre.setText(tCliente.getNombre());
		apellido.setText(tCliente.getApellido());
		DNI.setText(tCliente.getDNI());
		cuentaBancaria.setText(tCliente.getCuentaBancaria());
		puntosAcum.setText(Integer.toString(tCliente.getPuntosAcumulados()));
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
