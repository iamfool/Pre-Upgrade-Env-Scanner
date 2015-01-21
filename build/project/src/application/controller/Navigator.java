/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;



import java.io.IOException;

import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * @author ramas6
 *
 */
public class Navigator 
{

	private static LoadController loader;
	
	private static FXMLLoader fxLoader;

	private static HostServices hostServices;

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
        	Node loadedObject = FXMLLoader.load(Navigator.class.getResource(fxmlFile));
        	loader.loadScreen(loadedObject);
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

	/**
	 * @param hostServices
	 */
	public static void setHost(HostServices hostServices) 
	{
		Navigator.hostServices = hostServices;
		
	}
	
	public static HostServices getHost()
	{
		return hostServices;
	}
}
