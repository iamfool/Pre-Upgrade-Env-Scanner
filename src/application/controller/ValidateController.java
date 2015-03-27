/**
 *  © 2014 Engineering Customer Success. Pegasystems Worldwide Pvt.Ltd
 */
package application.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import application.AppServerCheck;
import application.AppServerCheck.APPVERSIONS;
import application.DBCheck;
import application.DBCheckFactory;
import application.DBMetaData;
import application.OSCheck;
import application.OSCheck.VERSIONS;
import application.SchemaCustomizationCheck;
import application.SchemaCustomizationCheck.CUSTOMIZATIONLISTS;
import application.utils.Constants;
import application.utils.DBUtils;
import application.utils.Utilities;

/**
 * @author ramas6
 *
 */
@SuppressWarnings("rawtypes")
public class ValidateController implements Initializable 
{
   @FXML Button backButton;
  
   @FXML ListView checklistview;
   @FXML ListView customizationview;
   @FXML ListView resultview;
   @FXML Button checkButton;
   @FXML Hyperlink pdnlink;
   @FXML Hyperlink exportexcel;
   @FXML Label errortext;
   @FXML Label forPRPCVersionText;
   private DBCheck check;
   private ObservableList checkList = FXCollections.observableArrayList();
   private ObservableList resultSetList = FXCollections.observableArrayList();
   private ObservableList customizationList = FXCollections.observableArrayList();
   private LoadController rootControl;
   private boolean buildSuccess = false;
   private HashMap<CUSTOMIZATIONLISTS, ArrayList<String>> resultMap = null;
   ArrayList<String> resultList = null;
   Workbook excelReport = null;
   
   
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		rootControl = Navigator.getFxLoader().getController();
		
		if(rootControl.fetchMetaData() != null) 
		{
			renderCheckList(rootControl.fetchMetaData().getDbType());
		}
		
