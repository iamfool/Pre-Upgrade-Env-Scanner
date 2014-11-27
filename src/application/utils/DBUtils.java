/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.utils;

/**
 * @author ramas6
 * 
 * DB related methods
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import application.DBMetaData;



public final class DBUtils 
{

	public static DBMetaData getConnection (String jdbcurl, String username, String password, String ruleSchema, String dataSchema)
	{
		DBMetaData dbConnect = new DBMetaData();
		try 
		{
			//establish connection
			Connection  connection = DriverManager.getConnection(jdbcurl,username,password); 
			dbConnect.setConnection(connection);
			dbConnect.setJDBCUrl(jdbcurl);
			dbConnect.setRulesName(ruleSchema);
			dbConnect.setDataName(dataSchema);
			fillDBMetaData(dbConnect,connection);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			dbConnect.setConnection(null);
			dbConnect.setConnSuccess(false);
			dbConnect.setErrorMessage(e.getMessage());
		}
		
		return dbConnect;
	}
	
	private static void fillDBMetaData (DBMetaData dbConnect, Connection connection) throws SQLException 
	{
		dbConnect.setConnSuccess(true);
		dbConnect.setDbType(connection.getMetaData().getDatabaseProductName());
		dbConnect.setUserName(connection.getMetaData().getUserName());
		
		
	}
}
