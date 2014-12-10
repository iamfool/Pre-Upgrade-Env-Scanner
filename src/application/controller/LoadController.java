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
	private String osChoice;
	private String appChoice;
	@FXML StackPane loader;
	
	
	
	public void loadScreen(Node node) 
	{
		loader.getChildren().setAll(node);
    }
	
	public void holdMetaData(String osChoice, String appChoice, DBMetaData dbData) 
	{
		this.dbData = dbData;
		this.osChoice = osChoice;
		this.appChoice = appChoice;
	}
    
	public DBMetaData fetchMetaData() 
	{
		return this.dbData;
	}
	
	public String getOsChoice()
	{
		return this.osChoice;
	}
	
	public String getAppServerChoice()
	{
		return this.appChoice;
	}
}
