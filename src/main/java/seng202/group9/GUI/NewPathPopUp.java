package seng202.group9.GUI;

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
            if (validate(sourceAirport) != true && validate(destinationAirport) != true){
                sourceAirport = null;
                destinationAirport = null;
                JOptionPane.showMessageDialog(null, "Enter a vaild ICAO Code!");
                return;
            }
        } else {
            sourceAirport = null;
            destinationAirport = null;
        }
    }

    // Checks the users entered string to make sure it is a 4 letter valid code.
    private static boolean validate(String airportCode){
        if(airportCode == ""){
            return false;
        } else if(airportCode.length() != 4){
            return false;
        } else if(!isLetter(airportCode)){
            return false;
        }
        return true;
    }

    // Used by the validate() method to cycle through the string looking for non letter characters.
    private static boolean isLetter(String airportCode){
        char[] chars = airportCode.toCharArray();

        for (char element : chars) {
            if(!Character.isLetter(element)) {
                return false;
            }
        }
        return true;
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }
}


