package presentacion.GUIPase;

import java.util.ArrayList;
import java.util.Collection;

import misc.JSwingUtils;
import misc.Messages;
import misc.JSwingUtils.TablaDefault;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.controlador.Controlador;

public class VistaActualizarPaseDescarga implements IGUI {
	
	public void cargarPase(TPase datos) {
		Collection<Object> p = new ArrayList<Object>();
		p.add((TPase)datos);
		String[] nomCols = {"ID","ID COMPANYA", "ID OBRA", "FECHA", "STOCK", "PRECIO"};
		TablaDefault tabla = JSwingUtils.createTabla("BUSCAR PASE", nomCols, p, true, true);
		tabla.getOkButton().addActionListener(e -> {
			int id = Integer.valueOf((String) tabla.getTable().getValueAt(0, 0));
			int idCompTea = Integer.valueOf((String) tabla.getTable().getValueAt(0, 1));
			int idObra = Integer.valueOf((String) tabla.getTable().getValueAt(0, 2));
			//int fecha = Integer.valueOf((String) tabla.getTable().getValueAt(0, 3));
			int stock = Integer.valueOf((String) tabla.getTable().getValueAt(0, 4));
			int precio = Integer.valueOf((String) tabla.getTable().getValueAt(0, 5));
			TPase tPaseNuevo = new TPase(id, idCompTea, idObra, true, null, stock, precio); 
			Controlador.getInstance().accion(Evento.ACTUALIZAR_PASE_DESCARGA, tPaseNuevo);
		});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			JSwingUtils.createDialogMessage(Messages.EX_PASE_ACTUALIZADO);
		}
		else if (evento==Evento.RES_OK) {
			JSwingUtils.createErrorDialogMessage(Messages.X_PASE_ACTUALIZADO + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}

}
