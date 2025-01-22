package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//Data Provider 1
	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
		String path = "./testData/Opencart_LoginData.xlsx"; // taking path of test data Excel file
		ExcelUtility xlutil = new ExcelUtility(path); // creating an object of ExcelUtility

		int totalrows = xlutil.getRowCount("DynamicData");
		int totalcols = xlutil.getCellCount("DynamicData", 1);

		String logindata[][] = new String[totalrows][totalcols];// creating 2d array to hold data to pass to test

		for (int i = 1; i <= totalrows; i++) {
			for (int j = 0; j < totalcols; j++) {
				logindata[i - 1][j] = xlutil.getCellData("DynamicData", i, j);
			}
		}
		return logindata;

	}
	
	// DataProvider 2	
	//DataProvider 3
	//DataProvider 4

}
