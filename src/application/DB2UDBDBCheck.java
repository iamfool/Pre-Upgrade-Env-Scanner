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
public class DB2UDBDBCheck implements DBCheck {

	private ArrayList<String> checks = new ArrayList<String>();
	/* (non-Javadoc)
	 * @see application.DBCheck#getCheckList()
	 */
	@Override
	public Object[] getCheckList() 
	{
		checks.clear();
		for(DB2checks check : DB2checks.values()) 
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
	
	private enum DB2checks 
	{
		JAVA_ENABLED(Constants.DB2_JAVA_ENABLED),
		SYSADM_PRIVILEGE(Constants.DB2_SYSADM_PRIVILEGE),
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
		
	}

}
