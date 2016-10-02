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
import seng202.group9.Core.Airline;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by fwy13 on 2/10/2016.
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
        loadDestGraph();
        loadSourceGraph();
        loadInCountryGraph();
        loadOutCountryGraph();
        loadEquipGraph();
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

    public void loadDestGraph(){
        destGraph.setTitle("Top 10 Destination Airports");
        destXAxis.setLabel("Airports");

        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Routes");

        HashMap<String, Integer> airports = new HashMap<>();
        for (Route route: routesFiltered){
            if (route.getDestinationAirport() != null) {
                if (airports.containsKey(route.getDestinationAirport().getName())) {
                    airports.put(route.getDestinationAirport().getName(), airports.get(route.getDestinationAirport().getName()) + 1);
                }else{

                    airports.put(route.getDestinationAirport().getName(), 1);
                }
            }
        }

        LinkedHashMap<String, Integer> maxAirports = new LinkedHashMap<>();
        int length = 10;
        if (airports.size() < 10){
            length = airports.size();
        }
        for (int i = 0 ; i < length; i ++) {
            int max = 0;
            String maxAirport = null;
            for (String airport: airports.keySet()){
                if (airports.get(airport) > max){
                    max = airports.get(airport);
                    maxAirport = airport;
                }
            }
            maxAirports.put(maxAirport, max);
            airports.remove(maxAirport);
        }

        for (String airport: maxAirports.keySet()){
            series.getData().add(new XYChart.Data<String, Integer>(airport, maxAirports.get(airport)));
        }
        destGraph.getData().add(series);
    }

    public void loadSourceGraph(){
        sourceGraph.setTitle("Top 10 Outgoing Airports");
        sourceXAxis.setLabel("Airports");

        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Routes");

        HashMap<String, Integer> airports = new HashMap<>();
        for (Route route: routesFiltered){
            if (route.getSourceAirport() != null) {
                if (airports.containsKey(route.getSourceAirport().getName())) {
                    airports.put(route.getSourceAirport().getName(), airports.get(route.getSourceAirport().getName()) + 1);
                }else{

                    airports.put(route.getSourceAirport().getName(), 1);
                }
            }
        }

        LinkedHashMap<String, Integer> maxAirports = new LinkedHashMap<>();
        int length = 10;
        if (airports.size() < 10){
            length = airports.size();
        }
        for (int i = 0 ; i < length; i ++) {
            int max = 0;
            String maxAirport = null;
            for (String airport: airports.keySet()){
                if (airports.get(airport) > max){
                    max = airports.get(airport);
                    maxAirport = airport;
                }
            }
            maxAirports.put(maxAirport, max);
            airports.remove(maxAirport);
        }

        for (String airport: maxAirports.keySet()){
            series.getData().add(new XYChart.Data<String, Integer>(airport, maxAirports.get(airport)));
        }
        sourceGraph.getData().add(series);
    }

    public void loadInCountryGraph(){
        inCountryGraph.setTitle("Top 10 Countries Visited.");
        inCountryXAxis.setLabel("Countries");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Countries");
        LinkedHashMap<String, Integer> countries = new LinkedHashMap<>();
        for (Route route: routesFiltered) {
            Airport dest = route.getDestinationAirport();
            if (dest != null){
                if (countries.containsKey(dest.getCountryName())) {
                    countries.put(dest.getCountryName(), countries.get(dest.getCountryName()) + 1);
                } else {
                    countries.put(dest.getCountryName(), 1);
                }
            }
        }
        int length = 10;
        if (countries.size() < 10){
            length = countries.size();
        }
        for (int i = 0 ; i < length; i ++) {
            int max = 0;
            String maxCountry = null;
            for (String country: countries.keySet()){
                if (countries.get(country) > max){
                    maxCountry = country;
                    max = countries.get(country);
                }
            }
            series.getData().add(new XYChart.Data<String, Integer>(maxCountry, max));
            countries.remove(maxCountry);
        }

        inCountryGraph.getData().add(series);
    }

    public void loadOutCountryGraph(){
        outCountryGraph.setTitle("Top 10 Countries Visited.");
        outCountryXAxis.setLabel("Countries");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Countries");
        LinkedHashMap<String, Integer> countries = new LinkedHashMap<>();
        for (Route route: routesFiltered) {
            Airport source = route.getSourceAirport();
            if (source != null){
                if (countries.containsKey(source.getCountryName())) {
                    countries.put(source.getCountryName(), countries.get(source.getCountryName()) + 1);
                } else {
                    countries.put(source.getCountryName(), 1);
                }
            }
        }
        int length = 10;
        if (countries.size() < 10){
            length = countries.size();
        }
        for (int i = 0 ; i < length; i ++) {
            int max = 0;
            String maxCountry = null;
            for (String country: countries.keySet()){
                if (countries.get(country) > max){
                    maxCountry = country;
                    max = countries.get(country);
                }
            }
            series.getData().add(new XYChart.Data<String, Integer>(maxCountry, max));
            countries.remove(maxCountry);
        }
        outCountryGraph.getData().add(series);
    }

    public void loadEquipGraph(){
        equipGraph.setTitle("Top 10 Equipment used by Routes");
        equipXAxis.setLabel("Equipment");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Equipment");
        HashMap<String, Integer> equipmentList = new HashMap<>();//equipment, count
        for (Route route: routesFiltered){
            String equipment[] = route.getEquipment().split(" ");
            for (String equip : equipment){
                if (equipmentList.containsKey(equip)){
                    equipmentList.put(equip, equipmentList.get(equip) + 1);
                }else{
                    equipmentList.put(equip, 1);
                }
            }
        }

        int length = 10;
        if (equipmentList.size() < 10){
            length = equipmentList.size();
        }
        for (int i = 0 ; i < length; i ++) {
            int max = 0;
            String maxEquip = "";
            for (String equip: equipmentList.keySet()){
                if (equipmentList.get(equip) > max){
                    max = equipmentList.get(equip);
                    maxEquip = equip;
                }
            }
            series.getData().add(new XYChart.Data<String, Integer>(maxEquip, max));
            equipmentList.remove(maxEquip);
        }

        equipGraph.getData().add(series);
    }

    public void goToRawData(){
        replaceSceneContent(SceneCode.ROUTE_RAW_DATA);
    }
}
