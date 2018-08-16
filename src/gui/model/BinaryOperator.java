package gui.model;


public class BinaryOperator extends Operator {
    int target;
    int control;

    public BinaryOperator(int target, int control, String name) {
        super(name);
        this.target = target;
        this.control = control;
        type = "Binary";
    }

    public int getControl() {
        return control;
    }

    public int getTarget() {
        return target;
    }

}
