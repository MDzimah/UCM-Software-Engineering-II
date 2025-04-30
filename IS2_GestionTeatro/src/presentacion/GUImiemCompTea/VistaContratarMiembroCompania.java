package presentacion.GUImiemCompTea;

import misc.Evento;
import misc.Messages;
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
    private JTextField edadField;
    private JTextField dniField;
    private JTextField emailField;    
    private JComboBox<String> generoField;

    public VistaContratarMiembroCompania() {
        super();

        nombreField = new JTextField();
        apellidoField = new JTextField();
        edadField = new JTextField();
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

        this.initComps(componentes, btnContratar, btnCancelar, false);
        this.setTitle("Contratar Miembro");
        this.setVisible(true);
    }

    private void contratarMiembro() {
    	String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        int edad = Integer.valueOf(edadField.getText());
        String dni = dniField.getText();
        String email = emailField.getText();
        String genero = (String) generoField.getSelectedItem();
        Genero generoEnum = genero == "Hombre" ? Genero.HOMBRE : Genero.MUJER;
        
        TMiemCompTea tMiem = new TMiemCompTea(dni, nombre, apellido, email, edad, true, generoEnum);
        Controlador.getInstance().accion(Evento.CONTRATAR_MIEMBRO_COMPANIA, tMiem);
    }

    @Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.EX_MIEMBRO_CONTRATADO);
		else if (evento == Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_MIEMBRO_CONTRATADO + ' ' + Messages.MOTIVO.formatted((String) datos));
		}
	}
}
