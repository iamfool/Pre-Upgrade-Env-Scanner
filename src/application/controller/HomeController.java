/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;

/**
 * @author ramas6
 *
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import application.DBCheck;
import application.DBMetaData;
import application.utils.Constants;
import application.utils.DBUtils;
import application.utils.Utilities;




public class HomeController implements Initializable 
{
	// list of fxids
	@SuppressWarnings("rawtypes")
	@FXML private ChoiceBox dbChoice;
	@FXML private Button submitButton; 
	@FXML private AnchorPane homeAnchor;
	@FXML private TextField uname;
	@FXML private PasswordField pwd;
	@FXML private TextField url;
	@FXML private TextField ruleSchema;
	@FXML private TextField dataSchema;
	@FXML private Label errorcode;
	
	 

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// initialize db vendor choice
		dbChoice.setItems(FXCollections.observableArrayList(DBCheck.vendors) );
		dbChoice.setValue(Constants.DB2_UDB);
		url.setText(Constants.DB2_UDB_URL);
		
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
	
	
	 
	@FXML protected void handleSubmitButtonAction(ActionEvent event) throws SQLException, IOException  
	{
	   if (!variablesEmpty())
	   {
			DBMetaData connect = DBUtils.getConnection(dbChoice.getValue().toString(),url.getText(),uname.getText(),pwd.getText(),ruleSchema.getText(),dataSchema.getText());
			if (connect.isConnSuccess())
			{
				connect.getConnection().close();
			}
			else
			{
				errorcode.setText(Constants.CONNECTION_ERROR + connect.getErrorMessage());
				return;
			}
			
			//set details in root controller so it can be accessed everywhere
			LoadController rootControl = Navigator.getFxLoader().getController();
			rootControl.holdMetaData(connect);
			
			 //load validate pane as connection succeeded 
			Navigator.loadScreen(Constants.VALIDATE_FXML);      
	}	
    }

	 
	private boolean variablesEmpty()
	{
		//check for uname,pwd,url filled
				if(Utilities.isEmpty(url.getText()) || Utilities.isEmpty(uname.getText()) || Utilities.isEmpty(pwd.getText()) 
						|| Utilities.isEmpty(ruleSchema.getText()) || Utilities.isEmpty(dataSchema.getText()))
				{
					errorcode.setText(Constants.SUBMIT_ERROR);
					return true;
				}
				return false;
	} 
}
