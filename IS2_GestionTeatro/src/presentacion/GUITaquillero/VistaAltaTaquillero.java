package presentacion.GUITaquillero;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Genero;
import misc.Messages;
import misc.Pair;
import negocio.taquillero.TTaquillero;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaAltaTaquillero extends VistaDefault {

	private JButton aceptar, cancelar;
	private JTextField nombre, apellido, dni, sueldo, edad;
	private JLabel l_nombre, l_apellido, l_DNI, l_sueldo, l_edad, l_genero;
	private JComboBox<Genero> genero;
	
	public VistaAltaTaquillero() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//iniciar componentes
		this.setTitle("Alta Taquillero");
		l_nombre = new JLabel("Nombre: ");
		l_apellido = new JLabel("Apellido: ");
		l_DNI = new JLabel("DNI: ");
		l_sueldo = new JLabel("Sueldo: ");
		l_edad = new JLabel("Edad: ");
		l_genero = new JLabel("Genero: ");
		nombre = new JTextField();
		apellido = new JTextField();
		dni = new JTextField();
		sueldo = new JTextField();
		edad = new JTextField();
		genero = new JComboBox<>(Genero.values());
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		
		
		//lista de parejas para initComps de VistaDefault
		ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
		componentes.add(new Pair<>(l_nombre, nombre));
		componentes.add(new Pair<>(l_apellido, apellido));
		componentes.add(new Pair<>(l_DNI, dni));
		componentes.add(new Pair<>(l_sueldo, sueldo));
		componentes.add(new Pair<>(l_edad, edad));
		componentes.add(new Pair<>(l_genero, genero));
		super.initComps(componentes, aceptar, cancelar);
		
		//oyentes
		aceptar.addActionListener((e) -> {
			try {
				String _nombre = nombre.getText();
				String _apellido = apellido.getText();
				String _DNI = dni.getText();
				float _sueldo = Float.valueOf(sueldo.getText());
				int _edad = Integer.valueOf(edad.getText());
				Genero _genero = (Genero) genero.getSelectedItem();
				TTaquillero tTaq = new TTaquillero(true, _DNI, _nombre, _apellido, 0, _sueldo, _edad, _genero);
				
				SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.ALTA_TAQUILLERO, tTaq); });
			} catch (Exception ex) {
				this.actualizar(Evento.RES_KO, ex);
			} finally {
				VistaAltaTaquillero.this.dispose();
			}
		});
		
		cancelar.addActionListener((e) -> {
			VistaAltaTaquillero.this.dispose();
		});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_TAQUILLERO_ANYADIDO_CORRECT + '\n' + "Id: "+ (int)datos);
		}
		else if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.EX_TAQUILLERO_ANYADIDO_ERROR + '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));
		}
	}

}
