package de.hftstuttgart.EasyExam.Models;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import javafx.collections.ObservableList;

public class Protokoll {

	
	/**
	 * The method is used to create a pdf document with data from the database  
	 * 
	 * @param fragen
	 * @return
	 */
	public  PdfPTable fragenTabelle(ObservableList<Frage> fragen) {
		
		// Create a pdf table structure
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);

		PdfPCell cell = null;
		// Thema
		table.addCell(getCell("Thema", PdfPCell.ALIGN_CENTER));

		// Fragestellung
		table.addCell(getCell("Fragestellung", PdfPCell.ALIGN_CENTER));

		// Punkte
		table.addCell(getCell("Punkte", PdfPCell.ALIGN_CENTER));

		/*
		 * //Erreichte Punkte table. addCell
		 * (getCell("Erreichte Punkte",PdfPCell.ALIGN_CENTER ));
		 */
	   


	   //Fill pdf. with objects from list of Fragen.objs
	    for(int i = 0; i < fragen.size() ; i++)
	    {
	    	
	    	String thema = fragen.get(i).getThemengebiet();
	    	String fragestellung = fragen.get(i).getFrageStellung();
	    	Double punkte = fragen.get(i).getPunkte();
	    	String pnkt = punkte.toString();
	    	
	        table. addCell (getCell(thema,PdfPCell.ALIGN_CENTER ));
	        table.addCell(getCell(fragestellung,PdfPCell.ALIGN_CENTER ));
	        table.addCell(getCell(pnkt,PdfPCell.ALIGN_CENTER ));
	       
	    }

	    return table;
	} 
	
	/**
	 * The method is used to get a cell of a table 
	 * 
	 * @param text
	 * @param alignment
	 * @return
	 */
	private PdfPCell getCell(String text, int alignment) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setPadding(0);
	    cell.setHorizontalAlignment(alignment);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    return cell;
	}

	
	
	
	
	
	
}
