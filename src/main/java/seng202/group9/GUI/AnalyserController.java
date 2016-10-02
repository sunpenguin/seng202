package seng202.group9.GUI;

import seng202.group9.Controller.SceneCode;

/**
 * Created by michael on 24/09/2016.
 */
public class AnalyserController extends Controller{

    public void barGraphButton(){
        replaceSceneContent(SceneCode.BAR_GRAPH_CHOOSER);
    }

    public void pieGraphButton(){
        replaceSceneContent(SceneCode.PIE_GRAPH_CHOOSER);
    }

    public void load() {}
}
