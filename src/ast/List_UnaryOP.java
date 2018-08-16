package ast;

import Quantum.Qubit;
import javafx.scene.layout.Pane;
import operater.UnaryGateOperater;
import interp.Environment;
import org.jscience.mathematics.vector.ComplexMatrix;
import util.FindSource;
import util.UnaryDraw;

import java.util.List;

////H Bob1
// 内置的单比特门
public class List_UnaryOP extends ASTList {

    public List_UnaryOP(List<ASTree> list) {
        super(list);
    }

    //第一个是操作门变量
    public ASTree unaryOp() {
        return child(0);
    }

    //第二个操作位置
    public ASTree argument() {
        return child(1);
    }

    public String toString() {
        return "(" + super.toString() + ")";
    }

    //H Bob1
    public Object eval(Environment env) {

        //获取操作矩阵
        ComplexMatrix matrix = (ComplexMatrix) unaryOp().eval(env);
        //获取操作位置
        int location = FindSource.findSource(env, argument().toString());
        //获取操作对象，在这个实验里是QuantumRegisterDX，正常应该解析Array得到对象，偷懒中。。。
        Qubit q = (Qubit) env.get("QuantumRegisterDX");

        //执行操作
        UnaryGateOperater.excute(matrix, q, location);
        //因为量子状态是全局的，所以都不要更新

        return null;
    }


    public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num) {
        eval(env);
        Qubit q = (Qubit) env.get("QuantumRegisterDX");
        //获取操作位置
        int location = FindSource.findSource(env, argument().toString());

        UnaryDraw.draw(env, circuitpane, outcomepane, location, unaryOp().toString().toUpperCase(), num);


        return null;
    }

}
