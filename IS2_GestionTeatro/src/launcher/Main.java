package launcher;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatIntelliJLaf;

import eventos.Evento;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class Main {
	
	public static void main(String[] args) {
		FlatIntelliJLaf.setup(); //Para una interfaz más vistosa
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FactoriaAbstractaPresentacion.getInstance().createOtrasVistas(Evento.MAINWINDOW, null);
            }
        });
	}
}
