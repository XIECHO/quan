package gui.controller;

import Quantum.Circuit;
import Quantum.Qubit;
import ast.List_Statements;
import gui.Main;
import gui.model.*;
import interp.Environment;
import interp.Lexer;
import interp.Parser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.text.Text;

import javafx.stage.FileChooser;
import util.CanvasManager;

import java.io.*;
import java.util.HashMap;

public class MainController {
    private Main main;
    private Circuit circuit;
    private CanvasManager canvasManager;


    public void setMain(Main main) {
        this.main = main;
        circuit = main.getCircuit();
        canvasManager = new CanvasManager(circuit, circuitpane, textarea, outcomepane);
    }
    @FXML
    private TextField times;

    @FXML
    private TextArea textarea;

    @FXML
    private AnchorPane circuitpane;

    @FXML
    private AnchorPane outcomepane;

    @FXML
    private Text consoletext;

    @FXML
    private MenuItem helpMe;

    //这里文件的关闭是有问题的
    @FXML
    private void export() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());
        // FileReader read = new FileReader(file);
        try {
            if (file != null) {
                FileWriter writer = new FileWriter(file);
                String str = textarea.getText();
                writer.write(str);
                writer.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //这边也是文件关闭有问题的
    @FXML
    private void open() {

        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());
        try {
            if (file != null) {
                FileReader read = new FileReader(file);
                BufferedReader br = new BufferedReader(read);
                String temp;
                String str = br.readLine();
                while ((temp = br.readLine()) != null) {
                    str = str + "\r\n" + temp;
                }
                textarea.setText(str);
                br.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void quit() {
       main.getPrimaryStage().close();
    }

    @FXML
    private void help() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("你可以访问 https://github.com/xiecho/ 获取更多信息");
        alert.setTitle("Help");
        alert.showAndWait();



    }
    //点击restart的时候，environment也应该清空的

    @FXML
    private void restart() {
        clearpane();
        run();
    }

    //清除面板的内容
    private void clearpane() {
        if (circuitpane.getChildren() != null) {
            circuitpane.getChildren().clear();
        }

        if (outcomepane.getChildren() != null) {
            outcomepane.getChildren().clear();
        }

        consoletext.setText("");
    }

    //执行方式
    //num 为1 run
    //num 为2 circuit

    private Qubit excute(int num) {
        String text;
        if (textarea.getSelectedText().isEmpty()) {
            text = textarea.getText();
        } else {
            text = textarea.getSelectedText();
        }
        Environment env = new Environment();
        try {
            Lexer lexer = new Lexer(text);
            Parser parser = new Parser(lexer);
            List_Statements st = parser.program();
            // env.put("lastState", null);
            st.eval(env, circuitpane, outcomepane, num);
            return (Qubit) env.get("QuantumRegisterDX");
        } catch (Exception e) {
            consoletext.setText("\n" + e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }


    //run , 代码全部执行
    @FXML
    private Qubit run() {

        clearpane();
        return excute(1);


        // return env.get("lastState");
    }

    // 生成量子电路
    @FXML
    private Qubit circuit() {

        clearpane();
        excute(2);
        return null;
    }


    //这里有一个全局变量记录执行到第几部用于next forward
    private int step = 1;

    //next
    @FXML
    private void next() {
        clearpane();
        Environment env = new Environment();
        String text = textarea.getText();
        try {
            Lexer lexer = new Lexer(text);
            Parser parser = new Parser(lexer);
            List_Statements st = parser.program();
            int i = st.numChildren();
            if (step < i) {
                step++;
            }
            st.eval(env, circuitpane, outcomepane, 2, step);
        } catch (Exception e) {
            consoletext.setText("\n" + e.getMessage());
            System.out.println(e.getMessage());
        }

    }

    //forward
    @FXML
    private void forward() {
        clearpane();
        Environment env = new Environment();
        String text = textarea.getText();
        try {
            Lexer lexer = new Lexer(text);
            Parser parser = new Parser(lexer);
            List_Statements st = parser.program();
            if (step > 1) {
                step--;
            }
            st.eval(env, circuitpane, outcomepane, 2, step);
        } catch (Exception e) {
            consoletext.setText("\n" + e.getMessage());
            System.out.println(e.getMessage());
        }
    }




    // 初始化寄存器，不带参数
    @FXML
    private void init() throws IOException {

        //main.showInitDialog();
        if (main.showInitDialog()) {
            canvasManager.resetCanvasManager(circuit);
            canvasManager.drawInitState();
        }
    }

    //初始化寄存器并初始化，带参数
    @FXML
    private void initP() throws IOException {
        main.showInitPDialog();
//        if(main.showAssRegistersDialog()) {
//            canvasManager.resetAssCanvasManager(circuit);
//            canvasManager.drawInitState();
//        }
    }


    //getnodeId
    private String getNodeId(ActionEvent event) {
        if (circuit.getNumberOfOperators() == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please init circuit");
            alert.setTitle("Error");
            alert.showAndWait();
            return null;
        } else {
            Node node = (Node) event.getSource();
            String data = node.getId();
            return data;
        }
    }

    //增加内置的Unary矩阵
    @FXML
    private void addUnary(ActionEvent event) throws IOException {
        if(getNodeId(event)!=null){
            int okClicked = main.showUnaryDialog(getNodeId(event));
            addAndDraw(okClicked, "Unary");
        }
    }

    //增加内置的Binary矩阵
    @FXML
    private void addCnot(ActionEvent event) throws IOException {
        if(getNodeId(event)!=null) {
            int okClicked = main.showCnotDialog(getNodeId(event));
            addAndDraw(okClicked, "Cnot");
        }
    }

    //增加通用U矩阵
    @FXML
    private void addU(ActionEvent event) throws IOException {
        if(getNodeId(event)!=null) {
            int okClicked = main.showUDialog(getNodeId(event));
            addAndDraw(okClicked, "U");
        }
    }

    //自定义G矩阵
    @FXML
    private void addG(ActionEvent event) throws IOException {
        if(getNodeId(event)!=null) {
            int okClicked = main.showGDialog(getNodeId(event));
            addAndDraw(okClicked, "Gate");
        }
    }

    //（总体测量）
    @FXML
    private void measure0(ActionEvent event) throws IOException {
        if(getNodeId(event)!=null) {
            int okClicked = main.showM01Dialog(getNodeId(event));
            addAndDraw(okClicked, "M01");
        }
    }


    //测量单个位置
    @FXML
    private void measure2(ActionEvent event) throws IOException {
        if(getNodeId(event)!=null) {
            int okClicked = main.showM2Dialog(getNodeId(event));
            addAndDraw(okClicked, "M2");
        }
    }


    // 画图
    //画图的时候下标可能有问题
    private void addAndDraw(int okClicked, String type) {
        //circuit 有几条命令
        int number = circuit.getNumberOfOperators();
        int temp = number -1;
        Text text = new Text(15, 20,"步骤："+temp);
        circuitpane.getChildren().add(text);

        if ((okClicked == number - 1) && (okClicked != -1)) {

            if (type.equals("Unary")) {
                canvasManager.drawUnaryOperator(number - 2,
                        (UnaryOperator) circuit.getLastOperator());
            } else if (type.equals("U")) {
                canvasManager.drawUOperator(number - 2, (UOperator) circuit.getLastOperator());
            } else if (type.equals("Gate")) {
                canvasManager.drawGateOperator(number - 2, (GateOperator) circuit.getLastOperator());
            } else if (type.equals("M2")) {
                canvasManager.drawMeasureOperator(number - 2,
                        (Measure2Operator) circuit.getLastOperator());
            } else if (type.equals("Cnot")) {
                canvasManager.drawBinaryOperator(number - 2,
                        (BinaryOperator) circuit.getLastOperator());
            } else if (type.equals("M01")) {
                canvasManager.drawMeasure01Operator(number - 2,
                        (Measure01Operator) circuit.getLastOperator());
            }
        } else if (okClicked != -1) {
            // circuit.reInitializeRegisterQubits();
            //这里可能有问题的
            circuitpane.getChildren().remove(okClicked + 1, circuitpane.getChildren().size());
            canvasManager.redrawOperatorsOnly(okClicked - 1);
        }
    }


    //经典的概率分布
    @FXML
    private void showD() throws IOException {

        int num = isInputValid();
        if (num != -1) {
            HashMap<String, Integer> hm = new HashMap<>();
            // hm 第一个为返回状态，第二个为次数
            for (int i = 0; i < num; i++) {
                Qubit temp = run();
                String tempS = temp.possibleValue();
                if (hm.get(tempS) == null) {
                    hm.put(tempS, 1);
                } else {
                    int numState = hm.get(tempS);
                    hm.put(tempS, numState + 1);
                }
            }

            try {
                showDistribution2(hm);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    private void showDistribution2(HashMap<String, Integer> hm) throws IOException {

        main.showDistribution(hm);
    }

    @FXML
    private void bloch()throws IOException{
        clearpane();
        Qubit q  =  run();
        main.showBloch(q);
    }

    private int isInputValid() {
        String errorMessage = "";
        int num = -1;
        try {
            num = Integer.parseInt(times.getText());
            if (num < 0) {
                errorMessage = "Enter positive values";
            }
        } catch (Exception e) {
            errorMessage = "Enter Integer values ";
        }

        if (errorMessage.length() == 0) {
            return num;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            alert.setTitle("Error");
            alert.showAndWait();
            return num;
        }
    }

}
