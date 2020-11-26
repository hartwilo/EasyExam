package de.hftstuttgart.EasyExam.Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DB.DBConn;
import DB.DBQueries;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Katalog {

	private static final Logger log;
	static DBQueries dBQuery = new DBQueries();

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	String Bezeichnung;

	//////////////// CodeBlock for exporting/importing questions to excel
	//////////////// file///////////////////////////

	/*
	 * public static void importFromExcel() throws IOException, SQLException {
	 * 
	 * //TO-DO: Send query method! String query =
	 * "insert into Frage(idFrage, Fragestellung, Musterloesung, Niveau, Punkte, gestellt, themengebiet) Values(?.?,?,?,?,?,?)"
	 * ; preparedStatement = DBConn.connection.prepareStatement(query);
	 * 
	 * 
	 * 
	 * Name of the file method must be changed somehow so as to make it correspond
	 * to the name of the saved catalog
	 * 
	 * 
	 * //Select the file from Kataloge folder String fileName; FileInputStream
	 * fileIn = new FileInputStream(new File(fileName)); //Read data from filein
	 * into workbook.obj XSSFWorkbook workbook = new XSSFWorkbook(fileIn); XSSFSheet
	 * sheet = workbook.getSheetAt(0); Row row;
	 * 
	 * for(int i=1; i<=sheet.getLastRowNum(); i++) {
	 * 
	 * row = sheet.getRow(i); preparedStatement.setInt(1, (int)
	 * row.getCell(0).getNumericCellValue()); preparedStatement.setString(2,
	 * row.getCell(2).getStringCellValue()); preparedStatement.setString(3,
	 * row.getCell(3).getStringCellValue()); preparedStatement.setDouble(4,
	 * row.getCell(4).getNumericCellValue()); preparedStatement.setInt(5, (int)
	 * row.getCell(5).getNumericCellValue()); preparedStatement.setBoolean(6,
	 * row.getCell(6).getBooleanCellValue()); preparedStatement.setString(7,
	 * row.getCell(7).getStringCellValue());
	 * 
	 * 
	 * //preparedStatement.setString(8, row.getCell(8).getStringCellValue());
	 * //preparedStatement.setString(9, row.getCell(9).getStringCellValue());
	 * preparedStatement.execute();
	 * 
	 * log.info("Data from Excel sheet imported");
	 * 
	 * workbook.close(); fileIn.close();
	 * 
	 * 
	 * TO-DO: All resultsets and prepared statements must be closed
	 * 
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * 
	 * 
	 * 
	 * 
	 * //preparedStatement.close(); //resultSet.close(); }
	 * 
	 * }
	 */

	public static void exportToExcel(String fileName) throws SQLException { // fileName = Name of the katalog.xlsx

		try {

			// TO-DO: Send query method!
			dBQuery.alleFrageLaden();

			// Create Excel Workbook object
			XSSFWorkbook workbook = new XSSFWorkbook();
			// Create Excel Sheet in this Workbook
			XSSFSheet sheet = workbook.createSheet("Fragen");
			// Create Header in this sheet
			XSSFRow kopfzeile = sheet.createRow(0);
			// Create structure of the header in the sheet
			kopfzeile.createCell(0).setCellValue("ID");
			kopfzeile.createCell(1).setCellValue("Themengebiet");
			kopfzeile.createCell(2).setCellValue("Fragestellung");
			kopfzeile.createCell(3).setCellValue("Musterlösung");
			kopfzeile.createCell(4).setCellValue("Niveau");
			kopfzeile.createCell(5).setCellValue("Punkte");
			kopfzeile.createCell(6).setCellValue("Gestellt?");
			kopfzeile.createCell(7).setCellValue("Modul");
			// kopfzeile.createCell(8).setCellValue("Fragekatalog");
			// Fragekatalog becomes redundant with this solution

			// EVT. TO-DO>? Add all relevant question data

			int index = 1;

			/////// !!!!!! while (rs.next() && rs.getString("Fragekatalog") == string in
			/////// view tied to katalog col) ///!!!!!!!!!!!

			while (DBQueries.rs.next()) {

				int ID = DBQueries.rs.getInt("idFrage");
				String thema = DBQueries.rs.getString("Themengebiet");
				String fragestellung = DBQueries.rs.getString("Fragestellung");
				String musterloesung = DBQueries.rs.getString("Musterloesung");
				int niveau = DBQueries.rs.getInt("Niveau");
				Float punkte = DBQueries.rs.getFloat("Punkte");
				Boolean istGestellt = DBQueries.rs.getBoolean("gestellt");
				String modul = DBQueries.rs.getString("Modul");
				String fragekatalog = DBQueries.rs.getString("Fragekatalog");

				XSSFRow zeile = sheet.createRow(index);
				zeile.createCell(0).setCellValue(ID);
				zeile.createCell(1).setCellValue(thema);
				zeile.createCell(2).setCellValue(fragestellung);
				zeile.createCell(3).setCellValue(musterloesung);
				zeile.createCell(4).setCellValue(niveau);
				zeile.createCell(5).setCellValue(punkte);
				zeile.createCell(6).setCellValue(istGestellt);
				zeile.createCell(7).setCellValue(modul);
				zeile.createCell(8).setCellValue(fragekatalog);


				index++;

			}

			// Create FileOutputStream and write the data in the workbook object in it
			File file = new File("Kataloge/" + fileName + ".xlsx");
			FileOutputStream fileout = new FileOutputStream(file);
			workbook.write(fileout);
			workbook.close();
			fileout.close();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Katalog erfolgreich erstellt");
			alert.showAndWait();

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("Excel Dokument könnte nicht erstellt werden");
		}

	}

	//////////////////////////////////////////////////////////////////////////////////////////////

}
