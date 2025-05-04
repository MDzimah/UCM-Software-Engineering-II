package presentacion.GUICompTea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.JSwingUtils;
import misc.Messages;
import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.controlador.Controlador;
import presentacion.VistaDefault;
import negocio.compTea.TCompTea;
import negocio.pase.TPase;

public class VistaActualizarCompania1 extends VistaDefault implements IGUI{
	
	
	public VistaActualizarCompania1() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI(TCompTea tCompTea) {
		
		ArrayList<Object> p = new ArrayList<Object>();
		p.add(tCompTea);
		String[] nomCols = {"ID","NOMBRE COMPANIA", "DIRECCION", "COSTE"};
		TablaDefault<TCompTea> tabla = new TablaDefault("BUSCAR PASE", nomCols, p, true, true);
		tabla.getOkButton().addActionListener(e -> {
			TCompTea tPaseNuevo = tabla.getEdicion();
			Controlador.getInstance().accion(Evento.ACTUALIZAR_PASE_DESCARGA, tPaseNuevo);
		});
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			if(datos instanceof TCompTea) {
			
			
			}
			 else {
				JSwingUtils.createDialogMessage(Messages.COMPANIA_ACTUALIZADA); 
			 }
			}
		else if(evento==Evento.RES_KO) {
			if(datos instanceof Exception) {
			JSwingUtils.createErrorDialogMessage( ((Exception) datos).getMessage());
			}
			else {
				JSwingUtils.createErrorDialogMessage("NO EXISTEN COMPANIAS CON ID: "+(int) datos);
			}
		}
		
	}
