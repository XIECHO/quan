package util;

import interp.Environment;

public class FindSource {

    public static int findSource(Environment env,String key){
        Object object = env.get(key);
        while(object!=null){
            if(!(object instanceof Integer)){
                object = env.get((String)object);
            }else{
                break;
            }
        }

        if(object==null){
            //报错
        }

        int value = (int) object;
        return value;
    }
}
