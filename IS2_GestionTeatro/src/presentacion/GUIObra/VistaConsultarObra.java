package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.*;
import negocio.obra.TObra;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaConsultarObra extends VistaDefault implements IGUI{
	
	private JButton consultar, cancelar;
	private JSpinner id;

	//Constructor
	public VistaConsultarObra() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//Inicializamos los componentes
		this.setTitle("Consultar obra");
		consultar = new JButton("Consultar");
		cancelar = new JButton("Cancelar");
		JLabel id1 = new JLabel("Id");
		id = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);

		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(id1, id));


		super.initComps(campos, consultar, cancelar);
		
		//Declaramos los listeners
		consultar.addActionListener(e ->{
			try {
				id.commitEdit();
				int idObra = (int)id.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.CONSULTAR_OBRA, idObra);});
				VistaConsultarObra.this.dispose();
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				id.updateUI();
			}
		});
		
		cancelar.addActionListener(e ->{
			VistaConsultarObra.this.dispose();
		});
	}
	
	@Override
	public void actualizar(presentacion.Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			ArrayList<TObra> obra= new ArrayList<TObra>();
			obra.add((TObra) datos);
			
            TablaDefault<TObra> tabla = new TablaDefault<>("OBRA", Messages.colNomsObra, obra, false);
            tabla.setVisible(true);
		}
		else if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.EX_OBRA_CONSULTAR_ERROR + '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));
		}
	}
}
