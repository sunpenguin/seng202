package seng202.group9.GUI;

import javafx.collections.FXCollections;
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

import java.util.*;

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
    private BarChart zoneGraph;
    @FXML
    private CategoryAxis zoneXAxis;

    /**
     * Initialise load Route Graphs
     */
    @Override
    public void load() {
        if (!checkDataset(SceneCode.ROUTE_GRAPHS)){
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
        loadSimilarGraph();
        loadStopsGraph();
        loadZoneGraph();
    }

    /**
     * Graphs top 10 most flown airline in routes.
     */
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

    /**
     * Grpahs top 10 destination airports
     */
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

    /**
     * Graphs top 10 outoging Airports.
     */
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

    /**
     * graphs top 10 countries Visited.
     */
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

    /**
     * graphs top 10 countries with departing routes.
     */
    public void loadOutCountryGraph(){
        outCountryGraph.setTitle("Top 10 Countries with Departing Routes.");
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

    /**
     * graphs top 10 equipement used by routes.
     */
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

    /**
     * graphs top 10 most similar routes.
     */
    public void loadSimilarGraph(){
        similarGraph.setTitle("Top 10 Most Similar Routes");
        similarXAxis.setLabel("Routes");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Routes");
        HashMap<String, Integer> routes = new HashMap<>();//equipment, count
        for (Route route: routesFiltered){
            String key = route.getDepartureAirport() + " to " + route.getArrivalAirport();
            if (routes.containsKey(key)){
                routes.put(key, routes.get(key) + 1);
            }else{
                routes.put(key, 1);
            }
        }

        int length = 10;
        if (routes.size() < 10){
            length = routes.size();
        }
        for (int i = 0 ; i < length; i ++) {
            int max = 0;
            String maxRoute = "";
            for (String route: routes.keySet()){
                if (routes.get(route) > max){
                    max = routes.get(route);
                    maxRoute = route;
                }
            }
            series.getData().add(new XYChart.Data<String, Integer>(maxRoute, max));
            routes.remove(maxRoute);
        }

        similarGraph.getData().add(series);
    }

    /**
     * graphs most common amount of stops by routes.
     */
    public void loadStopsGraph(){
        stopsGraph.setTitle("Top 10 Most Common Amount of Stops by Routes");
        ArrayList<PieChart.Data> data = new ArrayList<>();
        HashMap<Integer, Integer> stops = new HashMap<>();//equipment, count
        for (Route route: routesFiltered){
            if (stops.containsKey(route.getStops())){
                stops.put(route.getStops(), stops.get(route.getStops()) + 1);
            }else{
                stops.put(route.getStops(), 1);
            }
        }

        for (int stop: stops.keySet()){
            stopsGraph.getData().add(new PieChart.Data(String.valueOf(stop), stops.get(stop)));
        }

    }

    /**
     * graphs top zones with flights.
     */
    public void loadZoneGraph(){
        zoneGraph.setTitle("Top Zones With Flights.");
        zoneXAxis.setLabel("Zones");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Zones");
        LinkedHashMap<String, Integer> zones = new LinkedHashMap<>();
        for (Route route: routesFiltered) {
            Airport source = route.getSourceAirport();
            if (source != null){
                if (zones.containsKey(source.getTz())) {
                    zones.put(source.getTz(), zones.get(source.getTz()) + 1);
                } else {
                    zones.put(source.getTz(), 1);
                }
            }
            Airport dest = route.getDestinationAirport();
            if (dest != null){
                if (zones.containsKey(dest.getTz())) {
                    zones.put(dest.getTz(), zones.get(dest.getTz()) + 1);
                } else {
                    zones.put(dest.getTz(), 1);
                }
            }
        }
        int length = 10;
        if (zones.size() < 10){
            length = zones.size();
        }
        for (int i = 0 ; i < length; i ++) {
            int max = 0;
            String maxZone = null;
            for (String zone: zones.keySet()){
                if (zones.get(zone) > max){
                    maxZone = zone;
                    max = zones.get(zone);
                }
            }
            series.getData().add(new XYChart.Data<String, Integer>(maxZone, max));
            zones.remove(maxZone);
        }
        zoneGraph.getData().add(series);
    }

    /**
     * navigate to routeraw data page.
     */
    public void goToRawData(){
        replaceSceneContent(SceneCode.ROUTE_RAW_DATA);
    }
}
