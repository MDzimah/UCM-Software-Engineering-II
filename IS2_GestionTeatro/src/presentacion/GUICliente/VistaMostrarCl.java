package presentacion.GUICliente;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import misc.Messages;
import negocio.cliente.TCliente;
import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;
import negocio.taquillero.TTaquillero;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.GUITaquillero.VistaMostrarTaquilleros;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaMostrarCl extends VistaDefault implements IGUI{

private JButton mostrar, cancelar;
	
	public VistaMostrarCl() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.setTitle("Mostrar clientes");
		mostrar = new JButton("Mostrar");
		cancelar = new JButton("Cancelar");
		super.initComps(null, mostrar, cancelar);
		
		//oyentes
		mostrar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.MOSTRAR_CLIENTES, null);
			VistaMostrarCl.this.dispose();
		});
		
		cancelar.addActionListener(e->{
			VistaMostrarCl.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento == Evento.RES_OK) {
			ArrayList<TCliente> cls = (ArrayList<TCliente>) datos;
			ArrayList<TCliente> clv = new ArrayList<>();
			ArrayList<TCliente> cln = new ArrayList<>();
			
			for(TCliente tCliente : cls) {
				if (tCliente.getTipo() == "VIP") {
					clv.add((TClienteVIP)tCliente);
				}
				else if (tCliente.getTipo() == "Normal") {
					cln.add((TClienteNormal)tCliente);
				}
			}
			
			//creamos la tabla
			ArrayList<String[]> list = new ArrayList<String[]>(2);
			list.add(Messages.colNomsClienteVIP);
			list.add(Messages.colNomsClienteNormal);
			ArrayList<ArrayList<TCliente>> cl = new ArrayList<ArrayList<TCliente>>(2);
			cl.add(clv);
			cl.add(cln);
			TablaDefault<TCliente> tabla = new TablaDefault<TCliente>("Clientes",list,cl,false);
			//TablaDefault<TClienteVIP> tabla1 = new TablaDefault<TClienteVIP>("Clientes VIP", Messages.colNomsClienteVIP, clv, false);
			//TablaDefault<TClienteNormal> tabla2 = new TablaDefault<TClienteNormal>("Clientes Normales", Messages.colNomsClienteNormal, cln, false);
			tabla.setVisible(true);
			
		} else if (evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage("No se han podido mostrar los taquilleros.\n" + "Error: " +((Exception) datos).getMessage());
		}
		
	}

}
