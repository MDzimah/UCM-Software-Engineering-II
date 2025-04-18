package misc;

import java.awt.*;

import javax.swing.*;

public class PanelUtils {
	
	/**
	 * Creates a horizontally aligned {@code JPanel} containing two {@code JComponent}s with adjustable spacing between them.
	 * <p>
	 * The panel uses a horizontal {@code BoxLayout} and includes a fixed horizontal strut for consistent spacing between the
	 * two components. The entire panel is center-aligned and includes vertical padding, making it ideal for use in vertically
	 * stacked layouts such as {@code BoxLayout.Y_AXIS}, where each row should be centered and evenly spaced. The spacing
	 * between the components is only applied when both components are present.
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
	  	if (component1 != null && component2 != null) pair.add(Box.createHorizontalStrut(30));
	  	if (component2 != null) pair.add(component2);
	    
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
