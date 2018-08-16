package ast;

import Quantum.Qubit;
import interp.Environment;
import javafx.scene.layout.Pane;
import operater.UnaryGateOperater;
import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;
import util.*;

import java.util.ArrayList;
import java.util.List;

//Gate(1,2,3,4) a
//Gate(1,2,3,4...16) a b
public class List_Gate extends ASTList {
    public List_Gate(List<ASTree> list) {
        super(list);
    }

    //返回参数列表astlist: 是一个ArrayList
    public ASTree params(){
        return child(0);
    }

    public String toString() {
        return "(Gate" + super.toString() + ")";
    }

    //Gate(1,2,3,4) a
    //Gate(1,2,3,4...16) a b

    //Gate 处理复数的时候有问题。。。。必须要带负号
    public Object eval(Environment env) {

        //这里限定为只能是带一个参数
        //所以参数只能为四个
        ArrayList paramsNum =null;
        if(params().eval(env) instanceof ArrayList){
            paramsNum = (ArrayList)params().eval(env);
        }

        if(paramsNum.size() != 4 ){
            //报错
        }

        Complex[][] c = new Complex[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                c[i][j] = (Complex) paramsNum.get(i * 2 + j);
            }
        }

        ComplexMatrix cm = ComplexMatrix.valueOf(c);
        System.out.println(cm);

        // System.out.println(UnaryGate.Y.times(ConjugateTranspose.apply(UnaryGate.Y)));
        if (!(cm.times(ConjugateTranspose.apply(cm)).equals(UnaryGate.I))) {
            //报错
        }

        //获取位置
        int value = FindSource.findSource(env,child(1).toString());
        //获取操作对象，在这个实验里是QuantumRegisterDX，正常应该解析Array得到对象，偷懒中。。。
        Qubit q = (Qubit) env.get("QuantumRegisterDX");
        //执行操作
        UnaryGateOperater.excute(cm,q,value);



//        //首先判断参数的长度和 变量的长度匹配不匹配
//        int length = numChildren() - 1;
//        //参数应该有的个数
//        double geshu = Math.pow(4,length);
//        ArrayList paramsNum =null;
//        if(params().eval(env) instanceof ArrayList){
//            paramsNum = (ArrayList)params().eval(env);
//        }
//
//        if(paramsNum.size() != geshu ){
//            //报错
//        }
//
//        // 判断矩阵是不是酉矩阵
//
//        //4个数字，两行，两列，一个参数
//        //16个数字，四行，四列，两个参数
//
//        //多少行
//        int row = (int)Math.sqrt(geshu);
//        //多少列
//        int col = row;
//
//        Complex[][] c = new Complex[col][col];
//
//        for (int i = 0; i < col; i++ ) {
//            for (int j = 0; j < col; j++ ) {
//                c[i][j] = (Complex)paramsNum.get(i*col + j);
//            }
//        }
//
//        ComplexMatrix cm = ComplexMatrix.valueOf(c);
//
//        // System.out.println(UnaryGate.Y.times(ConjugateTranspose.apply(UnaryGate.Y)));
//        if(!(cm.times(ConjugateTranspose.apply(cm)).equals(UnaryGate.I))){
//            //报错
//        }
//        //获取位置

        return null;
    }


    public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num){
        eval(env);
        Qubit q = (Qubit)env.get("QuantumRegisterDX");
        //获取操作位置
        int location = FindSource.findSource(env,child(1).toString());

        UnaryDraw.draw(env,circuitpane,outcomepane,location,"G",num);


        return null;
    }

}
