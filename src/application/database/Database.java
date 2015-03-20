/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.database;

import java.util.ArrayList;

/**
 * @author ramas6
 *
 */
public class Database 
{
	private ArrayList<Table> tables = new ArrayList<Table>();
	private ArrayList<String> views = new ArrayList<String>();
	private ArrayList<String> routines = new ArrayList<String>();
	/**
	 * @return the tables
	 */
	public ArrayList<Table> getTables() {
		return tables;
	}
	/**
	 * @param tables the tables to set
	 */
	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}
	/**
	 * @return the views
	 */
	public ArrayList<String> getViews() {
		return views;
	}
	/**
	 * @param views the views to set
	 */
	public void setViews(ArrayList<String> views) {
		this.views = views;
	}
	/**
	 * @return the routines
	 */
	public ArrayList<String> getRoutines() {
		return routines;
	}
	/**
	 * @param routines the routines to set
	 */
	public void setRoutines(ArrayList<String> routines) {
		this.routines = routines;
	}
}
