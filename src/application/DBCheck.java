/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

import application.utils.Constants;

/**
 * @author ramas6
 *
 */
public interface DBCheck 
{

public Object[] vendors = {Constants.DB2_UDB,Constants.ORACLE,Constants.MSSQL};
/**
 * returns list of checks to be conducted
 * @return
 */
public Object[] getCheckList();

/**
 * runs the checks and returns result of individual checks
 * @return
 */
public Object[] executeChecks();

/**
 * if ALL checks are successful, returns true else false
 * @return
 */
public boolean getCheckState();


}
