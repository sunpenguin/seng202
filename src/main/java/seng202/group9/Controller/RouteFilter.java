package seng202.group9.Controller;

import seng202.group9.Core.Route;

import java.util.ArrayList;

/**
 * Created by fwy13 on 16/09/16.
 */
public class RouteFilter extends Filter{

    private ArrayList<Route> baseArray;
    private ArrayList<Route> filteredList;

    public RouteFilter(ArrayList<Route> baseList){
        filteredList = new ArrayList<Route>();
        baseArray = new ArrayList<Route>();
        for (Route route: baseList){
            baseArray.add(route);
            filteredList.add(route);
        }
    }

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

    public void filterDestinationStops(String stops){
        String regexCode = "(?i).*"+stops+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getArrivalAirport().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

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

    public void reset() {
        filteredList = new ArrayList<Route>();
        for (Route airline: filteredList){
            filteredList.add(airline);
        }
    }

    public ArrayList getFilteredData() {
        return filteredList;
    }

    public void setBaseList(ArrayList<Route> arrayList) {
        baseArray = new ArrayList<Route>();
        for (Route route: arrayList){
            baseArray.add(route);
        }
    }

    public void printFilter(){
        for (Route route: filteredList){
            System.out.println(route);
        }
    }
}
