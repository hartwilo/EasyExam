package de.hftstuttgart.EasyExam.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.GuiTest;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.testfx.framework.junit5.ApplicationTest;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import DB.DBConn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

/**
 * 
 * @author Ruth Kallenberger
 *
 */

class LoginControllerTest extends GuiTest{
	
	LoginController lc;
	
	/*@Mock
    LoginController login; 

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule(); */
    
	@Test
	void checkComponentsTest() {
		JFXTextField UsernameTextField = find("#UsernameTextField");
		assertTrue(UsernameTextField.getText().equals("Username oder E-mail"));
//		JFXButton Login = find("#Login");
//		assertTrue(Login.getText().equals("Absenden"));
//		Label Willkommen = find("#Willkommen");
//		assertTrue(Willkommen.getText().equals("Willkommen"));
		JFXPasswordField PasswordField = find("#PasswordField");
		assertTrue(PasswordField.getText().equals("Password"));
//		JFXButton PasswordVergessen = find("#PasswordVergessen");
//		assertTrue(PasswordVergessen.getText().equals("Password vergessen?"));
	}
	
	@Test
	void testHandleButtonAction() {
		fail("Not yet implemented");
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

	@Test
	void testPasswordVergessenClick() {
		fail("Not yet implemented");
	}

	@Override
	protected Parent getRootNode() {
		Parent parent = null;
		  try {
		    parent = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		  return parent;
	}

}
