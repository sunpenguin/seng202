package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;

/**
 * Created by Gondr on 2/10/2016.
 */
public class AirportGraphController extends Controller{
    private Dataset dataset;
    private Session session;
    @FXML
    private VBox chartContainer;

    @Override
    public void load() {
        if (!checkDataset()){
            return;
        }
        dataset = getParent().getCurrentDataset();
        session = getParent().getSession();
    }

    public void goToRawData(){
        replaceSceneContent(SceneCode.AIRPORT_RAW_DATA);
    }
}
