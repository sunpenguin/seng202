package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.web.WebView;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Position;
import seng202.group9.Core.Route;
import seng202.group9.Core.RoutePath;
import seng202.group9.Map.Map;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fwy13 on 2/10/16.
 */
public class PopUpAirportMapController extends Controller {
    @FXML
    WebView mapView;
    Dataset dataset;
    Map map;

    @Override
    public void load() {
        dataset = getParent().getCurrentDataset();
        loadAirports();
    }

    public void loadAirports(){
        ArrayList<RoutePath> routePaths = new ArrayList<>();
        HashMap airports = getParent().getSession().getFilteredAirports();
        for (int i = 0; i < airports.size(); i ++){
            routePaths.add(dataset.getAirportDictionary().get(airports.get(i)).getRoutePath());
        }
        map = new Map(mapView, routePaths, Map.loadAirports);
    }

    public void loadRoutes(ArrayList<Route> routes){
        ArrayList<RoutePath> routePaths = new ArrayList<>();
        for (Route route: routes){
            routePaths.add(route.getRoutePath());
        }
        map.displayRoutes(routePaths);
    }
}
