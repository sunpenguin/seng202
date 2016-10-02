package seng202.group9.GUI;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.Dataset;

import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by fwy13 on 30/09/16.
 */
public class DatasetController extends Controller{

    @FXML
    ListView datasetView;
    @FXML
    TextField datasetName;
    @FXML
    Button openDataset;
    Dataset curDataset = null;
    ObservableList<Dataset> datasetList = observableArrayList();

    public void load() {
        curDataset = getParent().getCurrentDataset();
        loadTable();
    }

    public void loadTable(){
        ArrayList<Dataset> datasets = getParent().getDatasets();
        datasetList = observableArrayList(datasets);
        datasetView.setItems(datasetList);
    }

    public void deleteDataset(){
        Dataset datasetToDelete = (Dataset) datasetView.getSelectionModel().getSelectedItem();
        getParent().deleteDataset(datasetToDelete);
        loadTable();
    }

    public void addDataset(){
        String name = datasetName.getText();
        if (!name.equals("") && name != null) {
            try {
                getParent().createDataset(name);
            } catch (DataException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dataset Creation Error");
                alert.setHeaderText("Error creating Dataset.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dataset Creation Error");
            alert.setHeaderText("Error creating Dataset.");
            alert.setContentText("Dataset Name Cannot be Empty");
            alert.showAndWait();
        }
        loadTable();
    }

    public void openDataset(){
        Dataset datasetToOpen = (Dataset) datasetView.getSelectionModel().getSelectedItem();
        getParent().setCurrentDataset(datasetToOpen);
        loadTable();
        getParent().getMenuController().replaceSceneContent(getParent().getSession().getSceneDisplayed());
        ((Stage) openDataset.getScene().getWindow()).close();
    }
}
