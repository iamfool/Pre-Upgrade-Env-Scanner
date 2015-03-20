/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import application.SchemaCustomizationCheck.CUSTOMIZATIONLISTS;
import application.database.Database;
import application.database.Table;
import application.utils.DBUtils;

/**
 * @author ramas6
 *
 */
public final class OracleCustomizationCheck 
{

	/**
	 * runs all customization checks and sets the result arraylist in the map
	 * @param custom 
	 * @param metaData
	 * @param database
	 * @param resultMap
	 */
	public static void executeChecks(CUSTOMIZATIONLISTS custom, DBMetaData metaData, Database database,HashMap<CUSTOMIZATIONLISTS, ArrayList<String>> resultMap) 
	{
		Statement stmt = null;
		ResultSet resultSet = null;
		// find all new tables
		String listTablesQuery = "select table_name from all_tables where owner='"+metaData.getDataName().toUpperCase()+"'";
		String TABLE_NAME = 	"TABLE_NAME";
		try
		{
			switch(custom)
			{
				case CUSTOMIZED_COLUMNS:
					break;
				case ALTERED_TABLES:
					break;
				case NEW_TABLES:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(listTablesQuery);
					ArrayList<String> tableNameList = new ArrayList<String>();
					ArrayList<String> newTableList = new ArrayList<String>();
					int count = 0;
					while(resultSet.next())
					{
						tableNameList.add(resultSet.getString(TABLE_NAME));
					}
					
					for(Table table : database.getTables())
					{
						String tableName = table.getName();
						if(!tableNameList.contains(tableName.toUpperCase()))
						{
							count++;
							newTableList.add(tableName);
						}
					}
					newTableList.add(0, "*** new table count = "+ count + " ***");
					resultMap.put(CUSTOMIZATIONLISTS.NEW_TABLES, newTableList);
					break;
				case NEW_SPS:
					break;
				case NEW_VIEWS:
					break;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		DBUtils.closeQueries(stmt, resultSet);
		
	}
	
}
