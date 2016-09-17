package seng202.group9.Core;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by brad on 9/09/16.
 * Edited by fwy13
 */
public class RoutePath {
    private ArrayList<Position> route = new ArrayList<Position>();

    public RoutePath(Position ...points) {
        Collections.addAll(route, points);
    }

    public RoutePath(){

    }

    public void addPosition(Position position){
        route.add(position);
    }

    public ArrayList<Position> getRoute() {
        return route;
    }

    public String toJSONArray() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Position pos : route){
            stringBuilder.append(
                    String.format("{lat: %f, lng: %f}, ", pos.lat, pos.lng));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}