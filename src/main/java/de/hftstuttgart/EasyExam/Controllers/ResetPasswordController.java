/**
 * 
 */
package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import DB.DBConn;
import DB.DBQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.*;


/**
 * @author bachir
 *
 */
public class ResetPasswordController extends PasswortZuruecksetzen1Controller {

	Stage stage = new Stage();
	static FXMLLoader loader;
	PasswortZuruecksetzen1Controller pw = new PasswortZuruecksetzen1Controller();
	
	@FXML
	public JFXTextField eMailAdresse1;

	@FXML
	private Label labelText1;

	@FXML
	private Label labelText2;

	@FXML
	private Label labelText3;
	
    @FXML
    private Label lblErrors4;

	@FXML
	private Label lblErrors;

	@FXML
	private JFXButton btnSenden;

	@FXML
	private Label VerifizierungscodeLabel;

	@FXML
	private JFXTextField Verifizierungscode;

	@FXML
	private JFXButton btnVerifizieren;
	

	public DBQueries dbQueries = new DBQueries(DBConn.connection);

	// Connection to DB
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet resultSet = null;


	/**
	 * The method sends an email to reset the password 
	 * 
	 * @param event
	 */

	int randomCode;
	

	@FXML
	void SendenClick(ActionEvent event) {
		String Empfäger = eMailAdresse1.getText().toString();
		if (event.getSource() == btnSenden) {
			
			if (checkEmail().equals("E-Mail wurde erfolgreich versendent")) {
				lblErrors.setTextFill(Color.GREEN);
				lblErrors.setText("E-Mail wurde erfolgreich versendent");
				try {
					EmailSenden(Empfäger);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			}
		}

	}

	/**
	 * The method checks the email address in the DB
	 * 
	 * @return
	 */
	private String checkEmail() {
		String eMail = eMailAdresse1.getText().toString();

		try {
			resultSet = dbQueries.getLoginData(eMail);

			if (!resultSet.next()) {
				lblErrors.setTextFill(Color.TOMATO);
				lblErrors.setText("Die Email ist in der DB nicht vorhanden");
				System.err.println("E-Mail könnte nicht versendet werden");
				return "Error";

			} else {
				return "E-Mail wurde erfolgreich versendent";
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return "Exception";

		}

	}

	// send an E-mail
	/**
	 * The method sends an email from the host email address to the user eail address 
	 * 
	 * @param recipient String 
	 * @throws Exception
	 */
	public void EmailSenden(String recipient) throws Exception {

		Random rand = new Random();
		randomCode = rand.nextInt(9999);

		String host = "smtp.gmail.com";
		final String from = "easyexam842@gmail.com";
		final String password = "easyexam2020";

		// Setup mail server
		java.util.Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props, new Authenticator() {

			// Password Authentication
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);

			}

		});

		// create a message
		Message messag = prepareMessage(session, from, recipient);
		Transport.send(messag);
		System.out.println("E-Mail wurde erfolgreich versendent");

	}

	/**
	 * The method prepares an message 
	 * 
	 * @param session Session 
	 * @param from String email 
	 * @param recipient String 
	 * @return
	 */
	private Message prepareMessage(Session session, String from, String recipient) {

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Passwort zurücksetzen");
			message.setText("Hallo,"+
					        "\nhier ist Ihre Verifizierungscode: " + randomCode+"\n"+
					        "Diese E-Mail wurde automatisch generiert"+"\n"+
					        "Sollten Sie das Zurücksetzen Ihrer Zugangsdaten naicht angefordert haben, betrachten Sie diese E-Mail"+"\n"+
					        "bitte als gegenstandslos"+"\n"+"\n"+
					        "Mit freundlichen Grüßen,"+"\n"+
					        "Ihr EasyExam Team");
			return message;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * The method shows the ResetPassword GUI
	 * 
	 * @throws IOException
	 */

	public void show() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/GUI/ResetPassword.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("ResetPassword");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();

	}

	@FXML
	void btnVerifizierenClick(ActionEvent event) throws IOException {
		try {
			if (Integer.valueOf(Verifizierungscode.getText()) == randomCode) {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
				pw.show();
				static_Label.setText(eMailAdresse1.getText());

			}else {
				
				lblErrors4.setTextFill(Color.TOMATO);
				lblErrors4.setText("Der Code ist nicht richtig ");
			}

			
		} catch (Exception e) {
			lblErrors4.setTextFill(Color.TOMATO);
			lblErrors4.setText("Bitte geben Sie einen Code ein");
		}
	
	}
}
