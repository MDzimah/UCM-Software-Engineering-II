package launcher;

import javax.swing.*;

import com.formdev.flatlaf.FlatIntelliJLaf;

import misc.Evento;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class Main {
	
	public static void main(String[] args) {
		FlatIntelliJLaf.setup(); //Para una interfaz más vistosa
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               FactoriaAbstractaPresentacion.getInstance().createVista(Evento.MAINWINDOW);
            }
        });
	}
}
