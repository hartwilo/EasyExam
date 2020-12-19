package de.hftstuttgart.EasyExam.Models;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Main.Main;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Note {
	private Logger log = Logger.getLogger(DBConn.class.getName());
	
	FileChooser chooser;

	
	String path;
	Image img;
	
	
	
	// The IMG in the note is selected via a FileChooser on the @Param window
	public Note(Window window) throws BadElementException, MalformedURLException, IOException {
		
;
		this.chooser = new FileChooser();
		this.chooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
		
		try {
			File file = this.chooser.showOpenDialog(window);
			this.setPath(file.getAbsolutePath());
			this.setImg(Image.getInstance(this.path));
			log.info("Notepath: "+this.path);
		} catch (Exception e) {
			log.warning("Note can't be instantiated "+ e.getMessage() +e.getCause());
		}
		
		
		
		
	}
	
	/*
	 * public Note() { this.chooser = new FileChooser(); //
	 * this.chooser.getExtensionFilters().addAll(new
	 * FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
	 * 
	 * 
	 * }
	 */
	
	public Note() {
	
	}
	
	public Image getImg() {
		return img;
	}


	public void setImg(Image img) {
		this.img = img;
	}


	public FileChooser getChooser() {
		return chooser;
	}


	public void setChooser(FileChooser chooser) {
		this.chooser = chooser;
	}

	
	

	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	
	// Sets the path of the Note via the objects file chooser
	public void selectPath() {
		/*
		 * this.chooser.getExtensionFilters().addAll //-> (new
		 * FileChooser.ExtensionFilter("PDF File", "*.pdf"));
		 */
		Window stage = Main.mainWindow;
		File file = this.chooser.showOpenDialog(stage);
		
		this.setPath(file.getAbsolutePath());
			
		
	}
	
	
	
}
