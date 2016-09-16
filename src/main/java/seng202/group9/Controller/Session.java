package seng202.group9.Controller;

import java.io.Serializable;

/**
 * Created by fwy13 on 16/09/16.
 */
public class Session implements Serializable {
    private SceneCode sceneDisplayed;

    public Session(){
        //blank constructor
        this.sceneDisplayed = SceneCode.INITIAL;
    }

    public Session(SceneCode scene){
        this.sceneDisplayed = scene;
    }

    public void setSceneDisplayed(SceneCode sceneDisplayed) {
        this.sceneDisplayed = sceneDisplayed;
    }
}
