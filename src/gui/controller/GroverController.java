package gui.controller;


import alogrithm.GroverAlgorithm;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import java.util.ArrayList;


public class GroverController extends Controller {

    int number;

    Boolean addClicked = true;

    @FXML
    private TextField sum;

    @FXML
    private TextField target;



    @FXML
    public String handleAdd() {

        int numQubits = 0, value = 0;
        double j;
        ArrayList<Double> vector = new ArrayList<Double>();

        numQubits = Integer.parseInt(sum.getText());
        value = Integer.parseInt(target.getText());

        for(double i = 0; i < Math.pow(2, numQubits); i++)
        {
            vector.add(i);
        }

        // Begins Grover's Algorithm.
        GroverAlgorithm grover = new GroverAlgorithm(numQubits, value, vector);
        grover.initializeBinary();
        grover.initialize();
        vector = grover.createArray();
        grover.setVector(vector);
        for (j = 0.0; j < (int) Math.sqrt(Math.pow(2, numQubits));j++)
        {
            grover.phaseInversion();
            grover.inversionMean();
        }

        // Iterating beyond the √(2^n). Yields worse results.

//  		for (j = 0.0; j < (int) Math.sqrt(Math.pow(2, numQubits)) + 1; j++)
//		{
//			alogrithm.phaseInversion();
//			alogrithm.inversionMean();
//		}


        // Prints the result.
        System.out.println("Final vector: " + vector);
        double probability = grover.findProbability();
        System.out.println("The probability of finding " + value + " is " + probability + ".");
        System.out.println("Found in " + (int) j + " steps.");
        super.dialogStage.close();
        return "Final vector: " + vector + "\nThe probability of finding" + value + " is " + probability + ".\n" + "Found in " + (int) j + " steps.";

    } /* main */




//    private boolean isInputValid() {
//        //从面板得到的Number
//        String x = register.getText();
//        String errorMessage = "";
//        if (x == null || x.length() == 0) {
//            errorMessage = "Enter number of qubits";
//        }
//        //rX 是String X 转为 Int x
//
//        try {
//            number = Integer.parseInt(x);
//        } catch (NumberFormatException e) {
//            errorMessage = "Enter an Integer value";
//        }
//
//        if (number < 0) {
//            errorMessage = "Enter positive values";
//        }
//
//        if (errorMessage.length() == 0) {
//            return true;
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText(errorMessage);
//            alert.setTitle("Error");
//            alert.showAndWait();
//            return false;
//        }
//    }


}
