package ast;

import interp.Environment;
import interp.Token;
import org.jscience.mathematics.number.Complex;

//复数
public class Leaf_Complex extends ASTLeaf {
    public Leaf_Complex(Token t) {
        super(t);
    }

    public Complex value() {
        String value = getToken().getText();
        int langle = value.indexOf('<');
        int comma = value.indexOf(',');
        int rangle = value.indexOf('>');

        double real = Double.parseDouble(value.substring(langle+1, comma));
        double complex = Double.parseDouble(value.substring(comma+1, rangle));
        return Complex.valueOf(real,complex);
    }

    public Object eval(Environment e) {
        return value();
    }


}
