package gui.model;

public class UnaryOperator extends Operator{
    int target1;

    public UnaryOperator(int target, String name) {
        super(name);
        target1 = target;
        type = "Unary";
    }

    public int getTarget() {
        return target1;
    }
}
