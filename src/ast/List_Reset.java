package ast;

import Quantum.Qubit;
import interp.Environment;
import org.jscience.mathematics.number.Complex;

import java.util.List;
//Reset ;
public class List_Reset extends ASTList {
    public List_Reset(List<ASTree> list) {
        super(list);
    }
    public String toString() {
        return "(Show " + super.toString()+")";
    }

    //重置量子寄存器的状态
    public Object eval(Environment env) {
        Qubit q = (Qubit)env.get("QuantumRegisterDX");
        Complex[] possible = new Complex[q.Length()];
        possible[0] = Complex.ONE;
        for (int i = 1; i < q.Length(); i++) {
            possible[i] = Complex.ZERO;
        }
        q.setPossibles(possible);
        return null;
    }
}
