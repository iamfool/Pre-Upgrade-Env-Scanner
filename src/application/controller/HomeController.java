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
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import application.utils.Constants;
import application.utils.Utilities;


public class HomeController implements Initializable 
{
	// list of fxids
	@SuppressWarnings("rawtypes")
	@FXML private ChoiceBox dbChoice;
	@FXML private Button submitButton; 
	@FXML private AnchorPane homeAnchor;
	@FXML private TextField uname;
	@FXML private TextField pwd;
	@FXML private TextField url;
	@FXML private TextField ruleSchema;
	@FXML private TextField dataSchema;
	@FXML private Label errorcode;
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// initialize db vendor choice
		dbChoice.setItems(FXCollections.observableArrayList(Constants.DB2_UDB,Constants.ORACLE,Constants.MSSQL) );
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
	
	
	 
	@FXML protected void handleSubmitButtonAction(ActionEvent event) 
	{
        
		if (!variablesEmpty())
		{
			try {
				Class.forName("COM.ibm.db2.jcc.DB2Driver");
				Connection  connection = 
		                DriverManager.getConnection(url.getText(),uname.getText(),pwd.getText()); 
		      
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				
				e.printStackTrace();
			} 
	        
			//errorcode.setText( "From DAO, connection obtained " );
			
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
