package ast;

import Quantum.Qubit;
import interp.Environment;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import operater.MeasureOperator;
import util.AmpToBra;
import util.FindSource;
import util.UnaryDraw;

import java.util.List;


//Measure 测量，有两种测量，第一种测量方式为测一个量子比特，另一个是总体测量
//Measure;
//Measure Bob1 ,j;

//我们可能还希望有第三种情况
//Measure cregister;
public class List_Measure extends ASTList {

    public List_Measure(List<ASTree> list) {
        super(list);
    }

    public String toString() {
        return "(Measure " + super.toString() + ")";
    }

    //因为有第三种情况，需哦一这里要重写
    //第一个参数，原变量名
    public ASTree argument1() {
        return numChildren() == 2 ? child(0) : null;
    }

    //第二个参数，新的变量名
    public ASTree argument2() {
        return numChildren() == 2 ? child(1) : null;
    }

    public Object eval(Environment env) {

        //Measure;
        if (numChildren() == 0) {

            //获取操作对象，在这个实验里是QuantumRegisterDX，正常应该解析Array得到对象，偷懒中。。。
            Qubit q = (Qubit) env.get("QuantumRegisterDX");
            //或者q的长度
            int number = q.number();
            //对每一位都做测量

            for (int i = 0; i <number ; i++) {

                MeasureOperator.measure(q,i);


            }

        }

        //Measure Bob1 ,j;

        if (numChildren() == 2) {
            //首先确定测量的位置
            int location = FindSource.findSource(env,argument1().toString());
            // 确定要赋值的对象
            String key = (String) argument2().eval(env);
            //获取操作对象，在这个实验里是QuantumRegisterDX，正常应该解析Array得到对象，偷懒中。。。
            Qubit q = (Qubit) env.get("QuantumRegisterDX");
            //测量
            int value = MeasureOperator.measure(q,location);

            //记录测量结果
            env.put(key,value);
        }

        if(numChildren() == 1){
            //获取操作对象，在这个实验里是QuantumRegisterDX，正常应该解析Array得到对象，偷懒中。。。
            Qubit q = (Qubit) env.get("QuantumRegisterDX");
            //或者q的长度
            int number = q.number();
            //获取存储的传统寄存器
            String key = (String) child(0).eval(env);
            //对每一位都做测量
            for (int i = 0; i <number ; i++) {
                int value = MeasureOperator.measure(q,i);
                env.put(key+"["+i+"]",value);
            }


        }

        return null;
    }


    public Object eval(Environment env, Pane circuitpane, Pane outcomepane, int num){


        if(num == 1){
            eval(env);
            if (numChildren() == 2 ) {
                //获取操作位置
                int location = FindSource.findSource(env,argument1().toString());

                //1 13  因为老师要改线， 所以改成了这个样子
                //UnaryDraw.draw(env,circuitpane,outcomepane,location,"M",num);
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
                r.setFill(Color.RED);
                Text t1 = new Text(startX, rowspace * location + beginLineY + 3 , "M");
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
            }


            //这里要重写
            if(numChildren() == 1 || numChildren()==0){


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

                Rectangle r = new Rectangle(startX, rowspace * 0 + beginLineY -15, 30,30+rowspace*(q.number()-1));
                r.setFill(Color.DARKSEAGREEN);
                Text t1 = new Text(startX, rowspace * Math.ceil((q.number()/2)) + beginLineY + 3 , "M");
                t1.setWrappingWidth(30);
                t1.setTextAlignment(TextAlignment.CENTER);
                g.getChildren().addAll(r, t1);
                circuitpane.getChildren().add(g);



                outcomepane.getChildren().clear();
                Text outcome = new Text(10,20,"当前的状态为："+ AmpToBra.translate(q.getPossibles()));
                outcomepane.getChildren().add(outcome);

                env.put("changeDx",changeNum+1);

                return null;

            }

        }




        if(num == 2){

//            if (numChildren() == 2 ) {
//                //获取操作位置
//                int location = FindSource.findSource(env,argument1().toString());
//                Qubit q = (Qubit)env.get("QuantumRegisterDX");
//                int changeNum = (int)env.get("changeDx");
//
//                //开始的位置
//                int startX = beginLineX + 20+  changeNum*rowspace;
//                Group g = new Group();
//
//                //三条横线
//                for (int i = 0; i < q.number(); i++) {
//                    Line l = new Line(startX, rowspace * i + beginLineY, startX + rowspace, rowspace * i + beginLineY);
//                    g.getChildren().add(l);
//                }
//
//                Rectangle r = new Rectangle(startX, rowspace * location + beginLineY -15, 30,30);
//                r.setFill(Color.DARKSEAGREEN);
//                Text t1 = new Text(startX, rowspace * location + beginLineY + 3 , "M");
//                t1.setWrappingWidth(30);
//                t1.setTextAlignment(TextAlignment.CENTER);
//                g.getChildren().addAll(r, t1);
//                circuitpane.getChildren().add(g);
//            }
//
//
//            //这里要重写
//            if(numChildren() == 1 || numChildren()==0){
//
//
//                Qubit q = (Qubit)env.get("QuantumRegisterDX");
//                int changeNum = (int)env.get("changeDx");
//
//
//
//                //开始的位置
//                int startX = beginLineX + 20+  changeNum*rowspace;
//                Group g = new Group();
//
//                //三条横线
//                for (int i = 0; i < q.number(); i++) {
//                    Line l = new Line(startX, rowspace * i + beginLineY, startX + rowspace, rowspace * i + beginLineY);
//                    g.getChildren().add(l);
//                }
//
//                Rectangle r = new Rectangle(startX, rowspace * 0 + beginLineY -15, 30,30+rowspace*(q.number()-1));
//                r.setFill(Color.DARKSEAGREEN);
//                Text t1 = new Text(startX, rowspace * Math.ceil((q.number()/2)) + beginLineY + 3 , "M");
//                t1.setWrappingWidth(30);
//                t1.setTextAlignment(TextAlignment.CENTER);
//                g.getChildren().addAll(r, t1);
//                circuitpane.getChildren().add(g);
//
//
//                env.put("changeDx",changeNum+1);


            Qubit q = (Qubit)env.get("QuantumRegisterDX");
            int changeNum = (int)env.get("changeDx");
            //开始的位置
            int startX = beginLineX + 20+  changeNum*rowspace;
            Group g = new Group();

            Line l = new Line(startX, 30, startX, rowspace *q.number() + 30);
            l.setStrokeWidth(6);
            l.setStroke(Color.RED);

            g.getChildren().add(l);

            circuitpane.getChildren().add(g);


            env.put("changeDx",changeNum+1);

            //同时这里画两条线
            return "buyunxuceliangde";

            }

        //}




        return null;
    }

}
