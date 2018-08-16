package gui.controller;

import gui.model.GateOperator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class GateController extends Controller{

    @FXML
    private TextField target;

    @FXML
    private TextField step;

    @FXML
    private TextField parameters;

    int addClicked = -1;

    // 操作的位置
    int targetL;
    // 第幾部操作
    int stepL;
    //寄存器
    int register;

    // 参数123
    String parametersL;

    public int isAdd() {
        return addClicked;
    }

    @FXML
    private void handleAdd() {
        if (isInputValid()) {
            addClicked = stepL;
            circuit.addOperator(new GateOperator(targetL, id, parametersL), stepL);
            dialogStage.close();
        }
    }


    //Gate 参数需要重写写
    private boolean isInputValid() {
        String errorMessage = "";
        try {
            targetL = Integer.parseInt(target.getText());
            stepL = Integer.parseInt(step.getText());
            parametersL = parameters.getText();
        } catch (Exception e) {
            errorMessage = "Enter Integer values ";
        }

        register = circuit.getRegister();

        if (targetL < 0 || targetL >= register) {
            errorMessage = "Enter valid qubit index";
        }

        if (stepL < 0 || stepL > circuit.getNumberOfOperators()) {
            errorMessage = "Enter valid index for gate step, should be between 0 and " + circuit.getNumberOfOperators();
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            alert.setTitle("Error");
            alert.showAndWait();
            return false;
        }
    }
}
