package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Frage;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerKatalogErstellen {
	
	@FXML
    private AnchorPane anchorPane;

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
	private TableColumn<Frage, String> frageStellungCol;

	@FXML
	private TableColumn<Frage, Number> punkteCol;

	@FXML
	private TableColumn<Frage, String> themaCol;

	@FXML
	private TableColumn<Frage, String> niveauCol;

	@FXML
	private TableColumn<Frage, String> musterloesungCol;

	public static PreparedStatement pst = null;
	public String query = null;

	@FXML
	public void fragenLaden(MouseEvent event) throws SQLException {
		ObservableList<Frage> list = FXCollections.observableArrayList();

		String query = "Select * from Fragen";
		pst = DBConn.connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			list.add(new Frage(rs.getString("themengebiet"), rs.getString("frageStellung"),
					rs.getString("musterLoesung"), rs.getString("niveau"), rs.getDouble("punktZahl"),
					rs.getBoolean("gestellt")));
		}

		// !!!!!!!!!!!!!!!!!!!!WARNING! YOU MIGHT HAVE TO MAKE FRAGE CLASS
		// IMPLEMENTJAVAFX PROPERTIES!!!!!!!!!!!!!!!!!!!!!!!!!

		frageStellungCol
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFragestellung()));
		punkteCol.setCellValueFactory(features -> new ReadOnlyDoubleWrapper(features.getValue().getPunkte()));
		themaCol.setCellValueFactory(new PropertyValueFactory<>("themengebiet"));
		niveauCol.setCellValueFactory(new PropertyValueFactory<>("niveau"));
		musterloesungCol.setCellValueFactory(new PropertyValueFactory<>("musterLoesung"));

		fragenTabelle.setItems(list);

	}

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
