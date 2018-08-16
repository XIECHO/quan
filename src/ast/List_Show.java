package ast;

import Quantum.Qubit;
import interp.Environment;

import java.util.List;

//返回中间状态的语句
//Show;
public class List_Show extends ASTList {

	public List_Show(List<ASTree> list) {
		super(list);
	}

	public String toString() {
		return "(Show " + super.toString()+")";
	}

	//show 用来量子寄存器中的值的状态，在控制台中输出状态，在GUI中不做任何变化
	public Object eval(Environment env) {
		Qubit q = (Qubit)env.get("QuantumRegisterDX");
		System.out.println(q.toString());
		return null;
	}
}
