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
	/*
	   +---------------------------------------------------+
	   |       [Componente 1A]     [Componente 1B]         |
	   |       [Componente 2A]     [Componente 2B]         |
	   |                        ...                        |
	   |       [Componente nA]     [Componente nB]         |
	   |                                                   |
	   |       [Positive Button]   [Negative Button]       |
	   +---------------------------------------------------+
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
