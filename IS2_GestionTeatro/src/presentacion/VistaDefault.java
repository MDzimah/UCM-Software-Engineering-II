package presentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.border.Border;

import misc.Pair;


@SuppressWarnings("serial")
public abstract class VistaDefault extends JFrame implements IGUI {
	/**
	 * Initializes and lays out a standard GUI form with vertically stacked rows of paired components 
	 * and optional action buttons at the bottom. This method ensures consistent UI structure across 
	 * application windows using {@code GroupLayout} for flexible and dynamic component alignment.
	 *
	 * <p>The layout has the following structure:</p>
	 * <pre>
	 * +---------------------------------------------------+
	 * |         [Component 1A]     [Component 1B]         |
	 * |         [Component 2A]     [Component 2B]         |
	 * |                        ...                        |
	 * |         [Component nA]     [Component nB]         |
	 * |                                                   |
	 * |       [Positive Button]   [Negative Button]       |
	 * +---------------------------------------------------+
	 * </pre>
	 *
	 * <p>Each entry in {@code pairComponents} is displayed as a row with two horizontally-aligned components 
	 * (e.g., label and field). The layout dynamically adjusts to fit the components using {@code pack()}.</p>
	 *
	 * <p>If {@code positiveResponse} or {@code negativeResponse} buttons are provided, they are placed 
	 * at the bottom of the window in a separate response panel.</p>
	 *
	 * <p>Padding is applied around the component group to provide spacing within the window.
	 * The window is automatically sized to fit its content and displayed centered relative to the screen.</p>
	 *
	 * @param pairComponents a list of {@code Pair<JComponent, JComponent>} representing rows of paired components.
	 * @param positiveResponse a {@code JButton} used for confirmation actions (e.g., OK or Submit), or {@code null}.
	 * @param negativeResponse a {@code JButton} used for cancellation or back actions, or {@code null}.
	 */
	public void initComps(ArrayList<Pair<JComponent, JComponent>> pairComponents,
	                        JButton positiveResponse, JButton negativeResponse) 
	{
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		boolean validInfoPanel = pairComponents != null && !pairComponents.isEmpty();
		if (validInfoPanel) {
			JPanel infoPanel = new JPanel();
			infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 0, 40));
			GroupLayout gL = new GroupLayout(infoPanel);
			infoPanel.setLayout(gL);
			gL.setAutoCreateGaps(true);
			gL.setAutoCreateContainerGaps(true);
		
			Group hor = gL.createSequentialGroup(); //El grupo de componentes si lees izquierda a derecha, por columnas
			Group horTrailing = gL.createParallelGroup(GroupLayout.Alignment.TRAILING);
			Group horLeading = gL.createParallelGroup(GroupLayout.Alignment.LEADING);
			Group vert = gL.createSequentialGroup(); //El grupo de componentes si lees de arriba a abajo, por filas
			for (Pair<JComponent, JComponent> p : pairComponents) {
				horTrailing.addComponent(p.getFirst());
				horLeading.addComponent(p.getSecond());
				vert.addGroup(gL.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(p.getFirst())
						.addComponent(p.getSecond())
				);
			}
			hor.addGroup(horTrailing).addGroup(horLeading);
			
			gL.setVerticalGroup(vert);
			gL.setHorizontalGroup(hor);
		    mainPanel.add(infoPanel, BorderLayout.NORTH);
	    }
	    
		if (positiveResponse != null || negativeResponse != null) {
		    JPanel responsePanel = this.createResponsePair(positiveResponse, negativeResponse);
		    Border b;
		    String where;
		    if (validInfoPanel) {
		    	b = BorderFactory.createEmptyBorder(30, 20, 10, 20);
		    	where = BorderLayout.SOUTH;
		    }
		    else {
		    	b = BorderFactory.createEmptyBorder(30, 60, 30, 60);
		    	where = BorderLayout.CENTER;
		    }
		    responsePanel.setBorder(b);
		    mainPanel.add(responsePanel,where);
		}
		this.add(mainPanel);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	 
	private JPanel createResponsePair(JButton button1, JButton button2) {
       JPanel pair = new JPanel();
       pair.setLayout(new FlowLayout(FlowLayout.CENTER));
       if (button1 != null) pair.add(button1);
       if (button2 != null) pair.add(button2);
       return pair;
   }
	
}
