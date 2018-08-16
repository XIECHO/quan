package ast;

import interp.Token;
import interp.Environment;

//操作符号，像+，-，*，/
public class Leaf_Operator extends ASTLeaf {
    public Leaf_Operator(Token t) {
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
