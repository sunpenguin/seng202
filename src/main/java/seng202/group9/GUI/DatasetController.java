package seng202.group9.GUI;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;

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
    Dataset selectedFirst = null;

    public void load() {
        curDataset = getParent().getCurrentDataset();
        datasetName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    addDataset();
                }
            }
        });
        datasetView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)){
                    if (event.getClickCount() == 1){
                        selectedFirst = (Dataset) datasetView.getSelectionModel().getSelectedItem();
                    }
                    if (event.getClickCount() == 2){
                        if (selectedFirst == (Dataset) datasetView.getSelectionModel().getSelectedItem()){
                            openDataset();
                        }
                    }
                }
            }
        });
        loadTable();
    }

    public void loadTable(){
        ArrayList<Dataset> datasets = getParent().getDatasets();
        datasetList = observableArrayList(datasets);
        datasetView.setItems(datasetList);
    }

    public void deleteDataset(){
        Dataset datasetToDelete = (Dataset) datasetView.getSelectionModel().getSelectedItem();
        if (datasetToDelete != null) {
            getParent().deleteDataset(datasetToDelete);
            loadTable();
        }
    }

    public void addDataset(){
        String name = datasetName.getText();
        if (!name.equals("") && name != null) {
            try {
                getParent().createDataset(name);
                datasetName.clear();
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
        if (datasetToOpen != null) {
            getParent().setCurrentDataset(datasetToOpen);
            loadTable();
            getParent().getMenuController().replaceSceneContent(getParent().getSession().getSceneDisplayed());
            ((Stage) openDataset.getScene().getWindow()).close();
        }
    }
}
