package seng202.group9.GUI;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Airline;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Route;
import java.util.ArrayList;
import java.util.HashMap;


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
        analyserGraph.getData().add(seriesDeparts);
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
            //datasetupCustomarAirport(d);
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
