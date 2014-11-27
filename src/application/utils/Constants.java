/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.utils;


/**
 * @author ramas6
 *
 *Holds all constants 
 */

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
	public static final String CONNECTION_ERROR = "Connection Error";
	public static final String CONNECTION_SUCCESS = "Connecion status : Success";
	

	
	/*All CSS references*/
	public static final String APP_CSS = "styles/application.css";
	
	
	
	/*All fxml references*/
	public static final String HOME_FXML= "/application/forms/home.fxml";
	public static final String VALIDATE_FXML= "/application/forms/validate.fxml";
	public static final String LOADER_FXML = "/application/forms/loader.fxml";
	
	
	/*All URLs*/
	public static final String DB2_UDB_URL = "jdbc:db2://localhost:50000/dbName:fullyMaterializeLobData=true;fullyMaterializeInputStreams=true;progressiveStreaming=2;useJDBC4ColumnNameAndLabelSemantics=2;";
	public static final String ORACLE_URL = "jdbc:oracle:thin:@//localhost:1521/dbName";
	public static final String MSSQL_URL = "jdbc:sqlserver://localhost:1433;database=dbName;SelectMethod=cursor;SendStringParametersAsUnicode=false";
	
	/*All check lists*/
	public static final Object[] DB2_LIST = {"Java MUST be enabled","User has SYSADM privilege","user has 32k tablespace",
		"User has CREATE_EXTERNAL_ROUTINE privilege","Log file size atleast 655360 bytes"};
	public static final Object[] ORACLE_LIST = {"Java MUST be enabled","User MUST have Resource and Connect",
		"User MUST have Create View and Create Procedure","User has unlimited tablespace"};
	public static final Object[] MSSQL_LIST = {"CLR enabled in database","User MUST have Public and DB_OWNER",
		"User MUST have create function,ALTER and EXECUTE privileges",};
	 
	 
	 

}
