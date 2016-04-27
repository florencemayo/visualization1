package spreadsheet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadSheet {

	//auto column
	private static void autoSizeColumns(Workbook wkbook){
		int numberOfSheets = wkbook.getNumberOfSheets();
		for (int i=0; i<numberOfSheets;i++){
			Sheet sheet =wkbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows()>0){
				Row row =sheet.getRow(0);
				Iterator<Cell> cellIterartor =row.cellIterator();
				while(cellIterartor.hasNext()){
					Cell cell =cellIterartor.next();
					int columnIndex =cell.getColumnIndex();
					sheet.autoSizeColumn(columnIndex);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		//Create a blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		//set fonts
		XSSFFont font = workbook.createFont();
		font.setFontHeight(18);
		//font.setFontHeightInPoints((short) 30);
		font.setFontName("IMPACT");
		//font.setBold(true);
		font.setColor(HSSFColor.BLACK.index);
				
		//set font into style
		XSSFCellStyle style= workbook.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		
		style.setFont(font);
		//create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("Visualization LCs");
		//spreadsheet.setColumnWidth(1,8000);
		//Create row Object
		XSSFRow row;
		//This data needs to be written to (Object [])
		
		Map <String, Object[]> LCsInfo = new TreeMap <String, Object[]>();
		LCsInfo.put("1", new Object[]{
		"Subsystem name", "LC name"		
		});
		LCsInfo.put( "2", new Object[] { 
			      "tp01", "Gopal"});
		
		//Iterate to write to a file
		Set <String> keyid =LCsInfo.keySet();
		int rowid =0;
		for (String key : keyid){
			row = spreadsheet.createRow(rowid++);
			//row.setHeight((short) 800);
			Object [] objectArr = LCsInfo.get(key);
			int cellid = 0;
			
			for (Object obj :objectArr){
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String)obj);
				if (rowid==1){
					cell.setCellStyle(style);	
				}
				
			}
			//spreadsheet.autoSizeColumn(0);
		}
		

		autoSizeColumns(workbook);
		
		//write a workbook in file system
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File
					("E://programs/eclipse/workspace/Visualization1/src/spreadsheets/artifacts.xlsx"));
			try {
				workbook.write(out);
				out.close();
				System.out.println("Done");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
