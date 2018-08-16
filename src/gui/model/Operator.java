package gui.model;

public class Operator {
    //类型
    String type;
    //名字， 像 U, X , Y ,Z 等
    String name;
    public Operator(){

    }

    public Operator (String n){
        name = n;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
