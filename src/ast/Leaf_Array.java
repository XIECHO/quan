package ast;

import interp.Token;
import interp.Environment;

//Array : 形如 a[1]
public class Leaf_Array extends ASTLeaf {

    public Leaf_Array(Token t) {
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
