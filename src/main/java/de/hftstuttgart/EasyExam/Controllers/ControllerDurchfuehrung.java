package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Frage;
import de.hftstuttgart.EasyExam.Themengebiet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControllerDurchfuehrung {
	public static PreparedStatement pst = null;
	// Prüfungsdurchführung......................................................................................

	@FXML
	public Button zueruckDurchfuehrung;

	public static ObservableList<Frage> fragenLaden() throws SQLException {
		ObservableList<Frage> list = FXCollections.observableArrayList();

		String query = "Select * from Fragen";
		pst = DBConn.connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			list.add(new Frage(rs.getString("themengebiet"), rs.getString("frageStellung"),
					rs.getString("musterLoesung"), rs.getString("niveau"), rs.getInt("punktZahl"),
					rs.getBoolean("gestellt")));
		}
		return list;

		

	}

	@FXML
	void zueruckDurchfuehrung(MouseEvent event) throws IOException {

		MainController.setWindow("AnfangsScreen");

	}

}
