package util;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;

public class BinaryGate {
    // Cont门的矩阵表示
    static Complex[][] arr = new Complex[][] { { Complex.ONE, Complex.ZERO, Complex.ZERO, Complex.ZERO },
            { Complex.ZERO, Complex.ONE, Complex.ZERO, Complex.ZERO },
            { Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ONE },
            { Complex.ZERO, Complex.ZERO, Complex.ONE, Complex.ZERO } };
    public static final  ComplexMatrix CNOT = ComplexMatrix.valueOf(arr);

    //如果控制位大于改变位
    static Complex[][] arr21 = new Complex[][] { { Complex.ONE, Complex.ZERO, Complex.ZERO, Complex.ZERO },
            { Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ONE },
            { Complex.ZERO, Complex.ZERO, Complex.ONE, Complex.ZERO },
            { Complex.ZERO, Complex.ONE, Complex.ZERO, Complex.ZERO } };
    static ComplexMatrix cnotGate21 = ComplexMatrix.valueOf(arr21);


}
