package ast;

import interp.Environment;
import util.FindSource;

import java.util.List;


//bool判断，这回主要是两种，一种是直接变量名判断是否为1，另一种带！
public class List_Bool extends ASTList {

	public List_Bool(List<ASTree> list) {
		super(list);
	}

	public String toString() {
		return "(" + super.toString() + ")";
	}

	public Object eval(Environment env) {
		if(numChildren()==2){
			//我们只有测量的时候会出现存数字，所以，其他情况是不会出现数字的
			//首先我们要找到变量的根存的值
			//Object;

			//取值取到什么会结束

			//这里正常应该用eval()方法的
//			Object object = env.get(child(1).toString());
//			while(object!=null){
//				if(!(object instanceof Integer)){
//					object = env.get((String)object);
//				}else{
//					break;
//				}
//			}
//
//			if(object==null){
//				//报错
//			}
//
//			int value = (int) object;
            int value = FindSource.findSource(env,child(1).toString());

			if(value == 0){
				return true;
			}else{
				return false;
			}

		}else{

//			Object object = env.get(child(0).toString());
//			while(object!=null){
//				if(!(object instanceof Integer)){
//					object = env.get((String)object);
//				}else{
//					break;
//				}
//			}
//
//			if(object==null){
//				//报错
//			}
//
//			int value = (int) object;
            int value = FindSource.findSource(env,child(0).toString());
			if(value == 0){
				return false;
			}else{
				return true;
			}


		}

	}

}
