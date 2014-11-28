/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

/**
 * @author ramas6
 *
 */
public class MSSQLDBCheck implements DBCheck 
{

	private static final Object[] MSSQL_LIST = {"CLR enabled in database","User MUST have Public and DB_OWNER",
		"User MUST have create function,ALTER and EXECUTE privileges",};
	 
	/* (non-Javadoc)
	 * @see application.DBCheck#getCheckList()
	 */
	@Override
	public Object[] getCheckList() 
	{
		return MSSQL_LIST;
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
