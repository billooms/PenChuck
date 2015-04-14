package billooms.penchuck.drawables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.vecmath.Point2d;

/**
 * Abstract definition of a drawable point defined by inch(cm) position.
 * Extend this to define points with a particular visible shape (i.e. Dot, Plus, etc).
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
public abstract class Pt implements Drawable {
	public final static int PT_SIZE = 6;	// defauls size of a drawn point (Dot, Plus, SquarePt, etc)
	
	protected Point2d pos;					// position in inches
	protected Color color;
	protected boolean visible = true;		// always visible unless specifically changed
    protected int ptSize = PT_SIZE;			// size of the point in pixels

	/**
	 * A drawable point defined by inch position
	 * @param pos point position in inches
	 * @param c Color
	 */
    public Pt(Point2d pos, Color c) {
		this.pos = pos;
        this.color = c;
    }

    /**
     * Paint the point
     * @param g Graphics g
	 * @param dpi pixels per inch
	 * @param zPix zero position in pixels
     */
	@Override
	public void paint(Graphics g, int dpi, Point zPix) {	// This is further customized by extensions
		if (visible) {
			Graphics2D g2d = (Graphics2D) g;	// 1 pixel dots unless overridden
			g2d.setStroke(SOLID_LINE);
			g2d.setColor(color);
			Point pp = getPix(dpi, zPix);
			g2d.drawLine(pp.x, pp.y, pp.x, pp.y);
		}
	}

    /**
     * Set the point color
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
     * Set the point size (diameter) in pixels
     * @param s Size in pixels
     */
    public void setSize(int s) {
        this.ptSize = s;
    }

	/**
	 * Get the point position in pixels (screen coordinates)
	 * @param dpi pixels per inch
	 * @param zPix zero position in pixels
	 * @return point position in pixels (screen coordinates)
	 */
	public Point getPix(int dpi, Point zPix) {
		return scaleInchToPix(dpi, zPix);
	}

	/**
	 * Get the point position in inches
	 * @return point position in inches
	 */
    public Point2d getPos() {
        return pos;
    }

	/**
	 * Move the point to a new point defined in inches
	 * @param p new point in inches
	 */
    public void moveTo(Point2d pos) {
		this.pos = pos;
    }

	/**
	 * Get the separation from this point to the specified point
	 * @param p point in inches
	 * @return distance in inches
	 */
	public double separation(Point2d p) {
		return Math.hypot(p.x - pos.x, p.y - pos.y);
	}

	/**
	 * Invert the point around the y-axis (i.e. x = x; y = -y)
	 */
	public void invert() {
		pos.y = -pos.y;
	}

	/**
	 * Offset in the y-direction by subtracting the given value from y-coordinate
	 * @param deltaY offset amount (in inches);
	 */
	public void offsetY(double deltaY) {
		pos.y = pos.y - deltaY;
	}

	/**
	 * Convert the inch position to a pixel position
	 * @param dpi pixels per inch
	 * @param zPix zero position in pixels
	 * @return point position in pixels (screen coordinates)
	 */
    private Point scaleInchToPix(int dpi, Point zPix) {
		return new Point(zPix.x + (int)(pos.x * dpi),
						 zPix.y - (int)(pos.y * dpi));
    }
}
