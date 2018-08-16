package gui.model;


public class UOperator extends Operator {
    int target;
    String pama1, pama2, pama3;

    public String getPama1() {
        return pama1;
    }

    public String getPama2() {
        return pama2;
    }

    public String getPama3() {
        return pama3;
    }

    public UOperator(int target, String name, String pama1, String pama2, String pama3) {
        super(name);
        this.target = target;
        type = "U";
        this.pama1 = pama1;
        this.pama2 = pama2;
        this.pama3 = pama3;
    }

    public int getTarget() {
        return target;
    }

}
