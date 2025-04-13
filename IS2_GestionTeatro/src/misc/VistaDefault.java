package misc;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;


@SuppressWarnings("serial")
public abstract class VistaDefault extends JFrame {
	/**
	 * Initializes and constructs a default GUI layout consisting of label-component pairs
	 * and two response buttons (positive and negative). The visual layout is structured as follows:
	 *
	 * <pre>
	 * +--------------------------------------------------+
	 * |                                                  |
	 * |           [Label 1]     [Component 1]            |
	 * |           [Label 2]     [Component 2]            |
	 * |                     ...                          |
	 * |           [Label n]     [Component n]            |						
	 * |                                                  |
	 * |                                                  |
	 * |      [Positive Button]  [Negative Button]        |
	 * +--------------------------------------------------+
	 * </pre>
	 *
	 * @param labeledComponents an {@code ArrayList} of {@code Pair<JLabel, JComponent>} objects,
	 *                          where each pair represents a label and its associated UI component (e.g., a text field)
	 * @param positiveResponse the {@code JButton} that triggers a positive action (e.g., OK, Submit)
	 * @param negativeResponse the {@code JButton} that triggers a negative or cancel action (e.g., Cancel, Back)
	 * @return the {@code JPanel} containing the vertical list of label-component pairs (the central info panel)
	 */

	public JPanel initComps(ArrayList<Pair<JLabel, JComponent>> labeledComponents,
			   JButton postiveResponse, JButton negativeResponse) 
	{
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(Constants.getScaledScreenDimension(2, 2));
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		for (Pair<JLabel, JComponent> p : labeledComponents) {
			infoPanel.add(PanelUtils.createLabelComponentPair(p.getFirst(), p.getSecond()));
		}
		mainPanel.add(infoPanel, BorderLayout.CENTER);
		
		JPanel responsePanel = PanelUtils.createResponsePair(postiveResponse, negativeResponse);
		mainPanel.add(responsePanel, BorderLayout.SOUTH);
		return infoPanel;
	}
}
