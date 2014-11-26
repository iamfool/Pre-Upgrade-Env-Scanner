/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import application.utils.Constants;


public class HomeController implements Initializable 
{
	// link fxids
	@SuppressWarnings("rawtypes")
	@FXML private ChoiceBox dbChoice;
	@FXML private Button submitButton; 
	@FXML private AnchorPane homeAnchor;
	

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// initialize db vendor choice
		dbChoice.setItems(FXCollections.observableArrayList(Constants.DB2_UDB,new Separator(),Constants.ORACLE,new Separator(),Constants.MSSQL) );
		dbChoice.setValue(Constants.DB2_UDB);
	}
	 
	@FXML protected void handleSubmitButtonAction(ActionEvent event) 
	{
        
        
		
		try 
		{
			homeAnchor.getChildren().setAll(FXMLLoader.load(getClass().getResource(Constants.HOME_FXML)));
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		}
		

		
    }

	 
	
	 
}
