/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.utils;

/**
 * @author ramas6
 *
 */
public final class Utilities 
{

	
	/**
	 * checks if given string is empty. Returns FALSE if NOT EMPTY
	 * @param input
	 * @return
	 */
	public static boolean isEmpty (String input)
	{
		if(input != null && !input.trim().equals("")) return false;
		return true;
		
	}

}
