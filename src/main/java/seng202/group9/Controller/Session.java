package seng202.group9.Controller;

import javafx.collections.ObservableList;
import seng202.group9.Core.Airline;

import seng202.group9.Core.FlightPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fwy13 on 16/09/16.
 * Users last session state is store here.
 */
public class Session implements Serializable {

    private SceneCode sceneDisplayed;

    private int currentFlightPointID;
    private int currentFlightPathID;
    private HashMap<Integer, String> filteredAirlines;
    private HashMap<Integer, String> filteredAirports;
    private HashMap<Integer, String> filteredRoutes;
    private String selectedgraphagainst;
    private ArrayList<String> selectedgraphoptions;
    private Boolean usefilter;
    private Boolean forceGraph;
    private String selectedDataToGraph;

    public Boolean getForceGraph() {
        return forceGraph;
    }

    public void setForceGraph(Boolean forceGraph) {
        this.forceGraph = forceGraph;
    }

    public String getSelectedDataToGraph() {
        return selectedDataToGraph;
    }

    public void setSelectedDataToGraph(String selectedDataToGraph) {
        this.selectedDataToGraph = selectedDataToGraph;
    }

    public String getSelectedgraphagainst() {
        return selectedgraphagainst;
    }

    public void setSelectedgraphagainst(String selectedgraphagainst) {
        this.selectedgraphagainst = selectedgraphagainst;
    }

    public ArrayList<String> getSelectedgraphoptions() {
        return selectedgraphoptions;
    }

    public void setSelectedgraphoptions(ArrayList<String> selectedgraphoptions) {
        this.selectedgraphoptions = selectedgraphoptions;
    }

    public Boolean getUsefilter() {
        return usefilter;
    }

    public void setUsefilter(Boolean usefilter) {
        this.usefilter = usefilter;
    }

    private String airlineToEdit;
    private String airportToEdit;
    private String routeToEdit;

    private String currentDataset;


    /**
     * Constructor for a new session
     */
    public Session(){
        //blank constructor
        this.sceneDisplayed = SceneCode.INITIAL;
        this.filteredAirlines = new HashMap<>();
        this.filteredAirports = new HashMap<>();
        this.filteredRoutes = new HashMap<>();
    }

    /**
     * Constructor for a previous session.
     * @param scene
     */
    public Session(SceneCode scene){
        this.sceneDisplayed = scene;
    }

    public String getCurrentDataset(){
        return this.currentDataset;
    }

    public void setCurrentDataset(String currentDataset){
        this.currentDataset = currentDataset;
    }

    /**
     * changes the serialized scene.
     * @param sceneDisplayed
     */
    public void setSceneDisplayed(SceneCode sceneDisplayed) {
        this.sceneDisplayed = sceneDisplayed;
    }

    /**
     * gets the last scene displayed.
     * @return
     */
    public SceneCode getSceneDisplayed() {
        return sceneDisplayed;
    }

    public void setFilteredAirlines(HashMap airlines) {
        this.filteredAirlines = airlines;
    }

    public HashMap<Integer, String> getFilteredAirlines() {
        return filteredAirlines;
    }

    public void setFilteredAirports(HashMap airports) {
        this.filteredAirports = airports;
    }

    public HashMap<Integer, String> getFilteredAirports() {
        return filteredAirports;
    }

    public void setFilteredRoutes(HashMap routes) {
        this.filteredRoutes = routes;
    }

    public HashMap<Integer, String> getFilteredRoutes() {
        return filteredRoutes;
    }


    public void setAirlineToEdit(String name) {
        this.airlineToEdit = name;
    }

    public String getAirlineToEdit() {
        return airlineToEdit;
    }

    public String getAirportToEdit() {
        return airportToEdit;
    }

    public void setAirportToEdit(String airport) {
        this.airportToEdit = airport;
    }

    public String getRouteToEdit() {
        return routeToEdit;
    }

    public void setRouteToEdit(String route) {
        this.routeToEdit = route;
    }

    /**
     * sets the current flight point
     * @param currentFlightPointID
     */
    public void setCurrentFlightPointID(int currentFlightPointID) {
        this.currentFlightPointID = currentFlightPointID;
    }

    /**
     * gets the current flight point
     * @return
     */
    public int getCurrentFlightPointID() {
        return currentFlightPointID;
    }
    /**
     * sets the current flight point
     * @param currentFlightPathID
     */
    public void setCurrentFlightPathtID(int currentFlightPathID) {
        this.currentFlightPathID = currentFlightPathID;
    }

    /**
     * gets the current flight point
     * @return
     */
    public int getCurrentFlightPathID() {
        return currentFlightPathID;
    }

}
