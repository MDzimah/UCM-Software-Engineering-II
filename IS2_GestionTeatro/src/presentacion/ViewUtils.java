package presentacion;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

import misc.Messages;

public class ViewUtils {
	public static final Dimension screenDimension() { return getScaledScreenDimension(1,1); }
	
	public static Dimension getScaledScreenDimension(int factorH, int factorW) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension(screenSize.width/factorH, screenSize.height/factorH);
	}
	
	//Imagenes
	public static Image logoTeatret() { return new ImageIcon("resources/imagenes/teatretLogo.png").getImage(); }
	
	public static Image marcoOro() { return new ImageIcon("resources/imagenes/marcoOro.png").getImage(); }
	
	//Fonts
	public static Font FontTablaDefaultCabecera() { return new Font("SansSerif", Font.BOLD, 16); }
	
	public static Font FontTablaDefaultCuerpo () { return new Font("SansSerif", Font.PLAIN, 15); }
	
	public static void setAppIcon(JFrame window) {
		window.setIconImage(logoTeatret());
	} 
	
	//Diálogos
	public static void createErrorDialogMessage(String message) {
		JOptionPane.showMessageDialog(null,
				message,
	            "Error",
	            JOptionPane.ERROR_MESSAGE);
	}
	
	public static void createInvalidFieldsPanel() {
		JOptionPane.showMessageDialog(null,
				Messages.EXC_CAMPOS_INCORRECTOS,
	            "Error",
	            JOptionPane.ERROR_MESSAGE);
	}
	
	public static void createDialogMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
}
