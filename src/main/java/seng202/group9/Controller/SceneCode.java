package seng202.group9.Controller;

/**
 * Created by fwy13 on 16/09/16.
 */
public enum SceneCode {
    INITIAL(""), AIRLINE_SUMMARY("airline_summary.fxml"), AIRLINE_RAW_DATA("airline_raw_data.fxml"),
    AIRPORT_SUMMARY("airport_summary.fxml"), AIRPORT_RAW_DATA("airport_raw_data.fxml"),
    ROUTE_SUMMARY("routes_summary.fxml"), ROUTE_RAW_DATA("route_raw_data.fxml"), FLIGHT_SUMMARY("flight_data_summary.fxml"),
    FLIGHT_RAW_DATA("flight_raw_data.fxml"), AIRPORT_ANALYSER("airport_analyser.fxml"), ROUTE_ANALYSER("route_analyser.fxml");

    private String filePath;

    SceneCode(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath(){
        return filePath;
    }
}
