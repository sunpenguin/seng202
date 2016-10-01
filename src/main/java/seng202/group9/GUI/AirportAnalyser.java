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
import seng202.group9.Core.Airport;

/**
 * Gui controller class currently for creating the bar graph of routes arriving and departing from airports.
 * Extend the class. {@link Controller}
 * Created by michael on 17/09/2016.
 */
public class AirportAnalyser extends Controller {
    //links fxml parts to the controller.
    @FXML
    PieChart pieGraph;

    //Used to store the data needed for making the graph.
    private Dataset currentdata = null;
    private HashMap<String, Integer> useddata = new HashMap<String, Integer>();
    private ArrayList<Airport> current_routes;

    /**
     * Takes data from the current dataset and places it into the displayed pie graph.
     */
    public void build_graph(){
        //Takes Airports from the full dataset.
        current_routes = currentdata.getAirports();
        datasetup(current_routes);
        //Turns the data into a usable list.
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        System.out.println(useddata.keySet().size());
        for (String airport : useddata.keySet()){
            Integer temp = useddata.get(airport);
            pieChartData.add(new PieChart.Data(airport,temp));
        }
        //Gives the data to the graph.
        pieGraph.setData(pieChartData);
    }

    /**
     * Takes the raw list of routes and fills the used data dictionary with the appropriate data to be displayed
     * @param current_air_ports
     */
    private void datasetup(ArrayList<Airport> current_air_ports){
        //Takes out the specified field  then adds to the used data dict.
        for (Airport entry : current_air_ports){
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

    /**
     * Takes the current dataset then loads the data to the graph using build graph.
     */
    public void load() {
        if (!checkDataset()){
            return;
        }
        currentdata = getParent().getCurrentDataset();
        build_graph();
    }

}
