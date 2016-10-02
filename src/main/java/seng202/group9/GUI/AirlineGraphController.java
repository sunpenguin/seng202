package seng202.group9.GUI;

import seng202.group9.Controller.SceneCode;

/**
 * Created by Gondr on 2/10/2016.
 */
public class AirlineGraphController extends Controller{

    @Override
    public void load() {

    }

    public void goToRawData(){
        replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
    }
}