		errortext.setVisible(false);
		exportexcel.setVisible(false);
		customizationview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
				{
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
		    		{
		        		if(resultMap != null && resultMap.get(CUSTOMIZATIONLISTS.getEnum(newValue)) != null)
		        		{
		        			resultSetList.clear();
		        			resultSetList.addAll(resultMap.get(CUSTOMIZATIONLISTS.getEnum(newValue)));
		        			resultview.setItems(resultSetList);
		        			
		        		}
		    		}
				}
		    );
	}
	
	
	@FXML protected void handleBackButtonAction(ActionEvent event)   
	{
		DBUtils.closeConnection(rootControl.fetchMetaData().getConnection());
		Navigator.loadScreen(Constants.HOME_FXML);
	}

	
	
	@SuppressWarnings("unchecked")
	private void renderCheckList (String dbType) 
	{
		// add supported OS and APP server checks
		checkList.add(Constants.OS_CHECK + Constants.HYPHEN + rootControl.getOsChoice());
		checkList.add(Constants.APP_SERVER_CHECK + Constants.HYPHEN + rootControl.getAppServerChoice());
		
		//add ml or updgrade build check
		checkList.add(Constants.UPGRADE_BUILD_CHECK);
		
		// add DB specific checks
		check = DBCheckFactory.getVendorCheck(dbType);
		checkList.addAll(check.getCheckList());
		checklistview.setItems(checkList);
		
		//add customization checks
		customizationList.addAll(SchemaCustomizationCheck.getCheckList());
		customizationview.setItems(customizationList);
	}
	
	@SuppressWarnings("unchecked")
	@FXML protected void handleCheckButtonAction(ActionEvent event)   
	{
		excelReport = new HSSFWorkbook();
		if(check != null)
		{
			checkButton.setDisable(true);
			errortext.setVisible(false);

			// clear checklist
			if(checkList.size()>0)
				{
				 	checkList.removeAll(check.getCheckList());
					checklistview.setItems(checkList);
				}
			ObservableList checkedList = FXCollections.observableArrayList();
			
			//execute OS and APP server checks
			checkandSetOSandAPPServer(checkedList);
			
			//check whether build is not ML build
			try
			{
				checkIfNotMLBuild(checkedList);
			}
			catch(Exception e)
			{
				errortext.setText(Constants.TEST_ERROR+e.getMessage());
				checkedList.add(Constants.TEST_ERROR+Constants.UPGRADE_BUILD_CHECK + Constants.HYPHEN + Constants.INSTALL_MEDIA_NOT_AVAILABLE);
				errortext.setVisible(true);
				
			}
			
			//execute DB checks and set result to listview
			checkedList.addAll(check.executeChecks(rootControl.fetchMetaData()));
			checklistview.setItems(checkedList);
			
			// fill excel report for environment checks
			Sheet envSheet = excelReport.createSheet("Environment Checks");
			for(int i=0;i<checkedList.size();i++)
			{
				Row row = envSheet.createRow(i);
				row.createCell(1).setCellValue(checkedList.get(i).toString());
			}
			
			
			//execute schema customization checks
			checkForSchemaCustomizations(rootControl.fetchMetaData());
			
			checkButton.setDisable(false);
			checkButton.setText(Constants.RE_CHECK);
			if(buildSuccess)
			{
				exportexcel.setVisible(true);
				errortext.setVisible(false);
			}
			
		}
		else
		{
			return;
		}
	}


	/**
	 * @param checkedList
	 * @param fetchMetaData
	 */
	private void checkForSchemaCustomizations(DBMetaData metaData) 
	{
		if(buildSuccess)
		{
			resultMap = SchemaCustomizationCheck.executeChecks(excelReport,metaData,rootControl.getFolderPath());
		}
		
	}


	/**
	 * @param checkedList
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	private void checkIfNotMLBuild(ObservableList checkedList) throws IOException 
	{
		String folderPath = rootControl.getFolderPath();
		boolean checkForPRPCSetup = false;
		boolean checkForResourceKitFolder = false;
		boolean checkForMLBuild = false;
		File assemblyFile = null;
		if(!Utilities.isEmpty(folderPath))
		{
			checkForPRPCSetup = new File(folderPath, Constants.PRPC_SETUP_JAR).exists();
			checkForResourceKitFolder = new File(folderPath, Constants.RESOURCEKIT_DIRECTORY_NAME).exists();
			checkForMLBuild = new File(folderPath,Constants.ML_SETUP_JAR).exists();
			assemblyFile = new File(folderPath+Constants.ASSEMBLY_FILE);
			BufferedReader bufferRead = new BufferedReader(new FileReader(assemblyFile));
		    String prpcBuild ;
			while((prpcBuild  = bufferRead.readLine())!=null)
		    {
		    	if(!Utilities.isEmpty(prpcBuild))
		    	{
		    		forPRPCVersionText.setText(Constants.FOR_VERSION+prpcBuild.substring(30));
		    		break;
		    	}
		    }
		    bufferRead.close();
		}
		else 
		{
			checkedList.add(Constants.TEST_ERROR+Constants.UPGRADE_BUILD_CHECK + Constants.HYPHEN + Constants.INSTALL_MEDIA_NOT_AVAILABLE);
			return;
		}
		
		if(checkForPRPCSetup && checkForResourceKitFolder)
		{
			checkedList.add(Constants.TEST_SUCCESS+Constants.UPGRADE_BUILD_CHECK );
			buildSuccess = true;
		}
		else if(checkForMLBuild)
		{
			checkedList.add(Constants.TEST_FAILURE+Constants.UPGRADE_BUILD_CHECK+ Constants.HYPHEN + Constants.ML_BUILD_ALERT );
		}
		else 
		{
			checkedList.add(Constants.TEST_FAILURE+Constants.UPGRADE_BUILD_CHECK + Constants.HYPHEN + Constants.UPGRADE_BUILD_NOT_AVAILABLE);
		}
	}


	/**
	 * @param checkedList 
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void checkandSetOSandAPPServer(ObservableList checkedList) 
	{
		VERSIONS OSVer = OSCheck.VERSIONS.getVersionForValue(rootControl.getOsChoice());
		if(OSVer.isSupported())
		{
			checkedList.add(Constants.TEST_SUCCESS+Constants.OS_CHECK + Constants.HYPHEN+ rootControl.getOsChoice());
		}
		else
		{
			checkedList.add(Constants.TEST_FAILURE+Constants.OS_CHECK + Constants.HYPHEN+ rootControl.getOsChoice());
		}
		
		APPVERSIONS appVer = AppServerCheck.APPVERSIONS.getVersionForValue(rootControl.getAppServerChoice());
		if(appVer.isSupported())
		{
			checkedList.add(Constants.TEST_SUCCESS+Constants.APP_SERVER_CHECK + Constants.HYPHEN + rootControl.getAppServerChoice());
		}
		else
		{
			checkedList.add(Constants.TEST_FAILURE+Constants.APP_SERVER_CHECK + Constants.HYPHEN + rootControl.getAppServerChoice());
		}
		
	}
	
	@FXML protected void handleLinkAction(ActionEvent event)
	{
		Navigator.getHost().showDocument(Constants.PDN_PLATFORM_LINK);
	} 
	
	@FXML protected void handleExportToExcelAction(ActionEvent event) throws Exception
	{
		String fileName = Constants.ENV_REPORT_NAME+System.currentTimeMillis()+Constants.ENV_REPORT_EXTN;
		if(excelReport != null)
		{
			FileOutputStream fileOut = new FileOutputStream(rootControl.getFolderPath()+"//"+fileName);
			excelReport.write(fileOut);
		    fileOut.close();
		    excelReport.close();
			errortext.setText(Constants.EXPORT_SUCCESS+ rootControl.getFolderPath()+ Constants.FILENAME+ fileName);
			errortext.setVisible(true);
		}
		
	} 
}
