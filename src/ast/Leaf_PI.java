package ast;

import interp.Token;
import interp.Environment;

// Π
public class Leaf_PI extends ASTLeaf {
    public Leaf_PI(Token t) {
        super(t);
    }

    public Double value() {
        return Math.PI;
    }

    public Object eval(Environment e) {
        return value();
    }
}
