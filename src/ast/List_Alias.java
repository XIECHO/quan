package ast;

import interp.Environment;

import java.util.List;

//赋值语句 ， 取别名用
//Alias a,b
//tree 只记录 变量a , 和变量b
public class List_Alias extends ASTList {

    public List_Alias(List<ASTree> list) {
        super(list);
    }

    //第一个参数，原变量名
    public ASTree argument1() {
        return child(0);
    }

    //第二个参数，新的变量名
    public ASTree argument2() {
        return child(1);
    }


    public String toString() {
        return "(Alias " + super.toString() + ")";
    }

    public Object eval(Environment env) {
        //将左边赋值给右边
        //取出左边的值
        String value = (String) argument1().eval(env);
        //取出右边的值
        String key = (String) argument2().eval(env);
        //存取的key右边的字符串，value是左边的字符串
        env.put(key, value);
        return key;
    }
}
