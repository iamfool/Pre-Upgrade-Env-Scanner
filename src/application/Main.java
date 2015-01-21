/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

/**
 * @author ramas6
 *
 */




import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import application.controller.LoadController;
import application.controller.Navigator;
import application.utils.Constants;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{	
			primaryStage.setTitle(Constants.APP_TITLE);
			primaryStage.setScene(createScene(loadMainPane()));
			//primaryStage.setMaximized(true);
			//primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.show();
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	
	 private Pane loadMainPane() throws IOException 
	 {
	       // create root pane
		  	FXMLLoader fxLoader = new FXMLLoader();
	        Pane mainPane = (Pane) fxLoader.load(getClass().getResourceAsStream(Constants.LOADER_FXML));
	        LoadController mainController = fxLoader.getController();
	        Navigator.setLoader(mainController);
	        Navigator.setFxLoader(fxLoader);
	        Navigator.setHost(this.getHostServices());
	        
	        //load home pane
	        Navigator.loadScreen(Constants.HOME_FXML);

	        return mainPane;
	  }
	 
	 private Scene createScene(Pane mainPane) 
	 {
	        Scene scene = new Scene(mainPane);
	        scene.getStylesheets().setAll(getClass().getResource(Constants.APP_CSS).toExternalForm());
	        return scene;
	    }

}
