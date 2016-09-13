package seng202.group9.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.Initializable;
import seng202.group9.Controller.App;

public class MenuController implements Initializable{

	App parent;

	public void importAirports(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}
	
	public void importAirlines(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}

	public void importRoutes(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}
	
	public void importFlightData(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}

	/**
	 * Load Airline Raw Data Function.
	 */
	public void loadAirlineRaw(){
		try {
			parent.replaceSceneContent("menu.fxml");
			System.out.println("Loaded Airline Raw Data.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	public void setApp(App parent){
		this.parent = parent;
	}

}
