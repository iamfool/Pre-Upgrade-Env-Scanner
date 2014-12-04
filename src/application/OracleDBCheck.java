/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.utils.Constants;

/**
 * @author ramas6
 *
 */
public class OracleDBCheck implements DBCheck 
{
	private ArrayList<String> checks = new ArrayList<String>();
	private ArrayList<String> executedChecks = new ArrayList<String>(); 
	
	OracleDBCheck()
	{
		for(Oraclechecks check : Oraclechecks.values()) 
		{
			checks.add(check.getValue());
		}
	}
	
	/* (non-Javadoc)
	 * @see application.DBCheck#getCheckList()
	 */
	@Override
	public Object[] getCheckList() 
	{
		return checks.toArray();
	}

	/* (non-Javadoc)
	 * @see application.DBCheck#executeChecks()
	 */
	@Override
	public Object[] executeChecks(DBMetaData metaData) throws SQLException 
	{
		executedChecks.clear();
		for(Oraclechecks check : Oraclechecks.values()) 
		{
			check.test(executedChecks,metaData);
		}
		
		return executedChecks.toArray();
	}

	/* (non-Javadoc)
	 * @see application.DBCheck#getCheckState()
	 */
	@Override
	public boolean getCheckState() 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	private enum Oraclechecks 
	{
		//If new checks are added, new cases MUST be added to handle them, else they will not be computed
		JAVA_ENABLED(Constants.ORACLE_JAVA_ENABLED),
		RESOURCE_CONNECT_PRIVILEGE(Constants.ORACLE_RESOURCE_CONNECT_PRIVILEGE),
		CREATE_VIEW_PROCEDURE_PRIVILEGE(Constants.ORACLE_CREATE_VIEW_PROCEDURE_PRIVILEGE),
		TABLESPACE(Constants.ORACLE_TABLESPACE);
	
		
		private String value;
		private String javaEnabledQuery = "select * from all_registry_banners";
		private String resourceConnectQuery = "SELECT * FROM USER_ROLE_PRIVS";
		private String createViewQuery = "SELECT * FROM USER_SYS_PRIVS";

		Oraclechecks(String value)
		{
			this.value= value;
		}
		
		
		public String getValue() 
		{
			return this.value;
		}
		
		public void test(ArrayList<String> executedChecks,DBMetaData metaData) throws SQLException
		{
			Statement stmt;
			ResultSet resultSet;
			boolean testPassed = false;
			boolean connectPassed = false;
			boolean resourcePassed = false;
			String grant = "GRANTED_ROLE";
			String connect = "CONNECT";
			String resource = "RESOURCE";
			String privilege = "PRIVILEGE";
			String createview = "CREATE VIEW";
			String banner = "BANNER";
			String jserver = "JServer";
			
			switch(this)
			{
				case JAVA_ENABLED:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(javaEnabledQuery);
					while(resultSet.next())
					{
						if(resultSet.getString(banner).startsWith(jserver)) testPassed = true;
					}
					
					break;
				case RESOURCE_CONNECT_PRIVILEGE:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(resourceConnectQuery);
					while(resultSet.next())
					{
						if(resultSet.getString(grant).equals(connect)) connectPassed = true;
						if(resultSet.getString(grant).equals(resource)) resourcePassed = true;
					}
					
					break;
				case CREATE_VIEW_PROCEDURE_PRIVILEGE:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(createViewQuery);
					while(resultSet.next())
					{
						if(resultSet.getString(privilege).equals(createview)) testPassed = true;
					}
					
					break;
				case TABLESPACE:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(resourceConnectQuery);
					while(resultSet.next())
					{
						if(resultSet.getString(grant).equals(resource)) testPassed = true;
					}
					
					break;
			}
			if(testPassed || (connectPassed && resourcePassed))
			{
				executedChecks.add(Constants.TEST_SUCCESS+this.value);
			}
			else 
			{
				executedChecks.add(Constants.TEST_FAILURE+this.value);
			}
			
			
		}
		
	}

}
