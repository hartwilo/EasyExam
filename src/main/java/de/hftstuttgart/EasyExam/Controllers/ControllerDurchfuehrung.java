package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControllerDurchfuehrung {

	// Prüfungsdurchführung......................................................................................

	@FXML
	public Button zueruckDurchfuehrung;

	@FXML
	void zueruckDurchfuehrung(MouseEvent event) throws IOException {

		MainController.setWindow("AnfangsScreen");

	}

}
