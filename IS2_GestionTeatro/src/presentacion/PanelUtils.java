package presentacion;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.*;

public class PanelUtils {
	/**
	 * Creates a JPanel containing a JLabel and any type of JComponent arranged horizontally.
	 * Adds a horizontal strut for spacing between the label and the component.
	 * The resulting panel is horizontally centered within any vertically stacked layout (e.g., BoxLayout.Y_AXIS),
	 * making it suitable for use in vertically structured forms where each row is centered.
	 *
	 * @param label the JLabel to be placed next to the component
	 * @param component the JComponent to be placed next to the label (e.g., JTextField, JButton, etc.)
	 * @return JPanel containing the horizontally aligned JLabel and JComponent, with center alignment
	 */
    public static JPanel createLabelComponentPair(JLabel label, JComponent component) {
        JPanel pair = new JPanel();
        pair.setLayout(new BoxLayout(pair, BoxLayout.X_AXIS));
        pair.setAlignmentX(Component.CENTER_ALIGNMENT);
        pair.add(label);
        pair.add(Box.createHorizontalStrut(10));
        pair.add(component);
        return pair;
    }
	
	/**
	 * Creates a JPanel containing two JButton components arranged horizontally at the center.
	 * 
	 * @param button1 the first JButton to be added to the panel
	 * @param button2 the second JButton to be added to the panel
	 * @return JPanel containing the two buttons arranged in the center
	 */
	public static JPanel createResponsePair(JButton button1, JButton button2) {
        JPanel pair = new JPanel();
        pair.setLayout(new FlowLayout(FlowLayout.CENTER));
        pair.add(button1);
        pair.add(button2);
        return pair;
    }
	
	/**
	 * Displays an error message dialog indicating that some fields are incorrect.
	 * The dialog will notify the user to complete all fields.
	 *
	 * @param parent the parent frame for the dialog, used to center the dialog relative to the parent frame
	 *               (can be {@code null} if no parent is needed).
	 */
	public static void panelCamposIncorrectos(JFrame parent) {
	    JOptionPane.showMessageDialog(parent, Messages.ERROR_CAMPOS_INCORRECTOS, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
