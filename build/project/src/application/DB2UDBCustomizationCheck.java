/**
 *  � 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import application.SchemaCustomizationCheck.CUSTOMIZATIONLISTS;
import application.database.Column;
import application.database.Database;
import application.database.Index;
import application.database.Table;
import application.utils.DBUtils;
import application.utils.Utilities;

/**
 * @author ramas6
 *
 */
public final class DB2UDBCustomizationCheck 
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
		//TODO: move strings to constants
		Statement stmt = null;
		Statement udfStmt = null;
		ResultSet resultSet = null;
		ResultSet udfResultSet = null;
		// find all new tables
		String listTablesQuery = "select TABNAME from syscat.tables where tabschema='"+metaData.getDataName().toUpperCase()+"'";
		String listSPsQuery = "select procname from syscat.procedures where procschema='"+metaData.getDataName().toUpperCase()+"'";
		String listUDFsQuery = "select funcname from syscat.functions where funcschema= '"+metaData.getDataName().toUpperCase()+"'";
		String listViewsQuery = "select viewname from syscat.views where  viewschema= '"+metaData.getDataName().toUpperCase()+"'";
		String COLUMN_NAME_TABLE = "TABNAME";
		String COLUMN_NAME_SPNAME = "PROCNAME";
		String COLUMN_NAME_FUNCNAME = "FUNCNAME";
		String COLUMN_NAME_VIEWNAME = "VIEWNAME";
		
		try
		{
			switch(custom)
			{
				case ALTERED_TABLES:
				case NEW_TABLES:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(listTablesQuery);
					ArrayList<String> tableNameList = new ArrayList<String>();
					ArrayList<String> newTableList = new ArrayList<String>();
					ArrayList<String> alterList = new ArrayList<String>();
					int count = 0;
					while(resultSet.next())
					{
						tableNameList.add(resultSet.getString(COLUMN_NAME_TABLE));
					}
					
					for(Table table : database.getTables())
					{
						String tableName = table.getName();
						if(tableNameList.contains(tableName.toUpperCase()))
						{
							String tableColumnQuery = "select COLNAME,LENGTH, NULLS,TYPENAME,SCALE from syscat.columns where tabschema='"+metaData.getDataName().toUpperCase()+"' and tabname='"+tableName.toUpperCase()+"'";
							String tableIndexQuery = "select INDNAME from syscat.indexes where tabschema='"+metaData.getDataName().toUpperCase()+"' and tabname='"+tableName.toUpperCase()+"'";
							String tableTriggerQuery = "select TRIGNAME from syscat.triggers where tabschema='"+metaData.getDataName().toUpperCase()+"' and tabname='"+tableName.toUpperCase()+"'";
							Statement tableColumnStmt = metaData.getConnection().createStatement();
							Statement tableIndexStmt =  metaData.getConnection().createStatement();
							Statement tableTriggerStmt = metaData.getConnection().createStatement();
							ResultSet tableColumnResultSet = tableColumnStmt.executeQuery(tableColumnQuery);
							ResultSet tableIndexResultSet = tableIndexStmt.executeQuery(tableIndexQuery);
							ResultSet tableTrgrResultSet = tableTriggerStmt.executeQuery(tableTriggerQuery);
							ArrayList<Column> dbColumns = new ArrayList<Column>();
							ArrayList<String> dbIndexes = new ArrayList<String>();
							ArrayList<String> dbTriggers = new ArrayList<String>();
							while(tableColumnResultSet.next())
							{
								Column column = new Column();
								column.setName(tableColumnResultSet.getString("COLNAME"));
								column.setRequired(tableColumnResultSet.getString("NULLS"));
								column.setType(tableColumnResultSet.getString("TYPENAME"));
								column.setSize(tableColumnResultSet.getString("LENGTH"));
								column.setScale(tableColumnResultSet.getString("SCALE"));
								dbColumns.add(column);
							}
							while(tableIndexResultSet.next())
							{
								dbIndexes.add(tableIndexResultSet.getString("INDNAME"));
							}
							while(tableTrgrResultSet.next())
							{
								dbTriggers.add(tableTrgrResultSet.getString("TRIGNAME"));
							}
							for(Column column: table.getColumns())
							{
								boolean columnFound = false;
								for(Column dbcolumn : dbColumns)
								{
									// if column exists in db check for other params. alter if size/type didnt match (TODO: nullable)
									if(dbcolumn.getName().equalsIgnoreCase(column.getName()))
									{
										columnFound =true;
										boolean sizeMatch=true;
										if(!Utilities.isEmpty(column.getSize()) && !Utilities.isEmpty(dbcolumn.getSize())) sizeMatch = dbcolumn.getSize().equals(column.getSize());
										if(!sizeMatch)
										{
											alterList.add("Alter Table :"+tableName+ " for size mismatch in column : "+ column.getName());
											break;
										}
										if(!Utilities.isEmpty(dbcolumn.getType())  && !dbcolumn.getType().equalsIgnoreCase(column.getType()))
										{
											alterList.add("Alter Table :"+tableName+ " for change in column type : "+ column.getName() + " to type : "+ column.getType());
											break;
										}
									}
									
								}
								if(!column.getFilter().equals("MT") && !columnFound)
								{
									alterList.add("Alter Table :"+tableName+ " for new column : "+ column.getName());
								}
							}
							for(Index index : table.getIndexes())
							{
								if(!dbIndexes.contains(index.getName().toUpperCase()))
								{
									alterList.add("Alter Table :"+tableName+ " for new index : "+ index.getName());
								}
							}
							for(String trigger : table.getTriggers())
							{
								if(!dbTriggers.contains(trigger.toUpperCase()))
								{
									alterList.add("Alter Table :"+tableName+ " for new trigger : "+ trigger);
								}
							}
							
							DBUtils.closeQueries(tableColumnStmt, tableColumnResultSet);
							DBUtils.closeQueries(tableIndexStmt, tableIndexResultSet);
							DBUtils.closeQueries(tableTriggerStmt, tableTrgrResultSet);
						}
						else
						{
							count++;
							newTableList.add("Table Name : "+ tableName + " Schema Type : "+ table.getSchemaType());
						}
					}
					newTableList.add(0, "Count = "+ count);
					resultMap.put(CUSTOMIZATIONLISTS.NEW_TABLES, newTableList);
					resultMap.put(CUSTOMIZATIONLISTS.ALTERED_TABLES, alterList);
					break;
				case NEW_SPS:
					stmt = metaData.getConnection().createStatement();
					resultSet = stmt.executeQuery(listSPsQuery);
					udfStmt = metaData.getConnection().createStatement();
					udfResultSet = udfStmt.executeQuery(listUDFsQuery);
					ArrayList<String> SPNameList = new ArrayList<String>();
					ArrayList<String> funcNameList = new ArrayList<String>();
					ArrayList<String> newSPList = new ArrayList<String>();
					int spCount = 0;
					int udfCount = 0;
					while(resultSet.next())
					{
						SPNameList.add(resultSet.getString(COLUMN_NAME_SPNAME));
					}
					while(udfResultSet.next())
					{
						funcNameList.add(udfResultSet.getString(COLUMN_NAME_FUNCNAME));
					}
					for(String routine : database.getRoutines())
					{
						if(routine.contains("_UDF"))
						{
							routine = routine.substring(0, routine.length()-4);
							if(!funcNameList.contains(routine.toUpperCase()))
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
						viewNameList.add(resultSet.getString(COLUMN_NAME_VIEWNAME));
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
					DBUtils.closeQueries(udfStmt, udfResultSet);
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