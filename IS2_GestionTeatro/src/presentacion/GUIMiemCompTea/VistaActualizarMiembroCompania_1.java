package presentacion.GUIMiemCompTea;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import misc.Messages;
import misc.Pair;
import negocio.compTea.TCompTea;
import negocio.miemCompTea.TMiemCompTea;
import negocio.obra.TObra;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaActualizarMiembroCompania_1 extends VistaDefault {
	
	public void cargarMiembro(TMiemCompTea datos) {
		ArrayList<TMiemCompTea> array = new ArrayList<TMiemCompTea>();
		array.add(datos);
		ArrayList<ArrayList<TMiemCompTea>> miembro= new ArrayList<ArrayList<TMiemCompTea>>();
		miembro.add(array);
		
		String[] nomCols = Messages.colNomsMiemCompTea;
		ArrayList<String[]>listaColumnas= new ArrayList<String[]>();
		listaColumnas.add(nomCols);
		
		TablaDefault<TMiemCompTea> tabla = new TablaDefault<TMiemCompTea>("Miembro de la compania", listaColumnas, miembro, true);
		tabla.setVisible(true);
		
		tabla.getOkButton().addActionListener(e -> {
			ArrayList<TMiemCompTea> listTMiem = tabla.getEdiciones();
			TMiemCompTea tMiemComp = listTMiem.get(0);
			Controlador.getInstance().accion(Evento.ACTUALIZAR_MIEMBRO_COMPANIA_1, tMiemComp);
			dispose();
		});
	}
    
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_MIEMBRO_ACTUALIZADO);
			dispose();
		}
		else if (evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_MIEMBRO_ACTUALIZADO + ' ' + Messages.MOTIVO.formatted(((Exception) datos).getMessage()));
		}
	}
}
