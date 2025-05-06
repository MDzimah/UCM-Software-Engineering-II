package presentacion.GUICliente;

import java.util.ArrayList;

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
				try {
					int idCl = (int) id.getValue();
					SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.BUSCAR_CLIENTE, idCl); });
				} 
				catch (NumberFormatException ex) {
					
				}
				finally {
					VistaBuscarCl.this.dispose();
				}
		});
				
		cancelar.addActionListener((e) -> {
			VistaBuscarCl.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento == Evento.RES_OK) {
			TCliente cliente = (TCliente)datos;
			if (cliente.getTipo() == "VIP") {
				ArrayList<TClienteVIP> cl = new ArrayList<TClienteVIP>();
				cl.add((TClienteVIP)datos);
				new TablaDefault<TClienteVIP>("Cliente", Messages.colNomsClienteVIP, cl, false).setVisible(true);
			}
			else if (cliente.getTipo() == "Normal") {
				ArrayList<TClienteNormal> cl = new ArrayList<TClienteNormal>();
				cl.add((TClienteNormal)datos);
				new TablaDefault<TClienteNormal>("Cliente", Messages.colNomsClienteNormal, cl, false).setVisible(true);
			}
			
		} else if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage("No se ha encontrado el cliente.\n" + "Error: " +((Exception) datos).getMessage());
		}
		
	}

}
