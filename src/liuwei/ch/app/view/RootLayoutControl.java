package liuwei.ch.app.view;

import liuwei.ch.app.MainApp;

/**
* The controller for the root layout. The root layout provides the basic
* application layout containing a menu bar and space where other JavaFX
* elements can be placed.
* 
* @author wei liu
*/
public class RootLayoutControl {

	// Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
