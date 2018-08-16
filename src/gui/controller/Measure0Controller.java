package gui.controller;

import gui.model.Measure01Operator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Measure0Controller extends Controller {
    int stepL;

    int addClicked = -1;

    @FXML
    private TextField step;

    public int isAdd() {
        return addClicked;
    }

    @FXML
    private void handleAdd() {
        if (isInputValid()) {
            addClicked = stepL;
            circuit.addOperator(new Measure01Operator(id), stepL);
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        //从面板得到的Number
        String x = step.getText();
        String errorMessage = "";
        if (x == null || x.length() == 0) {
            errorMessage = "Enter number of qubits";
        }
        //rX 是String X 转为 Int x

        try {
            stepL = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            errorMessage = "Enter an Integer value";
        }

        if (stepL < 0) {
            errorMessage = "Enter positive values";
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
