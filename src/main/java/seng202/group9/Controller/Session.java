package seng202.group9.Controller;

import seng202.group9.Core.FlightPoint;

import java.io.Serializable;

/**
 * Created by fwy13 on 16/09/16.
 * Users last session state is store here.
 */
public class Session implements Serializable {
    private SceneCode sceneDisplayed;
    private int currentFlightPointID;
    private int currentFlightPathID;

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

}
