package ast;

import interp.Environment;
import interp.Token;
import util.BinaryGate;


public class Leaf_BOperator extends ASTLeaf {
    public Leaf_BOperator(Token t) {
        super(t);
    }

    public String name() {
        return getToken().getText();
    }

    //矩阵是不是应该是这里提供出来
    public Object eval(Environment env) {
        String value =(String)name();
        switch (value) {
            case "cnot":
                return BinaryGate.CNOT;
        }
        return null;
    }
}
