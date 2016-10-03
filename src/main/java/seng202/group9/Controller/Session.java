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

    private int currentFlightPointID;//current selected flight point
    private int currentFlightPathID;//cureselected flight path
    private HashMap<Integer, String> filteredAirlines;//current filtered airlines
    private HashMap<Integer, String> filteredAirports;//current filtered airports
    private HashMap<Integer, String> filteredRoutes;//current filtered routes
    private HashMap<String, String> airportFilter;//storage for filter values for airports
    private HashMap<String, String> airlineFilter;//storage for filter values for airports
    private HashMap<String, String> routeFilter;//storage for filter values for airports
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
        //set the filters to nothing
        airportFilter = new HashMap<>();
        airlineFilter = new HashMap<>();
        routeFilter = new HashMap<>();
    }

    /**
     * Constructor for a previous session.
     * @param scene
     */
    public Session(SceneCode scene){
        this.sceneDisplayed = scene;
    }

    /**
     * gets the current dataset that the user is using
     * @return
     */
    public String getCurrentDataset(){
        return this.currentDataset;
    }

    /**
     * sets the current dataset ot the new dataset the user is using
     * @param currentDataset
     */
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

    /**
     * Sets filtered Airline Key Dictionary
     * @param airlines
     */
    public void setFilteredAirlines(HashMap airlines) {
        this.filteredAirlines = airlines;
    }

    /**
     * Gets Filtered Airline Key Dictionary
     * @return
     */
    public HashMap<Integer, String> getFilteredAirlines() {
        return filteredAirlines;
    }

    /**
     * sets filtered Airports Key dictionary.
     * @param airports
     */
    public void setFilteredAirports(HashMap airports) {
        this.filteredAirports = airports;
    }

    /**
     * gets filtered Airports Key Dictionary.
     * @return
     */
    public HashMap<Integer, String> getFilteredAirports() {
        return filteredAirports;
    }

    /**
     * gets filtered Routes key dictionary
     * @param routes
     */
    public void setFilteredRoutes(HashMap routes) {
        this.filteredRoutes = routes;
    }

    /**
     * get filtered routes key dictionary
     * @return
     */
    public HashMap<Integer, String> getFilteredRoutes() {
        return filteredRoutes;
    }

    /**
     * sets airline that is to be editted by dictionary key
     * @param name
     */
    public void setAirlineToEdit(String name) {
        this.airlineToEdit = name;
    }

    /**
     * get airline that is to be editted by dictionary key
     * @return
     */
    public String getAirlineToEdit() {
        return airlineToEdit;
    }

    /**
     * gets airport that is to be editted by dictionary key
     * @return
     */
    public String getAirportToEdit() {
        return airportToEdit;
    }

    /**
     * sets airport that is to be editted by dictionary key.
     * @param airport
     */
    public void setAirportToEdit(String airport) {
        this.airportToEdit = airport;
    }

    /**
     * gets route that is to be editted by dictionary key
     * @return
     */
    public String getRouteToEdit() {
        return routeToEdit;
    }

    /**
     * sets route that is to be editted by dictionary key
     * @param route
     */
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

    /**
     * gets current filter for airports
     * @return
     */
    public HashMap<String, String> getAirportFilter() {
        return airportFilter;
    }

    /**
     * sets current filter for airport
     * @param airportFilter
     */
    public void setAirportFilter(HashMap<String, String> airportFilter) {
        this.airportFilter = airportFilter;
    }

    /**
     * gets current filter for airline
     * @return
     */
    public HashMap<String, String> getAirlineFilter() {
        return airlineFilter;
    }

    /**
     * sets current filter for Airlines
     * @param airlineFilter
     */
    public void setAirlineFilter(HashMap<String, String> airlineFilter) {
        this.airlineFilter = airlineFilter;
    }

    /**
     * gets current filter for Route
     * @return
     */
    public HashMap<String, String> getRouteFilter() {
        return routeFilter;
    }

    /**
     * sets current filter for Routes
     * @param routeFilter
     */
    public void setRouteFilter(HashMap<String, String> routeFilter) {
        this.routeFilter = routeFilter;
    }
}
