package presentacion.GUIMiemCompTea;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import misc.Messages;
import misc.Pair;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaActualizarMiembroCompania_0 extends VistaDefault{
	private JSpinner idField;
	private JButton aceptar, cancelar;
	
	public VistaActualizarMiembroCompania_0() {
		super();
		this.setTitle("Actualizar miembro de la compania");
		
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
		idField = new JSpinner(spinnerModel);
		JLabel id1 = new JLabel("Id:");

		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(id1, idField));
		super.initComps(campos, aceptar, cancelar);
		
		aceptar.addActionListener(e ->{
			try {
                idField.commitEdit();
                int id = (Integer) idField.getValue();        
                Controlador.getInstance().accion(Evento.ACTUALIZAR_MIEMBRO_COMPANIA_0, id);
                dispose();
            } catch (java.text.ParseException ex) {
                ViewUtils.createErrorDialogMessage("El ID ingresado no es válido.");
            }
		});
		
		cancelar.addActionListener(e -> dispose());
		
		this.setVisible(true);
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_KO) {
			String error;
			if(datos instanceof String) error = (String) datos;
			else error = Messages.ID_NO_ENCONTRADO.formatted(String.valueOf(((int)datos)));
			ViewUtils.createErrorDialogMessage(Messages.X_MIEMBRO_ACTUALIZADO + ' ' + Messages.MOTIVO.formatted(error));
		}		
	}
	
}
