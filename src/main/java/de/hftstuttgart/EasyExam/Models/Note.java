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
	Image img;
	String path;
	String text;

	
	/**
	 * The IMG in the note is selected via a FileChooser on the @Param window
	 * 
	 * @param window
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
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
	/**
	 * empty constructor 
	 */
	public Note() {
		
	}
	
	/**
	 * The method is used to get an Image 
	 * 
	 * @return
	 */
	public Image getImg() {
		return img;
	}

	/**
	 * The method is used to set an Image 
	 * 
	 * @param img
	 */
	public void setImg(Image img) {
		this.img = img;
	}

	/**
	 * The method is used to get a Chooser 
	 * 
	 * @return
	 */
	public FileChooser getChooser() {
		return chooser;
	}

	/**
	 * The method is used to set a Chooser
	 * 
	 * @param chooser
	 */
	public void setChooser(FileChooser chooser) {
		this.chooser = chooser;
	}

	/**
	 * The method is used to get the path 
	 * 
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * The method is used to set the path 
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Sets the path of the Note via the objects file chooser
	 */
	public void selectPath() {
		/*
		 * this.chooser.getExtensionFilters().addAll //-> (new
		 * FileChooser.ExtensionFilter("PDF File", "*.pdf"));
		 */
		Window stage = Main.mainWindow;
		File file = this.chooser.showOpenDialog(stage);
		
		this.setPath(file.getAbsolutePath());
	}
	
	/**
	 * The method is used to get The text 
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * The method is used to set the text 
	 * 
	 * @param user_input
	 */
	public void setText(String user_input) {
		this.text = user_input;
	}
	
	
	
}
