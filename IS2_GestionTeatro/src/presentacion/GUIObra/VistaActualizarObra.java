package presentacion.GUIObra;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.*;
import negocio.obra.TObra;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaActualizarObra extends VistaDefault implements IGUI{
	//Atributos
	private JButton actualizar, cancelar;
	private JTextField titulo, autor , genero, sinopsis;

	

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			FactoriaAbstractaPresentacion.getInstance().createDialogMessage("Se ha actualizado correctamente la obra " + (int)datos);
		}
		else if(evento==Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createDialogMessage("No se ha podido actualizar la obra.\n" + (String)datos);
		}
	}
}
