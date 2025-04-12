package presentacion;

import java.awt.*;

public class Constants {
	/**
	 * Returns the scaled screen dimensions.
	 *
	 * @return Dimension object representing the screen dimensions
	 */
	public static final Dimension screenDimension() { return getScaledScreenDimension(1,1); }
	
	/**
	 * Returns the scaled screen dimensions based on the given height and width factors.
	 * The screen's width and height are divided by the provided factors (factorH and factorW).
	 *
	 * @param factorH the factor by which the screen's height is scaled
	 * @param factorW the factor by which the screen's width is scaled
	 * @return Dimension object representing the scaled screen dimensions
	 */
	public static Dimension getScaledScreenDimension(int factorH, int factorW) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension(screenSize.width/factorH, screenSize.height/factorH);
	}
}
