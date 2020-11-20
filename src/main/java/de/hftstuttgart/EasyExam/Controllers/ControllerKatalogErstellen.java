package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Frage;
import javafx.beans.property.ReadOnlyDoubleWrapper;
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

	/*
	 * // Initialized prepared Statement which will later be passed executed // with
	 * a query
	 */
	public static PreparedStatement pst = null;

	/*
	 * // Initialized query which will later be modified and passed to prepared //
	 * statement
	 */
	public String query = null;

	// This method loads relevant question data into a ViewTable in the GUI
	public void fragenLaden() throws SQLException {
		fragenTabelle.setFixedCellSize(25);
		ObservableList<Frage> list = FXCollections.observableArrayList();

		query = "Select * from Fragen";
		pst = DBConn.connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			list.add(new Frage(rs.getInt("ID"), rs.getString("themengebiet"), rs.getString("frageStellung"),
					rs.getString("musterLoesung"), rs.getString("niveau"), rs.getDouble("punktZahl"),
					rs.getBoolean("gestellt")));
		}

		/*
		 * // !!!!!!!!!!!!!!!!!!!!WARNING! YOU MIGHT HAVE TO MAKE FRAGE CLASS //
		 * IMPLEMENTJAVAFX PROPERTIES!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		frageStellungCol
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFragestellung()));
		punkteCol.setCellValueFactory(features -> new ReadOnlyDoubleWrapper(features.getValue().getPunkte()));
		themaCol.setCellValueFactory(new PropertyValueFactory<>("themengebiet"));
		niveauCol.setCellValueFactory(new PropertyValueFactory<>("niveau"));
		musterloesungCol.setCellValueFactory(new PropertyValueFactory<>("musterLoesung"));

		fragenTabelle.setItems(list);
	}

	@FXML /*
			 * //This method loads relevant question data into a ViewTable in the GUI (as
			 * soon as the mouse is entered into the GUI)
			 */
	public void fragenLaden(MouseEvent event) throws SQLException {
		fragenLaden();
	}

	@FXML // This method deletes questions from a currently Selected question catalog
	void frageLoeschen(MouseEvent event) throws SQLException {

		int ID = fragenTabelle.getSelectionModel().getSelectedItem().getId();
		query = "DELETE FROM fragen WHERE ID = " + ID;
		pst = DBConn.connection.prepareStatement(query);
		pst.executeUpdate();
		fragenLaden();
	}

	@FXML /*
			 * // Method for creating a new Catalog Table in Database, NOTE: Names of
			 * attributes // must later be adapted to AZURE database
			 */
	void katalogAnlegen(MouseEvent event) throws IOException {

		MainController.setWindow("KatalogErstellen");
	}

	@FXML // GUI Navigation - Go to FrageErstellen screen
	void frageAnlegen(MouseEvent event) throws IOException {

		MainController.setWindow("FrageErstellen");
	}

	@FXML // GUI Navigation - Go to AnfangsScreen screen
	void katalogSpeichern(MouseEvent event) throws IOException {

		MainController.setWindow("AnfangsScreen");
	}

}
