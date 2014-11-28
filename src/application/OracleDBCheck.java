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
public class OracleDBCheck implements DBCheck 
{
	private ArrayList<String> checks = new ArrayList<String>();
	
	/* (non-Javadoc)
	 * @see application.DBCheck#getCheckList()
	 */
	@Override
	public Object[] getCheckList() 
	{
		checks.clear();
		for(Oraclechecks check : Oraclechecks.values()) 
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
	
	private enum Oraclechecks 
	{
		JAVA_ENABLED(Constants.ORACLE_JAVA_ENABLED),
		RESOURCE_CONNECT_PRIVILEGE(Constants.ORACLE_RESOURCE_CONNECT_PRIVILEGE),
		CREATE_VIEW_PROCEDURE_PRIVILEGE(Constants.ORACLE_CREATE_VIEW_PROCEDURE_PRIVILEGE),
		TABLESPACE(Constants.ORACLE_TABLESPACE);
	
		
		private String value;

		Oraclechecks(String value)
		{
			this.value= value;
		}
		
		
		public String getValue() 
		{
			return this.value;
		}
		
	}

}
