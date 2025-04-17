package misc;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
	 * Shows an error dialog when a problem occurs while reading from a database file.
	 * <p>
	 * This dialog alerts the user that the specified database could not be read correctly.
	 * The message can be customized and typically includes the database name or path.
	 *
	 * @param parent       the parent frame for the dialog, used to position it relative to the parent;
	 *                     can be {@code null} if there is no parent frame.
	 * @param BBDDMessage  the error message to display, usually including the database name or description.
	 */
	public static void panelBBDDReadError(JFrame parent, String BBDDMessage) {
		JOptionPane.showMessageDialog(parent,
				BBDDMessage,
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
		JOptionPane.showMessageDialog(parent,
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
	 * Creates a horizontally aligned {@code JPanel} containing two {@code JComponent}s with adjustable spacing between them.
	 * <p>
	 * The panel uses a horizontal {@code BoxLayout} and includes a fixed horizontal strut for consistent spacing between the
	 * two components. The entire panel is center-aligned and includes vertical padding, making it ideal for use in vertically
	 * stacked layouts such as {@code BoxLayout.Y_AXIS}, where each row should be centered and evenly spaced. The spacing
	 * between the components is dynamically adjustable based on the panel's size, making it responsive to changes in the window
	 * or container size. The spacing is only applied when both components are present.
	 * </p>
	 *
	 * @param component1 the first {@code JComponent} to be added (e.g., {@code JLabel}, {@code JTextField}, etc.)
	 * @param component2 the second {@code JComponent} to be added (e.g., {@code JTextField}, {@code JButton}, etc.)
	 * @return a {@code JPanel} containing the two components arranged horizontally with adjustable spacing and vertical padding.
	 *         If either component is {@code null}, only the non-null component will be added, with spacing adjusted accordingly.
	 */
	public static JPanel createComponentPair(JComponent component1, JComponent component2) {
	    JPanel pair = new JPanel();
	    pair.setLayout(new BoxLayout(pair, BoxLayout.X_AXIS));
	    pair.setAlignmentX(Component.CENTER_ALIGNMENT);
	    pair.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

	    if (component1 != null) pair.add(component1);
	    Box strut;
	    if (component2 != null) {
	    	pair.add(component2);
	    	if (component1 != null) {
	    		strut = (Box) Box.createHorizontalStrut(10);
		    	pair.add(strut);
		    	
		    	//Para que el espacio entre botones del panel se redimensione dinámicamente
			    pair.addComponentListener(new ComponentAdapter() {
			        @Override
			        public void componentResized(ComponentEvent e) {
			            int width = pair.getWidth();
			            //Ajusta el espaciado basado en el panel
			            int spacing = Math.max(10, width / 50);
			            strut.setPreferredSize(new Dimension(spacing, 0));
			            pair.revalidate();
			        }
			    });
	    	}
	    }
	    
	    return pair;
	}
	
	/**
	 * Creates a JPanel containing two JButton components arranged horizontally at the center. Only the non-null are added.
	 *  
	 * @param button1 the first JButton to be added to the panel
	 * @param button2 the second JButton to be added to the panel
	 * @return JPanel containing the two buttons arranged in the center
	 */
	public static JPanel createResponsePair(JButton button1, JButton button2) {
        JPanel pair = new JPanel();
        pair.setLayout(new FlowLayout(FlowLayout.CENTER));
        if (button1 != null) pair.add(button1);
        if (button2 != null) pair.add(button2);
        return pair;
    }
}
