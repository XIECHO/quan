package gui.controller;

import Quantum.Circuit;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public abstract class Controller {
    protected Stage dialogStage;
    protected Circuit circuit;

    String id;
    public void setDialogStage(Stage dialogStage, String id) {
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
        this.id = id;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    @FXML
    private void handleAdd(){

    }
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
}
