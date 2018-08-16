package operator;

import Quantum.Qubit;

import org.jscience.mathematics.vector.ComplexMatrix;

import util.UnaryGate;

//H Bob1
public class UnaryGateOperater {
    public static Qubit excute(ComplexMatrix matrix, Qubit qs, int loc) {

        //Qbtis-->矩阵
        ComplexMatrix temp = qs.vector();
        // 得到状态是几量子比特
        int number = qs.number();
        // 单位矩阵
        ComplexMatrix identyMatrix = UnaryGate.I;

        //张量后的矩阵
        ComplexMatrix tensorMatrix = null;

        // 使得测量矩阵与单位矩阵张量
        int i = 0;
        while (i < loc) {
            tensorMatrix = identyMatrix.tensor(matrix);
            i++;
        }
        //如果没有左边张量
        if(tensorMatrix == null){
            tensorMatrix = matrix;
        }
        while (i < number-1) {
            tensorMatrix = tensorMatrix.tensor(identyMatrix);
            i++;
        }

        ComplexMatrix temp1 = tensorMatrix.times(temp);
        //更新qs
        qs.matrixToQbits(temp1);
        return qs;
    }
}
