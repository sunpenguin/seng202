package seng202.group9.GUI;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airline;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Route;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


/**
 * Gui controller class currently for creating the bar graph of routes arriving and departing from airports.
 * Extend the class. {@link Controller}
 * Created by michael on 16/09/2016.
 */
public class BarGraphController extends Controller {
    //Links fxml to the controller.
    @FXML
    private BarChart analyserGraph;

    //Used to store the data needed for making the tables.
    private ArrayList<Route> current_routes;
    private Dataset currentdata = null;
    private Session currentsession;
    private HashMap<String, Integer> stopsData = new HashMap<String, Integer>();
    private HashMap<String, Integer> codeShareData = new HashMap<String, Integer>();
    private HashMap<String, Integer> equipmentData = new HashMap<String, Integer>();
    private HashMap<String, Integer> airlineData = new HashMap<String, Integer>();
    private HashMap<String, Integer> departureAirportData = new HashMap<String, Integer>();
    private HashMap<String, Integer> arrivalAirportData = new HashMap<String, Integer>();

    private HashMap<String, Integer> nameData = new HashMap<String, Integer>();
    private HashMap<String, Integer> ICAOData = new HashMap<String, Integer>();
    private HashMap<String, Integer> IATAData = new HashMap<String, Integer>();
    private HashMap<String, Integer> altitudeData = new HashMap<String, Integer>();
    private HashMap<String, Integer> cityData = new HashMap<String, Integer>();
    private HashMap<String, Integer> countryData = new HashMap<String, Integer>();

    private HashMap<String, Integer> aliasData = new HashMap<String, Integer>();
    private HashMap<String, Integer> callSignData = new HashMap<String, Integer>();
    private HashMap<String, Integer> activeData = new HashMap<String, Integer>();


    private void datasetupCustomRoute(ArrayList<Route> current_routes){
        //Takes out the specified field  then adds to the used data dict.
        for (Route entry : current_routes){
            if (stopsData.containsKey(entry.getStops())){
                stopsData.replace(String.valueOf(entry.getStops()),stopsData.get(entry.getStops())+1);
            }else {
                stopsData.put(String.valueOf(entry.getStops()),1);
            }
            if (codeShareData.containsKey(entry.getCode())){
                codeShareData.replace(String.valueOf(entry.getCode()),codeShareData.get(entry.getCode())+1);
            }else {
                codeShareData.put(String.valueOf(entry.getStops()),1);
            }
            if (equipmentData.containsKey(entry.getEquipment())){
                equipmentData.replace(String.valueOf(entry.getEquipment()),equipmentData.get(entry.getEquipment())+1);
            }else {
                stopsData.put(String.valueOf(entry.getEquipment()),1);
            }
            if (airlineData.containsKey(entry.getAirlineName())){
                airlineData.replace(String.valueOf(entry.getAirlineName()), airlineData.get(entry.getAirlineName())+1);
            }else {
                airlineData.put(String.valueOf(entry.getAirline()),1);
            }
            if (departureAirportData.containsKey(entry.getDepartureAirport())){
                departureAirportData.replace(String.valueOf(entry.getDepartureAirport()),stopsData.get(entry.getDepartureAirport())+1);
            }else {
                departureAirportData.put(String.valueOf(entry.getDepartureAirport()),1);
            }
            if (arrivalAirportData.containsKey(entry.getArrivalAirport())){
                arrivalAirportData.replace(String.valueOf(entry.getArrivalAirport()),arrivalAirportData.get(entry.getArrivalAirport())+1);
            }else {
                arrivalAirportData.put(String.valueOf(entry.getArrivalAirport()),1);
            }
        }
    }

