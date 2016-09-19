package seng202.group9.GUI;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Route;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Gui controller class currently for creating the bar graph of routes arriving and departing from airports.
 * Extend the class. {@link Controller}
 * Created by michael on 16/09/2016.
 */
public class RouteAnalyser extends Controller {
    //Links fxml to the controller.
    @FXML
    private BarChart analyserGraph;

    //Used to store the data needed for making the tables.
    private ArrayList<Route> current_routes;
    private Dataset currentdata = null;
    private HashMap<String, ArrayList> useddata = new HashMap<String, ArrayList>();

    /**
     * Takes data from the current dataset and places it into the displayed bar graph.
     */
    public void build_graph(){
        //Takes routes from the full dataset.
        current_routes = currentdata.getRoutes();
        datasetup(current_routes);
        //Builds series needed for the graph.
        XYChart.Series seriesArivals = new XYChart.Series();
        XYChart.Series seriesDeparts = new XYChart.Series();
        seriesArivals.setName("Arriving routes");
        seriesDeparts.setName("Departs routes");
        System.out.println(useddata.keySet().size());
        for (String airport : useddata.keySet()){
            ArrayList<Integer> temp = useddata.get(airport);
            seriesArivals.getData().add(new XYChart.Data(airport,temp.get(0)));
            seriesDeparts.getData().add(new XYChart.Data(airport,temp.get(1)));
        }
        //Gives the formatted data to the graph.
        analyserGraph.getData().addAll(seriesArivals,seriesDeparts);
    }

    /**
     * Takes the raw list of routes and fills the used data dictionary with the appropriate data to be displayed
     * @param current_routes
     */

    private void datasetup(ArrayList<Route> current_routes){
        //Takes out the specified field (Currently departure airport and arrival airport) then adds to the used data dict.
        for (Route entry : current_routes){
            String departs = entry.getDepartureAirport();
            String arives = entry.getArrivalAirport();
            if (useddata.containsKey(departs)){
                ArrayList<Integer> temp = useddata.get(departs);
                temp.add(1,temp.get(1)+1);
                useddata.replace(departs,temp);
            }else {
                ArrayList<Integer> temp = new ArrayList<Integer>(2);
                temp.add(0);
                temp.add(1);
                useddata.put(departs,temp);
            }
            if (useddata.containsKey(arives)){
                ArrayList<Integer> temp = useddata.get(arives);
                temp.add(0,temp.get(0)+1);
                useddata.replace(arives,temp);
            }else {
                ArrayList<Integer> temp = new ArrayList<Integer>(2);
                temp.add(1);
                temp.add(0);
                useddata.put(arives,temp);
            }
        }
    }

    /**
     * Takes the current dataset then loads the data to the graph using build graph.
     */
    public void load() {
        currentdata = getParent().getCurrentDataset();
        build_graph();
    }
}
