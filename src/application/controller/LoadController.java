/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import application.DBMetaData;

/**
 * @author ramas6
 *
 */
public class LoadController {

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	
	private DBMetaData dbData;
	@FXML StackPane loader;
	
	
	
	public void loadScreen(Node node) 
	{
		loader.getChildren().setAll(node);
    }
	
	public void setMetaData(DBMetaData dbData) 
	{
		this.dbData = dbData;
	}
    
	public DBMetaData getMetaData() 
	{
		return this.dbData;
	}
}
