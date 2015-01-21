/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application;

import java.util.ArrayList;



/**
 * @author ramas6
 *
 */

public interface OSCheck 
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
	 * holds all OS versions and whether they are supported. Info from platform support guide (7.1.7)
	 *
	 */
	enum VERSIONS 
	{
		AIX53("AIX v5.3",false),AIX61("AIX v6.1",true),AIX7("AIX v7",true),
		HP11("HP-UX 11i v1",false),HP12("HP-UX 11i v2",false),HP13("HP-UX 11i v3",true),
		RHLE4("Red Hat Enterprise Linux AS v4",false),RHEL5("Red Hat Enterprise Linux v5",true),RHEL6("Red Hat Enterprise Linux v6",true),
		SUSE9("SUSE Linux Enterprise Server 9",false),SUSE10("SUSE Linux Enterprise Server 10",true),SUSE11("SUSE Linux Enterprise Server 11",true),
		SOLARIS9("Solaris 9",false),SOLARIS10("Oracle Solaris 10 (SPARC and Intel edition)",true),SOLARIS11("Oracle Solaris 11 (SPARC and Intel edition)",true),
		WIN03("Windows 2003",false),WIN08("Windows 2008",true),WIN12("Windows 2012",true);
		
		private String readableValue;
		private boolean isSupportedIn7;
		private static ArrayList<String> values = new ArrayList<String>();
		
		VERSIONS(String readableValue,boolean isSupportedIn7)
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
		
		public static Object[] getAllOS()
		{
			values.clear();
			for(VERSIONS ver : VERSIONS.values())
			{
				values.add(ver.getReadableValue());
			}
			return values.toArray();
		}
		
		public static VERSIONS getVersionForValue(String value)
		{
			values.clear();
			for(VERSIONS ver : VERSIONS.values())
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
