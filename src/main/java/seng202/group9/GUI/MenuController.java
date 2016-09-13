package seng202.group9.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
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
	public void loadAirlineRaw() {
		try {
			parent.replaceSceneContent("menu.fxml");
			System.out.println("Loaded Airline Raw Data.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setApp(App parent){
		this.parent = parent;
	}

	public void viewFlightSummary() {
		Stage stage = new Stage();
		BorderPane root = new BorderPane(); //root panel
		Scene scene = new Scene(root);
		//create the Flight summary page
		FlightDataSummary flightSummPage = new FlightDataSummary();
		root.setTop(flightSummPage.getFlightSummary());
		stage.setScene(scene);
		stage.show();
		//seng202.group9.Controller.App.primaryStage.setScene(scene);
		//seng202.group9.Controller.App.primaryStage.show();
	}

	public void viewFlightRawData() {

		Stage stage = new Stage();
		BorderPane root = new BorderPane(); //root panel
		Scene scene = new Scene(root);
		//create the Flight summary page
		FlightRawData flightRawDataPage = new FlightRawData();
		root.setTop(flightRawDataPage.getFlightRawData());
		stage.setScene(scene);
		stage.show();
		//seng202.group9.Controller.App.primaryStage.setScene(scene);
		//seng202.group9.Controller.App.primaryStage.show();
	}


	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
