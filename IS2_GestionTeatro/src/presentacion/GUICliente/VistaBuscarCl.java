package presentacion.GUICliente;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;

import misc.Messages;
import misc.Pair;
import negocio.cliente.TCliente;
import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;
import negocio.factura.TFactura;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaBuscarCl extends VistaDefault implements IGUI {

	private JLabel idl;
	private JSpinner id;
	private JButton aceptar, cancelar;
	
	public VistaBuscarCl() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//iniciar componentes
		this.setTitle("Buscar Cliente");
		idl = new JLabel("Id");
		id = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE,1);
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		
		//lista de parejas para initComps de VistaDefault
		ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
		componentes.add(new Pair<>(idl, id));
		
		super.initComps(componentes, aceptar, cancelar);
		
		//oyentes
		aceptar.addActionListener((e) -> {
				int idCl = (int) id.getValue();
				SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.BUSCAR_CLIENTE, idCl); });
				VistaBuscarCl.this.dispose();
		});
				
		cancelar.addActionListener((e) -> {
			VistaBuscarCl.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento == Evento.RES_OK) {
			TCliente cliente = (TCliente)datos;
			if (cliente.getTipo().equals("VIP")) {
				ArrayList<TCliente> cl = new ArrayList<TCliente>();
				cl.add((TCliente)datos);
				new TablaDefault<TCliente>("Cliente", new ArrayList<>(Collections.singletonList(Messages.colNomsClienteVIP)),new ArrayList<>(Collections.singletonList(cl)), false).setVisible(true);
			}
			else if (cliente.getTipo().equals("Normal")) {
				ArrayList<TCliente> cl = new ArrayList<TCliente>();
				cl.add((TCliente)datos);
				new TablaDefault<TCliente>("Cliente", new ArrayList<>(Collections.singletonList(Messages.colNomsClienteVIP)),new ArrayList<>(Collections.singletonList(cl)), false).setVisible(true);
			}
			
		} else if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage("No se ha encontrado el cliente.\n" + "Error: " +((Exception) datos).getMessage());
		}
		
	}

}
