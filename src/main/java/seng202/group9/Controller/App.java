package seng202.group9.Controller;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import seng202.group9.GUI.MainMenuBar;

/**
 * Main Application frame of the Flight Data Analyser.
 *
 */
public class App extends Application
{
	private ArrayList<Dataset> Datasets = new ArrayList<Dataset>();
	private Dataset currentDataset = null;
	
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
		BorderPane root = new BorderPane(); //root panel
		Scene scene = new Scene(root,400,400);
		//create the menu
		MainMenuBar menuBar = new MainMenuBar();
		root.setTop(menuBar.getmenuBar());
		//TODO add the getting started page here.
		
		primaryStage.setScene(scene);
		primaryStage.show();
		//testing out dataset
		try {
			currentDataset = new Dataset("test's", Dataset.getExisting);
		}catch (DataException e){
			e.printStackTrace();
		}
		//testing out airport parser
		AirportParser airportParser = new AirportParser("res/Samples/Airports.txt");
		try {
			System.out.println(airportParser.parse());
		} catch (DataException e) {
			e.printStackTrace();
		}
		//testing out airline parser
		AirlineParser airlineParser = new AirlineParser("res/Samples/Airlines.txt");
		try {
			System.out.println(airlineParser.parse());
		} catch (DataException e) {
			e.printStackTrace();
		}
		//testing out route parser
		RouteParser routeParser = new RouteParser("res/Samples/Routes.txt");
		try {
			System.out.println(routeParser.parse());
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	public Dataset getCurrentDataset(){
		return currentDataset;
	}
}
