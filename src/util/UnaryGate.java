package util;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;

public class UnaryGate {
    //H门
    static Complex[][] h = new Complex[][]{{Complex.valueOf(Math.sqrt(2) / 2, 0), Complex.valueOf(Math.sqrt(2) / 2, 0)},
            {Complex.valueOf(Math.sqrt(2) / 2, 0), Complex.valueOf(Math.sqrt(2) / (-2), 0)}};
    public static final ComplexMatrix H = ComplexMatrix.valueOf(h);

    //S门
    static Complex[][] s = new Complex[][]{{Complex.ONE, Complex.ZERO}, {Complex.ZERO, Complex.valueOf(0, 1)}};
    public static final ComplexMatrix S = ComplexMatrix.valueOf(s);

    //sdg门
    static Complex[][] sdg = new Complex[][]{{Complex.ONE, Complex.ZERO}, {Complex.ZERO, Complex.valueOf(0, -1)}};
    public static final ComplexMatrix SDG = ComplexMatrix.valueOf(sdg);

    //T门
    static Complex[][] t = new Complex[][]{{Complex.ONE, Complex.ZERO}, {Complex.ZERO, Complex.valueOf(Math.sqrt(2) / 2, Math.sqrt(2) / 2)}};
    public static final ComplexMatrix T = ComplexMatrix.valueOf(t);

    //TDG门
    static Complex[][] tdg = new Complex[][]{{Complex.ONE, Complex.ZERO}, {Complex.ZERO, Complex.valueOf(Math.sqrt(2) / 2, Math.sqrt(2) / -2)}};
    public static final ComplexMatrix TDG = ComplexMatrix.valueOf(tdg);

    //X门
    static Complex[][] x = new Complex[][]{{Complex.ZERO, Complex.ONE}, {Complex.ONE, Complex.ZERO}};
    public static final ComplexMatrix X = ComplexMatrix.valueOf(x);

    //Y门
    static Complex[][] y = new Complex[][]{{Complex.ZERO, Complex.valueOf(0, -1)}, {Complex.valueOf(0, 1), Complex.ZERO}};
    public static final ComplexMatrix Y = ComplexMatrix.valueOf(y);

    //Z门
    static Complex[][] z = new Complex[][]{{Complex.ONE, Complex.ZERO}, {Complex.ZERO, Complex.valueOf(-1, 0)}};
    public static final ComplexMatrix Z = ComplexMatrix.valueOf(z);

    //I门
    static Complex[][] i = new Complex[][]{{Complex.ONE, Complex.ZERO}, {Complex.ZERO, Complex.ONE}};
    public static final ComplexMatrix I = ComplexMatrix.valueOf(i);

    //生成U门
    public static final ComplexMatrix U(double a, double b, double c) {

        double temp = b + c;
        double temp1 = b - c;

        Complex temp3 = cx(Math.cos(temp/2)/Math.cos(a/2),Math.sin(temp/2)/Math.cos(a/2));
        return ComplexMatrix.valueOf(new Complex[][]{
                {cx(Math.cos(a / 2) * Math.cos(temp / -2), Math.cos(a / 2) * Math.sin(temp / -2)),
                        cx(-Math.sin(a / 2) * Math.cos(temp1 / -2), -Math.sin(a / 2) * Math.sin(temp1 / -2))},
                {cx(Math.sin(a / 2) * Math.cos(temp1 / 2), Math.sin(a / 2) * Math.sin(temp1 / 2)),
                        cx(Math.cos(a / 2) * Math.cos(temp / 2), Math.cos(a / 2) * Math.sin(temp / 2))}
        }).times(temp3);

    }

    private static Complex cx(double real, double imaginary) {
        return Complex.valueOf(real, imaginary);
    }
}
