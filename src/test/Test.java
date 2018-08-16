package test;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;
import util.ConjugateTranspose;
import util.UnaryGate;




public class Test {
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

    public static  void  main(String[] args){
        System.out.println(U(0,0,Math.PI/-4));
        System.out.println(Math.cos(Math.PI/8));
        System.out.println(0.5*Math.cos(Math.PI/8));
        System.out.println(0.5*Math.sin(Math.PI/8));
//        System.out.println(Math.ceil(2.5));
//        System.out.println( Double.parseDouble("-3.46"));
//        System.out.println(UnaryGate.Y);
//        System.out.println(UnaryGate.Y.times(ConjugateTranspose.apply(UnaryGate.Y)));

//        Complex aa = Complex.valueOf(1,1);
//        System.out.println(aa.times(2));
//        System.out.println(aa.conjugate());
//        System.out.println(aa.times(aa.conjugate()));
//        System.out.println(Math.log(8)/Math.log(2));
//        System.out.println(Math.log1p(8));
//        Complex a =Complex.valueOf(2,4);
//        Complex b =Complex.valueOf(1,2);
//        System.out.println(a.plus(b));
//        System.out.println(a.minus(b));
//        System.out.println(a.divide(b));
//        System.out.println(a.times(b));
    }
}
