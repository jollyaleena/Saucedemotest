package com.test.utility;

import java.util.ArrayList;

import com.excelreader.Xls_Reader;
public class TestUtil {
    
	static Xls_Reader reader;
	
	public static ArrayList<Object[]> getDataFromExcel(){
		
	ArrayList<Object[]> myData =new ArrayList<Object[]>();
	try {
		reader =new Xls_Reader("C:\\New folder\\New folder\\AssignmentTest\\src\\com\\testdata\\excel_data.xlsx");
	}catch(Exception e) {
		e.printStackTrace();
	}
 
	
	for(int rowNum=2; rowNum<=reader.getRowCount("LoginTestData"); rowNum++) {
		
		String Username =reader.getCellData("LoginTestData","Username",rowNum);
		String Password =reader.getCellData("LoginTestData","Password",rowNum);
        Object[] ob = {Username, Password};
		myData.add(ob);
		
	}
		
		return myData;
		}
	}
	
	

