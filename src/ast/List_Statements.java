package ast;

import interp.Environment;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

public class List_Statements extends ASTList {

	public List_Statements(List<ASTree> list) {
		super(list);
	}

	//返回所有的Statement列表
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ASTree ast : children) {
			sb.append(ast.toString() + "\r\n");
		}
		return sb.toString();
	}

	public Object eval(Environment env) {
		for (ASTree ast : children) {
			ast.eval(env);
		}
		return "";
	}

	public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num){
        Text ind = new Text(15, 20, "");
        circuitpane.getChildren().add(ind);

        for (int i = 0; i < numChildren(); i++) {
            Object o = children.get(i).eval(env,circuitpane,outcomepane,num);
            if(o instanceof String){
            	if(o.toString().equals("buyunxuceliangde")){
            		break;
				}
			}
            ind.setText("Step:" + Integer.toString(i + 1) );
        }
	    return null;
    }

    public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num , int step){
        Text ind = new Text(15, 20, "");
        circuitpane.getChildren().add(ind);

        for (int i = 0; i < step; i++) {
			Object o = children.get(i).eval(env,circuitpane,outcomepane,num);
			if(o instanceof String){
				if(o.toString().equals("buyunxuceliangde")){
					break;
				}
			}
            ind.setText("Step:" + Integer.toString(i + 1) );
        }
        return null;
    }


}
