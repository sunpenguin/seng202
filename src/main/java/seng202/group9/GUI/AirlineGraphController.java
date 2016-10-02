package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;

import static javafx.application.ConditionalFeature.FXML;

/**
 * Created by Gondr on 2/10/2016.
 */
public class AirlineGraphController extends Controller{
    private Dataset dataset;
    private Session session;
    @FXML
    private GridPane chartContainer;

    @Override
    public void load() {
        if (!checkDataset()){
            return;
        }
        dataset = getParent().getCurrentDataset();
        session = getParent().getSession();
    }

    public void goToRawData(){
        replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
    }
}
