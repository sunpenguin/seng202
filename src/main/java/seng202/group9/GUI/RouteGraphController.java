package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Gondr on 2/10/2016.
 */
public class RouteGraphController extends Controller{
    private Dataset dataset;
    private Session session;
    private LinkedHashMap<String, Route> routeDict;
    private HashMap<Integer, String> sessionDict;
    Route[] routesFiltered;
    @FXML
    private BarChart airlineGraph;
    @FXML
    private CategoryAxis airlineXAxis;
    @FXML
    private BarChart destGraph;
    @FXML
    private CategoryAxis destXAxis;
    @FXML
    private BarChart sourceGraph;
    @FXML
    private CategoryAxis sourceXAxis;
    @FXML
    private BarChart inCountryGraph;
    @FXML
    private CategoryAxis inCountryXAxis;
    @FXML
    private BarChart outCountryGraph;
    @FXML
    private CategoryAxis outCountryXAxis;
    @FXML
    private BarChart equipGraph;
    @FXML
    private CategoryAxis equipXAxis;
    @FXML
    private BarChart similarGraph;
    @FXML
    private CategoryAxis similarXAxis;
    @FXML
    private PieChart stopsGraph;
    @FXML
    private BarChart contienentGraph;
    @FXML
    private CategoryAxis contienentXAxis;

    @Override
    public void load() {
        if (!checkDataset()){
            return;
        }
        dataset = getParent().getCurrentDataset();
        routeDict = dataset.getRouteDictionary();
        session = getParent().getSession();
        sessionDict = session.getFilteredRoutes();
        if (sessionDict.size() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Data");
            alert.setHeaderText("No Filtered Data");
            alert.setContentText("There is no set Filtered Data Please go to the Route Raw Data and Filter a Dataset to Analyse.");
            alert.showAndWait();
        }
        ArrayList<Route> routeArrayList = new ArrayList<>();
        for (int key : sessionDict.keySet()) {
            routeArrayList.add(routeDict.get(sessionDict.get(key)));

        }
        routesFiltered = routeArrayList.toArray(new Route[routeArrayList.size()]);
        System.out.println(routesFiltered.length);
        //load graphs
        loadAirlineGraph();
    }

    public void loadAirlineGraph(){
        airlineGraph.setTitle("Top 10 Most Flown Airline in Routes");
        airlineXAxis.setLabel("Airline");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Time Flown");

        HashMap<String, Integer> airlines = new HashMap<>();
        for (Route route: routesFiltered){
            if (route.getAirline() != null) {
                if (airlines.containsKey(route.getAirline().getName())) {
                    airlines.put(route.getAirline().getName(), airlines.get(route.getAirline().getName()) + 1);
                } else {
                    airlines.put(route.getAirline().getName(), 1);
                }
            }
        }

        int length = 10;
        if (airlines.size() < 10){
            length = airlines.size();
        }
        for (int i = 0 ; i < length; i ++) {
            int max = 0;
            String maxAirline = "";
            for (String airline: airlines.keySet()){
                if (airlines.get(airline) > max){
                    max = airlines.get(airline);
                    maxAirline = airline;
                }
            }
            series.getData().add(new XYChart.Data<String, Integer>(maxAirline, max));
            airlines.remove(maxAirline);
        }

        airlineGraph.getData().add(series);
    }


    public void goToRawData(){
        replaceSceneContent(SceneCode.ROUTE_RAW_DATA);
    }
}
