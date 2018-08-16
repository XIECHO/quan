package gui.model;

public class UnaryOperator extends Operator{
    int target;

    public UnaryOperator(int target, String name) {
        super(name);
        type = "Unary";
    }

    public int getTarget() {
        return target;
    }
}
