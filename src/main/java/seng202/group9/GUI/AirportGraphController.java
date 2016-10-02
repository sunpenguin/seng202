package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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
 * Created by Gondr on 2/10/2016.
 */
public class AirportGraphController extends Controller{
    private Dataset dataset;
    private Session session;
    private LinkedHashMap<String, Airport> airportDict;
    private HashMap<Integer, String> sessionDict;
    Airport[] airportsFiltered;
    @FXML
    private BarChart countryGraph;
    @FXML
    private CategoryAxis countryXAxis;
    @FXML
    private BarChart destGraph;
    @FXML
    private CategoryAxis destXAxis;
    @FXML
    private BarChart transGraph;
    @FXML
    private CategoryAxis transXAxis;
    @FXML
    private BarChart airlineGraph;
    @FXML
    private CategoryAxis airlineXAxis;

    @Override
    public void load() {
        if (!checkDataset()){
            return;
        }
        dataset = getParent().getCurrentDataset();
        airportDict = dataset.getAirportDictionary();
        session = getParent().getSession();
        sessionDict = session.getFilteredAirports();
        if (sessionDict.size() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Data");
            alert.setHeaderText("No Filtered Data");
            alert.setContentText("There is no set Filtered Data Please go to the Airport Raw Data and Filter a Dataset to Analyse.");
            alert.showAndWait();
        }
        ArrayList<Airport> airportArrayList = new ArrayList<>();
        for (int key : sessionDict.keySet()) {
            airportArrayList.add(airportDict.get(sessionDict.get(key)));

        }
        airportsFiltered = airportArrayList.toArray(new Airport[airportArrayList.size()]);

        //load graphs
        loadCountryGraph();
        loadDestGraph();
        loadTransGraph();
        loadAirlineGraph();
    }

    public void loadCountryGraph(){
        countryGraph.setTitle("Top 10 Countries with the most Airports");
        countryXAxis.setLabel("Countries");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Countries");
        HashMap<String, Integer> countries = new HashMap<>();
        for (Airport airport: airportsFiltered){
            if (countries.containsKey(airport.getCountryName())) {
                countries.put(airport.getCountryName(), countries.get(airport.getCountryName()) + 1);
            }else{
                countries.put(airport.getCountryName(), 1);
            }
        }

        int length = 10;
        if (airportsFiltered.length < 10){
            length = airportsFiltered.length;
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

        countryGraph.getData().add(series);
    }

    public void loadDestGraph(){
        destGraph.setTitle("Top 10 Airports with Arriving Routes");
        destXAxis.setLabel("Airports");

        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Routes");
        ArrayList<Airport> airports = new ArrayList<>();
        airports.addAll(Arrays.asList(airportsFiltered));
        Airport maxRoutes[] = new Airport[10];
        for (int i = 0 ; i < maxRoutes.length; i ++) {
            int max = 0;
            Airport maxAirport = null;
            for (Airport airport: airports){
                if (airport != null) {
                    if (airport.getDepartureRoutes().size() > max) {
                        maxAirport = airport;
                        max = airport.getDepartureRoutes().size();
                    }
                }
            }
            maxRoutes[i] = maxAirport;
            airports.remove(maxAirport);
        }
        for (int i = 0; i < maxRoutes.length; i++){
            if (maxRoutes[i] != null) {
                series.getData().add(new XYChart.Data<String, Integer>(maxRoutes[i].getName(), maxRoutes[i].getDepartureRoutes().size()));
            }
        }
        destGraph.getData().add(series);
    }

    public void loadTransGraph(){
        transGraph.setTitle("Top 10 Airports with Leaving Routes");
        transXAxis.setLabel("Airports");

        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Routes");
        ArrayList<Airport> airports = new ArrayList<>();
        airports.addAll(Arrays.asList(airportsFiltered));
        Airport maxRoutes[] = new Airport[10];
        for (int i = 0 ; i < maxRoutes.length; i ++) {
            int max = 0;
            Airport maxAirport = null;
            for (Airport airport: airports){
                if (airport != null) {
                    if (airport.getArrivalRoutes().size() > max) {
                        maxAirport = airport;
                        max = airport.getArrivalRoutes().size();
                    }
                }
            }
            maxRoutes[i] = maxAirport;
            airports.remove(maxAirport);
        }
        for (int i = 0; i < maxRoutes.length; i++){
            if (maxRoutes[i] != null) {
                series.getData().add(new XYChart.Data<String, Integer>(maxRoutes[i].getName(), maxRoutes[i].getArrivalRoutes().size()));
            }
        }
        transGraph.getData().add(series);
    }

    public void loadAirlineGraph(){
        airlineGraph.setTitle("Top 10 Airlines Flown to Airports");
        airlineXAxis.setLabel("Airline");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Time Flown");

        HashMap<String, Integer> airlines = new HashMap<>();
        for (Airport airport: airportsFiltered){
            for (Route route: airport.getArrivalRoutes()){
                if (route.getAirline() != null) {
                    if (airlines.containsKey(route.getAirline().getName())) {
                        airlines.put(route.getAirline().getName(), airlines.get(route.getAirline().getName()) + 1);
                    } else {

                        airlines.put(route.getAirline().getName(), 1);
                    }
                }
            }
            for (Route route: airport.getDepartureRoutes()){
                if (route.getAirline() != null) {
                    if (airlines.containsKey(route.getAirline().getName())) {
                        airlines.put(route.getAirline().getName(), airlines.get(route.getAirline().getName()) + 1);
                    } else {

                        airlines.put(route.getAirline().getName(), 1);
                    }
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
        replaceSceneContent(SceneCode.AIRPORT_RAW_DATA);
    }
}
