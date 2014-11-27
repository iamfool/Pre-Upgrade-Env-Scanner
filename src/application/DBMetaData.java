/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

import java.sql.Connection;

/**
 * @author ramas6
 *
 *
 *Wrapper class to hold connection details
 */
public class DBMetaData 
{
 private Connection connection;
 private String errorMessage;
 private boolean isConnSuccess;
 private String dbType;
 private String hostName;
 private String dbName;
 private String userName;
 private String rulesName;
 private String dataName;
 private String JDBCUrl;
 
 /**
 * @return the connection
 */
public Connection getConnection() 
{
	return connection;
}
/**
 * @param connection the connection to set
 */
public void setConnection(Connection connection) 
{
	this.connection = connection;
}
/**
 * @return the errorMessage
 */
public String getErrorMessage() 
{
	return errorMessage;
}
/**
 * @param errorMessage the errorMessage to set
 */
public void setErrorMessage(String errorMessage) 
{
	this.errorMessage = errorMessage;
}
/**
 * @return the isConnSuccess
 */
public boolean isConnSuccess() 
{
	return isConnSuccess;
}
/**
 * @param isConnSuccess the isConnSuccess to set
 */
public void setConnSuccess(boolean isConnSuccess) 
{
	this.isConnSuccess = isConnSuccess;
}
/**
 * @return the dbType
 */
public String getDbType() 
{
	return dbType;
}
/**
 * @param dbType the dbType to set
 */
public void setDbType(String dbType) 
{
	this.dbType = dbType;
}
/**
 * @return the hostName
 */
public String getHostName() 
{
	return hostName;
}
/**
 * @param hostName the hostName to set
 */
public void setHostName(String hostName) 
{
	this.hostName = hostName;
}
/**
 * @return the dbName
 */
public String getDbName() 
{
	return dbName;
}
/**
 * @param dbName the dbName to set
 */
public void setDbName(String dbName) 
{
	this.dbName = dbName;
}
/**
 * @return the userName
 */
public String getUserName() 
{
	return userName;
}
/**
 * @param userName the userName to set
 */
public void setUserName(String userName) 
{
	this.userName = userName;
}
/**
 * @return the rulesName
 */
public String getRulesName() 
{
	return rulesName;
}
/**
 * @param rulesName the rulesName to set
 */
public void setRulesName(String rulesName) 
{
	this.rulesName = rulesName;
}
/**
 * @return the dataName
 */
public String getDataName() {
	return dataName;
}
/**
 * @param dataName the dataName to set
 */
public void setDataName(String dataName) {
	this.dataName = dataName;
}
/**
 * @return the jDBCUrl
 */
public String getJDBCUrl() {
	return JDBCUrl;
}
/**
 * @param jDBCUrl the jDBCUrl to set
 */
public void setJDBCUrl(String jDBCUrl) {
	JDBCUrl = jDBCUrl;
}

}
