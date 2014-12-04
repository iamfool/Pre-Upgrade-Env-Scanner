/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import application.DBCheck;
import application.DBCheckFactory;
import application.DBMetaData;
import application.utils.Constants;

/**
 * @author ramas6
 *
 */
@SuppressWarnings("rawtypes")
public class ValidateController implements Initializable 
{
   @FXML Button backButton;
   @FXML Label showDB;
   @FXML Label showDBType;
   @FXML Label showRules;
   @FXML Label showHost;
   @FXML Label showUname;
   @FXML Label showData;
   @FXML ListView checklistview;
   @FXML Button checkButton;
   private DBCheck check;
   private ObservableList checkList = FXCollections.observableArrayList();
   private LoadController rootControl;
   
   
   
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		rootControl = Navigator.getFxLoader().getController();
		
		if(rootControl.fetchMetaData() != null) 
		{
			renderMetaData(rootControl.fetchMetaData());
			renderCheckList(rootControl.fetchMetaData().getDbType());
		}
		
	}
	
	
	@FXML protected void handleBackButtonAction(ActionEvent event) throws SQLException, IOException  
	{
		rootControl.fetchMetaData().getConnection().close();
		Navigator.loadScreen(Constants.HOME_FXML);
	}

	private void renderMetaData(DBMetaData dbMetaData) 
	{
		showDBType.setText(dbMetaData.getDbType());
		showRules.setText(dbMetaData.getRulesName());
		showUname.setText(dbMetaData.getUserName());
		showData.setText(dbMetaData.getDataName());
		
	}
	
	@SuppressWarnings("unchecked")
	private void renderCheckList (String dbType) 
	{
		check = DBCheckFactory.getVendorCheck(dbType);
		checkList.addAll(check.getCheckList());
		checklistview.setItems(checkList);
	}
	
	@SuppressWarnings("unchecked")
	@FXML protected void handleCheckButtonAction(ActionEvent event) throws SQLException   
	{
		if(check != null)
		{
			checkButton.setText("checking...");
			checkButton.setDisable(true);
			
			//remove old list if first check
			if(checkList.size()>0)
				{
				 	checkList.removeAll(check.getCheckList());
					checklistview.setItems(checkList);
				}
			ObservableList checkedList = FXCollections.observableArrayList();
			
			//execute all checks and set result to listview
			checkedList.addAll(check.executeChecks(rootControl.fetchMetaData()));
			checklistview.setItems(checkedList);
			
			checkButton.setDisable(false);
			checkButton.setText("Re-Check");
			
			
		}
		else
		{
			return;
		}
	}
}
