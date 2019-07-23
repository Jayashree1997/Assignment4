package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {
	private String fileName;

	

	/*
	 * parameterized constructor to initialize filename. As you are trying to
	 * perform file reading, hence you need to be ready to handle the IO Exceptions.
	 */
	FileReader fileReader;
	public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		fileReader=new FileReader(fileName);
	     this.fileName=fileName;
	}

	/*
	 * implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 */
	@Override
	public Header getHeader() throws IOException {
		BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
		String[] headerData=bufferedReader.readLine().split(",");
		Header header=new Header(headerData);

		// TODO Auto-generated method stub
		return header;
	}
	

	/**
	 * This method will be used in the upcoming assignments
	 */
	@Override
	public void getDataRow() {
	}

	/*
	 * implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field values from it. In
	 * the previous assignment, we have tried to convert a specific field value to
	 * Integer or Double. However, in this assignment, we are going to use Regular
	 * Expression to find the appropriate data type of a field. Integers: should
	 * contain only digits without decimal point Double: should contain digits as
	 * well as decimal point Date: Dates can be written in many formats in the CSV
	 * file. However, in this assignment,we will test for the following date
	 * formats('dd/mm/yyyy',
	 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
	 */
	@Override
	public DataTypeDefinitions getColumnType() throws IOException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(fileName);
        }catch (FileNotFoundException e) {
            fileReader = new FileReader("data/ipl.csv");
        }
        BufferedReader br = new BufferedReader(fileReader);
        String setHeader = br.readLine();
        String setDataRow = br.readLine();
        String[] fields = setDataRow.split(",",18);
        String[] dataTypeArray = new String[fields.length];
        int count = 0;
        for (String s:fields) {
            if(s.matches("[0-9]+")) {
                dataTypeArray[count] = "java.lang.Integer";
                count++;
            }else if(s.matches("[0-9]+.[0-9]+")){
                dataTypeArray[count] = "java.lang.Double";
                count++;
            }else if(s.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}$")||s.matches("^[0-9]{2}-[a-z]{3}-[0-9]{2}$")||s.matches("^[0-9]{2}-[a-z]{3}-[0-9]{4}$")||s.matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{2}$")||s.matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{4}$")||s.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")){
                dataTypeArray[count] = "java.util.Date";
                count++;
            }else if(s.isEmpty()){
                dataTypeArray[count] = "java.lang.Object";
                count++;
            }
            else {
                dataTypeArray[count] = "java.lang.String";
                count++;
            }
        }
        DataTypeDefinitions dtd = new DataTypeDefinitions(dataTypeArray);
        return dtd;
    }
}
