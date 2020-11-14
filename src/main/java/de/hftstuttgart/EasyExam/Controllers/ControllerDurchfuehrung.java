package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Frage;
import de.hftstuttgart.EasyExam.Themengebiet;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerDurchfuehrung {

	public static PreparedStatement pst = null;
	// Prüfungsdurchführung......................................................................................

	@FXML
	private Tab themaTab1;

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
	public void fragenLaden(MouseEvent event) throws SQLException {
		ObservableList<Frage> list = FXCollections.observableArrayList();

		String query = "Select * from Fragen";
		pst = DBConn.connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			list.add(new Frage(rs.getString("themengebiet"), rs.getString("frageStellung"),
					rs.getString("musterLoesung"), rs.getString("niveau"), rs.getInt("punktZahl"),
					rs.getBoolean("gestellt")));
		}

		// !!!!!!!!!!!!!!!!!!!!WARNING! YOU MIGHT HAVE TO MAKE FRAGE CLASS
		// IMPLEMENTJAVAFX PROPERTIES!!!!!!!!!!!!!!!!!!!!!!!!!

		// frageStellungCol.setCellValueFactory(new
		// PropertyValueFactory<>("frageStellung"));
		// frageStellung.setCellValueFactory(new
		// PropertyValueFactory<>("frageStellung"));
		frageStellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFragestellung()));
//		punkteCol.setCellValueFactory(features -> new ReadOnlyIntegerWrapper(features.getValue().getPunkte()));
//		// punkteCol.setCellValueFactory(new PropertyValueFactory<>("punktZahl"));
//		themaCol.setCellValueFactory(new PropertyValueFactory<>("themengebiet"));
//		niveauCol.setCellValueFactory(new PropertyValueFactory<>("niveau"));
//		musterloesungCol.setCellValueFactory(new PropertyValueFactory<>("musterLoesung"));

		frageTabelle.setItems(list);

	}

	@FXML
	void detailsAnzeigen(MouseEvent event) throws SQLException {

		String fragestellungdetailliert = frageTabelle.getSelectionModel().getSelectedItem().getFragestellung();
		String musterloesungdetailliert = frageTabelle.getSelectionModel().getSelectedItem().getMusterLoesung();
		String punktzahl = Integer.toString(frageTabelle.getSelectionModel().getSelectedItem().getPunkte());

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
