package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Frage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerDurchfuehrung {

	// Database related variables
	public static PreparedStatement pst = null;
	String query = "Select * from Fragen ";

	@FXML
	private CheckBox niv1;

	@FXML
	private CheckBox niv2;

	@FXML
	private CheckBox niv3;

	@FXML
	private RadioButton nivalle;

	@FXML
	private Tab themaTab1;

	@FXML
	private TextField studName;

	@FXML
	private TextField matNr;

	@FXML
	private Button studentenLaden;

	@FXML
	private TableView<Frage> frageTabelle;

	@FXML
	private TableColumn<Frage, String> frageStellung;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private TextArea frageStellungDetail;

	@FXML
	private TextArea musterLoesungDetailliert;

	@FXML
	public Button zueruckDurchfuehrung;

	@FXML
	private TextField punktZahlDetail;

	@FXML
	private ComboBox<String> themen;

	@FXML
	private RadioButton niveau1;

	@FXML
	private ToggleGroup niveau;

	@FXML
	private RadioButton niveau2;

	@FXML
	private RadioButton niveau3;

	@FXML
	private TableColumn<Frage, CheckBox> gestellt;

	@FXML
	private Button start;

	CheckBox gestelltCheckBox = new CheckBox(null);

	// The following method is used to modify the query on the Database based upon
	// the desired level and topic of questions
	public void prepareWhereClausel() {
		String themengebiet = themen.getValue();
		if (themengebiet != null && !nivalle.isSelected()) { // Select based on Level RadioButton and Topics Combobox
			query = "Select * from Fragen where niveau = " + "'"
					+ (((RadioButton) niveau.getSelectedToggle()).getText()) + "'" + " and themengebiet = " + "'"
					+ themengebiet + "'";

		} else if (nivalle.isSelected() && themengebiet == null) { // Select all questions
			query = "Select * from Fragen";
		} else if (themengebiet != null) {
			query = "Select * from Fragen where themengebiet =" + "'" + themengebiet + "'"; // Select only based on
																							// Topic

		} else { // select only based on Level RadioButton
			query = "Select * from Fragen where niveau = " + "'"
					+ (((RadioButton) niveau.getSelectedToggle()).getText()) + "'";
		}
		System.out.println(query);
	}

	// The following method is used to read data from the Database into the
	// TableView
	@FXML
	public void fragenLaden(MouseEvent event) throws SQLException {

		prepareWhereClausel();
		frageTabelle.setFixedCellSize(25);
		ObservableList<Frage> list = FXCollections.observableArrayList();

		pst = DBConn.connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			list.add(new Frage(rs.getInt("ID"), rs.getString("themengebiet"), rs.getString("frageStellung"),
					rs.getString("musterLoesung"), rs.getString("niveau"), rs.getInt("punktZahl"),
					rs.getBoolean("gestellt")));
		}

		// !!!!!!!!!!!!!!!!!!!!WARNING! YOU MIGHT HAVE TO MAKE FRAGE CLASS
		// IMPLEMENTJAVAFX PROPERTIES!!!!!!!!!!!!!!!!!!!!!!!!!

		frageStellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFragestellung()));
		gestellt.setCellFactory(features -> new CheckBoxTableCell<>());
		gestellt.setEditable(true);
		frageTabelle.setItems(list);
		frageTabelle.setEditable(true);

	}

	// The following method is used to load all existing Topics from the databse
	// into the Topic ComboBox
	@FXML
	ObservableList<String> themengebieteLaden(MouseEvent event) throws SQLException {
		ObservableList<String> themengebiete = FXCollections.observableArrayList();

		query = "Select * from Fragen";
		pst = DBConn.connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery(query);

		while (rs.next()) {
			String thema = rs.getString("themengebiet");
			if (!themengebiete.contains(thema)) {
				themengebiete.add(thema);
			}

		}

		themen.setItems(themengebiete);
		return themengebiete;
	}

	// Upon clicking on a TableView corresponding to a Question, said Question's
	// details are displayed on the right side of the Screen/GUI
	@FXML
	void detailsAnzeigen(MouseEvent event) throws SQLException {

		frageStellungDetail.setWrapText(true);
		musterLoesungDetailliert.setWrapText(true);

		String fragestellungdetailliert = frageTabelle.getSelectionModel().getSelectedItem().getFragestellung();
		String musterloesungdetailliert = frageTabelle.getSelectionModel().getSelectedItem().getMusterLoesung();
		String punktzahl = Double.toString(frageTabelle.getSelectionModel().getSelectedItem().getPunkte());

		if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

			frageStellungDetail.setText(fragestellungdetailliert);
			musterLoesungDetailliert.setText(musterloesungdetailliert);
			punktZahlDetail.setText(punktzahl);
		}
	}

	// Navigation Function - Go back to starter Screen.
	@FXML
	void zueruckDurchfuehrung(MouseEvent event) throws IOException {

		MainController.setWindow("AnfangsScreen");

	}

}
