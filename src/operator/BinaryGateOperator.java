package operator;

import Quantum.Qubit;
import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;
import util.BinaryGate;
import util.BinaryToTen;
import util.TenToBinary;


public class BinaryGateOperator {
    public static Qubit excute(ComplexMatrix matrix, Qubit qs, int loc1,int loc2) {
        //这里的化，正常应该是矩阵操作，但是嗯，写起来有点麻烦，所以用投机的方法

        //我处理的方式，先把
        if(matrix.equals(BinaryGate.CNOT)){

            //获得概率值
            Complex[] qbitP = qs.getPossibles();
            //长度
            int length = qs.Length();
            //copy用
            Complex[] qbitcopy = new Complex[length];
            for(int i=0; i<length; i++) {
                String qbit = TenToBinary.ttb(i, length);
                char[] qbitChar = qbit.substring(1, qbit.length()-1).toCharArray();

                if(qbitChar[loc1]=='1') {

                    if(qbitChar[loc2]=='1') {
                        qbitChar[loc2]='0';
                    }else if(qbitChar[loc2]=='0'){
                        qbitChar[loc2]='1';
                    }
                }

                double newLoc = BinaryToTen.btt(qbitChar);
                qbitcopy[(int) newLoc] = qbitP[i];

            }
            qs.setPossibles(qbitcopy);
            return qs;
        }
        return qs;
    }
}
