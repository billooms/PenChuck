package billooms.penchuck.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.vecmath.Point2d;

/**
 * Interface defining things that are drawable
 * Note that all Drawable objects are defined in 2 dimensional space using XY coordinate system.
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
public interface Drawable {
	public final static BasicStroke SOLID_LINE = new BasicStroke(1.0f);
	public final static BasicStroke LIGHT_DOT = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, new float[] {1,5}, 0);
	public final static BasicStroke DEFAULT_STROKE = SOLID_LINE;

	/**
	 * Bounding box of a drawable shape
	 */
	public class BoundingBox {
		public Point2d min;
		public Point2d max;

		public BoundingBox(double xmin, double ymin, double xmax, double ymax) {
			min = new Point2d(xmin, ymin);
			max = new Point2d(xmax, ymax);
		}

		/**
		 * Create a bounding box which is encompasses both the given bounding boxes
		 * @param b1 BoundingBox
		 * @param b2 BoundingBox
		 */
		public BoundingBox(BoundingBox b1, BoundingBox b2) {
			min = new Point2d(Math.min(b1.min.x, b2.min.x), Math.min(b1.min.y, b2.min.y));
			max = new Point2d(Math.max(b1.max.x, b2.max.x), Math.max(b1.max.y, b2.max.y));
		}

		/**
		 * Scale the bounding box in place
		 * @param scale Scale factor
		 */
		public void scale(double scale) {
			min.x *= scale;
			min.y *= scale;
			max.x *= scale;
			max.y *= scale;
		}

		public double getWidth() {
			return max.x - min.x;
		}

		public double getHeight() {
			return max.y - min.y;
		}

		/**
		 * Round mins down to the floor, and maxs up to the ceiling
		 */
		public void round() {
			min.x = Math.floor(min.x);
			min.y = Math.floor(min.y);
			max.x = Math.ceil(max.x);
			max.y = Math.ceil(max.y);
		}
	}	// end BoundingBox

    /**
     * Paint the object
     * @param g Graphics g
     */
	public void paint(Graphics g, int dpi, Point zPix);

    /**
     * Set the object color
     * @param c Color c
     */
    public void setColor(Color c);

	/**
	 * Set the visibility of the object
	 * @param v true=visible; false=not drawn
	 */
	public void setVisible(boolean v);

}
