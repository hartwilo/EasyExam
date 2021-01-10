/**
 * 
 */
package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author bachir
 *
 */
public class PasswortZuruecksetzen1Controller implements Initializable {

	Stage stage = new Stage();

	@FXML
	private Label labelText1;

	@FXML
	private Label eMail;

	@FXML
	private JFXButton btnSpeichern;

	@FXML
	private JFXPasswordField NeuesPasswort;

	@FXML
	private JFXPasswordField PasswortWiederholung;

	@FXML
	private Label lblErrors;

//	public DBQueries dbQueries = new DBQueries(DBConn.connection);

	// Connection to DB
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet resultSet = null;
	
	public String user; // hier soll den wert vom label eMail als Variable gespeichert werden um im updateQuery zu benutzen.

	public static Label static_Label;



	@FXML
	void btnSpeichernClick(ActionEvent event) {
//		String user = eMail.getText();

		if (NeuesPasswort.getText().equals(PasswortWiederholung.getText())) {
			try {

				String updateQuery = "UPDATE Pruefer SET Passwort = ? where eMail ="+user+"";

				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyexamdb?serverTimezone=UTC","root","Bachir1991");
			    stmt = conn.prepareStatement(updateQuery);
				stmt.setString(1, PasswortWiederholung.getText());
				stmt.executeUpdate();

				System.out.println("Passwort wurde erfolgreich geändert");
				lblErrors.setTextFill(Color.GREEN);
				lblErrors.setText("Passwort wurde erfolgreich geändert");
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {

			lblErrors.setTextFill(Color.RED);
			lblErrors.setText("Passwort könnte nicht geändert werden");
		}

	}

	public void show() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/GUI/PasswortZuruecksetzen1.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("PasswortZuruecksetzen");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		static_Label = eMail;
		
	}

}
