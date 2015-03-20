package application.database;

import java.util.ArrayList;

/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */

/**
 * @author ramas6
 *
 */
public class Table 
{
	private String name;
	private String schemaType;
	private String ruleset;
	private ArrayList<Column> columns = new ArrayList<Column>();
	private ArrayList<Index> indexes = new ArrayList<Index>();
	private ArrayList<String> triggers = new ArrayList<String>();
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the schemaType
	 */
	public String getSchemaType() {
		return schemaType;
	}
	/**
	 * @param schemaType the schemaType to set
	 */
	public void setSchemaType(String schemaType) {
		this.schemaType = schemaType;
	}
	/**
	 * @return the ruleset
	 */
	public String getRuleset() {
		return ruleset;
	}
	/**
	 * @param ruleset the ruleset to set
	 */
	public void setRuleset(String ruleset) {
		this.ruleset = ruleset;
	}
	/**
	 * @return the columns
	 */
	public ArrayList<Column> getColumns() {
		return columns;
	}
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}
	/**
	 * @return the indexes
	 */
	public ArrayList<Index> getIndexes() {
		return indexes;
	}
	/**
	 * @param indexes the indexes to set
	 */
	public void setIndexes(ArrayList<Index> indexes) {
		this.indexes = indexes;
	}
	/**
	 * @return the triggers
	 */
	public ArrayList<String> getTriggers() {
		return triggers;
	}
	/**
	 * @param triggers the triggers to set
	 */
	public void setTriggers(ArrayList<String> triggers) {
		this.triggers = triggers;
	}

}
