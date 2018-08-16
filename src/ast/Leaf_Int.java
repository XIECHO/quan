package ast;

import interp.Token;
import interp.Environment;

//整数
public class Leaf_Int extends ASTLeaf {
    public Leaf_Int(Token t) {
        super(t);
    }

    public double value() {
        return Integer.parseInt(getToken().getText());
    }

    public Object eval(Environment e) {
        return value();
    }
}
