package seng202.group9.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.group9.Core.Position;
import seng202.group9.Core.RoutePath;

import java.util.ArrayList;

/**
 * Created by fwy13 on 17/09/16.
 */
public class Map {

    private WebEngine webEngine;
    private WebView webView;
    private boolean canLoad = false;
    public static int loadAirports = 0;
    public static int loadRoutes = 1;

    public Map(WebView webView, final RoutePath newRoute){
        this.webView = webView;
        webEngine = webView.getEngine();
        initMap();
        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<Worker.State>() {
                public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                    if (newState == Worker.State.SUCCEEDED){
                        displayRoute(newRoute);
                    }
                }
            });
    }

    public Map(WebView webView, final ArrayList<RoutePath> newRoute, int type){
        this.webView = webView;
        webEngine = webView.getEngine();
        initMap();
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED){
                            if (loadAirports == type) {
                                displayAirports(newRoute);
                            }else if (loadRoutes == type){
                                displayRoutes(newRoute);
                            }
                        }
                    }
                });
    }

    public Map(WebView webView, final RoutePath newRoute, final TableView table){
        this.webView = webView;
        webEngine = webView.getEngine();
        initMap();
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED){
                            displayRoute(newRoute);
                            table.getSelectionModel().selectFirst();
                        }
                    }
                });
    }

    public Map(WebView webView, final RoutePath newRoute, ListView table){
        this.webView = webView;
        webEngine = webView.getEngine();
        initMap();
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED){
                            displayRoute(newRoute);
                            table.getSelectionModel().selectFirst();
                        }
                    }
                });
    }

    public void initMap() {
        webEngine.load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }

    public void displayAirport(RoutePath newRoute) {
        String scriptToExecute = "displayAirport(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }

    public void displayRoute(RoutePath newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }

    public void displayAirports(ArrayList<RoutePath> airports) {
        String airportJSONArray = "[";
        int counter = 0;
        for (RoutePath airport: airports){
            airportJSONArray += airport.toJSONArray() + ", ";
            if (counter++ > 49){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Too Many Airports");
                alert.setHeaderText("Too Many Airports to display");
                alert.setContentText("As there are too many Airports to display only the first\n50 will be displayed.");
                alert.showAndWait();
                break;
            }
        }
        airportJSONArray += "]";
        webEngine.executeScript("displayAirports("+airportJSONArray+");");
    }

    public void displayRoutes(ArrayList<RoutePath> routes){
        String routeJSONArray = "[";
        int counter = 0;
        for (RoutePath route: routes){
            routeJSONArray += route.toJSONArray() + ", ";
            if (counter++ > 49){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Too Many Routes");
                alert.setHeaderText("Too Many Routes to display");
                alert.setContentText("As there are too many routes to display only the first\n50 will be displayed.");
                alert.showAndWait();
                break;
            }
        }
        routeJSONArray += "]";
        webEngine.executeScript("displayRoutes("+routeJSONArray+");");
    }

}
