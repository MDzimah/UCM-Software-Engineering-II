package presentacion;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import misc.Messages;

public class ViewUtils {
	public static final Dimension screenDimension() { return getScaledScreenDimension(1,1); }
	
	public static Dimension getScaledScreenDimension(int factorH, int factorW) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension(screenSize.width/factorH, screenSize.height/factorH);
	}
	
	public static JSpinner integerSpinner(int valorInicial, int min, int max, int step) {
        SpinnerNumberModel model = new SpinnerNumberModel(valorInicial, min, max, step);
        JSpinner spinner = new JSpinner(model);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        NumberFormatter formato = (NumberFormatter) editor.getTextField().getFormatter(); //Formateo del textfield del JSpinner
        formato.setValueClass(Integer.class); //Restringimos a solo integer
        formato.setAllowsInvalid(false); //Hace imposible escribir algo inválido en el spinner
        spinner.setEditor(editor);
        return spinner;
    }
	
	//Imagenes
	public static Image img_logoTeatret() { return new ImageIcon("resources/imagenes/teatretLogo.png").getImage(); }
	public static Image img_marcoOro() { return new ImageIcon("resources/imagenes/marcoOro.png").getImage(); }
	public static Image img_carrito() { return new ImageIcon("resources/imagenes/cart.png").getImage(); }
	public static void setAppIcon(JFrame window) { window.setIconImage(img_logoTeatret()); } 
	
	//Fonts
	public static Font fontCountCarrito() { return new Font("SansSerif", Font.BOLD, 15); }
	public static Font fontBotonesSubs() { return new Font("Georgia", Font.BOLD, 30); }
	public static Font fontTablaDefaultCabecera() { return new Font("SansSerif", Font.BOLD, 16); }
	public static Font fontTablaDefaultCuerpo() { return new Font("SansSerif", Font.PLAIN, 15); }
	
	
	//Diálogos
	public static void createErrorDialogMessage(String message) {
		JOptionPane.showMessageDialog(null,
				message,
	            "Error",
	            JOptionPane.ERROR_MESSAGE);
	}
	
	public static void createInvalidFieldsPanel() {
		createErrorDialogMessage(Messages.EXC_CAMPOS_INCORRECTOS);
	}
	
	public static void createDialogMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
}
