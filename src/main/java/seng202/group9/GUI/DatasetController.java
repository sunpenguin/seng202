package seng202.group9.GUI;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    Dataset curDataset = null;
    ObservableList<Dataset> datasetList = observableArrayList();

    public void load() {
        curDataset = getParent().getCurrentDataset();
    }

    public void loadTable(){
        ArrayList<Dataset> datasets = getParent().getDatasets();
        datasetList = observableArrayList(datasets);
        datasetView.setItems(datasetList);
    }

    public void deleteDataset(){
        Dataset datasetToDelete = (Dataset) datasetView.getSelectionModel().getSelectedItem();
        getParent().deleteDataset(datasetToDelete);
    }

    public void addDataset(){

    }

    public void openDataset(){

    }
}
