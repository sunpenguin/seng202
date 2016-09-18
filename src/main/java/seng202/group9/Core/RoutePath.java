package seng202.group9.Core;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by brad on 9/09/16.
 * Edited by fwy13
 */
public class RoutePath {
    private ArrayList<Position> route = new ArrayList<Position>();

    /**
     * Route Path constructor when the user knows the points.
     * @param points
     */
    public RoutePath(Position ...points) {
        Collections.addAll(route, points);
    }

    /**
     * Route Path constructor when the user doesn't know the points.
     */
    public RoutePath(){

    }

    /**
     * adds a {@link Position} to the RoutePath.
     * @param position
     */
    public void addPosition(Position position){
        route.add(position);
    }

    /**
     * Gets the RoutePath positions.
     * @return
     */
    public ArrayList<Position> getRoute() {
        return route;
    }

    /**
     * Converts the RoutePath to an Array in JSON which can then be passed to the Map to display.
     * @return
     */
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