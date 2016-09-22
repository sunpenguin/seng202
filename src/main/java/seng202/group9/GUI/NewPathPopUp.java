package seng202.group9.GUI;

import javafx.scene.control.Alert;
import seng202.group9.Controller.DataException;
import seng202.group9.Controller.EntryParser;
import seng202.group9.Core.FlightPoint;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.red;

/**
 * Creates the pop up box where the user will enter the Source and Destination airports for the path name, will reject
 * empty strings, strings of length 4 and characters that are not A-Z. Will convert lowercase to uppercase.
 * Created by Liam Beckett on 17/09/2016.
 */
public class NewPathPopUp {

    private String sourceAirport = null;
    private String destinationAirport = null;
    // Creates and displays the pop up box for the user to input data.
    public void display() {
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Source Airport ICAO Code" ));
        panel.add(field1);
        panel.add(new JLabel("Destination Airport ICAO Code: "));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
                sourceAirport = field1.getText().toUpperCase();
                destinationAirport = field2.getText().toUpperCase();
            try{
                EntryParser parser = new EntryParser();
                parser.parsePointName(sourceAirport);
            }catch (DataException e){
                sourceAirport = null;
                destinationAirport = null;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Flight Path Name Error");
                alert.setHeaderText("Error adding the Source airport ICAO code.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            try{
                EntryParser parser = new EntryParser();
                parser.parsePointName(destinationAirport);
            }catch (DataException e){
                sourceAirport = null;
                destinationAirport = null;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Flight Path Name Error");
                alert.setHeaderText("Error adding the Destination airport ICAO code.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            sourceAirport = null;
            destinationAirport = null;
        }
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }
}


