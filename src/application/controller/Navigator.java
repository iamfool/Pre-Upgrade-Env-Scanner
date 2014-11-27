/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;



import java.io.IOException;

import javafx.fxml.FXMLLoader;

/**
 * @author ramas6
 *
 */
public class Navigator 
{

	private static LoadController loader;
	
	private static FXMLLoader fxLoader;

	/**
	 * @return the loader
	 */
	public static LoadController getLoader() 
	{
		return loader;
	}

	/**
	 * @param loader the loader to set
	 */
	public static void setLoader(LoadController loader) 
	{
		Navigator.loader = loader;
	}
	
	public static void loadScreen(String fxmlFile) 
	{
        try 
        {
        	loader.loadScreen(FXMLLoader.load(Navigator.class.getResource(fxmlFile)));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
      
	}

	/**
	 * @return the fxLoader
	 */
	public static FXMLLoader getFxLoader() 
	{
		return fxLoader;
	}

	/**
	 * @param fxLoader the fxLoader to set
	 */
	public static void setFxLoader(FXMLLoader fxLoader) 
	{
		Navigator.fxLoader = fxLoader;
	}
}
