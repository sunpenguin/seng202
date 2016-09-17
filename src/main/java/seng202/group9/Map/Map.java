package seng202.group9.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.group9.Core.Position;
import seng202.group9.Core.RoutePath;

/**
 * Created by fwy13 on 17/09/16.
 */
public class Map {

    private WebEngine webEngine;
    private WebView webView;

    public Map(WebView webView){
        this.webView = webView;
        webEngine = webView.getEngine();
        initMap();
    }

    public void initMap() {
        webEngine.load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }

    public void displayRoute(final RoutePath newRoute) {
        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<Worker.State>(){
                public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState){
                    String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
                    webEngine.executeScript(scriptToExecute);
                }
            });
    }

}
