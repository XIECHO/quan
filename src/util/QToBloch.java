package util;

import Quantum.Qubit;
import org.jscience.mathematics.number.Complex;

public class QToBloch {
    public static double[] qtoBloch(Qubit q){
       Complex[] posssibe  = q.getPossibles();
       double alphaRealValue = posssibe[0].getReal();
       double alphaImaginaryValue = posssibe[0].getImaginary();
       double betaRealValue = posssibe[1].getReal();
       double betaImaginaryValue = posssibe[1].getImaginary();

        double newAlphaReal = alphaRealValue * alphaRealValue
                + alphaImaginaryValue * alphaImaginaryValue;
        double newBetaReal = alphaRealValue * betaRealValue
                + alphaImaginaryValue * betaImaginaryValue;
        double newBetaImaginary = alphaRealValue * betaImaginaryValue
                - betaRealValue * alphaImaginaryValue;

        double norm = Math.sqrt(newAlphaReal * newAlphaReal + newBetaReal
                * newBetaReal + newBetaImaginary * newBetaImaginary);
        if(norm == 0){
            return new double[]{0,0,-1};

        }else{
            return setAlphaBeta(newAlphaReal / norm, newBetaReal / norm, newBetaImaginary / norm);
        }

    }

    public static double[] setAlphaBeta(double newa, double newb, double newbi) {

//        System.out.println("..");
//        System.out.println(newa);
//        System.out.println(newb);
//        System.out.println(newbi);
        //得到theta
        double new_th_deg = (float) (Math.toDegrees(2.0 * Math.acos(newa)));
//
//        System.out.println(new_th_deg+"..");
        //得到sin(theta)
        double value = Math.sin(new_th_deg);
        double new_phi_deg ;
        if(value != 0){
            //得到phi
             new_phi_deg = (float) (Math.toDegrees(Math.atan2(newbi/value, newb/value)));
//        if (new_phi_deg < 0)
//            new_phi_deg += 360.0f;

        }else{
            new_phi_deg =0;
        }
//
//        System.out.println(new_phi_deg+"mmm");
//        System.out.println(new_th_deg+"gg");
        new_phi_deg = Math.toRadians(new_phi_deg);
        new_th_deg = Math.toRadians(new_th_deg);
        //三位坐标
        double a = Math.sin(new_th_deg) * Math.cos(new_phi_deg);
        double b = Math.sin(new_th_deg) * Math.sin(new_phi_deg);
        double c = Math.cos((new_th_deg));
        //这里可能有问题
        return new double[]{Math.round(a),Math.round(b),Math.round(c)};
    }


    public static void main(String[] args){
        Complex[] possible = new Complex[]{Complex.valueOf(Math.sqrt(2)/2,0),Complex.valueOf(Math.sqrt(2)/2,0)};
        qtoBloch(new Qubit(possible));

        System.out.println();
        Complex[] possible2 = new Complex[]{Complex.valueOf(Math.sqrt(2)/2,0),Complex.valueOf(-Math.sqrt(2)/2,0)};
        qtoBloch(new Qubit(possible2));

        System.out.println();
        Complex[] possible3 = new Complex[]{Complex.valueOf(Math.sqrt(2)/2,0),Complex.valueOf(0,Math.sqrt(2)/2)};
        qtoBloch(new Qubit(possible3));

        System.out.println();
        Complex[] possible4 = new Complex[]{Complex.valueOf(Math.sqrt(2)/2,0),Complex.valueOf(0,-Math.sqrt(2)/2)};
        qtoBloch(new Qubit(possible4));

        Complex[] possible1 = new Complex[]{Complex.ONE,Complex.ZERO};
        qtoBloch(new Qubit(possible1));

        Complex[] possible5 = new Complex[]{Complex.valueOf(0.924,0),Complex.valueOf(0.271,0.271)};
        qtoBloch(new Qubit(possible5));


        System.out.println(Math.cos(Math.PI/8));
        System.out.println(Math.sin(Math.PI/8)*Math.cos(Math.PI/4));
        System.out.println(Math.cos(Math.PI/4));
//        Complex[] possible5 = new Complex[]{Complex.ZERO,Complex.ONE};
//        qtoBloch(new Qubit(possible5));
    }
}
