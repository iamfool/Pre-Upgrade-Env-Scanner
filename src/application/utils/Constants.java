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
	public static final String TEST_SUCCESS = "Success: ";
	public static final String TEST_FAILURE = "Failed: ";
	

	
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
	
	
	/*All checklist related constants*/
	public static final String DB2_JAVA_ENABLED = "Java MUST be enabled";
	public static final String DB2_SYSADM_PRIVILEGE ="User has SYSADM privilege";
	public static final String DB2_DATAACCESS_PRIVILEGE ="User has DATAACCESS privilege";
	public static final String DB2_TABLESPACE ="User has been granted atleast one 32K tablespace";
	public static final String DB2_CREATE_EXTERNAL_ROUTINE_PRIVILEGE ="User has CREATE_EXTERNAL_ROUTINE privilege";
	public static final String DB2_LOGFILESIZE ="Log file size is set to atleast 655,360 KB";
	
	public static final String MSSQL_CLR_ENABLED ="CLR enabled in database";
	public static final String MSSQL_PUBLIC_DBOWNER_PRIVILEGE ="User MUST have Public and DB_OWNER";
	public static final String MSSQL_CREATE_FUNCTION_PRIVILEGE ="User MUST have create function";
	public static final String MSSQL_ALTER_EXECUTE_PRIVILEGE ="ALTER and EXECUTE privileges";
	
	public static final String ORACLE_JAVA_ENABLED ="Java MUST be enabled";
	public static final String ORACLE_RESOURCE_CONNECT_PRIVILEGE ="User MUST have Resource and Connect";
	public static final String ORACLE_CREATE_VIEW_PROCEDURE_PRIVILEGE ="User MUST have Create View and Create Procedure";
	public static final String ORACLE_TABLESPACE ="User has unlimited tablespace";
	
}
