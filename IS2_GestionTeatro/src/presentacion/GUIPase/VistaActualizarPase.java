package presentacion.GUIPase;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;

import misc.Evento;
import misc.JSwingUtils;
import misc.Messages;
import negocio.factura.TFactura;
import presentacion.VistaDefault;

public class VistaActualizarPase extends VistaDefault {
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			/*Collection<Object> p = new ArrayList<Object>();
			p.add((TFactura)datos);
			String[] nomCols = {"ID","ID COMPANYA", "ID OBRA", "FECHA", "STOCK", "PRECIO"};
			//FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.EX_PASE_BUSCADO);
			JFrame pantallaEdicion = JSwingUtils.createTabla("BUSCAR PASE", nomCols, p, true, false); *///se crea una tabla con una sola linea que contiene
																	  		 //la info del transfer del pase buscado
		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage(Messages + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}
}
