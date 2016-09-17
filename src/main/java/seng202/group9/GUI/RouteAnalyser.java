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
 * Created by michael on 16/09/2016.
 */
public class RouteAnalyser extends Controller {
    @FXML
    private BarChart analyserGraph;

    private ArrayList<Route> current_routes;

    private Dataset currentdata = null;
    private HashMap<String, ArrayList> useddata = new HashMap<String, ArrayList>();

    public void build_graph(){
        current_routes = currentdata.getRoutes();
        datasetup(current_routes);
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
        analyserGraph.getData().addAll(seriesArivals,seriesDeparts);
    }

    private void datasetup(ArrayList<Route> current_routes){
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

    public void load() {
        currentdata = getParent().getCurrentDataset();
        build_graph();
    }
}
