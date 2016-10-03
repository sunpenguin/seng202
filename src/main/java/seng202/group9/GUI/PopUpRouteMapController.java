package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Route;
import seng202.group9.Core.RoutePath;
import seng202.group9.Map.Map;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fwy13 on 2/10/16.
 */
public class PopUpRouteMapController extends Controller{
    @FXML
    WebView mapView;
    Dataset dataset;
    Map map;

    /**
     * initial load function.
     */
    @Override
    public void load() {
        dataset = getParent().getCurrentDataset();
        loadRoutes();
    }

    /**
     * Load Routes filtered from session.
     */
    public void loadRoutes(){
        ArrayList<RoutePath> routePaths = new ArrayList<>();
        HashMap routes = getParent().getSession().getFilteredRoutes();
        for (int i = 0; i < routes.size(); i ++){
            routePaths.add(dataset.getRouteDictionary().get(routes.get(i)).getRoutePath());
        }
        map = new Map(mapView, routePaths, Map.loadRoutes);
    }
}
