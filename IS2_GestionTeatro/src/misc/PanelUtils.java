package misc;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.*;

public class PanelUtils {
	/**
	 * Displays an error message dialog indicating that some fields are incorrect.
	 * The dialog will notify the user to complete all fields.
	 *
	 * @param parent the parent frame for the dialog, used to center the dialog relative to the parent frame
	 *               (can be {@code null} if no parent is needed).
	 */
	public static void panelCamposIncorrectos(JFrame parent) {
	    JOptionPane.showMessageDialog(parent, 
	    		Messages.ERROR_CAMPOS_INCORRECTOS, 
	    		"Error", 
	    		JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays an error message dialog indicating that a problem occurred while reading from the database file.
	 * The dialog will notify the user that the database could not be read properly.
	 *
	 * @param parent the parent frame for the dialog, used to center the dialog relative to the parent frame
	 *               (can be {@code null} if no parent is needed).
	 * @param BBDD   the name or path of the database file involved in the read error.
	 * @param message the message template to display, typically with a placeholder for the database name.
	 */
	public static void panelBBDDReadError(JFrame parent, String BBDD, String message) {
		JOptionPane.showMessageDialog(null,
                message.formatted(BBDD),
                "Error Lectura",
                JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays an error message dialog indicating that a problem occurred while writing to the database file.
	 * The dialog will notify the user that the database could not be saved properly.
	 *
	 * @param parent the parent frame for the dialog, used to center the dialog relative to the parent frame
	 *               (can be {@code null} if no parent is needed).
	 * @param BBDD   the name or path of the database file involved in the write error.
	 * @param message the message template to display, typically with a placeholder for the database name.
	 */
	public static void panelBBDDWriteError(JFrame parent, String BBDD, String message) {
		JOptionPane.showMessageDialog(null,
                message.formatted(BBDD),
                "Error Escritura",
                JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays an informational message dialog with the specified message.
	 * The dialog is centered relative to the provided parent frame.
	 *
	 * @param parent  the parent frame for the dialog, used to center the dialog relative to the parent frame
	 *                (can be {@code null} if no parent is needed).
	 * @param message the message to display in the dialog, typically a user-facing notification or status update.
	 */
	public static void panelMessage(JFrame parent, String message) {
		JOptionPane.showMessageDialog(parent, message);
	}
	
	/**
	 * Creates a JPanel containing a JLabel and any type of JComponent arranged horizontally.
	 * Adds a horizontal strut for spacing between the label and the component.
	 * The resulting panel is horizontally centered within any vertically stacked layout (e.g., BoxLayout.Y_AXIS),
	 * making it suitable for use in vertically structured forms where each row is centered.
	 * Adds vertical padding above and below the row for consistent spacing between multiple pairs.
	 *
	 * @param label the JLabel to be placed next to the component
	 * @param component the JComponent to be placed next to the label (e.g., JTextField, JButton, etc.)
	 * @return JPanel containing the horizontally aligned JLabel and JComponent, with center alignment and padding
	 */
	public static JPanel createLabelComponentPair(JLabel label, JComponent component) {
	    JPanel pair = new JPanel();
	    pair.setLayout(new BoxLayout(pair, BoxLayout.X_AXIS));
	    pair.setAlignmentX(Component.CENTER_ALIGNMENT);
	    pair.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	    
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
}
