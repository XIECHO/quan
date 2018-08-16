package ast;

import interp.Environment;
import org.jscience.mathematics.number.Complex;

import java.util.List;


public class List_Factor extends ASTList {

	public List_Factor(List<ASTree> list) {
		super(list);
	}

	//返回正数或者负数
	public String toString() {
		if (numChildren() == 1) {
			return "" + child(0);

		} else if (numChildren() == 2) {
			return "" + child(0)+child(1);
		}
		return null;
	}

	public Object eval(Environment env){
		if (numChildren() == 1) {
           // System.out.println("........"+child(0).eval(env));
			return child(0).eval(env);
		}

		//这里有问题的
		//这里可能返回的是复数
		if(numChildren() == 2){
            if(child(1).eval(env) instanceof Complex){
            	System.out.println(Complex.valueOf(0,0).minus((Complex)child(1).eval(env)));
		        return Complex.valueOf(0,0).minus((Complex)child(1).eval(env));
            }else{
				System.out.println(child(1).eval(env)+"....");
				double value = (double)child(1).eval(env);
		        return -value;
            }
		}
		return null;
	}

}
