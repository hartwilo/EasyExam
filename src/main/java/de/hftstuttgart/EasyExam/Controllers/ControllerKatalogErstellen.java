package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;

import de.hftstuttgart.EasyExam.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ControllerKatalogErstellen {
	// Katalog
		// Anlegen...........................................................................................................................

		@FXML
		public Button frageAnlegen;

		@FXML
		public Button katalogSpeichern;

		@FXML
		public Button katalogAnlegen;

		@FXML
		private TextField katalogNameTextField;
		
		@FXML
	    private TableView<de.hftstuttgart.EasyExam.Frage> fragenTabelle;

	    @FXML
	    private TableColumn<?, ?> frageStellungCol;

	    @FXML
	    private TableColumn<?, ?> punkteCol;

	    @FXML
	    private TableColumn<?, ?> themaCol;

	    @FXML
	    private TableColumn<?, ?> niveauCol;

	    @FXML
	    private TableColumn<?, ?> musterloesungCol;


		@FXML
		void katalogAnlegen(MouseEvent event) throws IOException {

			MainController.setWindow("KatalogErstellen");
		}

		@FXML
		void frageAnlegen(MouseEvent event) throws IOException {

			MainController.setWindow("FrageErstellen");
		}

		@FXML
		void katalogSpeichern(MouseEvent event) throws IOException {

			MainController.setWindow("AnfangsScreen");
		}
	

}
