package gui.controller;

import gui.model.UOperator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class UController extends Controller {
    @FXML
    private TextField target;

    @FXML
    private TextField step;

    @FXML
    private TextField parameter1;

    @FXML
    private TextField parameter2;

    @FXML
    private TextField parameter3;

    // 参数123
    String p1, p2, p3;

    // 操作的位置
    int targetL;
    // 第幾部操作
    int stepL;
    //寄存器
    int register;

    int addClicked = -1;

    public int isAdd() {
        return addClicked;
    }

    @FXML
    private void handleAdd() {
        if (isInputValid()) {
            circuit.addOperator(new UOperator(targetL, id, p1, p2, p3), stepL);
            dialogStage.close();
        }
    }


    private boolean isInputValid() {
        String errorMessage = "";
        // 参数123
        p1 = parameter1.getText();
        p2 = parameter2.getText();
        p3 = parameter3.getText();
        try {
            // 对第几个量子比特操作
            targetL = Integer.parseInt(target.getText());
            // 第几步操作
            stepL = Integer.parseInt(step.getText());
//			Integer.parseInt(p1);
//			Integer.parseInt(p2);
//			Integer.parseInt(p3);
        } catch (Exception e) {
            errorMessage = "Enter Integer values ";
        }

        // 寄存器的名字
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
