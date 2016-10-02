package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seng202.group9.Controller.*;
import seng202.group9.Core.Airline;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.Group;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Route;

/**
 * Gui controller class currently for creating the bar graph of routes arriving and departing from airports.
 * Extend the class. {@link Controller}
 * Created by michael on 17/09/2016.
 */
public class PieGraphController extends Controller {
    //links fxml parts to the controller.
    @FXML
    PieChart pieGraph;

    //Used to store the data needed for making the graph.
    private Dataset currentdata = null;
    private HashMap<String, Integer> useddata = new HashMap<String, Integer>();
    private Session currentsession;


    /**
     * Takes data from the current dataset and places it into the displayed pie graph.
     */
    public void build_graph(){
        //Turns the data into a usable list.
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        System.out.println(useddata.keySet().size());
        for (String airport : useddata.keySet()){
            Integer temp = useddata.get(airport);
            pieChartData.add(new PieChart.Data(airport,temp));
        }
        //Gives the data to the graph.
        if (useddata.keySet().size() > 250 && !currentsession.getForceGraph()){
            replaceSceneContent(SceneCode.CHART_ERROR);
        }
        else{
            pieGraph.setData(pieChartData);
        }
    }

    /**
     * Takes the raw list of routes and fills the used data dictionary with the appropriate data to be displayed
     * @param current_air_ports
     */
    private void datasetupCustomarAirport(ArrayList<Airport> current_air_ports){
        //Takes out the specified field  then adds to the used data dict.
        for (Airport entry : current_air_ports){
            String name = "Error";
            if (currentsession.getSelectedgraphagainst() == "Name") {
                name = entry.getName();
            }
            else if (currentsession.getSelectedgraphagainst() == "ICAO") {
                name = entry.getICAO();
            }
            else if (currentsession.getSelectedgraphagainst() == "IATA FFA") {
                name = entry.getIATA_FFA();
            }
            else if (currentsession.getSelectedgraphagainst() == "Altitude") {
                name = String.valueOf(entry.getAltitude());
            }
            else if (currentsession.getSelectedgraphagainst() == "City") {
                name = entry.getCityName();
            }
            else if (currentsession.getSelectedgraphagainst() == "Country") {
                name = entry.getCountryName();
            }
            if (useddata.containsKey(name)){
                int temp = useddata.get(name);
                useddata.replace(name,temp+1);
            }else {
                Integer temp = 1;
                useddata.put(name,temp);
            }
        }
    }


    private void datasetupCustomarAirline(ArrayList<Airline> current_air_ports){
        //Takes out the specified field  then adds to the used data dict.
        for (Airline entry : current_air_ports) {
            String name = "Error";
            if (currentsession.getSelectedgraphagainst() == "Name") {
                name = entry.getName();
            } else if (currentsession.getSelectedgraphagainst() == "ICAO") {
                name = entry.getICAO();
            } else if (currentsession.getSelectedgraphagainst() == "IATA") {
                name = entry.getIATA();
            } else if (currentsession.getSelectedgraphagainst() == "Country") {
                name = entry.getCountryName();
            } else if (currentsession.getSelectedgraphagainst() == "Active") {
                name = entry.getActive();
            }
            if (useddata.containsKey(name)) {
                int temp = useddata.get(name);
                useddata.replace(name, temp + 1);
            } else {
                Integer temp = 1;
                useddata.put(name, temp);
            }
        }
    }


    private void datasetupCustomRoute(ArrayList<Route> current_air_ports){
        //Takes out the specified field  then adds to the used data dict.
        for (Route entry : current_air_ports){
            String name = "Error";
            if (currentsession.getSelectedgraphagainst() == "Stops") {
                name = String.valueOf(entry.getStops());
            }
            else if (currentsession.getSelectedgraphagainst() == "Codeshare") {
                name = entry.getCode();
            }
            else if (currentsession.getSelectedgraphagainst() == "Equipment") {
                name = entry.getEquipment();
            }
            else if (currentsession.getSelectedgraphagainst() == "Airline") {
                name = entry.getAirlineName();
            }
            else if (currentsession.getSelectedgraphagainst() == "Departure Airport") {
                name = entry.getDepartureAirport();
            }
            else if (currentsession.getSelectedgraphagainst() == "Arival airport") {
                name = entry.getArrivalAirport();
            }
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
            datasetupCustomarAirport(d);
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
            datasetupCustomarAirline(d);
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
        }
        build_graph();
    }

}
