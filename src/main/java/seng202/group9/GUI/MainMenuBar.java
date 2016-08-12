package seng202.group9.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Main Menu Bar for the program
 * @author YaFedImYaEatIm
 *
 */
public class MainMenuBar{
	
	final private String menuFXML = "menu.fxml";
		
	public Parent getmenuBar(){
		try{
			Parent menuPane = FXMLLoader.load(getClass().getResource(menuFXML));
			return menuPane;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
