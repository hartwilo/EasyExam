/**
 * 
 */
package de.hftstuttgart.EasyExam.Controllers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.stage.Stage;

import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

import org.testfx.api.FxRobot;

/**
 * @author bachir
 *
 */
@ExtendWith(ApplicationExtension.class)
public class StartControllerTest {

	
	@Start
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/StartScreen.fxml"));
		stage.setTitle("EasyExam");
		stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
		stage.show();
		stage.setResizable(false);
		stage.toFront();

	}

	/**
	 * Test method for
	 * {StartController#setWindow(java.lang.String)}.
	 */
	@Test
	void testSetWindow() {

	}

	/**
	 * Test method for
	 * {StartController#katalogErstellen(javafx.scene.input.MouseEvent)}.
	 */
	@Test
	void testKatalogErstellen(FxRobot robot) {
		Button btnkatalogErstellen = robot.lookup("#katalogErstellen").queryAs(Button.class);
		Assert.assertNotNull(btnkatalogErstellen);
		robot.clickOn("#katalogErstellen");
		robot.lookup("#katalogErstellen").tryQuery().isPresent();

	}

	/**
	 * Test method for
	 * {StartController#pruefungStarten(javafx.scene.input.MouseEvent)}.
	 */
	@Test
	void testPruefungStarten(FxRobot robot) {
		Button btnPruefungStarten = robot.lookup("#pruefungStarten").queryAs(Button.class);
		Assert.assertNotNull(btnPruefungStarten);
		robot.clickOn("#pruefungStarten");
	

	}

	/**
	 * Test method for
	 * {StartController#statistikAnsehen(javafx.scene.input.MouseEvent)}.
	 */
	@Test
	void testStatistikAnsehen(FxRobot robot) {
		Button btnstatistikAnsehen = robot.lookup("#statistikAnsehen").queryAs(Button.class);
		Assert.assertNotNull(btnstatistikAnsehen);
		robot.clickOn("#statistikAnsehen");

	}

}
