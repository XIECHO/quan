package ast;
import interp.Environment;

import java.util.List;

public abstract class ASTList extends ASTree {
    //记录每句话要存储的内容
    protected List<ASTree> children;
    //构造器方法
    public ASTList(List<ASTree> list) { children = list; }
    //覆盖child（）
    public ASTree child(int i) { return children.get(i); }
    //覆盖numChildren()
    public int numChildren() { return children.size(); }

    //toString()
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String sep = "";
        for (ASTree t: children) {
            builder.append(sep);
            sep = " ";
            builder.append(t.toString());
        }
        return builder.toString();
    }

    public abstract Object eval(Environment env) ;

}
