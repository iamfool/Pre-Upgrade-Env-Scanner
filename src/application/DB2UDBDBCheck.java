/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

/**
 * @author ramas6
 *
 */
public class DB2UDBDBCheck implements DBCheck {

	private static final Object[] DB2_LIST = {"Java MUST be enabled","User has SYSADM privilege","user has 32k tablespace",
		"User has CREATE_EXTERNAL_ROUTINE privilege","Log file size atleast 655360 bytes"};
	/* (non-Javadoc)
	 * @see application.DBCheck#getCheckList()
	 */
	@Override
	public Object[] getCheckList() 
	{
		return DB2_LIST;
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

}
