package ast;

import interp.Token;
import interp.Environment;

//实数
public class Leaf_Real extends ASTLeaf {
    public Leaf_Real(Token t) {
        super(t);
    }

    public Double value() {
        return Double.parseDouble(getToken().getText());
    }

    public Object eval(Environment e) {
        return value();
    }
}
