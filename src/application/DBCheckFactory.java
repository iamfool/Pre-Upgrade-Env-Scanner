/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

import application.utils.Constants;

/**
 * @author ramas6
 *
 */
public final class DBCheckFactory 
{

	public static DBCheck getVendorCheck(String vendor)
	{
		if(Constants.DB2_UDB.equals(vendor))
		{
			return new DB2UDBDBCheck();
		}
		else if(Constants.ORACLE.equals(vendor)) 
		{
			return new OracleDBCheck();
		}
		else if(Constants.MSSQL.equals(vendor)) 
		{
			return new MSSQLDBCheck();
		}
		
		return null;
	}
}
