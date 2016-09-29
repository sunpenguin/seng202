package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by spe76 on 30/09/16.
 */
public class HelpController extends Controller {
    @FXML
    private ListView listView;
    @FXML
    private TextArea textArea;

    public static final ObservableList menu = FXCollections.observableArrayList();

    public void load() {
        menu.addAll("Importing Data", "Viewing Data", "Manipulating Data", "Analysis");

        textArea.setStyle("-fx-text-alignment: justify");
        listView.setItems(menu);
    }

    public void sss() {
        String menuValue = listView.getSelectionModel().getSelectedItem().toString();
        if (menuValue == "Importing Data") {
            textArea.setText("You can import data from the first start up of the application and " +
                    "from the 'File' menu on the top of the screen. \nTo import data, select the type " +
                    "of data you wish to import. Then select the file (.csv and .txt file) from the " +
                    "file selector. The data will be loaded into the program and taken to the " +
                    "corresponding summary page.");
        } else if (menuValue == "Viewing Data") {
            textArea.setText("There are two types of views available: Summary view and Raw Data view. " +
                    "These are accessable from the menu on the top of the screen under the " +
                    "'View' tab. You first choose which set of data you want to view and then you can select" +
                    " either 'Summary' or 'Raw Data'.\n" +
                    " The summary view does not have every column but provides a map of where the " +
                    "place is. \nThe raw data view allows the user to view the full data table.");
        } else if (menuValue == "Manipulating Data") {
            textArea.setText("Data manipulation is all available in the Raw Data views. There are four " +
                    "ways to manipulate data: 'Add', 'Filter', 'Edit' and 'Delete'. \n" +
                    "Add: To add a new entry, first go to the raw data view for that data type. Then click " +
                    "on the add button located on the bottom of the page. Then fill out the entries in the " +
                    "pop-up box and click add at the bottom of the screen. If there is an error with your entry, " +
                    "a message will pop up to help you.\n" +
                    "Filter: To filter all current entries, click on the filter option and a pop " +
                    "up will appear. Then type in the fields you wish to filter by and press the filter button. " +
                    "The table should update with the fields specified.\n" +
                    "Edit: The edit function can be accessed by right clicking on the entry you wish to edit and" +
                    " clicking the edit option. This will lead to a pop up where you can edit the current entry. " +
                    " When the edit has been completed, you can press the apply button on the bottom of the pop up. " +
                    "Again, when the program detects an invalid field, a message will pop up.\n" +
                    "Delete: The delete function is also accessed by right clicking an entry and pressing the delete field. " +
                    "This will come up with a pop up to confirm your delete. When you press ok, the entry will be deleted " +
                    "from the program. The program also allows multiple deletes.");
        } else if (menuValue == "Analysis") {
            textArea.setText("There are two ways to do analysis. \nThe first method is to go to the raw data page and " +
                    "press analyse. This will come up with specific graphs that are related to the set of data. " +
                    "\nThe second method is by accessing the 'Analysis' button on the menu on the top of the page. " +
                    "You can select which type of analysis you want from here.");
        }
    }

}