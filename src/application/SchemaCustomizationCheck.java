/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;



import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import application.database.Column;
import application.database.Database;
import application.database.Index;
import application.database.IndexColumn;
import application.database.Table;
import application.utils.Constants;


/**
 * parent class for checking ruleset customizations
 * 
 * @author ramas6
 *
 */
public final class SchemaCustomizationCheck 
{
	private static ArrayList<String> checks = new ArrayList<String>();
	
	
	public static Object[] getCheckList()
	{
		if(checks.isEmpty())
		{
			for(CUSTOMIZATIONLISTS cust : CUSTOMIZATIONLISTS.values())
			{
				checks.add(cust.getValue());
			}
		}
		
		return checks.toArray();	
	}
	
	
	public static HashMap<CUSTOMIZATIONLISTS,ArrayList<String>> executeChecks(DBMetaData metaData, String folderPath)
	{
		HashMap<CUSTOMIZATIONLISTS,ArrayList<String>> resultMap = new HashMap<CUSTOMIZATIONLISTS,ArrayList<String>>();
		Database database = createDBObjectFromXML(folderPath);
		//trigger db specific checks
		if(metaData.getDbType().equals(Constants.DB2_UDB))
		{
			for(CUSTOMIZATIONLISTS custom : CUSTOMIZATIONLISTS.values())
			{
				if(!custom.equals(CUSTOMIZATIONLISTS.NEW_TABLES))DB2UDBCustomizationCheck.executeChecks(custom,metaData,database,resultMap);
			}
		}
		else if(metaData.getDbType().equals(Constants.ORACLE))
		{
			for(CUSTOMIZATIONLISTS custom : CUSTOMIZATIONLISTS.values())
			{
				if(!custom.equals(CUSTOMIZATIONLISTS.NEW_TABLES))OracleCustomizationCheck.executeChecks(custom,metaData,database,resultMap);
			}
			
		}
		return resultMap;
	}
	
	
	private static Database createDBObjectFromXML(String folderPath) 
	{
		Database database = null;
		JarFile masterJar = null;
		try 
		{
			masterJar = new JarFile(folderPath+Constants.JAR_PATH);
			 final Enumeration<JarEntry> entries = masterJar.entries();
		        while (entries.hasMoreElements()) 
		        {
		            final JarEntry entry = entries.nextElement();
		            if (entry.getName().contains(Constants.MASTER_XML_FILENAME)) 
		            {
		            	JarEntry fileEntry = masterJar.getJarEntry(entry.getName());
		                InputStream input = masterJar.getInputStream(fileEntry);
		                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	                    DocumentBuilder builder = dbf.newDocumentBuilder();
	                    builder.setEntityResolver(new EntityResolver() {
		                       @Override
		                       //ignoring system id to skip dynamic entity
		                       public InputSource resolveEntity(String publicId, String systemId)
		                       {
		                    	   return new InputSource(new StringReader(""));
		                       }});
	                    database = new Database();
		                   
		                   // populate database object from xml
	                    Document doc = builder.parse(input);
	                    Element databaseNode = doc.getDocumentElement();
	                    NodeList childNodes = databaseNode.getChildNodes();
	                   for (int i = 0; i < childNodes.getLength(); i++) 
	                   {
	                	  // iterate thru tables 
	                      if(childNodes.item(i).getNodeName().equals("tables"))
	                      {
	                    	  NodeList tableNodes = childNodes.item(i).getChildNodes();
	                    	  for(int tableCount=0;tableCount<tableNodes.getLength();tableCount++)
	                    	  {
	                    		  
	                    		  if(tableNodes.item(tableCount).getNodeName().equals("table"))
	                    		  {
	                    			  Table table = new Table();
	                    			  Element currNode = (Element) tableNodes.item(tableCount);
	                    			  table.setName(currNode.getAttribute("name"));
	                    			  table.setSchemaType(currNode.getAttribute("schematype"));
	                    			  table.setRuleset(currNode.getAttribute("ruleset"));
	                    			  NodeList tableData = tableNodes.item(tableCount).getChildNodes();
	                    			  for(int tabData=0;tabData<tableData.getLength();tabData++)
	                    			  {
	                    				  if(tableData.item(tabData).getNodeName().equals("columns"))
	                    				  {
	                    					  // fill columns
	                    					  NodeList columns = tableData.item(tabData).getChildNodes();
	                    					  
	                    					  for(int colData=0;colData<columns.getLength();colData++)
	                    					  {
	                    						  if(columns.item(colData).getNodeType()==Node.ELEMENT_NODE)
	                    						  {
	                    							 Element currCol = (Element)columns.item(colData);
	                    							 Column column = new Column();
	                    							 column.setFilter(currCol.getAttribute("filter"));
	                    							 column.setName(currCol.getAttribute("name"));
	                    							 column.setType(currCol.getNodeName());
	                    							 column.setSize(currCol.getAttribute("size"));
	                    							 column.setScale(currCol.getAttribute("scale"));
	                    							 column.setRequired(currCol.getAttribute("required"));
	                    							 table.getColumns().add(column);
	                    						  }
	                    					  }
	                    				  }
	                    				  if(tableData.item(tabData).getNodeName().equals("indexes"))
	                    				  {
	                    					  //fill indexes
	                    					  NodeList indexList = tableData.item(tabData).getChildNodes();
	                    					  for(int indexData=0; indexData<indexList.getLength();indexData++)
	                    					  {
	                    						  if(indexList.item(indexData).getNodeType()==Node.ELEMENT_NODE)
	                    						  {
	                    							  Element indexElement = (Element)indexList.item(indexData);
	                    							  Index index = new Index();
	                    							  index.setName(indexElement.getAttribute("name"));
	                    							  
	                    							  NodeList indexColumnList = indexList.item(indexData).getChildNodes();
	                    							  for(int indexColumns=0;indexColumns<indexColumnList.getLength();indexColumns++)
	                    							  {
	                    								  if(indexColumnList.item(indexColumns).getNodeType() == Node.ELEMENT_NODE)
	                    								  {
	                    									  Element indexColumnElement = (Element)indexColumnList.item(indexColumns);
	    	                    							  IndexColumn indexColumn = new IndexColumn();
	    	                    							  indexColumn.setName(indexColumnElement.getAttribute("name"));
	    	                    							  indexColumn.setFilter(indexColumnElement.getAttribute("filter"));
	    	                    							  index.getIndexColumn().add(indexColumn);
	                    								  }
	                    							  }
	                    							  table.getIndexes().add(index);
	                    						  }
	                    					  }
	                    				  }
	                    				  if(tableData.item(tabData).getNodeName().equals("triggers"))
	                    				  {
	                    					  //fill triggers
	                    					  NodeList triggerList = tableData.item(tabData).getChildNodes();
	                    					  for(int trgrGrp = 0;trgrGrp<triggerList.getLength();trgrGrp++)
	                    					  {
	                    						  if(triggerList.item(trgrGrp).getNodeType()==Node.ELEMENT_NODE)
	                    						  {
	                    							  Element trgrElement = (Element)triggerList.item(trgrGrp);
	                    							  table.getTriggers().add(trgrElement.getAttribute("name"));
	                    						  }
	                    					  }
	                    				  }
	                    			  }
	                    			  database.getTables().add(table);  
	                    		  }  
	                    	  }
	                      }
	                      if(childNodes.item(i).getNodeName().equals("views"))
	                      {
	                    	  //fill views
	                    	  NodeList viewNodes = childNodes.item(i).getChildNodes();
	                    	  for(int viewCount = 0; viewCount < viewNodes.getLength();viewCount++)
	                    	  {
	                    		  if(viewNodes.item(viewCount).getNodeName().equals("viewGroup"))
	                    		  {
	                    			  Element viewElement = (Element)viewNodes.item(viewCount);
	                    			  database.getViews().add(viewElement.getAttribute("name"));
	                    		  }  
	                    	  }
	                      }
	                      if(childNodes.item(i).getNodeName().equals("routines"))
	                      {
	                    	  //fill sps
	                    	  NodeList routineNodes = childNodes.item(i).getChildNodes();
	                    	  for(int routineCount = 0; routineCount < routineNodes.getLength();routineCount++)
	                    	  {
	                    		  if(routineNodes.item(routineCount).getNodeName().equals("routineGroup"))
	                    		  {
	                    			  Element routineElement = (Element)routineNodes.item(routineCount);
	                    			  if(routineElement.getAttribute("filter").equals("udf"))
	                    			  {
	                    				  // if udf append flag
	                    				  database.getRoutines().add(routineElement.getAttribute("name")+"_UDF");
	                    			  }
	                    			  else
	                    			  {
	                    				  database.getRoutines().add(routineElement.getAttribute("name"));
	                    			  }
	                    		  }
	                    	  }
	                      }
	                   }
		            }
		        }
		}       
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			if(masterJar != null) 
			{
				try 
				{
					masterJar.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		return database;
	}
	
	public static enum CUSTOMIZATIONLISTS
	{
		ALTERED_TABLES("Tables to be altered during upgrade"),
		NEW_TABLES("Tables to be introduced during upgrade"),
		NEW_SPS("Stored procedures to be introduced during upgrade"),
		NEW_VIEWS("Views to be introduced during upgrade");
		
		private String readableValue; 
		
		CUSTOMIZATIONLISTS(String readableValue)
		{
			this.readableValue = readableValue;
		}
		
		public String getValue()
		{
			return this.readableValue;
		}
		
		public static CUSTOMIZATIONLISTS getEnum(String readableValue)
		{
			for(CUSTOMIZATIONLISTS item: CUSTOMIZATIONLISTS.values())
			{
				if(item.readableValue.equals(readableValue))return item;
			}
			return null;
		}
	}
}
