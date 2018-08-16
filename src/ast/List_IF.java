package ast;

import interp.Environment;
import javafx.scene.layout.Pane;

import java.util.List;


//If语句：控制 和 执行
public class List_IF extends ASTList {

	public List_IF(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	//条件
	public ASTree condition() {

		return child(0);
	}

	//执行的内容
	public ASTree thenBlock() {
		return child(1);
	}

	public String toString() {
		return "(if" + condition() + "then" + thenBlock() + " fi)";
	}

	//这边不知道对不对
    public Object eval(Environment env) {
        Object c = condition().eval(env);
		if (c instanceof Boolean && ((Boolean) c).equals(true)){
			thenBlock().eval(env);
		}
        return null;
    }

    public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num){
        Object c = condition().eval(env,circuitpane,outcomepane,num);

        if (c instanceof Boolean && ((Boolean) c).equals(true)){

            thenBlock().eval(env,circuitpane,outcomepane,num);
        }
        return null;
    }




}
