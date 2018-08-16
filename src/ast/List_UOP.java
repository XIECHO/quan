package ast;

import Quantum.Qubit;
import javafx.scene.layout.Pane;
import operater.UnaryGateOperater;
import interp.Environment;
import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;
import util.FindSource;
import util.UnaryDraw;
import util.UnaryGate;

import java.util.ArrayList;
import java.util.List;


//通用矩阵门
//U(PI/2,0.2,0.1) Alice;
public class List_UOP extends ASTList {

	public List_UOP(List<ASTree> list) {
		super(list);
	}

	public ASTree exp() {
		return child(0);
	}

	public ASTree argument() {
		return child(1);
	}

	public String toString() {
		return "(U(" + exp() + ") " + argument() + ")";
	}

	//U(PI/2,0.2,0.1) Alice;
	public Object eval(Environment env) {
		//判断参数是不是三个
		ArrayList list  =(ArrayList)exp().eval(env);
		if(list.size()!=3){
			//报错
		}

		//取出参数，并生成矩阵
		//没有走leafUoperator节点，没有存U
		//直接生成
		//三个参数都是实数
		for (int i = 0; i < list.size(); i++) {
			Complex temp = (Complex)list.get(i);
			if(temp.getImaginary()!=0){
				//报错
			}
		}
		double first = ((Complex)list.get(0)).getReal();
		double second = ((Complex)list.get(1)).getReal();
		double third = ((Complex)list.get(2)).getReal();

		ComplexMatrix matrix = UnaryGate.U(first,second,third);


		//获取操作位置
		int location = FindSource.findSource(env,argument().toString());
		//获取操作对象，在这个实验里是QuantumRegisterDX，正常应该解析Array得到对象，偷懒中。。。
		Qubit q = (Qubit) env.get("QuantumRegisterDX");
		//执行操作
		UnaryGateOperater.excute(matrix,q,location);
		//因为量子状态是全局的，所以都不要更新
		return null;
	}



	public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num){
		eval(env);
		Qubit q = (Qubit)env.get("QuantumRegisterDX");
		//获取操作位置
		int location = FindSource.findSource(env,argument().toString());

		UnaryDraw.draw(env,circuitpane,outcomepane,location,"U",num);
		return null;
	}

}
