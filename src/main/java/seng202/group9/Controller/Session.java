package seng202.group9.Controller;

import javafx.collections.ObservableList;
import seng202.group9.Core.Airline;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by fwy13 on 16/09/16.
 * Users last session state is store here.
 */
public class Session implements Serializable {
    private SceneCode sceneDisplayed;
    private HashMap<String, Airline> filteredAirlines;

    /**
     * Constructor for a new session
     */
    public Session(){
        //blank constructor
        this.sceneDisplayed = SceneCode.INITIAL;
    }

    /**
     * Constructor for a previous session.
     * @param scene
     */
    public Session(SceneCode scene){
        this.sceneDisplayed = scene;
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

    public HashMap<String, Airline> getFilteredAirlines() {
        return filteredAirlines;
    }
}
