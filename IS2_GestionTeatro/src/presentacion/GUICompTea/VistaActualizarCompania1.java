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


import misc.Messages;
import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;
import presentacion.VistaDefault;
import negocio.compTea.TCompTea;
import negocio.pase.TPase;

public class VistaActualizarCompania1 implements IGUI{
	
	
	public VistaActualizarCompania1() {
	}
	
	public void cargar(TCompTea tCompTea) {
		
		ArrayList<TCompTea> p = new ArrayList<TCompTea>();
		p.add(tCompTea);
		ArrayList<ArrayList<TCompTea>>t= new ArrayList<ArrayList<TCompTea>>();
		t.add(p);
		String[] nomCols = Messages.colNomsCompTea;
		ArrayList<String[]>listaColumnas= new ArrayList<String[]>();
		listaColumnas.add(nomCols);
		TablaDefault<TCompTea> tabla = new TablaDefault("BUSCAR PASE", listaColumnas, t, true);
		tabla.getOkButton().addActionListener(e -> {
<<<<<<< HEAD
			ArrayList<TCompTea> listTCompTea= tabla.getEdicion();
			TCompTea tCompTea2=listTCompTea.get(0);
=======
			TCompTea tCompTea2 = tabla.getEdiciones();
>>>>>>> branch 'main' of https://github.com/MDzimah/UCM-Software-Engineering-II.git
			Controlador.getInstance().accion(Evento.ACTUALIZAR1_COMPANIA_TEATRAL, tCompTea2);
		});
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			
			 
			}
		else if(evento==Evento.RES_KO) {
			
			ViewUtils.createErrorDialogMessage( ((Exception) datos).getMessage());
			
			
		}
	}
}
		


