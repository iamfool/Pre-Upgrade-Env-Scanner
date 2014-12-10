/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;

/**
 * @author ramas6
 *
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import application.AppServerCheck;
import application.DBCheck;
import application.DBMetaData;
import application.OSCheck;
import application.utils.Constants;
import application.utils.DBUtils;
import application.utils.Utilities;



@SuppressWarnings("rawtypes")
public class HomeController implements Initializable 
{
	// list of fxids
	
	@FXML private ChoiceBox oschoice;
	@FXML private ChoiceBox appchoice;
	@FXML private ChoiceBox dbChoice;
	@FXML private Button submitButton; 
	@FXML private AnchorPane homeAnchor;
	@FXML private TextField uname;
	@FXML private PasswordField pwd;
	@FXML private TextField url;
	@FXML private TextField ruleSchema;
	@FXML private TextField dataSchema;
	@FXML private Label errorcode;
	@FXML private TextArea errortext;
	
	 

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// initialize OS vendor choice
		oschoice.setItems(FXCollections.observableArrayList(OSCheck.VERSIONS.getAllOS()));
		oschoice.setValue(OSCheck.VERSIONS.AIX53.getReadableValue());
		
		//initialize app server choice
		appchoice.setItems(FXCollections.observableArrayList(AppServerCheck.APPVERSIONS.getAllAppServers()));
		appchoice.setValue(AppServerCheck.APPVERSIONS.WAS61.getReadableValue());
		
		// initialize db vendor choice
		dbChoice.setItems(FXCollections.observableArrayList(DBCheck.VENDORS));
		dbChoice.setValue(Constants.DB2_UDB);
		url.setText(Constants.DB2_UDB_URL);
		
		errortext.setVisible(false);
		
		// add listener to set url
		dbChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() 
		{

			@Override
			public void changed(ObservableValue<? extends Number> observable,Number oldValue, Number newValue) 
			{
				//setting jdbc url acc to vendor
				switch (newValue.intValue())
				{
					case 0:
						url.setText(Constants.DB2_UDB_URL);
						break;
					case 1:
						url.setText(Constants.ORACLE_URL);
						break;
					case 2:
						url.setText(Constants.MSSQL_URL);
						break;
					default:
						url.setText(Constants.EMPTY);
				}
			}
		});
	}
	
	
	 
	@FXML protected void handleSubmitButtonAction(ActionEvent event)   
	{
	   if (!variablesEmpty())
	   {
			DBMetaData connect = DBUtils.getConnection(dbChoice.getValue().toString(),url.getText(),uname.getText(),pwd.getText(),ruleSchema.getText(),dataSchema.getText());
			if (!connect.isConnSuccess())
			{
				errortext.setText(Constants.CONNECTION_ERROR + connect.getErrorMessage());
				errortext.setVisible(true);
				return;
			}
			
			
			//set details in root controller so it can be accessed everywhere
			LoadController rootControl = Navigator.getFxLoader().getController();
			rootControl.holdMetaData(oschoice.getValue().toString(),appchoice.getValue().toString(),connect);
			
			 //load validate pane as connection succeeded 
			Navigator.loadScreen(Constants.VALIDATE_FXML);      
	}	
    }

	 
	private boolean variablesEmpty()
	{
		//check for uname,pwd,url filled
				if(Utilities.isEmpty(url.getText()) || Utilities.isEmpty(uname.getText()) || Utilities.isEmpty(pwd.getText()))
				{
					errorcode.setText(Constants.SUBMIT_ERROR);
					errortext.setVisible(false);
					return true;
				}
				return false;
	} 
}
