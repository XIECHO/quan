package gui.model;

public class Measure2Operator extends Operator {
    int target;

    public Measure2Operator(int target, String name) {
        super(name);
        type = "Measure2";
    }

    public int getTarget() {
        return target;
    }
}