    /**
     * Takes data from the current dataset and places it into the displayed bar graph.
     */
    public void buildRouteGraph(){
        XYChart.Series seriesStops = new XYChart.Series();
        XYChart.Series seriesCode = new XYChart.Series();
        XYChart.Series seriesEquipment = new XYChart.Series();
        XYChart.Series seriesAirline = new XYChart.Series();
        XYChart.Series seriesArivals = new XYChart.Series();
        XYChart.Series seriesDeparts = new XYChart.Series();

        seriesStops.setName("Stops");
        seriesCode.setName("CodeShare");
        seriesEquipment.setName("Equipment");
        seriesAirline.setName("Airline");
        seriesArivals.setName("Arrivals");
        seriesDeparts.setName("Departures");
        //Gives the formatted data to the graph.
        for (String entry : stopsData.keySet()){
            seriesStops.getData().add(new XYChart.Data(entry,stopsData.get(entry)));
        }
        for (String entry : codeShareData.keySet()){
            seriesCode.getData().add(new XYChart.Data(entry,codeShareData.get(entry)));
        }
        for (String entry : equipmentData.keySet()){
            seriesEquipment.getData().add(new XYChart.Data(entry,equipmentData.get(entry)));
        }
        for (String entry : airlineData.keySet()){
            seriesAirline.getData().add(new XYChart.Data(entry,airlineData.get(entry)));
        }
        for (String entry : arrivalAirportData.keySet()){
            seriesArivals.getData().add(new XYChart.Data(entry,arrivalAirportData.get(entry)));
        }
        for (String entry : departureAirportData.keySet()){
            seriesDeparts.getData().add(new XYChart.Data(entry,departureAirportData.get(entry)));
        }
        if (stopsData.keySet().size() > 125 && !currentsession.getForceGraph()){
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yes, no);
            alert.setTitle("Too many fields selected");
            alert.setHeaderText("You have selected too many fields to graph.");
            alert.setContentText("This could potentially cause errors in the program.\nDo you want to proceed?");
            //alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == no) {
                replaceSceneContent(SceneCode.PIE_GRAPH_CHOOSER);
            }
        }
        else {
            if (currentsession.getSelectedgraphoptions().contains("Stops")) {
                analyserGraph.getData().add(seriesStops);
            }
            if (currentsession.getSelectedgraphoptions().contains("Codeshare")) {
                analyserGraph.getData().add(seriesCode);
            }
            if (currentsession.getSelectedgraphoptions().contains("Equipment")) {
                analyserGraph.getData().add(seriesEquipment);
            }
            if (currentsession.getSelectedgraphoptions().contains("Airline")) {
                analyserGraph.getData().add(seriesAirline);
            }
            if (currentsession.getSelectedgraphoptions().contains("Arrival airport")) {
                analyserGraph.getData().add(seriesArivals);
            }
            if (currentsession.getSelectedgraphoptions().contains("Departure Airport")) {
                analyserGraph.getData().add(seriesDeparts);
            }
        }
    }

    private void datasetupCustomAirport(ArrayList<Airport> current_airports){
        //Takes out the specified field  then adds to the used data dict.
        for (Airport entry : current_airports){
            if (nameData.containsKey(entry.getName())){
                nameData.replace(String.valueOf(entry.getName()),nameData.get(entry.getName())+1);
            }else {
                nameData.put(String.valueOf(entry.getName()),1);
            }
            if (ICAOData.containsKey(entry.getICAO())){
                ICAOData.replace(String.valueOf(entry.getICAO()),ICAOData.get(entry.getICAO())+1);
            }else {
                ICAOData.put(String.valueOf(entry.getICAO()),1);
            }
            if (IATAData.containsKey(entry.getIATA_FFA())){
                IATAData.replace(String.valueOf(entry.getIATA_FFA()),IATAData.get(entry.getIATA_FFA())+1);
            }else {
                IATAData.put(String.valueOf(entry.getIATA_FFA()),1);
            }
            if (altitudeData.containsKey(entry.getAltitude())){
                altitudeData.replace(String.valueOf(entry.getAltitude()), altitudeData.get(entry.getAltitude())+1);
            }else {
                altitudeData.put(String.valueOf(entry.getAltitude()),1);
            }
            if (cityData.containsKey(entry.getCityName())){
                cityData.replace(String.valueOf(entry.getCityName()),cityData.get(entry.getCityName())+1);
            }else {
                departureAirportData.put(String.valueOf(entry.getCityName()),1);
            }
            if (countryData.containsKey(entry.getCountryName())){
                countryData.replace(String.valueOf(entry.getCountryName()),countryData.get(entry.getCountryName())+1);
            }else {
                countryData.put(String.valueOf(entry.getCountryName()),1);
            }
        }
    }

    /**
     * Takes data from the current dataset and places it into the displayed bar graph.
     */
    public void buildAirportGraph(){
        XYChart.Series seriesName = new XYChart.Series();
        XYChart.Series seriesICAO = new XYChart.Series();
        XYChart.Series seriesIATA = new XYChart.Series();
        XYChart.Series seriesAltitude = new XYChart.Series();
        XYChart.Series seriesCity = new XYChart.Series();
        XYChart.Series seriesCountry = new XYChart.Series();

        seriesName.setName("Name");
        seriesICAO.setName("ICAO");
        seriesIATA.setName("IATA FFA");
        seriesAltitude.setName("Altitude");
        seriesCity.setName("City");
        seriesCountry.setName("Country");
        //Gives the formatted data to the graph.
        for (String entry : nameData.keySet()){
            seriesName.getData().add(new XYChart.Data(entry,nameData.get(entry)));
        }
        for (String entry : ICAOData.keySet()){
            seriesICAO.getData().add(new XYChart.Data(entry,ICAOData.get(entry)));
        }
        for (String entry : IATAData.keySet()){
            seriesIATA.getData().add(new XYChart.Data(entry,IATAData.get(entry)));
        }
        for (String entry : altitudeData.keySet()){
            seriesAltitude.getData().add(new XYChart.Data(entry,altitudeData.get(entry)));
        }
        for (String entry : cityData.keySet()){
            seriesCity.getData().add(new XYChart.Data(entry,cityData.get(entry)));
        }
        for (String entry : countryData.keySet()){
            seriesCountry.getData().add(new XYChart.Data(entry,countryData.get(entry)));
        }
        if (nameData.keySet().size() > 125 && !currentsession.getForceGraph()){
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yes, no);
            alert.setTitle("Too many fields selected");
            alert.setHeaderText("You have selected too many fields to graph.");
            alert.setContentText("This could potentially cause errors in the program.\nDo you want to proceed?");
            //alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == no) {
                replaceSceneContent(SceneCode.PIE_GRAPH_CHOOSER);
            }
        }
        else {
            if (currentsession.getSelectedgraphoptions().contains("Name")) {
                analyserGraph.getData().add(seriesName);
            }
            if (currentsession.getSelectedgraphoptions().contains("ICAO")) {
                analyserGraph.getData().add(seriesIATA);
            }
            if (currentsession.getSelectedgraphoptions().contains("IATA FFA")) {
                analyserGraph.getData().add(seriesIATA);
            }
            if (currentsession.getSelectedgraphoptions().contains("Altitude")) {
                analyserGraph.getData().add(seriesAltitude);
            }
            if (currentsession.getSelectedgraphoptions().contains("City")) {
                analyserGraph.getData().add(seriesCity);
            }
            if (currentsession.getSelectedgraphoptions().contains("Country")) {
                analyserGraph.getData().add(seriesCountry);
            }
        }
    }

    private void datasetupCustomAirline(ArrayList<Airline> current_airlines){
        //Takes out the specified field  then adds to the used data dict.
        for (Airline entry : current_airlines){
            if (nameData.containsKey(entry.getName())){
                nameData.replace(String.valueOf(entry.getName()),nameData.get(entry.getName())+1);
            }else {
                nameData.put(String.valueOf(entry.getName()),1);
            }
            if (ICAOData.containsKey(entry.getICAO())){
                ICAOData.replace(String.valueOf(entry.getICAO()),ICAOData.get(entry.getICAO())+1);
            }else {
                ICAOData.put(String.valueOf(entry.getICAO()),1);
            }
            if (IATAData.containsKey(entry.getIATA())){
                IATAData.replace(String.valueOf(entry.getIATA()),IATAData.get(entry.getIATA())+1);
            }else {
                IATAData.put(String.valueOf(entry.getIATA()),1);
            }
            if (altitudeData.containsKey(entry.getAlias())){
                aliasData.replace(String.valueOf(entry.getAlias()), aliasData.get(entry.getAlias())+1);
            }else {
                aliasData.put(String.valueOf(entry.getAlias()),1);
            }
            if (cityData.containsKey(entry.getCallSign())){
                callSignData.replace(String.valueOf(entry.getCallSign()),cityData.get(entry.getCallSign())+1);
            }else {
                departureAirportData.put(String.valueOf(entry.getCallSign()),1);
            }
            if (activeData.containsKey(entry.getCallSign())){
                activeData.replace(String.valueOf(entry.getActive()),activeData.get(entry.getActive())+1);
            }else {
                activeData.put(String.valueOf(entry.getActive()),1);
            }
            if (countryData.containsKey(entry.getCountryName())){
                countryData.replace(String.valueOf(entry.getCountryName()),countryData.get(entry.getCountryName())+1);
            }else {
                countryData.put(String.valueOf(entry.getCountryName()),1);
            }
        }
    }

    /**
     * Takes data from the current dataset and places it into the displayed bar graph.
     */
    public void buildAirlineGraph(){
        XYChart.Series seriesName = new XYChart.Series();
        XYChart.Series seriesICAO = new XYChart.Series();
        XYChart.Series seriesIATA = new XYChart.Series();
        XYChart.Series seriesAlias = new XYChart.Series();
        XYChart.Series seriesCallsign = new XYChart.Series();
        XYChart.Series seriesActive = new XYChart.Series();
        XYChart.Series seriesCountry = new XYChart.Series();

        seriesName.setName("Name");
        seriesICAO.setName("ICAO");
        seriesIATA.setName("IATA");
        seriesAlias.setName("Alias");
        seriesCallsign.setName("Call sign");
        seriesActive.setName("Active");
        seriesCountry.setName("Country");
        //Gives the formatted data to the graph.
        for (String entry : nameData.keySet()){
            seriesName.getData().add(new XYChart.Data(entry,nameData.get(entry)));
        }
        for (String entry : ICAOData.keySet()){
            seriesICAO.getData().add(new XYChart.Data(entry,ICAOData.get(entry)));
        }
        for (String entry : IATAData.keySet()){
            seriesIATA.getData().add(new XYChart.Data(entry,IATAData.get(entry)));
        }
        for (String entry : aliasData.keySet()){
            seriesAlias.getData().add(new XYChart.Data(entry,aliasData.get(entry)));
        }
        for (String entry : callSignData.keySet()){
            seriesCallsign.getData().add(new XYChart.Data(entry,callSignData.get(entry)));
        }
        for (String entry : activeData.keySet()){
            seriesActive.getData().add(new XYChart.Data(entry,activeData.get(entry)));
        }
        for (String entry : countryData.keySet()){
            seriesCountry.getData().add(new XYChart.Data(entry,countryData.get(entry)));
        }
        if (nameData.keySet().size() > 125 && !currentsession.getForceGraph()){
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yes, no);
            alert.setTitle("Too many fields selected");
            alert.setHeaderText("You have selected too many fields to graph.");
            alert.setContentText("This could potentially cause errors in the program.\nDo you want to proceed?");
            //alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == no) {
                replaceSceneContent(SceneCode.PIE_GRAPH_CHOOSER);
            }
        }
        else {
            if (currentsession.getSelectedgraphoptions().contains("Name")) {
                analyserGraph.getData().add(seriesName);
            }
            if (currentsession.getSelectedgraphoptions().contains("ICAO")) {
                analyserGraph.getData().add(seriesIATA);
            }
            if (currentsession.getSelectedgraphoptions().contains("IATA FFA")) {
                analyserGraph.getData().add(seriesIATA);
            }
            if (currentsession.getSelectedgraphoptions().contains("Altitude")) {
                analyserGraph.getData().add(seriesAlias);
            }
            if (currentsession.getSelectedgraphoptions().contains("Call Sign")) {
                analyserGraph.getData().add(seriesCallsign);
            }
            if (currentsession.getSelectedgraphoptions().contains("Active")) {
                analyserGraph.getData().add(seriesActive);
            }
            if (currentsession.getSelectedgraphoptions().contains("Country")) {
                analyserGraph.getData().add(seriesCountry);
            }
        }
    }



    /**
     * Takes the current dataset then loads the data to the graph using build graph.
     */
    public void load() {
        currentdata = getParent().getCurrentDataset();
        currentsession = this.getParent().getSession();
        String temp = currentsession.getSelectedDataToGraph();
        if (temp == "Airports") {
            ArrayList<Airport> d = new ArrayList();
            if (currentsession.getUsefilter()){
                for(int i = 0; i < currentdata.getAirports().size(); i++) {
                    if (currentsession.getFilteredAirports().containsValue(currentdata.getAirports().get(i).getName())
                            && currentsession.getFilteredAirports().containsKey(i)) {
                        d.add(currentdata.getAirports().get(i));
                    }
                }
            }
            else{
                d = currentdata.getAirports();
            }
            datasetupCustomAirport(d);
            buildAirportGraph();
        }
        else if (temp == "Airlines"){
            ArrayList<Airline> d = new ArrayList();
            if (currentsession.getUsefilter()){
                for(int i = 0; i < currentdata.getAirports().size(); i++) {
                    if (currentsession.getFilteredAirlines().containsValue(currentdata.getAirlines().get(i).getName())
                            && currentsession.getFilteredAirlines().containsKey(i)) {
                        d.add(currentdata.getAirlines().get(i));
                    }
                }
            }
            else{
                d = currentdata.getAirlines();
            }
            //datasetupCustomarAirline(d);
        }
        else if (temp == "Routes") {
            ArrayList<Route> d = new ArrayList();
            if (currentsession.getUsefilter()){
                for(int i = 0; i < currentdata.getRoutes().size(); i++) {
                    if (currentsession.getFilteredRoutes().containsValue(currentdata.getRoutes().get(i).getAirlineName())
                            && currentsession.getFilteredRoutes().containsKey(i)) {
                        d.add(currentdata.getRoutes().get(i));
                    }
                }
            }
            else{
                d = currentdata.getRoutes();
            }
            datasetupCustomRoute(d);
            buildRouteGraph();
        }
    }

}
