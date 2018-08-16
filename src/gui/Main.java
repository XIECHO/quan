package gui;

import Quantum.Circuit;
import Quantum.Qubit;
import gui.controller.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.DrawBloch;
import util.QToBloch;

import java.io.IOException;
import java.util.HashMap;


public class Main extends Application {
    private Stage primaryStage;
    private Pane rootLayout;

    public Circuit getCircuit() {
        return circuit;
    }

    private Circuit circuit;

    public Main(){
        circuit = new Circuit();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("QuantumOperator Simulation");
            primaryStage.getIcons().add(new Image("file:src/gui/image/Quantum-setup-icon.png"));

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Main.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            MainController controller = loader.getController();
            controller.setMain(this);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("...");
            System.out.println(e.getMessage()+"...");
        }
    }


    private Controller showpane(String name , String id) throws IOException{
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource(name));
        AnchorPane page = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle(id);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        Controller controller = null;
        controller = loader.getController();
//        switch (title){
//            case  "init" :
//            case  "initP":
//                controller = loader.getController();
//                break;
//        }


         controller.setDialogStage(dialogStage,id);
         controller.setCircuit(circuit);
         dialogStage.showAndWait();
         return controller;
         //return controller.isAdd();

    }

    //初始化寄存器
    public boolean showInitDialog() throws IOException {
       InitController controller = (InitController) showpane("view/Init.fxml","init");
       return controller.isAdd();
    }

    //初始化寄存器并带参数
    public boolean showInitPDialog() throws IOException {
        showpane("view/InitP.fxml","initP");
        return true;
       // return controller.isAdd();
    }

    //单个比特门
    public int showUnaryDialog(String data) throws IOException {
        UnaryController controller = (UnaryController)showpane("view/Unary.fxml",data);
        return controller.isAdd();
    }

    //cont门
    public int showCnotDialog(String data) throws IOException {
        CnotController cnot = (CnotController)showpane("view/Cnot.fxml",data);
        return cnot.isAdd();
        // return controller.isAdd();
    }

    //U门
    public int showUDialog(String data) throws IOException {
        UController controller = (UController)showpane("view/U.fxml",data);
        return controller.isAdd();
        // return controller.isAdd();
    }

    //G门
    public int showGDialog(String data) throws IOException {
        GateController controller = (GateController)showpane("view/Gate.fxml",data);
        return controller.isAdd();
        // return controller.isAdd();
    }

    //M0门 M1门
    public int showM01Dialog(String data) throws IOException {

            Measure0Controller controller = (Measure0Controller) showpane("view/Measure01.fxml",data);
            return controller.isAdd();

       // return ;
        // return controller.isAdd();
    }

    //M2
    public int showM2Dialog(String data) throws IOException {
        Measure2Controller controller = (Measure2Controller)showpane("view/Measure.fxml", data);
        return controller.isAdd();
        // return controller.isAdd();
    }

    //概率分布
    public void showDistribution(HashMap<String, Integer> hm) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource("view/Distribution.fxml"));
        AnchorPane page = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Distribution");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        DistributionController controller = loader.getController();

        controller.setOutCome(hm);

        dialogStage.show();

    }



    //
    public void showBloch(Qubit q)throws IOException{
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Bloch");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);

        double[] a = QToBloch.qtoBloch(q);
        Scene scene =  new DrawBloch().drawBloch(a);
        dialogStage.setScene(scene);
       // DistributionController controller = loader.getController();

       // controller.setOutCome(hm);




        dialogStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public  Stage getPrimaryStage() {
        // TODO Auto-generated method stub
        return primaryStage;
    }



}
