package seng202.group9.GUI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group9.Controller.App;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.SceneCode;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by fwy13 on 17/09/16.
 */
public class Importer{
    /**
     * importer GUI for importing files.
     */
    public Importer(SceneCode scene, App parent, Stage primaryStage){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                "Text & .csv", "*.txt", "*.csv");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showOpenDialog(primaryStage);
        if (file != null) {
            if (scene == SceneCode.AIRLINE_RAW_DATA) {
                try {
                    showSuccessAlert(parent.getCurrentDataset().importAirline(file.getPath()));
                } catch (DataException e) {
                    e.printStackTrace();
                }
            } else if (scene == SceneCode.AIRPORT_RAW_DATA) {
                try {
                    showSuccessAlert(parent.getCurrentDataset().importAirport(file.getPath()));
                } catch (DataException e) {
                    e.printStackTrace();
                }
            } else if (scene == SceneCode.ROUTE_RAW_DATA) {
                try {
                    showSuccessAlert(parent.getCurrentDataset().importRoute(file.getPath()));
                } catch (DataException e) {
                    e.printStackTrace();
                }
            } else if (scene == SceneCode.FLIGHT_RAW_DATA) {
                try {
                    showSuccessAlert(parent.getCurrentDataset().importFlight(file.getPath()));
                } catch (DataException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showSuccessAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Import File Success!");
        alert.setHeaderText("Your File has been Parsed.");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Import File Error");
        alert.setHeaderText("Your File has resulted in an error. This may be because of an invalid file format or a corrupted file.");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
