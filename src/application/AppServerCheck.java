/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

import java.util.ArrayList;





/**
 * @author ramas6
 *
 */
public interface AppServerCheck 
{
	/**
	 * returns list of checks to be conducted
	 * @return
	 */
	public Object[] getCheckList();

	/**
	 * runs the checks and returns result of individual checks
	 * @return
	 * 
	 */
	public Object[] executeChecks();
	
	
	/**
	 * holds all app server versions and whether they are supported. Info from platform support guide (7.1.7)
	 *
	 */
	enum APPVERSIONS 
	{
		WAS61("WebSphere 6.1.x",false),WAS70("WebSphere 7.0.x",true),WAS80("WebSphere 8.0.x",true),WAS85("WebSphere 8.5 ",true),WAS855("WebSphere 8.5.5",true),
		ORAWEB9("Oracle Weblogic Server 9.x",false),ORAWEB11("Oracle Weblogic Server 11g (10.3.1 and higher)",true),ORAWEB12("Oracle Weblogic Server 12c",true),
		JBOSS4("JBoss Redhat EAP 4.3.x",false),JBOSS5("JBoss Redhat EAP 5.0.x",true),JBOSS51("JBoss Redhat EAP 5.1.2",true),
		JBOSS61("JBoss Redhat EAP 6.1.x",true),JBOSS62("JBoss Redhat EAP 6.2.x",true),JBOSS63("JBoss Redhat EAP 6.3.x",true),JBOSSEWS2("JBoss EWS (Enterprise Web Server) 2.0",true),
		TCSERV("tc Server 2.8.2",true),TC6("Tomcat 6.x",true),TC7("Tomcat 7.x",true);
		private String readableValue;
		private boolean isSupportedIn7;
		private static ArrayList<String> values = new ArrayList<String>();
		
		APPVERSIONS(String readableValue,boolean isSupportedIn7)
		{
			this.readableValue = readableValue;
			this.isSupportedIn7 = isSupportedIn7;
		}
		
		public String getReadableValue()
		{
			return this.readableValue;
		}
		
		public boolean isSupported()
		{
			return this.isSupportedIn7;
		}
		
		public static Object[] getAllAppServers()
		{
			values.clear();
			for(APPVERSIONS ver : APPVERSIONS.values())
			{
				values.add(ver.getReadableValue());
			}
			return values.toArray();
			
		}
		
		public static APPVERSIONS getVersionForValue(String value)
		{
			values.clear();
			for(APPVERSIONS ver : APPVERSIONS.values())
			{
				if(value.equals(ver.getReadableValue()))
				{
					return ver;
				}
			}
			return null;
		}
		
	}
}
