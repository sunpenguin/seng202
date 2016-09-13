package seng202.group9.GUI;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Liam Beckett on 13/09/2016.
 */
public class FlightRawDataController  implements Initializable {


    //TableColumn<>

    private Dataset theDataSet = null;
    App parent;


    public void setApp(App parent){
        this.parent = parent;
    }

    //public void loadTables() {

    //}


    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }
}