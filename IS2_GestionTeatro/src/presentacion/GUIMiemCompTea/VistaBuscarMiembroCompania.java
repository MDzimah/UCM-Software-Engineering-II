package presentacion.GUIMiemCompTea;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import exceptions.BBDDReadException;
import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import negocio.miemCompTea.TMiemCompTea;
import negocio.miemCompTea.TMiemCompTea.Genero;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaBuscarMiembroCompania extends VistaDefault{
	
    private JSpinner idField;

    public VistaBuscarMiembroCompania(){
        super();
        
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        idField = new JSpinner(spinnerModel);
        
        ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
        componentes.add(new Pair<>(new JLabel("Id:"), idField));

        JButton btnBuscar = new JButton("Buscar");
        JButton btnCancelar = new JButton("Cancelar");
        
        this.initComps(componentes, btnBuscar, btnCancelar);

        btnBuscar.addActionListener(e -> buscarMiembro());
        btnCancelar.addActionListener(e -> dispose());

        this.setTitle("Buscar Miembro");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void buscarMiembro() {
        try {
            idField.commitEdit();
            int id = (Integer) idField.getValue();        
            Controlador.getInstance().accion(Evento.BUSCAR_MIEMBRO_COMPANIA, id);
            dispose();
        } catch (java.text.ParseException ex) {
            ViewUtils.createErrorDialogMessage("El ID ingresado no es válido.");
        }
    }

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ArrayList<TMiemCompTea> miemComp = new ArrayList<TMiemCompTea>();
			miemComp.add((TMiemCompTea)datos);
			
			new TablaDefault<TMiemCompTea>("MIEMBRO DE LA COMPANIA", Messages.colNomsMiemCompTea, miemComp, false).setVisible(true);
			dispose();
		}
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof String) error = (String) datos;
			else error = Messages.ID_NO_ENCONTRADO.formatted(String.valueOf(((int)datos)));
			ViewUtils.createErrorDialogMessage(Messages.X_MIEMBRO_ENCONTRADO + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
