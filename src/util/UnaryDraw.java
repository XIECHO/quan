package util;

import Quantum.Qubit;
import ast.Size;
import interp.Environment;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class UnaryDraw implements Size{

    public static Object draw(Environment env, Pane circuitpane, Pane outcomepane,int location,String key,int num){

        Qubit q = (Qubit)env.get("QuantumRegisterDX");
        int changeNum = (int)env.get("changeDx");

        //开始的位置
        int startX = beginLineX + 20+  changeNum*rowspace;
        Group g = new Group();

        //三条横线
        for (int i = 0; i < q.number(); i++) {
            Line l = new Line(startX, rowspace * i + beginLineY, startX + rowspace, rowspace * i + beginLineY);
            g.getChildren().add(l);
        }

        Rectangle r = new Rectangle(startX, rowspace * location + beginLineY -15, 30,30);
        r.setFill(Color.DARKSEAGREEN);
        Text t1 = new Text(startX, rowspace * location + beginLineY + 3 , key);
        t1.setWrappingWidth(30);
        t1.setTextAlignment(TextAlignment.CENTER);
        g.getChildren().addAll(r, t1);
        circuitpane.getChildren().add(g);


        outcomepane.getChildren().clear();
        if(num == 1){
            Text outcome = new Text(10,20,"当前的状态为："+ AmpToBra.translate(q.getPossibles()));
            outcomepane.getChildren().add(outcome);
        }
        if(num == 2){
            Text outcome = new Text(10,20,"当前的状态为："+ q.matrix());
            outcomepane.getChildren().add(outcome);
        }


        env.put("changeDx",changeNum+1);
        return null;
    }


}
