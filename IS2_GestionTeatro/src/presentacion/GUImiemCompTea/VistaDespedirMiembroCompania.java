package presentacion.GUImiemCompTea;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import misc.JSwingUtils;
import misc.Messages;
import misc.Pair;
import presentacion.Evento;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaDespedirMiembroCompania extends VistaDefault {
	private JSpinner idField;

    public VistaDespedirMiembroCompania(){
        super();
        
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        idField = new JSpinner(spinnerModel);

        
        ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
        componentes.add(new Pair<>(new JLabel("Id:"), idField));

        JButton btnDespedir = new JButton("Despedir");
        JButton btnCancelar = new JButton("Cancelar");

        btnDespedir.addActionListener(e -> despedirMiembro());
        btnCancelar.addActionListener(e -> dispose());

        this.initComps(componentes, btnDespedir, btnCancelar);
        this.setTitle("Despedir Miembro");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void despedirMiembro() {
        int id = (Integer) idField.getValue();
        Controlador.getInstance().accion(Evento.DESPEDIR_MIEMBRO_COMPANIA, id);
        dispose();
    }

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) JSwingUtils.createDialogMessage(Messages.EX_MIEMBRO_DESPEDIDO);
		else if (evento == Evento.RES_KO) {
			String error;
			if (datos instanceof String) error = (String) datos;
			else error = Messages.ID_NO_ENCONTRADO.formatted(String.valueOf(((int)datos)));
			JSwingUtils.createErrorDialogMessage(Messages.EX_MIEMBRO_DESPEDIDO + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
