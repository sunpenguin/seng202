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
import seng202.group9.Core.Route;

import java.util.*;

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
    private CategoryAxis routeXAxis;
    @FXML
    private BarChart countryGraph;
    @FXML
    private CategoryAxis countryXAxis;
    @FXML
    private BarChart equipGraph;
    @FXML
    private CategoryAxis equipXAxis;


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
        loadCountryGraph();
        loadEquipGraph();
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

    public void loadCountryGraph(){
        countryGraph.setTitle("Top 10 Countries with the most Airlines");
        countryXAxis.setLabel("Countries");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Countries");
        HashMap<String, Integer> countries = new HashMap<>();
        for (Airline airline: airlinesFiltered){
            if (countries.containsKey(airline.getCountryName())) {
                countries.put(airline.getCountryName(), countries.get(airline.getCountryName()) + 1);
            }else{
                countries.put(airline.getCountryName(), 1);
            }
        }

        int length = 10;
        if (airlinesFiltered.length < 10){
            length = airlinesFiltered.length;
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

    public void loadEquipGraph(){
        equipGraph.setTitle("Top 10 Equipment used by Airlines");
        equipXAxis.setLabel("Equipment");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Number of Equipment");
        HashMap<String, Integer> equipmentList = new HashMap<>();//equipment, count
        for (Airline airline: airlinesFiltered){
            for (Route route: airline.getRoutes()){
                String equipment[] = route.getEquipment().split(" ");
                for (String equip : equipment){
                    if (equipmentList.containsKey(equip)){
                        equipmentList.put(equip, equipmentList.get(equip) + 1);
                    }else{
                        equipmentList.put(equip, 1);
                    }
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
        replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
    }
}
