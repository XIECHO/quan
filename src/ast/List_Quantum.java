package ast;

import Quantum.Qubit;
import interp.Environment;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jscience.mathematics.number.Complex;
import util.AmpToBra;
import util.DensityMatrix;
import util.DrawBloch;
import util.QToBloch;

import java.util.ArrayList;
import java.util.List;

// 在这个软件里面有一个全局的固定不变的量子变量
// 叫QuantumRegisterDX
//quantum 语句定义的变量指向QuantumRegisterDX ,并初始化QuantumRegisterDX
//定义量子门的语句，有两种定义方式，一种是没参数，一种有参数
//QuantumOperator c 15 (<1,2>,1,1+3)
//QuantumOperator c 15
public class List_Quantum extends ASTList {

	public List_Quantum(List<ASTree> list) {
		super(list);
	}

	public ASTree identity() { return child(0);}

	public ASTree count(){ return child(1);}

	//返回参数列表astlist: 是一个ArrayList
	public ASTree params(){
	    return child(2);
    }

	public String toString() {
		return "(QuantumOperator " + super.toString()+")";
	}

    public Object eval(Environment env) {
	    //qubit名字
        //这里应该用eval方法获取变量的，偷懒了
	    String name = identity().toString();
	    //qubit长度
        //同上。偷懒
	    int count = Integer.parseInt(count().toString());
	    //qubit对应的概率长度，如3个qubit 8种可能
        int number = (int)Math.pow(2,count);

        //生成Qubit
        Qubit q = null;

        Complex[] possible = null;
        //如果没有参数
        //QuantumOperator c 15
        if(numChildren()<3){
            possible = new Complex[number];
            possible[0] = Complex.ONE;
            for (int i = 1; i < number; i++) {
                possible[i] = Complex.ZERO;
            }

        }
        //QuantumOperator c 15 (<1,2>,1,1+3)
        else{
            ArrayList paramsNum =null;
            if(params().eval(env) instanceof ArrayList){
                paramsNum = (ArrayList)params().eval(env);
            }

            //判断个数对不对
            //参数个数

            //如果不对，提示错误
            if(paramsNum.size() != number){

            }
            //如果对了，提取数据

            double sum =0;
            //参数都是复数,归一化
            for (int i = 0; i <paramsNum.size() ; i++) {
                Complex temp  = (Complex)paramsNum.get(i);
                sum = sum + temp.times(temp.conjugate()).getReal();
            }

            //如果都对则
            possible = new Complex[number];
            for (int i = 0; i <number ; i++) {
                Complex temp = (Complex)paramsNum.get(i);
                possible[i] = temp.divide(Math.sqrt(sum));
            }
        }

        q = new Qubit(possible);
        //将name指向QuantumRegisterDX
        env.put(name, "QuantumRegisterDX");
        //初始化QuantumRegisterDX
        env.put("QuantumRegisterDX",q);
        //初始化寄存器,就是说name[i] 存的为i,即第i个位置
        for (int i = 0; i <number ; i++) {
            env.put(name+"["+i+"]",i);
        }
        return q ;
    }




    public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num){
        eval(env);
        Qubit q = (Qubit)env.get("QuantumRegisterDX");

	    if(num == 1){
            draw(env,circuitpane,q);
            Text outcome = new Text(10,20,"当前的状态为："+ AmpToBra.translate(q.getPossibles()));
            outcomepane.getChildren().add(outcome);
        }

        if( num == 2){
            draw(env,circuitpane,q);
            Text outcome = new Text(10,20,"当前的状态的密度矩阵为："+q.matrix());
            outcomepane.getChildren().add(outcome);
        }

//        if(num == 3){
//            double[] a = QToBloch.qtoBloch(q);
//            DrawBloch.drawBloch(a,circuitpane);
//        }
        return null;
    }

    private void draw(Environment env, Pane circuitpane,Qubit q){

        int number = q.number();
        Group g = new Group();
        Line step = new Line(beginLineX+10, 30, beginLineX + 10, 30 + rowspace * number);
        for (int i = 0; i < number; i++) {
            //行数
            Text index = new Text(10, rowspace * i + beginLineY + 5, Integer.toString(i));
            index.setFont(new Font(14));
            index.setFill(Color.TEAL);

            Text qubitVal = new Text(20, rowspace * i + beginLineY + 5, " |q" + i + ">");
            qubitVal.setFont(new Font(14));

            Line l = new Line(beginLineX, beginLineY + rowspace * i, beginLineX + 20, beginLineY + rowspace * i);
            g.getChildren().addAll(index, qubitVal, l);
        }
        circuitpane.getChildren().addAll(step, g);

        //这里将记录改变的此数
        env.put("changeDx",0);
    }

}
