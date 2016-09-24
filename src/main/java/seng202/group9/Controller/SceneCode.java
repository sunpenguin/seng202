package seng202.group9.Controller;

/**
 * Created by fwy13 on 16/09/16.
 * SceneCode enum is used for Serialization of sessions as well as changing the GUI state from one to the other.
 */
public enum SceneCode {
    INITIAL(""), AIRLINE_SUMMARY("airline_summary.fxml"), AIRLINE_RAW_DATA("airline_raw_data.fxml"),
    AIRPORT_SUMMARY("airport_summary.fxml"), AIRPORT_RAW_DATA("airport_raw_data.fxml"),
    ROUTE_SUMMARY("routes_summary.fxml"), ROUTE_RAW_DATA("route_raw_data.fxml"), FLIGHT_SUMMARY("flight_data_summary.fxml"),
    FLIGHT_RAW_DATA("flight_raw_data.fxml"), AIRPORT_ANALYSER("airport_analyser.fxml"), ROUTE_ANALYSER("route_analyser.fxml"),
    AIRPORT_DIST_CALC("airport_dist_calc.fxml"), AIRLINE_ADD("airline_add_form.fxml"), AIRLINE_FILTER("airline_filter_form.fxml"),
    AIRPORT_ADD("airport_add_form.fxml"), AIRPORT_FILTER("airport_filter_form.fxml"), ROUTE_ADD("route_add_form.fxml"),
    ROUTE_FILTER("route_filter_form.fxml"), AIRLINE_EDIT("airline_edit_form.fxml");

    private String filePath;

    /**
     * COnstructor for Scene
     * @param filePath
     */
    SceneCode(String filePath){
        this.filePath = filePath;
    }
    /**
     * gets the filepath of the specific scene
     */
    public String getFilePath(){
        return filePath;
    }
}
