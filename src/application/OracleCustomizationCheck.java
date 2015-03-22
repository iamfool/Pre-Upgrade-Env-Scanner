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
		String listSPsQuery = "select OBJECT_NAME from ALL_OBJECTS where  owner = '"+metaData.getDataName().toUpperCase()+"' AND object_type in ( 'PROCEDURE', 'FUNCTION')";
		String listViewsQuery = "select OBJECT_NAME from ALL_OBJECTS where  owner = '"+metaData.getDataName().toUpperCase()+"' AND object_type in ( 'VIEW')";
		String COLUMN_NAME_TABLE = "TABLE_NAME";
		String COLUMN_NAME_OBJNAME = "OBJECT_NAME";
		
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
						tableNameList.add(resultSet.getString(COLUMN_NAME_TABLE));
					}
					
					for(Table table : database.getTables())
					{
						String tableName = table.getName();
						if(!tableNameList.contains(tableName.toUpperCase()))
						{
							count++;
							newTableList.add("Table Name : "+ tableName + " Schema Type : "+ table.getSchemaType());
						}
					}
					newTableList.add(0, "Count = "+ count);
					resultMap.put(CUSTOMIZATIONLISTS.NEW_TABLES, newTableList);
					break;
				case NEW_SPS:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(listSPsQuery);
					ArrayList<String> SPNameList = new ArrayList<String>();
					ArrayList<String> newSPList = new ArrayList<String>();
					int spCount = 0;
					int udfCount = 0;
					while(resultSet.next())
					{
						SPNameList.add(resultSet.getString(COLUMN_NAME_OBJNAME));
					}
					for(String routine : database.getRoutines())
					{
						if(routine.contains("_UDF"))
						{
							routine = routine.substring(0, routine.length()-4);
							if(!SPNameList.contains(routine.toUpperCase()))
							{
								udfCount++;
								newSPList.add(routine + " (udf)");
							}
						}
						else if(!SPNameList.contains(routine.toUpperCase()))
						{
							spCount++;
							newSPList.add(routine);
						}
					}
					newSPList.add(0,"Count: udf- "+ udfCount + " , stored proc- "+ spCount);
					resultMap.put(CUSTOMIZATIONLISTS.NEW_SPS, newSPList);
					break;
				case NEW_VIEWS:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(listViewsQuery);
					ArrayList<String> viewNameList = new ArrayList<String>();
					ArrayList<String> newViewList = new ArrayList<String>();
					int viewCount = 0;
					while(resultSet.next())
					{
						viewNameList.add(resultSet.getString(COLUMN_NAME_OBJNAME));
					}
					for(String view : database.getViews())
					{
						
						if(!viewNameList.contains(view.toUpperCase()))
						{
							viewCount++;
							newViewList.add(view);
						}
					}
					newViewList.add(0,"Count: "+ viewCount);
					resultMap.put(CUSTOMIZATIONLISTS.NEW_VIEWS, newViewList);
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
