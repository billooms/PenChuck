package billooms.penchuck.rosettemodel.api;

import java.awt.Graphics;
import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

/**
 * This describes a Rose Engine rosette wheel.
 * @author Bill Ooms Copyright (c) 2010 Studio of Bill Ooms all rights reserved
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public interface Rosette extends Serializable {

	/**
	 * This defines the names for various styles of rosettes
	 */
	public enum Styles {
		NONE("None"),
		NSIDE("N-Side (C)"),
		FLOWER("Flower (D)"),
		SINE("Sine (A)"),
		HEART("Heart (F)"),
		LOTUS("Lotus"),
		BIGSMALL("BigSmall (B)"),
		BIGSMALL_("BigSmall Inverse"),
		TRIANGLE("Triangle"),
		TUDOR("Tudor Rose"),
		INDEX("Index");
		
		public String text;

		private Styles(String s) {
			text = s;
		}
	}
	
	public final static Styles DEFAULT_STYLE = Styles.NSIDE;
	public final static int DEFAULT_REPEAT = 4;
	public final static double DEFAULT_PTOP = 0.1;
	public final static double DEFAULT_PHASE = 0.0;
	public final static double DEFAULT_PUMP_AMP = 0.0;
	public final static double DEFAULT_PUMP_PH = 0.0;

	/**
	 * Get the name of the rosette (STYLE + REPEAT)
	 * @return the name of the rosette
	 */
	public String getName();

	/**
	 * Get the style of the rosette
	 * @return style of the rosette
	 */
	public Styles getStyle();

	/**
	 * Set the style of the rosette
	 * @param s integer number for Styles.values()[]
	 */
	public void setStyle(int s);

	/**
	 * Set the style of the rosette
	 * @param s one of the Styles
	 */
	public void setStyle(Styles s);

	/**
	 * Get the number of repeats on the rosette
	 * @return number of repeats
	 */
	public int getRepeat();

	/**
	 * Set the number of repeats on the rosette
	 * @param n number of repeats
	 */
	public void setRepeat(int n);

	/**
	 * Get the peak-to-peak amplitude of the rosette
	 * @return peak-to-peak amplitude
	 */
	public double getPToP();

	/**
	 * Set the peak-to-peak amplitude of the rosette
	 * @param p peak-to-peak amplitude
	 */
	public void setPToP(double p);

	/**
	 * Get the phase of the rosette
	 * @return phase in degrees: 180 means 1/2 of the repeat, 90 means 1/4 of the repeat, etc.
	 */
	public double getPhase();

	/**
	 * Set the phase of the rosette
	 * @param ph phase in degrees: 180 means 1/2 of the repeat, 90 means 1/4 of the repeat, etc.
	 */
	public void setPhase(double ph);

	/**
	 * Draw the rosette at a given DPI
	 * @param g Graphic context
	 * @param dpi desired dots per inch on the screen
	 * @param zPix desired zero position on the screen
	 */
	public void draw(Graphics g, int dpi, Point zPix);

	/**
	 * Get the amplitude (offset from nominal radius) of the rosette at a given angle in degrees.
	 * A value of zero means zero cutter deflection from its nominal radius.
	 * @param ang Angle in degrees around the rosette
	 * @return amplitude which will be a positive number from 0.0 to pToP
	 */
	public double getAmplitudeAt(double ang);

	/**
	 * Add a property change listener for the rosette
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Remove the given property change listener
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);
}
