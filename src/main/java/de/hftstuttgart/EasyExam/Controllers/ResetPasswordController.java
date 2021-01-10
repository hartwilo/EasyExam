/**
 * 
 */
package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import DB.DBConn;
import DB.DBQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class ResetPasswordController {

	Stage stage = new Stage();
	
	@FXML
	private JFXTextField eMailAdresse1;

	@FXML
	private Label labelText1;

	@FXML
	private Label labelText2;

	@FXML
	private Label labelText3;

	@FXML
	private Label lblErrors;

	@FXML
	private JFXButton btnSenden;

	public DBQueries dbQueries = new DBQueries(DBConn.connection);

	// Connection to DB
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet resultSet = null;

	// Action event 
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

	// E-mail check in the DB
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
	public void EmailSenden(String recipient) throws Exception {

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

	// prepare a message
	private Message prepareMessage(Session session, String from, String recipient) {

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Passwort zurücksetzen");
			message.setText("Hallo XY ,\n hier ist Ihre neue Passwort.................");
			return message;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	 public void show () throws IOException  {
		 FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/GUI/ResetPassword.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("ResetPassword");
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.show();
			
		}

}