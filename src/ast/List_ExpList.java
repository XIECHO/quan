package ast;

import interp.Environment;

import java.util.ArrayList;
import java.util.List;

//参数列表
public class List_ExpList extends ASTList {

    public List_ExpList(List<ASTree> list) {
        super(list);
    }

    //变量可能不止三个。。。。
    public String toString() {
        return "(" + super.toString() + ")";
    }

    public Object eval(Environment env) {

        //其实这里我是没有懂的哈哈哈
        //就是先把所有的值取出来，放进了一个list
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < numChildren(); i++) {
            list.add(child(i).eval(env));
        }
        return list;
    }

}
