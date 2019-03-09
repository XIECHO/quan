package ast;

import Quantum.Qubit;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import operater.BinaryGateOperator;
import interp.Environment;
import org.jscience.mathematics.vector.ComplexMatrix;
import util.AmpToBra;
import util.DensityMatrix;
import util.FindSource;

import java.util.List;


//两比特操作门，这会只有Cont
public class List_BinaryOP extends ASTList {

	public List_BinaryOP(List<ASTree> list) {
		super(list);
	}

	//第一个是操作门变量
	public ASTree binaryOp() {
		return child(0);
	}

	//第一个变量
	public ASTree argument1() {
		return child(1);
	}

	//第二个变量
	public ASTree argument2() {
		return child(2);
	}

	public String toString() {
		return "(" + argument1() + " " + argument2() + ")";
	}

	public Object eval(Environment env) {
		//获取操作矩阵
		ComplexMatrix matrix =(ComplexMatrix) binaryOp().eval(env);
		//获取变量1操作位置
		int location1 = FindSource.findSource(env,argument1().toString());
		//获取变量2操作位置
		int location2 = FindSource.findSource(env,argument2().toString());
		//获取操作对象，在这个实验里是QuantumRegisterDX，正常应该解析Array得到对象，偷懒中。。。
		Qubit q = (Qubit) env.get("QuantumRegisterDX");
		//执行操作
		BinaryGateOperator.excute(matrix,q,location1,location2);
		//因为量子状态是全局的，所以都不要更新
		return null;
	}


	public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num) {
		eval(env);
		Qubit q = (Qubit)env.get("QuantumRegisterDX");

		int changeNum = (int)env.get("changeDx");

		int startX = beginLineX + 20+  changeNum*rowspace;
		Group g = new Group();

		//三条横线
		for (int i = 0; i < q.number(); i++) {
			Line l = new Line(startX, rowspace * i + beginLineY, startX + rowspace, rowspace * i + beginLineY);
			g.getChildren().add(l);
		}

		//获取变量1操作位置
		int location1 = FindSource.findSource(env,argument1().toString());
		//获取变量2操作位置
		int location2 = FindSource.findSource(env,argument2().toString());


		Circle circle = new Circle(startX+15, rowspace*location1 + beginLineY , 6);
		circle.setFill(Color.DARKSEAGREEN);
		Circle circle2 = new Circle(startX+15, rowspace*location2 + beginLineY , 13);
		circle2.setFill(Color.DARKSEAGREEN);
		Line line = new Line(startX+15, rowspace*location1 + beginLineY,startX+15, rowspace*location2 + beginLineY);
		line.setStroke(Color.DARKSEAGREEN);
		g.getChildren().addAll(circle,line,circle2);
		circuitpane.getChildren().add(g);

		outcomepane.getChildren().clear();
        if(num == 1){
            Text outcome = new Text(10,20,"当前的状态为："+ AmpToBra.translate(q.getPossibles()));
            outcomepane.getChildren().add(outcome);
        }

        if( num == 2){
            Text outcome = new Text(10,20,"当前的状态的密度矩阵为："+ q.matrix());
            outcomepane.getChildren().add(outcome);
        }

		env.put("changeDx",changeNum+1);
		return null;
	}
}
