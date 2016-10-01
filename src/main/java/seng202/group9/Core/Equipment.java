package seng202.group9.Core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fwy13 on 2/10/16.
 */
public class Equipment {
    private String name;
    private HashMap<Integer, Route> routesUsed;

    public Equipment(String name){
        this.name = name;
        routesUsed = new HashMap<>();
    }

    public void resetRoutes(){
        routesUsed = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRoute(Route route){
        routesUsed.put(routesUsed.size(), route);
    }

    public HashMap<Integer, Route> getRoutesUsed() {
        return routesUsed;
    }

    public void setRoutesUsed(HashMap<Integer, Route> routesUsed) {
        this.routesUsed = routesUsed;
    }

    public int getRouteNum(){
        return routesUsed.size();
    }
}
