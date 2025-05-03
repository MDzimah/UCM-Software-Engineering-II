package presentacion.GUIMiemCompTea;

import misc.JSwingUtils;
import misc.Messages;
import presentacion.Evento;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;
import javax.swing.*;
import java.util.ArrayList;
import misc.Pair;
import negocio.miemCompTea.TMiemCompTea;
import negocio.miemCompTea.TMiemCompTea.Genero;

public class VistaContratarMiembroCompania extends VistaDefault{
	
	private JTextField nombreField;
    private JTextField apellidoField;
    private JSpinner edadField;
    private JTextField dniField;
    private JTextField emailField;    
    private JComboBox<String> generoField;

    public VistaContratarMiembroCompania() {
        super();

        nombreField = new JTextField();
        apellidoField = new JTextField();
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        edadField = new JSpinner(spinnerModel);
        dniField = new JTextField();
        emailField = new JTextField();       
        generoField = new JComboBox<String>();
        generoField.addItem("Hombre");
        generoField.addItem("Mujer");
        
        ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
        componentes.add(new Pair<>(new JLabel("Nombre:"), nombreField));
        componentes.add(new Pair<>(new JLabel("Apellido:"), apellidoField));
        componentes.add(new Pair<>(new JLabel("Edad:"), edadField));
        componentes.add(new Pair<>(new JLabel("DNI:"), dniField));
        componentes.add(new Pair<>(new JLabel("Email:"), emailField));
        componentes.add(new Pair<>(new JLabel("Genero:"), generoField));
        

        JButton btnContratar = new JButton("Contratar");
        JButton btnCancelar = new JButton("Cancelar");

        btnContratar.addActionListener(e -> contratarMiembro());
        btnCancelar.addActionListener(e -> dispose());

        this.initComps(componentes, btnContratar, btnCancelar);
        this.setTitle("Contratar Miembro");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void contratarMiembro() {
    	String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        int edad = (Integer) edadField.getValue();
        String dni = dniField.getText();
        String email = emailField.getText();
        String genero = (String) generoField.getSelectedItem();
        Genero generoEnum = "Hombre".equals(genero) ? Genero.HOMBRE : Genero.MUJER;
        
        TMiemCompTea tMiem = new TMiemCompTea(dni, nombre, apellido, email, edad, true, generoEnum);
        Controlador.getInstance().accion(Evento.CONTRATAR_MIEMBRO_COMPANIA, tMiem);
        dispose();
    }

    @Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) JSwingUtils.createDialogMessage(Messages.EX_MIEMBRO_CONTRATADO);
		else if (evento == Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage(Messages.X_MIEMBRO_CONTRATADO + ' ' + Messages.MOTIVO.formatted((String) datos));
		}
	}
}
