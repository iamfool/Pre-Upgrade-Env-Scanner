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

public Object[] VENDORS = {Constants.DB2_UDB,Constants.ORACLE};
/**
 * returns list of checks to be conducted
 * @return
 */
public Object[] getCheckList();

/**
 * runs the checks and returns result of individual checks
 * @return
 *  
 * 
 */
public Object[] executeChecks(DBMetaData metadata);

/**
 * After executing checks, if ALL checks are successful, returns true else false.
 * @return
 */
public boolean getCheckState();


}
