package ast;

import interp.Token;
import interp.Environment;
import util.UnaryGate;

//U算子 例如 T，S
public class Leaf_UOperator extends ASTLeaf {
    public Leaf_UOperator(Token t) {
        super(t);
    }

    public String name() {
        return getToken().getText();
    }

    //矩阵是不是应该是这里提供出来
    public Object eval(Environment env) {
        String value =(String)name();
        switch (value) {
            case "x":
                return UnaryGate.X;

            case "y":
                return UnaryGate.Y;

            case "z":
                return UnaryGate.Z;

            case "h":
                return UnaryGate.H;

            case "s":
                return UnaryGate.S;

            case "sdg":
                return UnaryGate.SDG;

            case "t":
                return UnaryGate.T;

            case "tdg":
                return UnaryGate.TDG;

            case "i":
                return UnaryGate.I;

        }
        return UnaryGate.I;
    }
}
