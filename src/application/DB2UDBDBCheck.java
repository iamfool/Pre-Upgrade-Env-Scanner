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
public class DB2UDBDBCheck implements DBCheck {

	private ArrayList<String> checks = new ArrayList<String>();
	private ArrayList<String> executedChecks = new ArrayList<String>(); 
	
	DB2UDBDBCheck()
	{
		for(DB2checks check : DB2checks.values()) 
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
		for(DB2checks check : DB2checks.values())
		{
			check.test(executedChecks, metaData);
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
	
	private enum DB2checks 
	{
		//If new checks are added, new cases MUST be added to handle them, else they will not be computed
		JAVA_ENABLED(Constants.DB2_JAVA_ENABLED),
		SYSADM_PRIVILEGE(Constants.DB2_SYSADM_PRIVILEGE),
		DATAACCESS_PRIVILEGE(Constants.DB2_DATAACCESS_PRIVILEGE),
		TABLESPACE(Constants.DB2_TABLESPACE),
		CREATE_EXTERNAL_ROUTINE_PRIVILEGE(Constants.DB2_CREATE_EXTERNAL_ROUTINE_PRIVILEGE),
		LOGFILESIZE(Constants.DB2_LOGFILESIZE);
	
		
		private String value;
		

		DB2checks(String value)
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
			String authQuery = "SELECT * FROM TABLE (SYSPROC.AUTH_LIST_AUTHORITIES_FOR_AUTHID('"
			+metaData.getUserName().toUpperCase()+"', 'U')) AS T ORDER BY AUTHORITY";
			String dataAccessQuery = "SELECT DATAACCESSAUTH FROM SYSCAT.DBAUTH WHERE GRANTEE='"+metaData.getUserName().toUpperCase()+"' AND GRANTEETYPE = 'U'";
			String createExternalRoutineQuery = "SELECT EXTERNALROUTINEAUTH FROM SYSCAT.DBAUTH WHERE GRANTEE='"
			+metaData.getUserName().toUpperCase()+"' AND GRANTEETYPE = 'U'";
			String tablespaceQuery = "SELECT PAGESIZE,TBSPACE FROM SYSIBM.SYSTABLESPACES WHERE TBSPACE IN (SELECT TBSPACE FROM SYSIBM.SYSTBSPACEAUTH "
					+ "WHERE GRANTEE ='"+metaData.getUserName().toUpperCase()+"')";
			String logFileSizeQuery = "SELECT VALUE FROM SYSIBMADM.DBCFG WHERE NAME IN ('logfilsiz')";
			String authority = "AUTHORITY";
			String sysadm = "SYSADM";
			String d_group = "D_GROUP";
			String y = "Y";
			String dataaccess = "DATAACCESSAUTH";
			String createexternalroutine = "EXTERNALROUTINEAUTH";
			boolean testPassed = false;
			String value = "VALUE";
			String pagesize = "PAGESIZE";
			
			switch(this)
			{
				case JAVA_ENABLED:
					//TODO: Implement the check
					testPassed=true;
					break;
				case SYSADM_PRIVILEGE:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(authQuery);
					while(resultSet.next())
					{
						
						if(resultSet.getString(authority).equals(sysadm))
						{
							if(resultSet.getString(d_group).equals(y))
							{
								testPassed = true;
							}
						}
						
					}
					
					break;
				case DATAACCESS_PRIVILEGE:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(dataAccessQuery);
					while(resultSet.next())
					{
						if(resultSet.getString(dataaccess).equals(y)) 
						{
							testPassed = true;
						}
					}
					
					break;
				case CREATE_EXTERNAL_ROUTINE_PRIVILEGE:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(createExternalRoutineQuery);
					while(resultSet.next())
					{
						if(resultSet.getString(createexternalroutine).equals(y))
						{
							testPassed = true;
						} 
					}
					
					break;
				case TABLESPACE:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(tablespaceQuery);
					while(resultSet.next())
					{
						if(resultSet.getInt(pagesize)==32768)
						{
							testPassed = true;
						} 
					}
					
					break;
				case LOGFILESIZE:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(logFileSizeQuery);
					while(resultSet.next())
					{
						if((Integer.parseInt(resultSet.getString(value))*4 >= 655360))
						{
							testPassed = true;
						} 
					}
					break;
			}
			
			if(testPassed)
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
