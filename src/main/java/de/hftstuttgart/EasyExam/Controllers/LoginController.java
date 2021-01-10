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
import de.hftstuttgart.EasyExam.Main.Main;
import de.hftstuttgart.EasyExam.Models.Pruefer;
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
	
	static FXMLLoader loader;
	
	private String eMail;
	private String Password;
	private String vergleichsPW;
	public Pruefer pruefer;
	public static Pruefer globPruef = null;

	

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

	/**
	 * Check if Login is successful
	 * 
	 * @param event Login button is clicked 
	 */
	public void handleButtonAction(MouseEvent event) {

		if (event.getSource() == Login) {

			if (LogIn().equals("Login ist erfolgreich")) {

				try {
					Node node = (Node) event.getSource();
					Stage stage = (Stage) node.getScene().getWindow();
					stage.close();

					Scene scene = new Scene(loader.load());
					stage.setScene(scene);
					stage.show();
					
					

				} catch (IOException e) {

					System.err.println(e.getMessage());
				}
			}
		}
	}

	/**
	 * initialize the LoginController
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loader = new FXMLLoader(Main.class.getResource("/GUI/Pruefung2.fxml"));

	}

	// show password if checkBox is selected // Noch nicht implementiert//
	@FXML
	void CheckBoxClick1(ActionEvent event) {

		if (CheckBox1.isSelected()) {

		}

	}

	// Connection to DB
	Connection conn = null;
//	conn = DBConn.connection;
	PreparedStatement stmt = null;
	ResultSet resultSet = null;
	
	
	/**
	 * Method to check the login data in the DB
	 * 
	 * @return String with error or login 
	 */
	public String LogIn() {

		eMail = UsernameTextField.getText().toString();
		Password = PasswordField.getText().toString();
		vergleichsPW = null;
		

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
					System.out.println("akt. Email: " + eMail);
					System.out.println("Conn: " + DBConn.connection.toString());
					try{
						globPruef = dbQueries.getPruefer(eMail);
						System.out.println(globPruef.getVorname() + " " + globPruef.getNachname());
					}catch(Exception e) {
						e.toString();
					}
					return "Login ist erfolgreich";
					
				}
				

			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return "Exception";

		}
		

	}

	/**
	 * show forgotten password window
	 * 
	 * @param event button is clicked 
	 * @throws Exception
	 */ 
	@FXML
	void PasswordVergessenClick(ActionEvent event) throws Exception {

		rpController.show();

	}
	
	/**
	 * Methods for Junit Testing 
	 * 
	 * @param connection Connection for test database 
	 * @param email String with the email address 
	 * @param password String with the password 
	 * @return String with error or login 
	 */
	public String LogInWithTestDBConn(Connection connection, String email, String password) {

		eMail = email;
		Password = password;
		vergleichsPW = null;

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

	/**
	 * The method returns the email address
	 * 
	 * @return email 
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * The method returns the password 
	 * 
	 * @return password 
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * The method returns an password to compare with 
	 * 
	 * @return password to compare 
	 */
	public String getVergleichsPW() {
		return vergleichsPW;
	}
	
	
}
