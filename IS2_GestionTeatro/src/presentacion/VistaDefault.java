package presentacion;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
Vista default. initComps devuelve el infoPanel:
+--------------------------------------------------+
|                                                  |
|           [Label 1]     [Component 1]            |
|           [Label 2]     [Component 2]            |
|                                                  |
|                                                  |
|      [Positive Button]  [Negative Button]        |
+--------------------------------------------------+
*/

@SuppressWarnings("serial")
public abstract class VistaDefault extends JFrame {
	
	public JPanel initComps(JLabel label1, JComponent component1, 
			   JLabel label2, JComponent component2,
			   JButton postiveResponse, JButton negativeResponse) 
	{
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(Constants.getScaledScreenDimension(2, 2));
	
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(PanelUtils.createLabelComponentPair(label1, component1));
		infoPanel.add(PanelUtils.createLabelComponentPair(label2, component2));
		mainPanel.add(infoPanel, BorderLayout.CENTER);
		
		JPanel responsePanel = PanelUtils.createResponsePair(postiveResponse, negativeResponse);
		mainPanel.add(responsePanel, BorderLayout.SOUTH);
		return infoPanel;
	}
}
