package seng202.group9.GUI;

import javafx.fxml.Initializable;
import seng202.group9.Controller.App;
import seng202.group9.Controller.SceneCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gondr on 16/09/2016.
 */
public abstract class Controller implements Initializable{
    private App parent;

    /**
     * set Parent app
     * @param parent
     */
    public void setApp(App parent){
        this.parent = parent;
    }

    /**
     * get parent App
     * @return
     */
    public App getParent(){
        return parent;
    }

    /**
     * functions to load
     */
    public abstract void load();

    /**
     * Replaces content and does normal loading
     * @param scene
     */
    public void replaceSceneContent(SceneCode scene){
        try {
            Controller controller = (Controller)
                    parent.replaceSceneContent(scene);
            controller.setApp(parent);
            controller.load();
            controller.loadOnce();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Functions here will only load once and after the load function.
     */
    public void loadOnce(){

    }

    /**
     * Initialize statement before anything loads.
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {

    }

}
