/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.database;

import java.util.ArrayList;

/**
 * @author ramas6
 *
 */
public class Index 
{
	private String name;
	private String ruleset;
	private String type;
	private String primarykey;
	private String unique;
	private ArrayList<IndexColumn> indexColumn = new ArrayList<IndexColumn>();
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the primarykey
	 */
	public String isPrimarykey() {
		return primarykey;
	}
	/**
	 * @param primarykey the primarykey to set
	 */
	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}
	/**
	 * @return the unique
	 */
	public String isUnique() {
		return unique;
	}
	/**
	 * @param unique the unique to set
	 */
	public void setUnique(String unique) {
		this.unique = unique;
	}
	/**
	 * @return the indexColumn
	 */
	public ArrayList<IndexColumn> getIndexColumn() {
		return indexColumn;
	}
	/**
	 * @param indexColumn the indexColumn to set
	 */
	public void setIndexColumn(ArrayList<IndexColumn> indexColumn) {
		this.indexColumn = indexColumn;
	}
}

