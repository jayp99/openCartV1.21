package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.activation.DataSource;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.TestBase;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter; // UI of the HTML Report
	public ExtentReports extent; // populate common info on the report
	public ExtentTest test; // creating test case entries in the report and update status of the test methods
	
	String repName;
	
	public void onStart(ITestContext testContext) {
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date()); //time stamp formatted
		
		repName = "Test-Report-" + timeStamp + ".html";
		
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // report location
		sparkReporter.config().setDocumentTitle("openCart Automation Report"); // Title of report
		sparkReporter.config().setReportName("openCart Functional Testing"); // Name of report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter); //attach reporter 
		
		// hard coded for learning
		extent.setSystemInfo("Application", "openCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
		
	}
	
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); //create a new entry in the report
		test.assignCategory(result.getMethod().getGroups()); //to display test groups name in report
		test.log(Status.PASS, result.getName()+ " got successfully executed"); // update test status
	}
	
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName() + " FAILED" );
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try {
			String imgPath = new TestBase().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} 
		catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " SKIPPED");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	/*
	 * public void onTestResult(ITestResult result) { test =
	 * extent.createTest(result.getName()); test.log(Status.SKIP,
	 * "Test case SKIPPED is: " + result.getName()); }
	 */
	
	public void onFinish(ITestContext context) {
		extent.flush(); // this step is very important. Without this report will not write to file;
		
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);
		
		//to open report automatically after execution is complete
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//sent email report
		/*
		 * try { URL url = new URL("file://"+System.getProperty("user.dir") +
		 * "\\reports\\" + repName);
		 * 
		 * //create email message ImageHtmlEmail email = new ImageHtmlEmail();
		 * email.setDataSourceResolver(new DataSourceUrlResolver(url));
		 * email.setHostName("smtp.googlemail.com"); email.setSmtpPort(465);
		 * email.setAuthenticator(new DefaultAuthenticator("someones@gmail.com",
		 * "password")); email.setSSLOnConnect(true);
		 * email.setFrom("someone@gmail.com"); // sender
		 * email.setSubject("Automation Test Results");
		 * email.setMsg("Please find attached report...");
		 * email.addTo("receivergroup@gmail.com"); email.attach(url, "extent report",
		 * "please check report..."); email.send();
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
		
		
	  }
	

}
