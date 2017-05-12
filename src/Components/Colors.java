package Components;

import java.awt.Color;

/**
 * <h1>Colors</h1>Save location for colors throughout the application. Useful to
 * keep a consistent color palette.
 * 
 * @author James F. Taylor
 *
 */
public class Colors {
	private Color darkBlue = new Color(32, 42, 71);
	private Color darkGreen = new Color(32, 71, 42);
	private Color Orange = new Color(230, 117, 72);
	private Color Yellow = new Color(255, 127, 80);

	public Color getDarkBlue() {
		return darkBlue;
	}

	public void setDarkBlue(Color darkBlue) {
		this.darkBlue = darkBlue;
	}

	public Color getDarkGreen() {
		return darkGreen;
	}

	public void setDarkGreen(Color darkGreen) {
		this.darkGreen = darkGreen;
	}

	public Color getOrange() {
		return Orange;
	}

	public void setOrange(Color darkOrange) {
		this.Orange = darkOrange;
	}
}
