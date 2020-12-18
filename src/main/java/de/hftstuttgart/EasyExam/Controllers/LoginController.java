/**
 * 
 */
package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import DB.DBConn;
import DB.DBQueries;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * @author bachir
 *
 */
public class LoginController implements Initializable {

	ResetPasswordController rpController = new ResetPasswordController();

	@FXML
	private AnchorPane AnchorPane;

	@FXML
	private Pane Pane1;

	@FXML
	private Pane Pane2;

	@FXML
	private Pane ImagePane;

	@FXML
	private ImageView imageView;

	@FXML
	private JFXTextField UsernameTextField;

	@FXML
	private JFXCheckBox CheckBox1;

	@FXML
	private JFXButton Login;

	@FXML
	private Label Willkommen;

	@FXML
	private JFXPasswordField PasswordField;

	@FXML
	private Label lblErrors;

	@FXML
	private JFXButton PasswordVergessen;
	public DBQueries dbQueries = new DBQueries(DBConn.connection);

//	@FXML
//	void LoginClick(ActionEvent event) throws IOException {
//
//		StartController.setWindow("StartScreen");
//
//	}

	// Check if Login is successful
	public void handleButtonAction(MouseEvent event) {

		if (event.getSource() == Login) {

			if (LogIn().equals("Login ist erfolgreich")) {

				try {
					Node node = (Node) event.getSource();
					Stage stage = (Stage) node.getScene().getWindow();
					stage.close();

					Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/GUI/StartScreen.fxml")));
					stage.setScene(scene);
					stage.show();

				} catch (IOException e) {

					System.err.println(e.getMessage());
				}
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	// Build connection
	public LoginController() {

		conn = DBConn.connection;

	}

	// show password if checkBox is selected // Noch nicht implementiert//
	@FXML
	void CheckBoxClick1(ActionEvent event) {

		if (CheckBox1.isSelected()) {

		}

	}

	// Connection to DB
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet resultSet = null;

	// Method to check the login data in the DB
	private String LogIn() {

		String eMail = UsernameTextField.getText().toString();
		String Password = PasswordField.getText().toString();
		String vergleichsPW = null;

		try {
			resultSet = dbQueries.getLoginData(eMail);

			if (!resultSet.next()) {
				lblErrors.setTextFill(Color.TOMATO);
				lblErrors.setText("Die Email ist falsch.");
				System.err.println("Login Error");
				return "Error";

			} else {
				vergleichsPW = resultSet.getString("Passwort");
				System.out.println(vergleichsPW);
				if (!vergleichsPW.equals(Password)) {
					lblErrors.setTextFill(Color.TOMATO);
					lblErrors.setText("Das Passwort ist falsch");
					System.err.println("Login Error");
					return "Error";
				} else {
					return "Login ist erfolgreich";
				}

			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return "Exception";

		}

	}

	// show forgotten password window
	@FXML
	void PasswordVergessenClick(ActionEvent event) throws Exception {

		rpController.show();

	}

	// Methods for Junit Testing 
	public String LogInWithTestDBConn(Connection connection, String email, String password) {

		String eMail = email;
		String Password = password;
		String vergleichsPW = null;

		try {
			DBQueries db = new DBQueries(connection);
			resultSet = db.getLoginData(eMail);

			if (!resultSet.next()) {
				lblErrors.setTextFill(Color.TOMATO);
				lblErrors.setText("Die Email ist falsch.");
				System.err.println("Login Error");
				return "Error";

			} else {
				vergleichsPW = resultSet.getString("Passwort");
				System.out.println(vergleichsPW);
				if (!vergleichsPW.equals(Password)) {
					lblErrors.setTextFill(Color.TOMATO);
					lblErrors.setText("Das Passwort ist falsch");
					System.err.println("Login Error");
					return "Error";
				} else {
					return "Login ist erfolgreich";
				}

			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return "Exception";

		}

	}
	
}
