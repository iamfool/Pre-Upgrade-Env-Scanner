/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

/**
 * @author ramas6
 *
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import application.utils.Constants;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource(Constants.HOME_FXML));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(Constants.APP_CSS).toExternalForm());
			primaryStage.setTitle(Constants.APP_TITLE);
			primaryStage.setScene(scene);
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
}
