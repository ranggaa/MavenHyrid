package driverFactory;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import config.AppUtil;
import utiities.ExcelFileUtil;

public class AppTest extends AppUtil{
String inputpath ="./FileInput/DataEngine.xlsx";
String outputpath ="./FileOutput/HybridResults.xlsx";
String TCSheet ="TestCases";
String TSsheet ="TestSteps";
ExtentReports report;
ExtentTest logger;
@Test
public void startTest() throws Throwable
{
	boolean res =false;
	String tcres="";
	///create object for excfile util class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count no of rows in TCSheet and TSsheet
	int TCcount =xl.rowCount(TCSheet);
	int TSCount = xl.rowCount(TSsheet);
	Reporter.log("No of rows in TCSheet::"+TCcount+"   "+"No of rows in TSSheet::"+TSCount,true);
	//iterate all rows in TCSheet
	for(int i=1;i<=TCcount;i++)
	{
		//read module name cell
		String Modulename = xl.getCellData(TCSheet, i, 1);
		//define html path
		report= new ExtentReports("./target/Reports/"+Modulename+".html");
		logger = report.startTest(Modulename);
		//read module status cell
		String modulestatus = xl.getCellData(TCSheet, i, 2);
		if(modulestatus.equalsIgnoreCase("Y"))
		{
		//read tcid ceel from TCSheet
			String tcid = xl.getCellData(TCSheet, i, 0);
			//iterate all rows in TSSheet
			for(int j=1;j<=TSCount;j++)
			{
			
			//read description cell
				String Description =xl.getCellData(TSsheet, j, 2);
			
				//read tcid from TSSheet
				String tsid =xl.getCellData(TSsheet, j, 0);
				if(tcid.equalsIgnoreCase(tsid))
				{
					//read keyword cell from TSsheet
					String keyword = xl.getCellData(TSsheet, j, 3);
					if(keyword.equalsIgnoreCase("adminLogin"))
					{
						String para1=xl.getCellData(TSsheet, j, 5);
						String para2 = xl.getCellData(TSsheet, j, 6);
						res =FunctionLibrary.adminLogin(para1, para2);
						logger.log(LogStatus.INFO, Description);
					}
					if(keyword.equalsIgnoreCase("branchCreation"))
					{
						String para1 =xl.getCellData(TSsheet, j, 5);
						String para2 =xl.getCellData(TSsheet, j, 6);
						String para3 =xl.getCellData(TSsheet, j, 7);
						String para4 =xl.getCellData(TSsheet, j, 8);
						String para5 =xl.getCellData(TSsheet, j, 9);
						String para6 =xl.getCellData(TSsheet, j, 10);
						String para7 =xl.getCellData(TSsheet, j, 11);
						String para8 =xl.getCellData(TSsheet, j, 12);
						String para9 =xl.getCellData(TSsheet, j, 13);
						FunctionLibrary.ClickBranches();
						res =FunctionLibrary.branchCreation(para1, para2, para3, para4, para5, para6, para7, para8, para9);
						logger.log(LogStatus.INFO, Description);
						
					}
					if(keyword.equalsIgnoreCase("branchUpdation"))
					{
						String para1 =xl.getCellData(TSsheet, j, 5);
						String para2 =xl.getCellData(TSsheet, j, 6);
						String para5 =xl.getCellData(TSsheet, j, 9);
						String para6 =xl.getCellData(TSsheet, j, 10);
						FunctionLibrary.ClickBranches();
						res = FunctionLibrary.branchUpdation(para1, para2, para5, para6);
						logger.log(LogStatus.INFO, Description);
					}
					if(keyword.equalsIgnoreCase("adminLogout"))
					{
						res =FunctionLibrary.adminLogout();
						logger.log(LogStatus.INFO, Description);
					}
					
					String tsres ="";
					if(res)
					{
						//if res is true write as pass into status cell in TSsheet
						tsres="pass";
						xl.setCellData(TSsheet, j, 4, tsres, outputpath);
						logger.log(LogStatus.PASS, Description);
					}
					else
					{
						////if res is false write as fail into status cell in TSsheet
						tsres="Fail";
						xl.setCellData(TSsheet, j, 4, tsres, outputpath);
						logger.log(LogStatus.FAIL, Description);
					}
					tcres=tsres;
					
					
				}
				report.endTest(logger);
				report.flush();
			}
			
			xl.setCellData(TCSheet, i, 3, tcres, outputpath);
		}
		else
		{
			//write as blocked in to status cell in TCSheet which are flag to N
			xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
		}
		
	}
	
}
}














