package seng202.group9.Controller;

import seng202.group9.Core.Airline;

import java.util.ArrayList;

/**
 * Created by fwy13 on 16/09/16.
 */
public class AirlineFilter extends Filter{

    private ArrayList<Airline> baseArray;
    private ArrayList<Airline> filteredList;

    /**
     * Constructor of the class sets the base array that will be used for filtering
     * @param baseList
     */
    public AirlineFilter(ArrayList<Airline> baseList){
        filteredList = new ArrayList<Airline>();
        baseArray = new ArrayList<Airline>();
        for (Airline airline: baseList){
            baseArray.add(airline);
            filteredList.add(airline);
        }
    }

    /**
     * filters the list base by name case insensitive.
     * @param name
     */
    public void filterName(String name){
        String regexCode = "(?i).*"+name+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getName().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * filters list based on alias case insensitive.
     * @param alias
     */
    public void filterAlias(String alias){
        String regexCode = "(?i).*"+alias+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getAlias().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Filters the list based on IATA code Case Insensitive.
     * @param IATA
     */
    public void filterIATA(String IATA){
        String regexCode = "(?i).*"+IATA+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getIATA().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Filters the list based on ICAO code Case Insensitive.
     * @param ICAO
     */
    public void filterICAO(String ICAO){
        String regexCode = "(?i).*"+ICAO+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getICAO().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * FIlters the list based on Callsign case Insensitive.
     * @param callsign
     */
    public void filterCallsign(String callsign){
        String regexCode = "(?i).*"+callsign+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getCallSign().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Filters the list based on the country case insensitive.
     * @param country
     */
    public void filterCountry(String country){
        String regexCode = "(?i).*"+country+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getCountryName().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Filters the list by activity.
     * @param active
     */
    public void filterActive(String active){
        String regexCode = "(?i).*"+active+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getActive().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * resets the filtered list back to the original list.
     */
    public void reset() {
        filteredList = new ArrayList<Airline>();
        for (Airline airline: filteredList){
            filteredList.add(airline);
        }
    }

    /**
     * gets the filtered list.
     * @return
     */
    public ArrayList getFilteredData() {
        return filteredList;
    }

    /**
     * sets a new base list of the filter.
     * @param arrayList
     */
    public void setBaseList(ArrayList<Airline> arrayList) {
        baseArray = new ArrayList<Airline>();
        for (Airline airline: arrayList){
            baseArray.add(airline);
        }
    }

    /**
     * prints the filter to the console.
     */
    public void printFilter(){
        for (Airline airline: filteredList){
            System.out.println(airline);
        }
    }
}
