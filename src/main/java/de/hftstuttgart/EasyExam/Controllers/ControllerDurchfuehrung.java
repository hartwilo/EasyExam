package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Frage;
import de.hftstuttgart.EasyExam.Musterloesung;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerDurchfuehrung {

	// Database related variables
	/*public static PreparedStatement pst = null;
	String query = "SELECT idFrage, Fragestellung, Musterloesung, Niveau, Punkte, gestellt, themengebiet.bezeichnung "
			+ "FROM frage, themengebiet WHERE Frage.Themengebiet_fk = themengebiet.idThemengebiet";*/
	
	
	public static PreparedStatement pst = null;
	String query = "Select * from Frage";
	
	@FXML
	private Button refreshQuestions;

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
	private TextField frageStellungDetail;

	@FXML
	public Button zueruckDurchfuehrung;

	@FXML
	private TextField musterLoesungDetailliert;

	@FXML
	private TextField punktZahlDetail;

	@FXML
	private ComboBox<String> themen;

	@FXML
	private RadioButton niveau1;

	@FXML
	public ToggleGroup niveau;

	@FXML
	private RadioButton niveau2;

	@FXML
	private RadioButton niveau3;

	@FXML
	private TableColumn<?, ?> gestellt;

//	@FXML
//	public void prepareWhereClausel() {
//		if (niv1.isSelected()) {
//			query = "Select * from Fragen where niveau = 'Niveau 1'";
//		} else if (niv2.isSelected()) {
//			query = "Select * from Fragen where niveau = 'Niveau 2'";
//		} else if (niv3.isSelected()) {
//			query = "Select * from Fragen where niveau = 'Niveau 3'";
//		} else if (niv1.isSelected() && niv2.isSelected()) {
//			query = "Select * from Fragen where niveau in ('Niveau 1','Niveau2')";
//			// query = "Select * from Fragen where niveau = 'Niveau 1' or niveau = 'Niveau 2'";
//		} else if (niv1.isSelected() && niv3.isSelected()) {
//			query = "Select * from Fragen where niveau = 'Niveau 1' or niveau = 'Niveau 3'";
//		} else if (niv2.isSelected() && niv3.isSelected()) {
//			query = "Select * from Fragen where niveau = 'Niveau 2' or niveau = 'Niveau 3'";
//		} else {
//			query = "Select * from Fragen";
//		}
//	}
	
	

	@FXML
	public void prepareWhereClausel(MouseEvent event) {

		query = "Select * from Frage where niveau = " + "'" + (((RadioButton) niveau.getSelectedToggle()).getText())
				+ "'";
		String themengebiet = themen.getValue();
		if (themengebiet != null) {
			query = "Select * from Frage where niveau = " + "'"
					+ (((RadioButton) niveau.getSelectedToggle()).getText()) + "'" + " and themengebiet = " + "'"
					+ themengebiet + "'";

		}
		if (nivalle.isSelected()) {
			query = "Select * from Frage";
		}
		System.out.print(query);
	}

	@FXML
	public void fragenLaden(MouseEvent event) throws SQLException {
		
		frageTabelle.setFixedCellSize(25);
		ObservableList<Frage> list = FXCollections.observableArrayList();

		pst = DBConn.connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
			
			
			while(rs.next()){
			
			list.add(new Frage(rs.getInt("idFrage"), rs.getString("Fragestellung"), rs.getString("Musterloesung"), rs.getInt("Niveau"), rs.getFloat("Punkte"), rs.getBoolean("gestellt"), rs.getString("Themengebiet_fk"), rs.getInt("Fragekatalog_fk")));
		}

		
		// !!!!!!!!!!!!!!!!!!!!WARNING! YOU MIGHT HAVE TO MAKE FRAGE CLASS
		// IMPLEMENTJAVAFX PROPERTIES!!!!!!!!!!!!!!!!!!!!!!!!!

		frageStellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFragestellung()));
		frageTabelle.setItems(list);

	}

	@FXML
	ObservableList<String> themengebieteLaden(MouseEvent event) throws SQLException {
		ObservableList<String> themengebiete = FXCollections.observableArrayList();

		query = "Select Bezeichnung from Themengebiet";
		pst = DBConn.connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery(query);

		while (rs.next()) {
			String thema = rs.getString("Bezeichnung");
			if (!themengebiete.contains(thema)) {
				themengebiete.add(thema);
			}

		}

		themen.setItems(themengebiete);
		return themengebiete;
	}

	@FXML
	void detailsAnzeigen(MouseEvent event) throws SQLException {
		
		

		String fragestellungdetailliert = frageTabelle.getSelectionModel().getSelectedItem().getFragestellung();
		String musterloesungdetailliert = frageTabelle.getSelectionModel().getSelectedItem().getMusterloesung();
		String punktzahl = Float.toString(frageTabelle.getSelectionModel().getSelectedItem().getPunkte());
		//String musterloesungdetailliert = frageTabelle.getSelectionModel().getSelectedItem().getMusterLoesung();
		//String punktzahl = Double.toString(frageTabelle.getSelectionModel().getSelectedItem().getPunkte());
		if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

			frageStellungDetail.setText(fragestellungdetailliert);
			musterLoesungDetailliert.setText(musterloesungdetailliert);
			punktZahlDetail.setText(punktzahl);
		}
	}

	@FXML
	void zueruckDurchfuehrung(MouseEvent event) throws IOException {

		MainController.setWindow("AnfangsScreen");

	}

}
