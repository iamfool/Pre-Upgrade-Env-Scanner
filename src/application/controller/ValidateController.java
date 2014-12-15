/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import application.AppServerCheck;
import application.AppServerCheck.APPVERSIONS;
import application.DBCheck;
import application.DBCheckFactory;
import application.OSCheck;
import application.OSCheck.VERSIONS;
import application.utils.Constants;

/**
 * @author ramas6
 *
 */
@SuppressWarnings("rawtypes")
public class ValidateController implements Initializable 
{
   @FXML Button backButton;
  
   @FXML ListView checklistview;
   @FXML Button checkButton;
   @FXML Hyperlink pdnlink;
   @FXML TextArea errortext;
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
			renderCheckList(rootControl.fetchMetaData().getDbType());
		}
		
		errortext.setVisible(false);
	}
	
	
	@FXML protected void handleBackButtonAction(ActionEvent event)   
	{
		try
		{
			rootControl.fetchMetaData().getConnection().close();
			Navigator.loadScreen(Constants.HOME_FXML);
		}
		catch(Exception e)
		{
			errortext.setVisible(true);
			errortext.setText(e.getMessage());
		}
		
	}

	
	
	@SuppressWarnings("unchecked")
	private void renderCheckList (String dbType) 
	{
		// add supported OS and APP server checks
		checkList.add(Constants.OS_CHECK + " - "+ rootControl.getOsChoice());
		checkList.add(Constants.APP_SERVER_CHECK + " - "+ rootControl.getAppServerChoice());
		
		// add DB specific checks
		check = DBCheckFactory.getVendorCheck(dbType);
		checkList.addAll(check.getCheckList());
		checklistview.setItems(checkList);
	}
	
	@SuppressWarnings("unchecked")
	@FXML protected void handleCheckButtonAction(ActionEvent event)   
	{
		if(check != null)
		{
			checkButton.setText("checking...");
			checkButton.setDisable(true);

			// clear checklist
			if(checkList.size()>0)
				{
				 	checkList.removeAll(check.getCheckList());
					checklistview.setItems(checkList);
				}
			ObservableList checkedList = FXCollections.observableArrayList();
			
			//execute OS and APP server checks
			checkandSetOSandAPPServer(checkedList);
			
			//execute DB checks and set result to listview
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


	/**
	 * @param checkedList 
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void checkandSetOSandAPPServer(ObservableList checkedList) 
	{
		VERSIONS OSVer = OSCheck.VERSIONS.getVersionForValue(rootControl.getOsChoice());
		if(OSVer.isSupported())
		{
			checkedList.add(Constants.TEST_SUCCESS+Constants.OS_CHECK + " - "+ rootControl.getOsChoice());
		}
		else
		{
			checkedList.add(Constants.TEST_FAILURE+Constants.OS_CHECK + " - "+ rootControl.getOsChoice());
		}
		
		APPVERSIONS appVer = AppServerCheck.APPVERSIONS.getVersionForValue(rootControl.getAppServerChoice());
		if(appVer.isSupported())
		{
			checkedList.add(Constants.TEST_SUCCESS+Constants.APP_SERVER_CHECK + " - "+ rootControl.getAppServerChoice());
		}
		else
		{
			checkedList.add(Constants.TEST_FAILURE+Constants.APP_SERVER_CHECK + " - "+ rootControl.getAppServerChoice());
		}
		
	}
	
	@FXML protected void handleLinkAction(ActionEvent event)
	{
		Navigator.getHost().showDocument(Constants.PDN_PLATFORM_LINK);
	} 
}
