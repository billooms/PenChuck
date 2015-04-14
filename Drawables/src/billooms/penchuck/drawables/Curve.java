package billooms.penchuck.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import javax.vecmath.Point2d;

/**
 * A curve defined by an array of points
 * Points are ordered, bottom first and top last
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
public class Curve extends Shape {
    public final static Color CURVE_COLOR = Color.YELLOW;
	private final static double EPSILON = 0.001;	// a point is equal if x & y within EPSILON
	private Point2d[] points;

	/**
	 * A drawable curve defined by an array of points
	 * @param pts array of Point2d
	 * @param c Color
	 * @param s BasicStroke
	 */
	public Curve(Point2d[] pts, Color c, BasicStroke s) {
		super(c, s);
		this.points = pts;
	}

	/**
	 * A drawable curve defined by an array of points
	 * @param pts array of Point2d
	 * @param c Color
	 */
	public Curve(Point2d[] pts, Color c) {
		this(pts, c, DEFAULT_STROKE);
	}

	/**
	 * A drawable curve defined by an array of points (default color)
	 * @param pts array of Point2d
	 */
	public Curve(Point2d[] pts) {
		this(pts, CURVE_COLOR);
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
			GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, points.length);
			Point pix = scaleInchToPix(points[0], dpi, zPix);
			polyline.moveTo(pix.x, pix.y);
			for (Point2d pt : points) {
				pix = scaleInchToPix(pt, dpi, zPix);
				polyline.lineTo(pix.x, pix.y);
			}

			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(color);
			g2d.setStroke(stroke);
			g2d.draw(polyline);
		}
	}

	/**
	 * Get the array of Point2d that defines the curve
	 * @return array of Point2d
	 */
	public Point2d[] getPoints() {
		return points;
	}

	/**
	 * Get the number of points in the curve
	 * @return the number of points
	 */
	public int getSize() {
		return points.length;
	}

	/**
	 * Clear the points associated with this curve
	 */
	public void clear() {
		this.points = new Point2d[0];
	}

	/**
	 * Set the array of points defining the curve
	 * @param pts array of Point2d that defines the curve
	 */
	public void setPoints(Point2d[] pts) {
		this.points = pts;
	}

	/**
	 * Compare two points to see if they are about equal (i.e. within EPSILON)
	 * @param p0 First point
	 * @param p1 Second point
	 * @return true if both x and y are within EPSILON of each other
	 */
	private boolean aboutEqual(Point2d p0, Point2d p1) {
		return ((Math.abs(p0.x - p1.x) <= EPSILON) && (Math.abs(p0.y - p1.y) <= EPSILON));
	}

	/**
	 * Find the nearest point on the curve to the given point
	 * @param pInch Given point in inches
	 * @return the nearest point on the curve (null if no points)
	 */
	public Point2d nearestPoint(Point2d pInch) {
		if (points.length == 0) 
			return null;
		if (points.length == 1) 
			return new Point2d(points[0]);
		Point2d closePt = points[0];
		double dist = closePt.distance(pInch);
		for (Point2d pt : points) {
			if (pt.distance(pInch) < dist) {
				dist = pt.distance(pInch);
				closePt = pt;
			}
		}
		return new Point2d(closePt);	// return a copy, not the original
	}

	/**
	 * Find the index of the nearest point on the curve to the given point
	 * @param p Given point in inches
	 * @return index of the nearest point on the curve (-1 if no points)
	 */
	private int idxOfNearestPoint(Point2d pInch) {
		if (points.length == 0) 
			return -1;
		if (points.length == 1) 
			return 0;
		double dist = points[0].distance(pInch);
		int closeIdx = 0;
		for (int i = 0; i < points.length; i++) {
			Point2d pt = points[i];
			if (pt.distance(pInch) < dist) {
				dist = pt.distance(pInch);
				closeIdx = i;
			}
		}
		return closeIdx;
	}

	/**
	 * Make a subset of points on the curve.
	 * Include all points between p0 and p1 (inclusive).
	 * If points are off the curve, use the nearest point.
	 * @param p0 first point
	 * @param p1 second point
	 * @return array of points between p0 and p1 (inclusive) (null if no points on curve)
	 */
	public Point2d[] subsetPoints(Point2d p0, Point2d p1) {
		int i0 = idxOfNearestPoint(p0);
		int i1 = idxOfNearestPoint(p1);
		if ((i0 == -1) || (i1 == -1)) 
			return null;
		Point2d[] pts = new Point2d[Math.abs(i1-i0)+1];
		int idx = 0;
		if (i0 < i1) {		// points are either bottom->up or top->down (depends on order of p0,p1)
			for (int i = i0; i <= i1; i++) {
				pts[idx] = points[i];
				idx++;
			}
		} else {
			for (int i = i0; i >= i1; i--) {
				pts[idx] = points[i];
				idx++;
			}
		}
		return pts;
	}

	/**
	 * Flip the curve by changing sign of x-coordinates of all points
	 */
	public void flipX() {
		for (int i = 0; i < points.length; i++) {
			points[i].x = -points[i].x;		// change sign of x
		}
		return;
	}

	/**
	 * Move the curve by moving all points parallel with the tangent at each point.
	 * @param cut offset amount
	 */
	public void offsetPts(double cut) {
		setPoints(ptsOffsetBy(cut));
	}

	/**
	 * Return an array of points which are offset from this curve by the specified amount.
	 * This assumes cutting on the inside (use negative value for cutting outside)
	 * @param cut offset amount
	 * @return new array of Point2d representing the points on a new curve
	 */
	public Point2d[] ptsOffsetBy(double cut) {
		if (points.length <= 1) {	// no offset for one point
			return points;
		}

		Point2d[] newPts = new Point2d[points.length];
		for (int i = 0; i < points.length; i++) {
			int im1 = Math.max(i - 1, 0);
			int ip1 = Math.min(i + 1, points.length - 1);
			if (points[im1].x == points[ip1].x) {		// check for vertical
				newPts[i] = new Point2d(points[i].x + cut, points[i].y);	// if vertical, move x-direction only
//			} else if (points[im1].y == points[ip1].y) {	// check for horizontal
//				newPts[i] = new Point2d(points[i].x, points[i].y - cut);	// if horizontal, move y-direction only
			} else {
				double theta = Math.atan2(points[ip1].y - points[im1].y, points[ip1].x - points[im1].x);
				newPts[i] = new Point2d(points[i].x + cut * Math.sin(theta),
						points[i].y - cut * Math.cos(theta));
			}
		}
		return newPts;
	}

	/**
	 * Get the bounding box for the points defining this shape
	 * @return bounding box (which might not include 0.0, 0.0)
	 */
	public BoundingBox getBoundingBox() {
		if (points.length == 0) {
			return new BoundingBox(0.0, 0.0, 0.0, 0.0);
		}
		Point2d p0 = points[0];
		double minX = p0.x, maxX = p0.x, minY = p0.y, maxY = p0.y;
		for (Point2d p : points) {
			if (p.y < minY) {
				minY = p.y;
			}
			if (p.y > maxY) {
				maxY = p.y;
			}
			if (p.x > maxX) {
				maxX = p.x;
			}
			if (p.x < minX) {
				minX = p.x;
			}
		}
		return new BoundingBox(minX, minY, maxX, maxY);
	}

	/**
	 * get the total length of the curve
	 * @return total length
	 */
	public double getLength() {
		if (points.length < 2)
			return 0.0;
		double length = 0.0;
		for (int i = 0; i < points.length-1; i++) {
			Point2d p0 = points[i];
			Point2d p1 = points[i+1];
			length += Math.hypot(p0.x - p1.x, p0.y - p1.y);
		}
		return length;
	}
}
