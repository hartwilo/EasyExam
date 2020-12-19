package de.hftstuttgart.EasyExam.Models;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfStamper;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Controllers.PruefungController;
import de.hftstuttgart.EasyExam.Main.Main;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;


public class PDFCreate {
	


	private static final Logger log;

	DBQueries dbQuery = new DBQueries(DBConn.connection);

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	static PruefungController conn = new PruefungController();

	
	
	/**
	 * Unterschiedliche Schriftarten für Erstellung von PDF Dokumenten.
	 */
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font bigBold = new Font(Font.FontFamily.TIMES_ROMAN,32, Font.BOLD);

	
/**
 * Hinzufügen von Metadaten zum PDF Dokument
 * @param document
 */
    
	 public static void addMetaData(Document document) {
		 
	        document.addTitle("Mündliche Prüfung");
	        //document.addSubject(conn.katalogeComboBox.getValue());
	        
	        /* Pruefer einfügen, sobald in DBQueries vorhanden
	        document.addAuthor("Lars Vogel");
	        document.addCreator("Lars Vogel"); */
	        
	        /* Student einfügen, sobald vorhanden ind DBQueries
	        document.add(student.getName);
	        */
	    }

	 /**
	  * Erstellen von Dokument Titel
	  * Enthält Titel, Pruefer und Student
	  * @param pdfDocument
	  * @throws DocumentException
	  */
	 
	 public static void addTitlePage(Document pdfDocument, ObservableList<Frage> fragen) throws DocumentException {
	        Paragraph preface = new Paragraph();
	        
	        addEmptyLine(preface, 1);
	        // Überschrift - Pruefungsname / Katalogname
	        preface.add(new Paragraph("Prüfung TEST", catFont));
	        
	        addEmptyLine(preface, 1);
	        // Pruefer, Student, Datum
	        preface.add(new Paragraph("Pruefer: Pruefer einfügen "+"; " + "Student: Student einfügen "+ "; " + new Date(), smallBold));
	        
	        addEmptyLine(preface, 10);
	        
	        preface.add(new Paragraph("Mündliche Prüfung " + fragen.get(1).getModul(), bigBold));
	        preface.setAlignment(Element.ALIGN_MIDDLE);
	        
	        pdfDocument.add(preface);
	        pdfDocument.newPage();
	        
	 }
	 
	 /**
	  * Erstellung der Fragetabelle
	  * Enthält eine Tabelle aus Fragen
	  * @param pdfDocument
	  * @throws DocumentException
	  */
	 
	 public static void add_questions(Document document, ObservableList<Frage> fragen) throws DocumentException {

		  	Anchor anchor = new Anchor("Prüfungsdurchführung", catFont);

	        // Second parameter is the number of the chapter
	        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

	        Paragraph subPara = new Paragraph("Fragenliste", subFont);
	        Section subCatPart = catPart.addSection(subPara);
	        
	        addEmptyLine(subPara, 2);
	        
	        addFragenTable(subCatPart, fragen);
	        
	        addEmptyLine(subPara, 2);

	        subPara = new Paragraph("Anhang", subFont);
	        subCatPart = catPart.addSection(subPara);
	        subCatPart.add(new Paragraph("Anhang 1"));
	        subCatPart.add(new Paragraph("Anhang 2"));
	        subCatPart.add(new Paragraph("Anhang 3"));

	        // now add all this to the document
	        document.add(catPart);
		 
	 }
	 
	 /**
	  * Erstellung der Fragetabelle
	  * Enthält eine Tabelle aus Fragen
	  * @param pdfDocument
	  * @throws DocumentException
	  */ 
	 
	 public static void add_image(Document document, Image img) {
		 
		 // Formatting
		 Anchor anchor = new Anchor("Noizien", catFont);
		 Chapter catPart = new Chapter(new Paragraph(anchor), 2);
		 Paragraph subPara = new Paragraph("Notizien", subFont);
		 Section subCatPart = catPart.addSection(subPara);	
		 addEmptyLine(subPara, 2);
		 
		 subCatPart.add(img);
		   
		   
		 
		 try { //adding the picture of the note in the document
			document.add(subCatPart);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	 }
	 
		/*
		 * public static void add_notizien(Document document, String document_path) { //
		 * Set default path to //Set default path to 'C:\Users\...\ String defaultPath =
		 * System.getProperty("user.home"); File userDirectory = new File(defaultPath);
		 * 
		 * FileChooser chooser = new FileChooser();
		 * chooser.setInitialDirectory(userDirectory);
		 * chooser.getExtensionFilters().addAll(new
		 * FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
		 * 
		 * File notes = chooser.showOpenDialog(Main.mainWindow); String note_path =
		 * notes.getPath(); //Path of notes log.info("Selected image path : "
		 * +note_path);
		 * 
		 * PdfReader reader = new PdfReader(src); PdfStamper stamper = new
		 * PdfStamper(reader, new FileOutputStream(dest)); }
		 */
	 
	 /**
	  * Erstellen und Befüllen einer Tabelle mit den Prüfungsfragen, Antworten und erreichten 
	  * Punkten des jeweiligen Prüflings
	  * @param subCatPart
	  * @throws BadElementException
	  */ 
	 
	 public static void addFragenTable(Section subCatPart, ObservableList<Frage> fragen) throws BadElementException{
		 
		 
		 PdfPTable table = new PdfPTable(3);
		 
		 PdfPCell c1 = new PdfPCell(new Phrase("Prüfungsfragen"));
		 c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        
	     c1 = new PdfPCell(new Phrase("Antwort"));
	     c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(c1);
	     
	     c1 = new PdfPCell(new Phrase("Punktezahl"));
	     c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(c1);
	     table.setHeaderRows(1);
	     
	     
	     //Befüllung der Tabelle mit Prüfungsfragen, Antworten und erreichter Punktzahl
	     
	     //Test-Befüllunge
	     
	     for (Frage frage : fragen) {
	     table.addCell(frage.getFrageStellung());
	     table.addCell(frage.getMusterloesung());
	     table.addCell(String.valueOf(frage.getPunkte()));

	     
	     
	     
	     }
	     subCatPart.add(table);
	 }
	 
	 private static void addEmptyLine(Paragraph paragraph, int number) {
	        for (int i = 0; i < number; i++) {
	            paragraph.add(new Paragraph(" "));
	        }
	 }
	 
	 
}
