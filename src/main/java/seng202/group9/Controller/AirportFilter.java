package seng202.group9.Controller;

import seng202.group9.Core.Airline;
import seng202.group9.Core.Airport;

import java.util.ArrayList;

/**
 * Created by fwy13 on 16/09/16.
 */
public class AirportFilter extends Filter{

    private ArrayList<Airport> baseArray;
    private ArrayList<Airport> filteredList;

    /**
     * Constructor of the Airport FIlter.
     * @param baseList
     */
    public AirportFilter(ArrayList<Airport> baseList){
        filteredList = new ArrayList<Airport>();
        baseArray = new ArrayList<Airport>();
        for (Airport airport: baseList){
            baseArray.add(airport);
            filteredList.add(airport);
        }
    }

    /**
     * Filters the list by name Case Insensitive.
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
     * filters the list by the city Case Insensitive.
     * @param city
     */
    public void filterCity(String city){
        String regexCode = "(?i).*"+city+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getCityName().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Filters the list by Country  Case Insensitive.
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
     * FIlters list by IATA/FFA  Case Insensitive.
     * @param IATA_FFA
     */
    public void filterIATA_FFA(String IATA_FFA){
        String regexCode = "(?i).*"+IATA_FFA+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getIATA_FFA().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Filters the list by ICAO Case Insensitive.
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
     * FIlters the list by latitude Case Insensitive.
     * @param latitude
     */
    public void filterLatitude(String latitude){
        String regexCode = ".*"+latitude+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!String.valueOf(filteredList.get(index).getLatitude()).matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Filters the list by longitude Case Insensitive.
     * @param longitude
     */
    public void filterLongitude(String longitude){
        String regexCode = ".*"+longitude+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!String.valueOf(filteredList.get(index).getLongitude()).matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * FIlters the list by Altitude Case Insensitive.
     * @param altitude
     */
    public void filterAltitude(String altitude){
        String regexCode = ".*"+altitude+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!String.valueOf(filteredList.get(index).getAltitude()).matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * FIlters the list by Timezeon  Case Insensitive.
     * @param timezone
     */
    public void filterTimezone(String timezone){
        String regexCode = "(?i).*"+timezone+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!String.valueOf(filteredList.get(index).getCity(). getTimezone()).matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Filters the list by Olson Timezone format Case Insensitive.
     * @param olson
     */
    public void filterOlson(String olson){
        String regexCode = "(?i).*"+olson+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getCity().getTimeOlson().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Filters the list by DST Case Insensitive.
     * @param DST
     */
    public void filterDST(String DST){
        String regexCode = "(?i).*"+DST+".*";
        int index = 0;
        while(index < filteredList.size()){
            if (!filteredList.get(index).getDST().matches(regexCode)){
                filteredList.remove(index);
            }else{
                index++;
            }
        }
    }

    /**
     * Resets the list to it original state before filter
     */
    public void reset() {
        filteredList = new ArrayList<Airport>();
        for (Airport airport: filteredList){
            filteredList.add(airport);
        }
    }

    /**
     * gets the filtered list
     * @return
     */
    public ArrayList getFilteredData() {
        return filteredList;
    }

    public void setBaseList(ArrayList<Airport> arrayList) {
        baseArray = new ArrayList<Airport>();
        for (Airport airport: arrayList){
            baseArray.add(airport);
        }
        reset();
    }

    /**
     * prints the filtered list to console.
     */
    public void printFilter(){
        for (Airport airport: filteredList){
            System.out.println(airport);
        }
    }
}
