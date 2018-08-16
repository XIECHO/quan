package gui.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class InitController extends Controller {

    int number;

    Boolean addClicked = true;

    @FXML
    private TextField register;

    public boolean isAdd() {
        return addClicked;
    }

    @FXML
    private void handleAdd() {
        if (isInputValid()) {
            circuit.initializeRegisters(number);
            addClicked = true;
            super.dialogStage.close();
        }
    }

    private boolean isInputValid() {
        //从面板得到的Number
        String x = register.getText();
        String errorMessage = "";
        if (x == null || x.length() == 0) {
            errorMessage = "Enter number of qubits";
        }
        //rX 是String X 转为 Int x

        try {
            number = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            errorMessage = "Enter an Integer value";
        }

        if (number < 0) {
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
