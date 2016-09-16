package seng202.group9.Controller;

import seng202.group9.Core.Airline;

import java.util.ArrayList;

/**
 * Created by fwy13 on 16/09/16.
 */
public class AirlineFilter extends Filter{

    private ArrayList<Airline> baseArray;
    private ArrayList<Airline> filteredList;

    public AirlineFilter(ArrayList<Airline> baseList){
        filteredList = new ArrayList<Airline>();
        baseArray = new ArrayList<Airline>();
        for (Airline airline: baseList){
            baseArray.add(airline);
            filteredList.add(airline);
        }
    }

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

    public void reset() {
        filteredList = new ArrayList<Airline>();
        for (Airline airline: filteredList){
            filteredList.add(airline);
        }
    }

    public ArrayList getFilteredData() {
        return filteredList;
    }

    public void setBaseList(ArrayList<Airline> arrayList) {
        baseArray = new ArrayList<Airline>();
        for (Airline airline: arrayList){
            baseArray.add(airline);
        }
    }

    public void printFilter(){
        for (Airline airline: filteredList){
            System.out.println(airline);
        }
    }
}
