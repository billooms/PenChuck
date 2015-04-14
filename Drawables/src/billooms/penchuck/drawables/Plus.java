package billooms.penchuck.drawables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.vecmath.Point2d;

/**
 * A Drawable plus (+) defined by inch(cm) location
 * @author Bill Ooms. Copyright 2010 Studio of Bill Ooms. All rights reserved.
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
public class Plus extends Pt {
    public final static Color PLUS_COLOR = Color.RED;

	/**
	 * A drawable plus defined by inch location
	 * @param pos plus location in inches
	 * @param c Color
	 */
	public Plus(Point2d pos, Color c) {
		super(pos, c);
	}

	/**
	 * A drawable plus defined by inch location (default color)
	 * @param pos plus location in inches
	 */
    public Plus(Point2d pos) {
		super(pos, PLUS_COLOR);
    }

    /**
     * Paint the plus
     * @param g Graphics g
	 * @param dpi pixels per inch
	 * @param zPix zero location in pixels
     */
	@Override
	public void paint(Graphics g, int dpi, Point zPix) {
		if (visible) {
			Point pix = getPix(dpi, zPix);
			g.setColor(color);
			g.drawLine(pix.x, pix.y + ptSize / 2, pix.x, pix.y - ptSize / 2);
			g.drawLine(pix.x + ptSize / 2, pix.y, pix.x - ptSize / 2, pix.y);
		}
	}

}
