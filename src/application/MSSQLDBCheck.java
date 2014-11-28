/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

import java.util.ArrayList;



import application.utils.Constants;

/**
 * @author ramas6
 *
 */
public class MSSQLDBCheck implements DBCheck 
{
	private ArrayList<String> checks = new ArrayList<String>();
	
	/* (non-Javadoc)
	 * @see application.DBCheck#getCheckList()
	 */
	@Override
	public Object[] getCheckList() 
	{
		checks.clear();
		for(MSSQLchecks check : MSSQLchecks.values()) 
		{
			checks.add(check.getValue());
		}
		return checks.toArray();
	}

	/* (non-Javadoc)
	 * @see application.DBCheck#executeChecks()
	 */
	@Override
	public Object[] executeChecks() 
	{
		// TODO Auto-generated method stub
		return null;
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
	
	private enum MSSQLchecks 
	{
		CLR_ENABLED(Constants.MSSQL_CLR_ENABLED),
		PUBLIC_DBOWNER_PRIVILEGE(Constants.MSSQL_PUBLIC_DBOWNER_PRIVILEGE),
		CREATE_FUNCTION_PRIVILEGE(Constants.MSSQL_CREATE_FUNCTION_PRIVILEGE),
		ALTER_EXECUTE_PRIVILEGE(Constants.MSSQL_ALTER_EXECUTE_PRIVILEGE);
	
		
		private String value;

		MSSQLchecks(String value)
		{
			this.value= value;
		}
		

		public String getValue() 
		{
			return this.value;
		}
		
	}

}
