package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.web.WebView;
import seng202.group9.Core.Airport;
import seng202.group9.Core.Position;
import seng202.group9.Core.Route;
import seng202.group9.Core.RoutePath;
import seng202.group9.Map.Map;

import java.util.ArrayList;

/**
 * Created by fwy13 on 2/10/16.
 */
public class PopUpMapController extends Controller {
    @FXML
    WebView mapView;
    Map map;

    @Override
    public void load() {
        map = new Map(mapView, new RoutePath());
    }

    public void loadAirports(ArrayList<Airport> airports){
        ArrayList<RoutePath> routePaths = new ArrayList<>();
        for (Airport airport: airports){
            routePaths.add(airport.getRoutePath());
        }
        map.displayAirports(routePaths);
    }

    public void loadRoutes(ArrayList<Route> routes){
        ArrayList<RoutePath> routePaths = new ArrayList<>();
        for (Route route: routes){
            routePaths.add(route.getRoutePath());
        }
        map.displayRoutes(routePaths);
    }
}
