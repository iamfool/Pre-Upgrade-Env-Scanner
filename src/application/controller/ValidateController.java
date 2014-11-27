/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import application.DBMetaData;
import application.utils.Constants;

/**
 * @author ramas6
 *
 */
public class ValidateController implements Initializable 
{
   @FXML Button backButton;
   @FXML Label showDB;
   @FXML Label showDBType;
   @FXML Label showRules;
   @FXML Label showHost;
   @FXML Label showUname;
   @FXML Label showData;
   
   
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		LoadController rootControl = Navigator.getFxLoader().getController();
		
		if(rootControl.getMetaData() != null) 
		{
			renderMetaData(rootControl.getMetaData());
		}
		
	}
	
	
	@FXML protected void handleBackButtonAction(ActionEvent event) throws SQLException, IOException  
	{
      Navigator.loadScreen(Constants.HOME_FXML);
	}

	private void renderMetaData(DBMetaData dbMetaData) 
	{
		showDB.setText(dbMetaData.getDbName());
		showDBType.setText(dbMetaData.getDbType());
		showRules.setText(dbMetaData.getRulesName());
		showHost.setText(dbMetaData.getJDBCUrl());
		showUname.setText(dbMetaData.getUserName());
		showData.setText(dbMetaData.getDataName());
		
	}
	
}
