package billooms.penchuck.rosettemodel;

import billooms.penchuck.rosettemodel.api.Rosette.Styles;

/**
 * This defines a basic pattern that can be applied to a rosette
 * A pattern has an amplitude ranging from 0.0 to 1.0 for an input index of 0.0 to 1.0
 * Various patterns can be added, generally based on a formula description,
 * although other approaches (like table lookup and interpolation) could be used as well.
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
public class Pattern {

    private Styles style;	// style of the pattern
    private int repeat;     // How many times the pattern is repeated per revolution

    /**
     * The pattern style is defined by the enclosed enum.
     * Certain patterns (N-Sided & Flower) also depend on the number of repeats.
     * @param st Style
     * @param n Number of repeats
     */
    public Pattern(Styles st, int n) {
        setStyle(st);
		setRepeat(n);
    }

	/**
	 * Get the name of the pattern as a string
	 * @return name of the pattern
	 */
    public String getName() {
        return style.toString();
    }

	/**
	 * Get the style of the pattern
	 * @return style of the pattern
	 */
    public Styles getStyle() {
        return style;
    }

	/**
	 * Set the style of the pattern
	 * @param st style of the pattern
	 */
    public final void setStyle(Styles st) {
        style = st;
		setRepeat(repeat);
    }

	/**
	 * Get the number of repeats for the pattern
	 * @return number of repeats
	 */
    public int getRepeat() {
        return repeat;
    }

	/**
	 * Set the number of repeats for the pattern
	 * @param n number of repeats
	 */
	public final void setRepeat(int n) {
		repeat = n;
		switch (style) {		// limit the minimum repeat for each pattern
			case NSIDE:
			case FLOWER:
				if (n < 3) {
					repeat = 3;
				}
				break;
			case BIGSMALL:
			case BIGSMALL_:
			case TRIANGLE:
				if (n < 2) {
					repeat = 2;
				}
				break;
			case NONE:
				repeat = 1;
				break;
			case TUDOR:
			default:
				if (n < 1) {
					repeat = 1;
				}
				break;
		}
	}

    /**
     * A pattern is like an x-y plot with input x ranging from 0.0 to 1.0,
     * and output value ranging from 0.0 to 1.0.
	 * A value of 1.0 indicates maximum cutter movement from it's nominal position.
     * The repeat value is required for N-Sided & Flower.
     * @param n A value from 0.0 to 1.0 representing the fraction of the distance into the pattern.
     * @return A value from 0.0 to 1.0 representing the output value.
     */
    public double getValue(double n) {
		return getValue(style, repeat, n);
	}

    /**
     * A pattern is like an x-y plot with input x ranging from 0.0 to 1.0,
     * and output value ranging from 0.0 to 1.0.
     * The repeat value is required for patterns such as N-Sided & Flower.
	 * This method is only used internally for making composite patterns.
	 * Styles and repeat are arguments so that this can be called to make other patterns from given patterns.
	 * @param st style to be used
	 * @param rpt repeat of the pattern
     * @param n A value from 0.0 to 1.0 representing the fraction of the distance into the pattern.
     * @return A value from 0.0 to 1.0 representing the output value.
	 */
    private double getValue(Styles st, int rpt, double n) {
        double z, xx, yy, alphaRad, tanTheta, c2, maxP, p, s, rPrime, dr, minS;
        while (n < 0.0) {		// wrap around for values outside of range 0.0 to 1.0
            n += 1.0;
        }
        while (n > 1.0) {
            n -= 1.0;
        }
        switch (st) {
			case NONE:
				z = 0.0;
				break;
			case INDEX:
				if ((n == 0.0) || (n == 1.0)) {
					z = 1.0;
				} else {
					z = 0.0;
				}
				break;
            case SINE:                          // easy for a simple sine wave offset to give 0.0 to 1.0
                z = 0.5 + 0.5 * Math.cos(n * 2.0 * Math.PI);
                break;
            case HEART:
                n = 2.0 * n;                    // make a symmetrical pattern by only defining half of it
                if (n > 1.0) {                  // and mirroring the other half
                    n = 2.0 - n;                // around the center point
                }
                z = Math.sin(n * 2 * Math.PI);	// for 0.0 to 0.25
                if (n >= 0.75) {
                    z = z + 1.0;				// for 0.75 to 1.0
                } else if (n > 0.25) {          // for 0.25 to 0.75
                    z = z + (1.0 - Math.sin(n * 2 * Math.PI)) / 2.0;
                }
                break;
            case LOTUS:
                n = 2.0 * n;                    // make a symmetrical pattern by only defining half of it
                if (n > 1.0) {                  // and mirroring the other half
                    n = 2.0 - n;                // around the center point
                }
                z = Math.sin(n * 2 * Math.PI);	// for 0.0 to 0.25
				z = n + 0.1*z;					// the factor 0.1 was determined by a nice appearance
                break;
            case NSIDE:								// curve sections are based on portions of a circle of various sizes
                alphaRad = Math.PI / rpt;
                c2 = Math.sin(alphaRad);			// Chord/2.0
                tanTheta = Math.tan((n * 2.0 - 1.0) * alphaRad);
                maxP = (1.0 - Math.cos(alphaRad));  // maximum p-to-p for this repeat (The radius is normalized to 1.0)
                xx = Math.cos(alphaRad);
                yy = xx * tanTheta;
                z = 1.0 - Math.sqrt(xx * xx + yy * yy); // z is the amount in from the nominal radius (normalized to radius 1.0)
                z = z / maxP;						// normalize so always range 0 to 1 for parm1=1
                break;
            case FLOWER:							// curve sections are based on portions of a circle of various sizes
                alphaRad = Math.PI / rpt;
                c2 = Math.sin(alphaRad);			// Chord/2.0
                tanTheta = Math.tan((n * 2.0 - 1.0) * alphaRad);
                minS = (1.0 - Math.cos(alphaRad));	// minimum saggita (The radius is normalized to 1.0)
                maxP = c2 - minS;					// maximum bump out
                s = minS + maxP;					// saggita
                rPrime = c2 * c2 / (2.0 * s) + s / 2.0;	// R' is radius of side
                dr = (1.0 + maxP) - rPrime;			// dr is a positive number
                xx = (dr + Math.sqrt(dr * dr - (1 + tanTheta * tanTheta) * (dr * dr - rPrime * rPrime))) / (1 + tanTheta * tanTheta);
                yy = xx * tanTheta;
                z = Math.sqrt(xx * xx + yy * yy) - 1.0; // z is the amount out from the nominal radius (normalized to radius 1.0)
                z = 1 - z / maxP;					// invert and normalize so always range 0 to 1 for parm1=1
                break;
			case BIGSMALL:
			case BIGSMALL_:
				if (n < 1.0/3.0) {
					z = getValue(Styles.FLOWER, rpt*3, n*3.0);	// small Flower for 1/3
				} else {
					z = getValue(Styles.FLOWER, rpt*3/2, (n-1.0/3.0)*3.0/2.0);	// big flower for 2/3
				}
				if (st == Styles.BIGSMALL_) {	// inverse
					z = 1.0 - z;
				}
				break;
			case TRIANGLE:
                n = 2.0 * n;                    // make a symmetrical pattern by only defining half of it
                if (n > 1.0) {                  // and mirroring the other half
                    n = 2.0 - n;                // around the center point
                }
				z = n;
				break;
			case TUDOR:
                double z1 = 0.5 + 0.5 * Math.cos(2.0*n * 2.0 * Math.PI);	// basic pattern is 2xSINE
				double z2 = 5.0 * getValue(Styles.TRIANGLE, rpt, n);		// with a point between
				z = Math.min(z1, z2);
				break;
            default:
                z = 0.0;
                break;
        }
        return z;
    }
}
