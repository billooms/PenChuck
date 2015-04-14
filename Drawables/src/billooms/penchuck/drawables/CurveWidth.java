package billooms.penchuck.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.vecmath.Point2d;

/**
 * A curve defined by an array of points and an array indicating width (in pixels)
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
public class CurveWidth extends Shape {
	private Point2d[] points;
	private int[] widths;

	/**
	 * A drawable curve defined by an array of points
	 * @param pts array of Point2d
	 * @param wids array of widths in pixels
	 * @param c Color
	 */
	public CurveWidth(Point2d[] pts, int[] wids, Color c) {
		super(c);
		this.points = pts;
		this.widths = wids;
	}

    /**
     * Paint the curve
     * @param g Graphics g
	 * @param dpi pixels per inch
	 * @param zPix zero location in pixels
     */
	@Override
	public void paint(Graphics g, int dpi, Point zPix) {
		if (points.length < 2)
			return;

		if (visible) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(color);
			Point p, pm1;
			for (int i = 1; i < points.length; i++) {
				stroke = new BasicStroke(widths[i], BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
				g2d.setStroke(stroke);
				p = scaleInchToPix(points[i], dpi, zPix);
				pm1 = scaleInchToPix(points[i-1], dpi, zPix);
				g2d.drawLine(pm1.x, pm1.y, p.x, p.y);
			}
		}
	}

	/**
	 * Get the number of points in the curve
	 * @return the number of points
	 */
	public int getSize() {
		return points.length;
	}

	/**
	 * Get the array of Point2d that defines the curve
	 * @return array of Point2d
	 */
	public Point2d[] getPoints() {
		return points;
	}

	/**
	 * Set the array of points defining the curve
	 * @param pts array of Point2d that defines the curve
	 */
	public void setPoints(Point2d[] pts) {
		this.points = pts;
	}

	/**
	 * Get the array of widths
	 * @return array of widths
	 */
	public int[] getWidths() {
		return widths;
	}

	/**
	 * Set the array of widths
	 * @param wids widths (in pixels)
	 */
	public void setWidths(int[] wids) {
		this.widths = wids;
	}
}
