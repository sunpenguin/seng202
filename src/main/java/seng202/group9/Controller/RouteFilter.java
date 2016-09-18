package seng202.group9.Controller;

import seng202.group9.Core.Route;

import java.util.ArrayList;

/**
 * Created by fwy13 on 16/09/16.
 */
public class RouteFilter extends Filter{

    private ArrayList<Route> baseArray;
    private ArrayList<Route> filteredList;

    /**
     * Constructor which takes in a base List which all data is filtered from.
     * @param baseList
     */
    public RouteFilter(ArrayList<Route> baseList){
        filteredList = new ArrayList<Route>();
        baseArray = new ArrayList<Route>();
        for (Route route: baseList){
            baseArray.add(route);
            filteredList.add(route);
        }
    }

    /**
     * Filters the list by the Airline Case Insensitive.
     * @param airline
     */
    public void filterAirline(String airline){
        String regexCode = "(?i).*"+airline+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getAirlineName().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * filters list by Airport Case Insensitive.
     * @param airport
     */
    public void filterSourceAirport(String airport){
        String regexCode = "(?i).*"+airport+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getDepartureAirport().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * filters lsit by destination airport Case Insensitive.
     * @param airport
     */
    public void filterDestinationAirport(String airport){
        String regexCode = "(?i).*"+airport+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getArrivalAirport().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * filters the list by its codeshare Case Insensitive.
     * @param code
     */
    public void filterCodeshare(String code){
        String regexCode = "(?i).*"+code+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getCode().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * filters the list by how many stops it does Case Insensitive, Doesn't have to match exactly.
     * @param stops
     */
    public void filterDestinationStops(String stops){
        String regexCode = "(?i).*"+stops+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!String.valueOf(filteredList.get(index).getStops()).matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * filters the list by the equipment used for the route Case Insensitive.
     * @param equipment
     */
    public void filterEquipment(String equipment){
        String regexCode = "(?i).*"+equipment+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getEquipment().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * resets the list to what the baselist.
     */
    public void reset() {
        filteredList = new ArrayList<Route>();
        for (Route airline: filteredList){
            filteredList.add(airline);
        }
    }

    /**
     * get the filnal filtered array.
     * @return
     */
    public ArrayList getFilteredData() {
        return filteredList;
    }

    /**
     * sets the new baselist.
     * @param arrayList
     */
    public void setBaseList(ArrayList<Route> arrayList) {
        baseArray = new ArrayList<Route>();
        for (Route route: arrayList){
            baseArray.add(route);
        }
        reset();
    }

    /**
     * prints the filtered list to Console.
     */
    public void printFilter(){
        for (Route route: filteredList){
            System.out.println(route);
        }
    }
}
