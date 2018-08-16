package util;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;

public class MeasureGate {
    //[1 0;0 0]
    static Complex[][] measurement_0 = new Complex[][]{{Complex.ONE, Complex.ZERO}, {Complex.ZERO, Complex.ZERO}};
    public static final ComplexMatrix M_0 = ComplexMatrix.valueOf(measurement_0);

    //[0 0;0 1]
    static Complex[][] measurement_1 = new Complex[][]{{Complex.ZERO, Complex.ZERO}, {Complex.ZERO, Complex.ONE}};
    public static final ComplexMatrix M_1 = ComplexMatrix.valueOf(measurement_1);
}
