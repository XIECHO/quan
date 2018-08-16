package ast;

import interp.Environment;
import javafx.scene.layout.Pane;

public abstract class ASTree implements Size{
    //返回第i个节点
    public abstract ASTree child(int i);
    //返回子节点的个数（如果没有子节点则返回0）
    public abstract int numChildren();

   // public abstract Iterator<ASTree> children();
   // public Iterator<ASTree> iterator() { return children(); }

    //语义操作
    public abstract Object eval(Environment env) ;


    public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num){
        return eval(env);
    }



}
