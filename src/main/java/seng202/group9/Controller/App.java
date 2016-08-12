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
	ArrayList<DatasetController> Datasets = new ArrayList<DatasetController>();
	
    public static void main( String[] args )
    {
        launch(args);
    }

    /**
     * Starts the application
     * @param primaryStage main "stage" of the program
     * @see The last sessions menu or the getting started page.
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
	}
}
