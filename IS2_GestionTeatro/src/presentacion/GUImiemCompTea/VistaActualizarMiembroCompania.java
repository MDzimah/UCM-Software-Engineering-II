package presentacion.GUIMiemCompTea;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import misc.Pair;
import negocio.miemCompTea.TMiemCompTea;
import negocio.miemCompTea.TMiemCompTea.Genero;
import presentacion.Evento;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaActualizarMiembroCompania extends VistaDefault {
	
	private JTextField nombreField;
    private JTextField apellidoField;
    private JSpinner edadField;
    private JTextField dniField;
    private JTextField emailField;    
    private JComboBox<String> generoField;

    public VistaActualizarMiembroCompania(){
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

        JButton btnContratar = new JButton("Actualizar");
        JButton btnCancelar = new JButton("Cancelar");

        btnContratar.addActionListener(e -> actualizarMiembro());
        btnCancelar.addActionListener(e -> dispose());

        this.initComps(componentes, btnContratar, btnCancelar);
        this.setTitle("Actualizar Miembro");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void actualizarMiembro() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        int edad = (Integer) edadField.getValue();
        String dni = dniField.getText();
        String email = emailField.getText();
        String genero = (String) generoField.getSelectedItem();
        Genero generoEnum = "Hombre".equals(genero) ? Genero.HOMBRE : Genero.MUJER;
        
        TMiemCompTea tMiem = new TMiemCompTea(dni, nombre, apellido, email, edad, true, generoEnum);
        Controlador.getInstance().accion(Evento.ACTUALIZAR_MIEMBRO_COMPANIA, tMiem);
        dispose();
    }

	@Override
	public void actualizar(Evento evento, Object datos) {
		
	}
}
