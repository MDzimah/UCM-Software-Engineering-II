package presentacion;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;

import misc.Constants;
import misc.Pair;
import misc.JSwingUtils;


@SuppressWarnings("serial")
public abstract class VistaDefault extends JFrame implements IGUI {
	/**
	 * Initializes a standard GUI layout with vertically stacked component pairs and optional 
	 * action buttons aligned at the bottom. This method is intended to streamline GUI construction 
	 * for consistent UI design across the application.
	 *
	 * <p>The layout follows this structure:</p>
	 * <pre>
	 * +---------------------------------------------------+
	 * |                                                   |
	 * |         [Component 1A]     [Component 1B]         |
	 * |         [Component 2A]     [Component 2B]         |
	 * |                        ...                        |
	 * |         [Component nA]     [Component nB]         |
	 * |                                                   |
	 * |                                                   |
	 * |       [Positive Button]   [Negative Button]       |
	 * +---------------------------------------------------+
	 * </pre>
	 *
	 * <p>Each entry in {@code pairComponents} represents a horizontal pair of components 
	 * (e.g., label and field), stacked vertically. Non-null components within each pair are added 
	 * to the layout. If action buttons are provided, they are added as a response row at the bottom 
	 * of the window.</p>
	 *
	 * <p>The window is set to a scaled size based on screen dimensions, and resizing is disabled.</p>
	 *
	 * @param pairComponents a list of {@code Pair<JComponent, JComponent>} where each pair represents 
	 *                       a row of horizontally-aligned UI components. Only non-null components are added.
	 * @param positiveResponse a {@code JButton} for confirming actions (e.g., OK or Submit). May be {@code null}.
	 * @param negativeResponse a {@code JButton} for cancelling or dismissing (e.g., Cancel or Back). May be {@code null}.
	 * @return the main {@code JPanel} containing the structured layout.
	 */
	public JPanel initComps(ArrayList<Pair<JComponent, JComponent>> pairComponents,
	                        JButton positiveResponse, JButton negativeResponse) 
	{
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		this.setSize(Constants.getScaledScreenDimension(4, 4));
		this.setResizable(false);
	
		if (!pairComponents.isEmpty()) {
		    JPanel infoPanel = new JPanel();
		    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		    for (Pair<JComponent, JComponent> p : pairComponents) {
		        infoPanel.add(JSwingUtils.createComponentPair(p.getFirst(), p.getSecond()));
		    }
	
		    mainPanel.add(infoPanel, BorderLayout.NORTH);
	    }
	    
		if (positiveResponse != null || negativeResponse != null) {
		    JPanel responsePanel = JSwingUtils.createResponsePair(positiveResponse, negativeResponse);
		    responsePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
		    mainPanel.add(responsePanel, BorderLayout.SOUTH);
		}
		this.add(mainPanel);
	    
	    return mainPanel;
	}
}
