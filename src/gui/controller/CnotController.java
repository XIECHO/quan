package gui.controller;

import gui.model.BinaryOperator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class CnotController extends Controller{
    @FXML
    private TextField target;

    @FXML
    private TextField control;

    @FXML
    private TextField step;

    int addClicked = -1;

    // 操作的位置
    int targetL;

    // 控制的位置
    int controlL;

    // 第幾部操作
    int stepL;
    // 寄存器
    int register;



    public int isAdd() {
        return addClicked;
    }

    @FXML
    private void handleAdd() {
        if (isInputValid()) {
            addClicked = stepL;
            circuit.addOperator(new BinaryOperator( targetL, controlL, id), stepL);
            dialogStage.close();
        }
    }


    private boolean isInputValid() {
        String errorMessage = "";
        try {
            targetL = Integer.parseInt(target.getText());
            controlL = Integer.parseInt(control.getText());
            stepL = Integer.parseInt(step.getText());
        } catch (Exception e) {
            errorMessage = "Enter Integer values ";
        }
        register = circuit.getRegister();

        if ( targetL == controlL ){
            errorMessage = "Control and target qubits must have different values";
        }

        if (targetL < 0 || targetL >= register ||controlL < 0 || controlL >= register) {
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
