/**
 * 
 */
package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import DB.DBConn;
import DB.DBQueries;
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

	public DBQueries dbQueries = new DBQueries(DBConn.connection);

	// static label to show value of JFXTextField eMailAdresse1 from
	// "ResetPasswordController" in "PasswortZuruecksetzen1Controller"
	public static Label static_Label;

	// check new Password + change Password
	@FXML
	void btnSpeichernClick(ActionEvent event) {

		if (NeuesPasswort.getText().equals(PasswortWiederholung.getText())) {
			try {

				dbQueries.passwortZuruecksetzen(String.valueOf(PasswortWiederholung.getText()), eMail.getText());
				System.out.println("Passwort wurde erfolgreich geändert");
				lblErrors.setTextFill(Color.GREEN);
				lblErrors.setText("Passwort wurde erfolgreich geändert");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {

			lblErrors.setTextFill(Color.RED);
			lblErrors.setText("Passwort könnte nicht geändert werden");
		}

	}

	// show "PasswortZuruecksetzen1" GUI
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
