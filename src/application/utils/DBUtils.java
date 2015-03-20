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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.DBMetaData;



public final class DBUtils 
{

	public static DBMetaData getConnection (String dbtype, String jdbcurl, String username, String password, String dataSchema)
	{
		DBMetaData dbConnect = new DBMetaData();
		try 
		{
			//establish connection
			Connection  connection = DriverManager.getConnection(jdbcurl,username,password); 
			dbConnect.setConnection(connection);
			dbConnect.setConnSuccess(true);
			
			//set input details
			dbConnect.setDbType(dbtype);
			
			dbConnect.setDataName(dataSchema);
			dbConnect.setUserName(connection.getMetaData().getUserName());
			
			
		} 
		catch (Exception e) 
		{
			dbConnect.setConnection(null);
			dbConnect.setConnSuccess(false);
			dbConnect.setErrorMessage(e.getMessage());
		}
		
		return dbConnect;
	}
	
	public static void closeQueries(Statement stmt,ResultSet resultSet)
	{
		try
		{
			if(stmt != null)
			{
				stmt.close();
			}
			if(resultSet != null)
			{
				resultSet.close();
			}
		}
		catch(Exception e)
		{
			// do nothing
		}
		
	}
	
	public static void closeConnection(Connection connection)
	{
		try 
		{
			connection.close();
		} 
		catch (SQLException e) 
		{
			// do nothing
		}
		
	}
}
