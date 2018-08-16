package gui.model;

public class GateOperator extends Operator {
    int target;
    String pama1;

    public String getPama1() {
        return pama1;
    }


    public GateOperator(int target, String name, String pama1) {
        super(name);
        this.target = target;
        type = "Gate";
        this.pama1 = pama1;
    }

    public int getTarget() {
        return target;
    }
}
