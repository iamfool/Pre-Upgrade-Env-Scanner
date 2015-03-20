/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.database;

/**
 * @author ramas6
 *
 */
public class Column 
{
	private String type;
	private String name;
 	private String filter;
 	private String size;
 	private String scale;
 	private String required;	
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
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}
	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return the scale
	 */
	public String getScale() {
		return scale;
	}
	/**
	 * @param scale the scale to set
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}
	/**
	 * @return the required
	 */
	public String isRequired() {
		return required;
	}
	/**
	 * @param required the required to set
	 */
	public void setRequired(String required) {
		this.required = required;
	}
	
}
