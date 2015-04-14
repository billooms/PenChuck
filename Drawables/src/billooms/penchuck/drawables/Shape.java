
package billooms.penchuck.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.vecmath.Point2d;

/**
 * Abstract definition of a shape that can be drawn.
 * Extend this to define lines with particular characteristics (i.e. FittedCurve, PiecedLine, etc).
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
public abstract class Shape implements Drawable {
	protected Color color;
	protected BasicStroke stroke;
	protected boolean visible = true;	// always visible unless specifically changed

	/**
	 * A drawable shape
	 * @param c Color
	 * @param s BasicStroke
	 */
	public Shape(Color c, BasicStroke s) {
		this.color = c;
		this.stroke = s;
	}

	/**
	 * A drawable shape (default stroke)
	 * @param c Color
	 */
	public Shape(Color c) {
        this(c, DEFAULT_STROKE);
	}

    /**
     * Paint the shape
     * @param g Graphics g
	 * @param dpi pixels per inch
	 * @param zPix zero location in pixels
     */
	@Override
	public void paint(Graphics g, int dpi, Point zPix) {	// This is further customized by extensions
		if (visible) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(color);
			g2d.setStroke(stroke);			
		}
	}

    /**
     * Set the shape color (does not change the color of the dots)
     * @param c Color c
     */
	@Override
    public void setColor(Color c) {
        this.color = c;
    }
	
	/**
	 * Set the visibility of the object
	 * @param v true=visible; false=not drawn
	 */
	@Override
	public void setVisible(boolean v) {
		visible = v;
	}

	/**
	 * Set the stroke for the shape
	 * @param s BasicStroke
	 */
	public void setStroke(BasicStroke s) {
		stroke = s;
	}

	/**
	 * Convert the inch location to a pixel location
	 * @param inch location in inches
	 * @param dpi pixels per inch
	 * @param zPix zero location in pixels
	 * @return point location in pixels (screen coordinates)
	 */
    protected Point scaleInchToPix(Point2d inch, int dpi, Point zPix) {
		return new Point(zPix.x + (int)(inch.x * dpi),
						 zPix.y - (int)(inch.y * dpi));
    }
}
