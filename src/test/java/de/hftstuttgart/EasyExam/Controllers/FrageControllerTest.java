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
 * @author Esma Durmus
 *
 */
@ExtendWith(ApplicationExtension.class)
public class FrageControllerTest {
	FrageController fc = new FrageController();
	

	/**
	 * Test method for {@link de.hftstuttgart.EasyExam.Controllers.FrageController#speichern()}.
	 */
	@Start
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/Frageverwaltung.fxml"));
		stage.setTitle("");
		stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
		stage.show();
		stage.setResizable(false);
		stage.toFront();

	}
	@Test
	void testFrageSpeichern(FxRobot robot) {
		Button btnfrageSpeichern = robot.lookup("#frageSpeichern").queryAs(Button.class);
		Assert.assertNotNull(btnfrageSpeichern);
		robot.clickOn("#frageSpeichern");
		robot.lookup("#frageSpeichern").tryQuery().isPresent();
		
	}
	@Test
	void testZurück(FxRobot robot) {
		Button btnzurück = robot.lookup("#zurück").queryAs(Button.class);
		Assert.assertNotNull(btnzurück);
		robot.clickOn("#zurück");
		robot.lookup("#zurück").tryQuery().isPresent();
		
	}
	
	
	
	
	
	@Test
	void testFragedetailsKorrektEingeben() {
		//fail("Not yet implemented");
	}
	/**
	 * Test method for {@link de.hftstuttgart.EasyExam.Controllers.FrageController#frageSpeichern(javafx.scene.input.MouseEvent)}.
	 */
	@Test
	void testFrageSpeichern() {
	
		//fail("Not yet implemented");
	}
	
	@Test
	void testWarnungAnzeigen() {
		
	//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.hftstuttgart.EasyExam.Controllers.FrageController#frageSpeichernOnEnter(javafx.scene.input.KeyEvent)}.
	 */
	@Test
	void testFrageSpeichernOnEnter() {
		//fail("Not yet implemented");
	}
	@Test
	void testPunkteValidieren() {
		
		boolean bsp1 = fc.punkteValidieren("5");
		boolean bsp2 = fc.punkteValidieren("a");
		Assert.assertEquals(bsp1, true);
		Assert.assertEquals(bsp2, false);
		
	}

	/**
	 * Test method for {@link de.hftstuttgart.EasyExam.Controllers.FrageController#zueruck(javafx.scene.input.MouseEvent)}.
	 */
	@Test
	void testZueruck() {
	//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.hftstuttgart.EasyExam.Controllers.FrageController#frageEditieren(javafx.scene.input.MouseEvent)}.
	 */
	@Test
	void testFrageEditieren() {
		//fail("Not yet implemented");
	}



}



