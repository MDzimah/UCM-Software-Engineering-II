package presentacion.GUImiemCompTea;

import exceptions.BBDDReadException;
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
import presentacion.controlador.Controlador;

public class VistaContratarMiembroCompania extends VistaDefault{
	
	private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField edadField;
    private JTextField dniField;
    private JTextField emailField;    
    private JComboBox<String> generoField;

    public VistaContratarMiembroCompania() {
        super();

        nombreField = new JTextField(20);
        apellidoField = new JTextField(20);
        edadField = new JTextField(4);
        dniField = new JTextField(15);
        emailField = new JTextField(30);       
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
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        int edad = Integer.valueOf(edadField.getText().trim());
        String dni = dniField.getText().trim();
        String email = emailField.getText().trim();
        String genero = generoField.getName();
        
        TMiemCompTea tMiem = new TMiemCompTea(dni, nombre, apellido, email, edad, true, Genero.valueOf(genero));
        Controlador.getInstance().accion(Evento.CONTRATAR_MIEMBRO_COMPANIA, tMiem);
    }

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.EX_MIEMBRO_CONTRATADO);
		else if (evento == Evento.RES_KO) {
			String error;
			if (datos instanceof String) error = (String)datos;
			else if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else {
				if (datos != null) error = Messages.ID_NO_ENCONTRADO.formatted((int)datos);
				else //error = Messages.STOCK_INSUF; 
			}
			
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.EX_MIEMBRO_CONTRATADO + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
