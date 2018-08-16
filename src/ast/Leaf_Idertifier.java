package ast;

import interp.Token;
import interp.Environment;
//变量
public class Leaf_Idertifier extends ASTLeaf {
    public Leaf_Idertifier(Token t) {
        super(t);
    }

    public String name() {
        return getToken().getText();
    }

    public Object eval(Environment env) {
        Object value = name();
        return value;
    }
}
