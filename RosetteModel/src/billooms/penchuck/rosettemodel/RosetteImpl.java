
package billooms.penchuck.rosettemodel;

import billooms.penchuck.drawables.Curve;
import billooms.penchuck.drawables.Drawable;
import billooms.penchuck.drawables.Plus;
import billooms.penchuck.rosettemodel.api.Rosette;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javax.vecmath.Point2d;

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
public class RosetteImpl implements Rosette {
	public final static String PROP_AMP = "amplitude";
	public final static String PROP_PHASE = "phase";
	public final static String PROP_REPEAT = "repeat";
	public final static String PROP_STYLE = "style";
    public final static double DEFAULT_RADIUS = 2.5;

    private Pattern pattern;		// pattern to use (which has style and repeat)
    private double pToP;			// Peak-to-Peak
    private double phase;			// phase shift in degrees (0 to 360) relative to one repetition, +phase is CCW

	/* Information for drawing */
    private final static BasicStroke SOLID_LINE = new BasicStroke(1.0f);
    private final static BasicStroke DOT_LINE = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, new float[]{3, 3}, 0);
    private final static Color OUTLINE_COLOR = Color.BLACK;
    private final static Color RADIUS_COLOR = Color.BLUE;

    private final double nomRadius = DEFAULT_RADIUS;		// nominal (reference) radius of the rosette
    private final Point2d center = new Point2d(0.0, 0.0);   // center of the rosette is always 0.0, 0.0
    private ArrayList<Drawable> drawList;   // a list of things to draw for a visual representaiton of the rosette
	private PropertyChangeSupport pss;

    /**
     * Define a rosette
     * @param st Style
     * @param rep Repeat
     * @param pp Peak-to-Peak
     * @param ph Phase in degrees, where 360 means one pattern repeat
     */
    public RosetteImpl(Styles st, int rep, double pp, double ph) {
        pattern = new Pattern(st, rep);
		if (pattern.getStyle() == Styles.NONE) {
			this.pToP = 0.0;
			this.phase = 0.0;
		} else {
			this.pToP = pp;
			this.phase = angleCheck(ph);
		}
        drawList = new ArrayList<Drawable>();
		this.pss = new PropertyChangeSupport(this);
        makeDrawables();
    }

    /**
     * Define a rosette with default values
     */
    public RosetteImpl() {
        this(DEFAULT_STYLE, DEFAULT_REPEAT, DEFAULT_PTOP, DEFAULT_PHASE);
    }

	/**
	 * Get the name of the rosette (STYLE + REPEAT)
	 * @return the name of the rosette
	 */
	@Override
	public String getName() {
        return pattern.getName() + Integer.toString(pattern.getRepeat());
	}

	/**
	 * Get the style of the rosette
	 * @return style of the rosette
	 */
	@Override
	public Styles getStyle() {
        return pattern.getStyle();
	}

	/**
	 * Set the style of the rosette
	 * @param s integer number for Styles.values()[]
	 */
	@Override
	public void setStyle(int s) {
        setStyle(Styles.values()[s]);
	}

	/**
	 * Set the style of the rosette
	 * @param s one of the Styles
	 */
	@Override
	public void setStyle(Styles s) {
		Styles old = pattern.getStyle();
		pattern.setStyle(s);
		if (pattern.getStyle() == Styles.NONE) {
			this.pToP = 0.0;
			this.phase = 0.0;
		}
        makeDrawables();
		this.pss.firePropertyChange(PROP_STYLE, old, s);
	}

	/**
	 * Get the number of repeats on the rosette
	 * @return number of repeats
	 */
	@Override
	public int getRepeat() {
        return pattern.getRepeat();
	}

	/**
	 * Set the number of repeats on the rosette
	 * @param n number of repeats
	 */
	@Override
	public void setRepeat(int n) {
		int old = pattern.getRepeat();
		pattern.setRepeat(n);
        makeDrawables();
		this.pss.firePropertyChange(PROP_REPEAT, old, n);
	}

	/**
	 * Get the peak-to-peak amplitude of the rosette
	 * @return peak-to-peak amplitude
	 */
	@Override
	public double getPToP() {
        return pToP;
	}

	/**
	 * Set the peak-to-peak amplitude of the rosette
	 * @param p peak-to-peak amplitude
	 */
	@Override
	public void setPToP(double p) {
        double old = this.pToP;
		this.pToP = p;
		if (pattern.getStyle() == Styles.NONE) {
			this.pToP = 0.0;
		}
        makeDrawables();
		this.pss.firePropertyChange(PROP_AMP, old, p);
	}

	/**
	 * Get the phase of the rosette
	 * @return phase in degrees: 180 means 1/2 of the repeat, 90 means 1/4 of the repeat, etc.
	 */
	@Override
	public double getPhase() {
        return phase;
	}

	/**
	 * Set the phase of the rosette
	 * @param ph phase in degrees: 180 means 1/2 of the repeat, 90 means 1/4 of the repeat, etc.
	 */
	@Override
	public void setPhase(double ph) {
		double old = this.phase;
		this.phase = angleCheck(ph);
		if (pattern.getStyle() == Styles.NONE) {
			this.phase = 0.0;
		}
        makeDrawables();
		this.pss.firePropertyChange(PROP_PHASE, old, ph);
	}

	/**
	 * Draw the rosette at a given DPI
	 * @param g Graphic context
	 * @param dpi desired dots per inch on the screen
	 * @param zPix desired zero position on the screen
	 */
	@Override
	public void draw(Graphics g, int dpi, Point zPix) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(SOLID_LINE);

        for (Drawable item : drawList) {	// paint everything in the drawlist
            item.paint(g, dpi, zPix);
        }

        g2d.setPaint(RADIUS_COLOR);			// draw the inner reference circle
        g2d.setStroke(DOT_LINE);
        g2d.draw(new Ellipse2D.Double(scaleXInchToPix(center.x - nomRadius, dpi, zPix),
				scaleYInchToPix(center.y + nomRadius, dpi, zPix),
				2 * dpi * nomRadius, 2 * dpi * nomRadius));
        g2d.dispose();
	}

	/**
	 * Get the amplitude (offset from nominal radius) of the rosette at a given angle in degrees.
	 * A value of zero means zero cutter deflection from its nominal radius.
	 * @param ang Angle in degrees around the rosette
	 * @return amplitude which will be a positive number from 0.0 to pToP
	 */
	@Override
	public double getAmplitudeAt(double ang) {
		double angle = angleCheck(ang);
        double anglePerRepeat = 360.0 / pattern.getRepeat();	// degrees per every repeat of pattern
        angle = angle + anglePerRepeat * phase / 360.0;
        int m = (int) (angle / anglePerRepeat);			// which repeat is the pattern in (0 to repeat-1)
        double partAngle = angle - m * anglePerRepeat;	// degrees into the pattern
        return pToP * pattern.getValue(partAngle / anglePerRepeat);
	}

	/**
	 * Make sure angle is in range 0.0 <= a < 360.0
	 * @param a angle in degrees
	 * @return angle in range 0.0 <= a < 360.0
	 */
	private double angleCheck(double a) {
		while (a < 0.0)
			a += 360.0;
		while (a >= 360.0)
			a -= 360.0;
		return a;
	}

    /**
     * Make the rosette appearance based on stored values
     */
    private void makeDrawables() {
        drawList.clear();			// clear out the old drawlist
        drawList.add(new Plus(center, RADIUS_COLOR)); // always draw a center mark

        int nPts = 360 / pattern.getRepeat();			// a point roughly every degree

        double angleRad = 0.0 + (phase / (double)pattern.getRepeat()) * Math.PI / 180.0;	// cummulative angle
        double deltaAngleRad = (360.0 / (pattern.getRepeat() * nPts)) * Math.PI / 180.0;		// add this amount every for every point
        for (int i = 0; i < pattern.getRepeat(); i++) {				// make a CurveSection for each repeat
            Point2d[] pts = new Point2d[nPts + 1];		// new each time so each curve has different points
            for (int j = 0; j <= nPts; j++) {			// calculate the points for the CurveSection
                double r = nomRadius - pToP * pattern.getValue((double) j / (double) nPts);
                pts[j] = new Point2d(r * Math.cos(angleRad), r * Math.sin(angleRad));
                angleRad = angleRad + deltaAngleRad;
            }
            angleRad = angleRad - deltaAngleRad;		// use this point again for the next curve
            drawList.add(new Curve(pts, OUTLINE_COLOR));
        }
    }

	/**
	 * Scale inches to pixels in x direction
	 * @param x inch value
	 * @param dpi desired dots per inch on the screen
	 * @param zPix desired zero position on the screen
	 * @return
	 */
    private int scaleXInchToPix(double x, int dpi, Point zPix) {
        return zPix.x + (int) (x * dpi);
    }

	/**
	 * Scale inches to pixels in y direction
	 * @param y inch value
	 * @param dpi desired dots per inch on the screen
	 * @param zPix desired zero position on the screen
	 * @return
	 */
    private int scaleYInchToPix(double y, int dpi, Point zPix) {
        return zPix.y - (int) (y * dpi);
    }

	/**
	 * Add a property change listener for the rosette
	 * @param listener
	 */
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pss.addPropertyChangeListener(listener);
	}

	/**
	 * Remove the given property change listener
	 * @param listener
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pss.removePropertyChangeListener(listener);
	}

}
