package presentacion.GUIMiemCompTea;

import misc.Messages;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;
import javax.swing.*;
import java.util.ArrayList;
import misc.Pair;
import negocio.miemCompTea.TMiemCompTea;
import negocio.miemCompTea.TMiemCompTea.Genero;

public class VistaAltaMiembroCompania extends VistaDefault{
	
	private JTextField nombreField;
    private JTextField apellidoField;
    private JSpinner edadField;
    private JTextField dniField;
    private JTextField emailField;    
    private JComboBox<String> generoField;

    public VistaAltaMiembroCompania() {
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
        

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> contratarMiembro());
        btnCancelar.addActionListener(e -> dispose());

        this.initComps(componentes, btnAceptar, btnCancelar);
        this.setTitle("Dar de alta miembro");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void contratarMiembro() {
        try {
        	String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            edadField.commitEdit();
            int edad = (Integer) edadField.getValue();        
            String dni = dniField.getText();
            String email = emailField.getText();
            String genero = (String) generoField.getSelectedItem();
            Genero generoEnum = "Hombre".equals(genero) ? Genero.HOMBRE : Genero.MUJER;
            TMiemCompTea tMiem = new TMiemCompTea(dni, nombre, apellido, email, edad, true, generoEnum);
            Controlador.getInstance().accion(Evento.ALTA_MIEMBRO_COMPANIA, tMiem);
            dispose();
        } catch (java.text.ParseException ex) {
            ViewUtils.createErrorDialogMessage("El ID ingresado no es válido.");
        }
    }

    @Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_MIEMBRO_ALTA);
			dispose();
		}
		else if (evento == Evento.RES_KO) {
			String error;
			if(datos instanceof String) error = (String) datos;
			else error = "Ya hay un miembro activo con id: \"" + (int) datos + "\" activo";
			ViewUtils.createErrorDialogMessage(Messages.X_MIEMBRO_ALTA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
