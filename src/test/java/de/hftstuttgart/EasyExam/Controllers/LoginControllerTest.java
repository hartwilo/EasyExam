package de.hftstuttgart.EasyExam.Controllers;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loadui.testfx.GuiTest;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import DB.DBConn;
import DB.DBQueries;
import DB.TestDB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Ruth Kallenberger
 *
 */

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest{
	LoginController login = new LoginController();

	@Start
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
		stage.setTitle("EasyExam");
		stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
		stage.show();
		stage.setResizable(false);
		stage.toFront();
		
	}
	
	
	/**
	 * Test method for {@link de.hftstuttgart.EasyExam.Controller.LoginController.handleButtonAction(MouseEvent event)}
	 */
	@Test
	void testHandleButtonAction(FxRobot robot) {
		Button btnLogin = robot.lookup("#Login").queryAs(Button.class);
		assertNotNull(btnLogin);
		robot.clickOn("#Login");
		robot.lookup("#Login").tryQuery().isPresent();
	}

	@Test
	void testInitialize() {
		fail("Not yet implemented");
	}

	@Test
	void testLoginController() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckBoxClick1() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.hftstuttgart.EasyExam.Controller.LoginController.LogIn()}
	 */
	@Test
	void testLogIn() {
		TestDB dbc = TestDB.getInstance();
		Connection connection;
		//DBQueries db;
		dbc.initDBConnection();
        dbc.handleDB();
        connection = TestDB.connection;
        //db =new DBQueries(connection);
        
		//Test with right LoginData
		String email = "sebastian.speiser@hft-stuttgart.de";
		String password = "123456";
		assertEquals("Login ist erfolgreich", login.LogInWithTestDBConn(connection, email, password));
		
		//Test with wrong password 
		password = "password";
		assertEquals("Error", login.LogInWithTestDBConn(connection, email, password));
		
		//Test with wrong email
		email="prof@hft-stuttgart.de";
		assertEquals("Error", login.LogInWithTestDBConn(connection, email, password));
		
	}
	
	@Test
	void testPasswordVergessenClick() {
		fail("Not yet implemented");
	}

}
