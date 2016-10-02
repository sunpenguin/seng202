package seng202.group9.GUI;

import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;

/**
 * Created by michael on 2/10/2016.
 */
public class BarChartErrorController extends Controller {
    private Session currentSession;

    public void load(){
        currentSession = this.getParent().getSession();
    }

    public void ignoredWarning() {
        currentSession.setForceGraph(Boolean.TRUE);
        replaceSceneContent(SceneCode.ROUTE_ANALYSER);
    }
    public void backToSafety(){
        replaceSceneContent(SceneCode.BAR_GRAPH_CHOOSER);
    }
}
