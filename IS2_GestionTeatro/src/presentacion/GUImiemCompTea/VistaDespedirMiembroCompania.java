package presentacion.GUImiemCompTea;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Evento;
import misc.Messages;
import misc.Pair;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaDespedirMiembroCompania extends VistaDefault {
	private JTextField idField;

    public VistaDespedirMiembroCompania(){
        super();
        
        idField = new JTextField();

        
        ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
        componentes.add(new Pair<>(new JLabel("Id:"), idField));

        JButton btnDespedir = new JButton("Despedir");
        JButton btnCancelar = new JButton("Cancelar");

        btnDespedir.addActionListener(e -> despedirMiembro());
        btnCancelar.addActionListener(e -> dispose());

        this.initComps(componentes, btnDespedir, btnCancelar, false);
        this.setTitle("Despedir Miembro");
        this.setVisible(true);
    }

    private void despedirMiembro() {
        int id = Integer.valueOf(idField.getText());
        Controlador.getInstance().accion(Evento.DESPEDIR_MIEMBRO_COMPANIA, id);
    }

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.EX_MIEMBRO_DESPEDIDO);
		else if (evento == Evento.RES_KO) {
			String error;
			if (datos instanceof String) error = (String) datos;
			else error = Messages.ID_NO_ENCONTRADO.formatted(String.valueOf(((int)datos)));
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.EX_MIEMBRO_DESPEDIDO + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
