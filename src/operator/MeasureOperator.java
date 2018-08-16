package operator;

import Quantum.Qubit;
import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;
import util.*;

public class MeasureOperator {
    public static int measure(Qubit qs, int loc) {
        //Qbtis-->矩阵
        ComplexMatrix temp = qs.vector();
        // 得到状态是几量子比特
        int number = qs.number();
        //长度
        int length = qs.Length();
        // 单位矩阵
        ComplexMatrix identyMatrix = UnaryGate.I;

        //张量后的矩阵
        ComplexMatrix tensorMatrix = null;

        //随机值
        double t = Math.random();

        //返回量子比特概率
        double[] possibleValues = qs.getPossiblesValue();

        //测量结果为0的概率
        double possible0 = 0;
        for (int i = 0; i < length; i++) {
            String qbit = TenToBinary.ttb(i, length);
            char[] qbitChar = qbit.substring(1, qbit.length() - 1).toCharArray();

            if (qbitChar[loc] == '0') {
                possible0 = possible0 + possibleValues[i];
            }
        }

        //测量矩阵
        ComplexMatrix matrix = null;
        if(t<possible0){
            matrix = MeasureGate.M_0;
        }else{
            matrix = MeasureGate.M_1;
        }



        // 使得测量矩阵与单位矩阵张量
        int i = 0;
        while (i < loc) {
            tensorMatrix = identyMatrix.tensor(matrix);
            i++;
        }
        //如果没有左边张量
        if (tensorMatrix == null) {
            tensorMatrix = matrix;
        }
        while (i < number - 1) {
            tensorMatrix = tensorMatrix.tensor(identyMatrix);
            i++;
        }


        ComplexMatrix possible = ConjugateTranspose.apply(temp).times(tensorMatrix).times(temp);
        ComplexMatrix temp1 = tensorMatrix.times(temp).times(Complex.ONE.divide(possible.get(0, 0).pow(0.5)));


        //更新qs
        qs.matrixToQbits(temp1);
        return t<possible0 ? 0 : 1;
    }
}
