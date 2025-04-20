package presentacion;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;

import misc.Constants;
import misc.Pair;
import misc.SwingUtils;


@SuppressWarnings("serial")
public abstract class VistaDefault extends JFrame implements IGUI {
	/**
	 * Initializes and constructs a default GUI layout consisting of vertically stacked pairs of UI components,
	 * followed by a row of two response buttons (positive and negative) at the bottom. Optionally, the layout can 
	 * occupy the full screen.
	 *
	 * <p>The layout is visually structured as follows:</p>
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
	 * <p>If {@code fullScreen} is set to {@code true}, the layout will maximize the window to occupy the entire screen.
	 * Otherwise, it will scale based on the given screen size. The components in the {@code pairComponents} list are 
	 * placed in a vertically stacked layout, and only the non-null elements of each pair are added to the layout. 
	 * The response buttons are added if either button is non-null.</p>
	 *
	 * @param pairComponents an {@code ArrayList} of {@code Pair<JComponent, JComponent>} objects,
	 *                       where each pair represents two horizontally aligned components (e.g., label and field,
	 *                       or icon and input). Only the non-null element of the pair is added.
	 * @param positiveResponse the {@code JButton} that triggers a positive action (e.g., OK, Submit). Only added if not null.
	 * @param negativeResponse the {@code JButton} that triggers a negative or cancel action (e.g., Cancel, Back). Only added if not null.
	 * @param fullScreen if {@code true}, the window will be maximized to occupy the entire screen; otherwise, it will scale 
	 *                   based on a defined screen dimension.
	 * @return the {@code JPanel} containing the entire layout, including the vertical list of component pairs and the row 
	 *         of response buttons at the bottom.
	 */
	public JPanel initComps(ArrayList<Pair<JComponent, JComponent>> pairComponents,
	                        JButton positiveResponse, JButton negativeResponse, boolean fullScreen) 
	{
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		this.setSize(Constants.getScaledScreenDimension(4, 4));
		if (fullScreen) this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		else this.setResizable(false);
	
		if (!pairComponents.isEmpty()) {
		    JPanel infoPanel = new JPanel();
		    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		    for (Pair<JComponent, JComponent> p : pairComponents) {
		        infoPanel.add(SwingUtils.createComponentPair(p.getFirst(), p.getSecond()));
		    }
	
		    mainPanel.add(infoPanel, BorderLayout.NORTH);
	    }
	    
		if (positiveResponse != null || negativeResponse != null) {
		    JPanel responsePanel = SwingUtils.createResponsePair(positiveResponse, negativeResponse);
		    responsePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
		    mainPanel.add(responsePanel, BorderLayout.SOUTH);
		}
		this.add(mainPanel);
	    
	    return mainPanel;
	}
}
