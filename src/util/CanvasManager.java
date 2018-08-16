package util;

import java.util.HashMap;

import Quantum.Circuit;
import ast.Size;
import gui.model.*;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class CanvasManager implements Size{

    private Circuit circuit;
    // 画电路图的面板
    private Pane circuitpane;

    private Pane outcomepane;

    private TextArea textarea;
    // 電路圖的行數
    int xLines;

    // 存输出的语句
    HashMap<Integer, String> hm = new HashMap<>();


    public CanvasManager(Circuit circuit, Pane circuitpane, TextArea textarea, Pane outcomepane) {
        this.circuit = circuit;
        this.circuitpane = circuitpane;
        this.textarea = textarea;
        this.outcomepane = outcomepane;

    }

    //清除面板的内容
    private void clearpane() {
        if (circuitpane.getChildren() != null) {
            circuitpane.getChildren().clear();
        }

        if (outcomepane.getChildren() != null) {
            outcomepane.getChildren().clear();
        }
    }

    public void resetCanvasManager(Circuit circuit) {
        this.circuit = circuit;

        clearpane();

//		int x = this.circuit.getRegister();
//		xLines = this.circuit.getX() != null ? x.getNumberOfQubits() : 0;
        xLines = this.circuit.getRegister();

        // 文本輸出
        hm.put(0, "Quantum a " + xLines);
        textarea.setText(hm.get(0));
    }

//	public void resetAssCanvasManager(Circuit circuit) {
//		this.circuit = circuit;
//		if (circuitPane.getChildren().size() != 0) {
//			circuitPane.getChildren().clear();
//		}
//		statePane.getChildren().clear();
//
//		Register x = this.circuit.getX();
//		xLines = this.circuit.getX() != null ? x.getNumberOfQubits() : 0;
//
//
//
//		String value = "";
//		for(int i=0;i<circuit.getAmplitudes().length;i++) {
//			value = value + circuit.getAmplitudes()[i];
//			if(i<circuit.getAmplitudes().length-1) {
//				value = value + ",";
//			}
//		}
//		// 文本輸出
//		hm.put(0, "QuantumOperator a[" + xLines + "]("+value+")");
//
//		textarea.setText(hm.get(0));
//
//
//		//这儿还要加assignment()!!!!!!
//	}


    public void drawInitState() {

        Group g = new Group();
        Line step = new Line(beginLineX + 10, 30, beginLineX + 10, 30 + rowspace * xLines);
        for (int i = 0; i < xLines; i++) {
            //行数
            Text index = new Text(10, rowspace * i + beginLineY + 5, Integer.toString(i));
            index.setFont(new Font(14));
            index.setFill(Color.TEAL);

            Text qubitVal = new Text(20, rowspace * i + beginLineY + 5, " |q" + i + ">");
            qubitVal.setFont(new Font(14));

            Line l = new Line(beginLineX, beginLineY + rowspace * i, beginLineX + 20, beginLineY + rowspace * i);
            g.getChildren().addAll(index, qubitVal, l);
        }
        circuitpane.getChildren().addAll(step, g);
    }


    private void drawRecangle(int index, int target, String name) {
        //开始的位置
        int startX = beginLineX + 20 + index * rowspace;
        Group g = new Group();

        //三条横线
        for (int i = 0; i < xLines; i++) {
            Line l = new Line(startX, rowspace * i + beginLineY, startX + rowspace, rowspace * i + beginLineY);
            g.getChildren().add(l);
        }

        Rectangle r = new Rectangle(startX, rowspace * target + beginLineY - 15, 30, 30);
        r.setFill(Color.DARKSEAGREEN);
        Text t1 = new Text(startX, rowspace * target + beginLineY + 3, name);
        t1.setWrappingWidth(30);
        t1.setTextAlignment(TextAlignment.CENTER);
        g.getChildren().addAll(r, t1);
        circuitpane.getChildren().add(g);
    }

    private void printText(int index) {
        String a = "";
        for (int i = 0; i <= index + 1; i++) {
            a = a + hm.get(i);
        }
        textarea.setText(a);
    }

    // 正常顺序下的输出，index指的执行的第几个步骤-1
    public void drawUnaryOperator(int index, UnaryOperator operator) {

        String name = operator.getName();
        int target = operator.getTarget();


        drawRecangle(index, target, name);


        hm.put(index + 1, ";\r\n" + name + " a[" + target + "]");
        printText(index);
    }

    public void drawUOperator(int index, UOperator operator) {
        String name = operator.getName();
        int target = operator.getTarget();

        drawRecangle(index, target, name);

        // U(PI/2,0.2,0.1) Alice;
        String p1 = operator.getPama1();
        String p2 = operator.getPama2();
        String p3 = operator.getPama3();

        hm.put(index + 1, ";\r\n" + name + "(" + p1 + "," + p2 + "," + p3 + ") a[" + target + "]");
        printText(index);
    }

    //这里给变量赋值有问题的e
    public void drawMeasureOperator(int index, Measure2Operator operator) {
        String name = operator.getName();
        int target = operator.getTarget();


        drawRecangle(index, target, name);

        // Measure Alice => i;
        // 赋值给随机一个字符串
        char[] temp = String.valueOf(index + 1).toCharArray();
        for (int i = 0; i < temp.length; i++) {
            temp[i] = (char) ((int) temp[i] + 48);
        }
        hm.put(index + 1, ";\r\n" + "Measure a[" + target + "]" + " , " + String.valueOf(temp));

        printText(index);
    }

    public void drawMeasure01Operator(int index, Measure01Operator operator) {
        String name = operator.getName();
        //开始的位置
        int startX = beginLineX + 20 + index * rowspace;
        Group g = new Group();

        //三条横线
        for (int i = 0; i < xLines; i++) {
            Line l = new Line(startX, rowspace * i + beginLineY, startX + rowspace, rowspace * i + beginLineY);
            g.getChildren().add(l);
        }

        Rectangle r = new Rectangle(startX, rowspace * 0 + beginLineY - 15, 30, 30 + rowspace * (xLines - 1));
        r.setFill(Color.DARKSEAGREEN);
        Text t1 = new Text(startX, rowspace * Math.ceil((xLines / 2)) + beginLineY + 3, "M");
        t1.setWrappingWidth(30);
        t1.setTextAlignment(TextAlignment.CENTER);
        g.getChildren().addAll(r, t1);
        circuitpane.getChildren().add(g);


        // Measure Alice => i;
        // 赋值给随机一个字符串

        if (name.equals("measure")) {
            hm.put(index + 1, ";\r\n" + "Measure");
        } else {
            char[] temp = String.valueOf(index + 1).toCharArray();
            for (int i = 0; i < temp.length; i++) {
                temp[i] = (char) ((int) temp[i] + 48);
            }
            hm.put(index + 1, ";\r\n" + "Measure " + String.valueOf(temp));

        }
        printText(index);

    }


    public void drawGateOperator(int index, GateOperator operator) {

        String name = operator.getName();
        int target = operator.getTarget();
        String parammeters = operator.getPama1();

        drawRecangle(index, target, name);

        hm.put(index + 1, ";\r\n" + name + "(" + parammeters + ")" + " a[" + target + "]");
        //hm.put(index + 1, ";\r\n" + name + "(" + p1 + "," + p2 + "," + p3 + ") a[" + target + "]");
        printText(index);

    }

    public void drawBinaryOperator(int index, BinaryOperator operator) {
        String name = operator.getName();
        if (name.equals("cnot")) {

            int target = operator.getTarget();
            int control = operator.getControl();
            // TODO Auto-generated method stub

            int startX = beginLineX + 20 + index * rowspace;
            Group g = new Group();

            //三条横线
            for (int i = 0; i < xLines; i++) {
                Line l = new Line(startX, rowspace * i + beginLineY, startX + rowspace, rowspace * i + beginLineY);
                g.getChildren().add(l);
            }

            //获取变量1操作位置
            int location1 = control;
            //获取变量2操作位置
            int location2 = target;


            Circle circle = new Circle(startX + 15, rowspace * location1 + beginLineY, 13);
            circle.setFill(Color.DARKSEAGREEN);
            Circle circle2 = new Circle(startX + 15, rowspace * location2 + beginLineY, 6);
            circle2.setFill(Color.DARKSEAGREEN);
            Line line = new Line(startX + 15, rowspace * location1 + beginLineY, startX + 15, rowspace * location2 + beginLineY);
            line.setStroke(Color.DARKSEAGREEN);
            g.getChildren().addAll(circle, line, circle2);
            circuitpane.getChildren().add(g);


            // CNOT Alice Bob1;

            hm.put(index + 1, ";\r\n" + "CNOT a[" + control + "]" + " a[" + target + "]");
            printText(index);
        }

    }

    // 重新输出from后面的图像
    public void redrawOperatorsOnly(int from) {
        for (int i = from + 1; i < hm.size(); i++) {
            hm.remove(i);
        }
        for (int i = from ; i < circuit.getNumberOfOperators(); i++) {
            Operator o = circuit.getOperator(i);

            String s = o.getType();
            switch (s) {
                case "Unary":
                    drawUnaryOperator(i-1, (UnaryOperator) o);
                    break;
                case "Binary":
                    drawBinaryOperator(i-1, (BinaryOperator) o);
                    break;
                case "U":
                    drawUOperator(i-1, (UOperator) o);
                    break;
                case "Measure01":
                    drawMeasure01Operator(i-1, (Measure01Operator) o);
                    break;
                case "Measure2":
                    drawMeasureOperator(i-1, (Measure2Operator) o);
                    break;
                case "Gate":
                    drawGateOperator(i-1, (GateOperator) o);
                    break;
            }
        }
    }


}
