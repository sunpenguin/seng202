package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airline;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.Group;

/**
 * Created by michael on 17/09/2016.
 */
public class AirportAnalyser extends Controller {
    @FXML
    PieChart pieGraph;

    private Dataset currentdata = null;
    private HashMap<String, Integer> useddata = new HashMap<String, Integer>();

    private ArrayList<Airline> current_routes;
    public void build_graph(){
        current_routes = currentdata.getAirlines();
        datasetup(current_routes);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        System.out.println(useddata.keySet().size());
        for (String airport : useddata.keySet()){
            Integer temp = useddata.get(airport);
            pieChartData.add(new PieChart.Data(airport,temp));
        }
        pieGraph.setData(pieChartData);
    }

    private void datasetup(ArrayList<Airline> current_routes){
        for (Airline entry : current_routes){
            String name = entry.getCountryName();
            if (useddata.containsKey(name)){
                int temp = useddata.get(name);
                useddata.replace(name,temp+1);
            }else {
                Integer temp = 1;
                useddata.put(name,temp);
            }
        }
    }

    public void load() {
        currentdata = getParent().getCurrentDataset();
        build_graph();
    }

}
