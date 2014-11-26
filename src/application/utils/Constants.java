/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.utils;

public final class Constants 
{
	/*All string constants*/
	public static final String APP_TITLE="ECS Pre Upgrade Environment Scanner";
	public static final String DB2_UDB = "DB2-udb";
	public static final String ORACLE = "Oracle";
	public static final String MSSQL = "MS SQL Server";
	public static final String DB2_ZOS = "DB2-ZOS";
	public static final String POSTGRE = "Postgre SQL";
	public static final String EMPTY = "";
	public static final String SUBMIT_ERROR = "Connection failed - One or more values are empty";
	

	
	/*All CSS references*/
	public static final String APP_CSS = "styles/application.css";
	
	
	
	/*All fxml references*/
	public static final String HOME_FXML= "forms/home.fxml";
	
	
	/*All URLs*/
	public static final String DB2_UDB_URL = "jdbc:db2://<server_name>:<port_name>/<database_name>";
	public static final String ORACLE_URL = "jdbc:oracle:thin:@<server>[:<1521>]:<database_name>";
	public static final String MSSQL_URL = "jdbc:sqlserver://<server_name>:1433;databaseName=<database_name>";
	
	


}
