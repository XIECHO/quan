package gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import util.TenToBinary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DistributionController {
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> outComeNames = FXCollections.observableArrayList();

    public void setOutCome(HashMap<String, Integer> hm) {
        Set<String> outcome = hm.keySet();
        String[] outcomeValue = new String[outcome.size()];


        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        Iterator<String> it = outcome.iterator();
        int j =0;
        while(it.hasNext()) {
            String key = it.next();
            //System.out.println(key);
            String[] possib = key.split(" ");
            String possValue = "";
            System.out.println(Arrays.toString(possib));
            for(int i=0; i<possib.length; i++) {

                if(possib[i].startsWith("0.0")) {
                    //possValue =possValue + String.format("%.2f", possib[i]) + TenToBinary.ttb(i, possib.length);
                }else {
                    if(possValue.equals("")) {
                        possValue = possValue + String.format("%.4S", possib[i]) +"|" + i + ">" +"<" + i + "|";
                    }else {
                        possValue = possValue  + "+" + String.format("%.4S", possib[i]) +"|" + i + ">" +"<" + i + "|";
                    }
                }
            }
            outcomeValue[j++] = possValue;
            int value = hm.get(key);
            //System.out.println(new XYChart.Data<>(possValue, value));

            series.getData().add(new XYChart.Data<>(possValue, value));
        }

        outComeNames.addAll(Arrays.asList(outcomeValue));
        //System.out.println(outComeNames);

        xAxis.setCategories(outComeNames);

        barChart.getData().add(series);

    }
}
