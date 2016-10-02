package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static javafx.application.ConditionalFeature.FXML;

/**
 * Created by Gondr on 2/10/2016.
 */
public class AirlineGraphController extends Controller{
    private Dataset dataset;
    private Session session;
    private LinkedHashMap<String, Airline> airlineDict;
    private HashMap<Integer, String> sessionDict;
    Airline[] airlinesFiltered;
    @FXML
    private BarChart routeGraph;
    @FXML
    private NumberAxis routeYAxis;
    @FXML
    private CategoryAxis routeXAxis;

    @Override
    public void load() {
        if (!checkDataset()){
            return;
        }
        dataset = getParent().getCurrentDataset();
        airlineDict = dataset.getAirlineDictionary();
        session = getParent().getSession();
        sessionDict = session.getFilteredAirlines();
        ArrayList<Airline> airlinesArrayList = new ArrayList<>();
        for (int key : sessionDict.keySet()) {
            airlinesArrayList.add(airlineDict.get(sessionDict.get(key)));

        }
        airlinesFiltered = airlinesArrayList.toArray(new Airline[airlinesArrayList.size()]);

        loadRoutesGraph();
    }

    public void loadRoutesGraph(){
        routeGraph.setTitle("Top 10 Number of Routes vs Airports");
        routeXAxis.setLabel("Airports");

        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Routes");
        ArrayList<Airline> airlines = new ArrayList<>();
        airlines.addAll(Arrays.asList(airlinesFiltered));
        Airline maxRoutes[] = new Airline[10];
        System.out.println(airlines.size());
        for (int i = 0 ; i < maxRoutes.length; i ++) {
            int max = 0;
            Airline maxAirline = null;
            for (Airline airline: airlines){
                if (airline != null) {
                    if (airline.getRoutes().size() > max) {
                        maxAirline = airline;
                        max = airline.getRoutes().size();
                    }
                }
            }
            maxRoutes[i] = maxAirline;
            airlines.remove(maxAirline);
        }
        for (int i = 0; i < maxRoutes.length; i++){
            if (maxRoutes[i] != null) {
                series.getData().add(new XYChart.Data<String, Integer>(maxRoutes[i].getName(), maxRoutes[i].getRoutes().size()));
            }
        }
        routeGraph.getData().add(series);
    }

    public void goToRawData(){
        replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
    }
}
