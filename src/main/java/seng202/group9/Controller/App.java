package seng202.group9.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seng202.group9.GUI.MenuController;

/**
 * Main Application frame of the Flight Data Analyser.
 *
 */
public class App extends Application
{
	private ArrayList<Dataset> datasets = new ArrayList<Dataset>();
	private Dataset currentDataset = null;
	private Stage primaryStage = null;
	private VBox mainContainer;
	
    public static void main( String[] args )
    {
        launch(args);
    }
    /**
     * Starts the application
     * @param primaryStage main "stage" of the program
     */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		//load the menu and the first container
		try {
			FXMLLoader loader = new FXMLLoader();
			InputStream in = getClass().getClassLoader().getResourceAsStream("menu.fxml");
			mainContainer = (VBox) loader.load(in);
			Scene scene = new Scene(mainContainer, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			MenuController menuController = (MenuController) loader.getController();
			menuController.setApp(this);
			//replaceSceneContent("flight_raw_data.fxml");//replace this to check your fxml file
		} catch (Exception e) {
			e.printStackTrace();
		}
		primaryStage.show();

		//testing out dataset
		try {
			currentDataset = new Dataset("test's", Dataset.getExisting);
		}catch (DataException e){
			e.printStackTrace();

		}
		AirlineFilter filter = new AirlineFilter(currentDataset.getAirlines());
		filter.filterName("NZ");
		filter.filterAlias("flight");
		filter.printFilter();
/*
		//testout single route adding
		try {
			currentDataset.addRoute("D2", "MAC", "WIN", "Y", "0", "NOW");
		}catch (DataException e){
			e.printStackTrace();
		}
		//testout single airport adding
		try {
			currentDataset.addAirport("Windows 10", "PC", "Windows", "WIN", "WIND", "0.0", "0.0", "0.0", "0.0", "U", "PC/Windows");
		}catch (DataException e){
			e.printStackTrace();
		}
		//testout single airline adding
		try {
			currentDataset.addAirline("Dota2", "Valve", "D2", "DOT", "Defence of the Ancients", "Steam", "Y");
		}catch (DataException e){
			e.printStackTrace();
		}
		//testing out airport parser
		try {
			System.out.println(currentDataset.importAirport("res/Samples/Airports.txt"));
		} catch (DataException e) {
			e.printStackTrace();
		}
		//testing out airline parser
		try{
			System.out.println(currentDataset.importAirline("res/Samples/Airlines.txt"));
		} catch (DataException e){
			e.printStackTrace();
		}
		//testing out route parser
		try {
			System.out.println(currentDataset.importRoute("res/Samples/Routes.txt"));
		} catch (DataException e) {
			e.printStackTrace();
		}

		//testing out flight parser
        try {
            System.out.println(currentDataset.importFlight("res/Samples/NZCH-WSSS.csv"));
        } catch (DataException e) {
            e.printStackTrace();
        }*/

	}

	/**
	 * Replace Scene Content with fxml file code from oracle.
	 * @param fxml
	 * @return
	 * @throws Exception
	 */
	public Initializable replaceSceneContent(String fxml) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		InputStream in = getClass().getClassLoader().getResourceAsStream(fxml);
		Parent page;
		try {
			page = (Parent) loader.load(in);
		} finally {
			in.close();
		}
		while(mainContainer.getChildren().size() > 1) {
			mainContainer.getChildren().remove(1);
		}
		mainContainer.getChildren().add(page);
		return (Initializable) loader.getController();
	}

	public Dataset getCurrentDataset(){
		return currentDataset;
	}

	public void deleteDataset(Dataset dataset){
		dataset.deleteDataset();
		datasets.remove(dataset);
	}
}
