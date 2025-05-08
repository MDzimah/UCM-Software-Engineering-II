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
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaBajaMiembroCompania extends VistaDefault {
	private JSpinner idField;

    public VistaBajaMiembroCompania(){
        super();
        
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        idField = new JSpinner(spinnerModel);

        
        ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
        componentes.add(new Pair<>(new JLabel("Id:"), idField));

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> despedirMiembro());
        btnCancelar.addActionListener(e -> dispose());

        this.initComps(componentes, btnAceptar, btnCancelar);
        this.setTitle("Dar de baja a miembro");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void despedirMiembro() {
    	 try {
             idField.commitEdit();
             int id = (Integer) idField.getValue(); 
             dispose();
             Controlador.getInstance().accion(Evento.BAJA_MIEMBRO_COMPANIA, id);
         } catch (java.text.ParseException ex) {
             ViewUtils.createErrorDialogMessage(Messages.EXC_CAMPOS_INCORRECTOS);
         }
    }

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_MIEMBRO_BAJA);
			dispose();
		}
		else if (evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_MIEMBRO_BAJA + ' ' + Messages.MOTIVO.formatted(((Exception) datos).getMessage()));
		}
	}
}
