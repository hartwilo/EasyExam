package de.hftstuttgart.EasyExam.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.base.WindowMatchers;
import javafx.stage.Window;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

class KatalogControllerTest {
	
	@Start
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/Katalogverwaltung.fxml"));
		stage.setTitle("");
		stage.setScene(new Scene(root,USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
		stage.show();
		stage.setResizable(false);
		stage.toFront();
		}
	

	@Test
	public void frageAnzeigenTest() {
		fail("Not yet implemented");
		
	}
	
	@Test
	public void fillListTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void showInMainTableTest() {
		
	}
	
	//nur Mouse Event testen
	@Test
	public void fragenLadenTest(FxRobot robot) {
		/*Button btnzurück = robot.lookup("#zurück").queryAs(Button.class);
		Assert.assertNotNull(btnzurück);
		robot.clickOn("#zurück");
		robot.lookup("#zurück").tryQuery().isPresent();*/
	}
	
	@Test
	public void frageLoeschenTest(FxRobot robot) {
		Button frageLoeschen = robot.lookup("#frageLoeschen").queryAs(Button.class);
		Assert.assertNotNull(frageLoeschen);
		robot.clickOn("#frageLoeschen");
		robot.lookup("#frageLoeschen").tryQuery().isPresent();
	}
	
	@Test
	private void katalogLadenTest(FxRobot robot) {
		/*Button frageLoeschen = robot.lookup("#frageLoeschen").queryAs(Button.class);
		Assert.assertNotNull(frageLoeschen);
		robot.clickOn("#frageLoeschen");
		robot.lookup("#frageLoeschen").tryQuery().isPresent();*/
	}
	
	@Test 
	void katalogLoeschenTest(FxRobot robot) {
		Button katalogLoeschen = robot.lookup("#katalogLoeschen").queryAs(Button.class);
		Assert.assertNotNull(katalogLoeschen);
		robot.clickOn("#katalogLoeschen");
		robot.lookup("#katalogLoeschen").tryQuery().isPresent();
		
	}
	
	//nur MouseEvent testen und gucken ob WIndow in FXML vorhanden ist
	@Test
	void katalogAnlegenTest(FxRobot robot) {
		Button katalogAnlegen = robot.lookup("#katalogAnlegen").queryAs(Button.class);
		Assert.assertNotNull(katalogAnlegen);
		robot.clickOn("#katalogAnlegen");
		robot.lookup("#katalogAnlegen").tryQuery().isPresent();
		//FxAssert.verifyThat("Katalogverwaltung.fxml", WindowMatchers.isShowing());
		
	}
	
	@Test
	void frageAnlegenTest(FxRobot robot) {
		Button frageAnlegen = robot.lookup("#frageAnlege").queryAs(Button.class);
		Assert.assertNotNull(frageAnlegen);
		robot.clickOn("#frageAnlegen");
		robot.lookup("#frageAnlegen").tryQuery().isPresent();
	}
	
	@Test
	void katalogSpeichernTest(FxRobot robot) {
		Button katalogSpeichern = robot.lookup("#katalogSpeichern").queryAs(Button.class);
		Assert.assertNotNull(katalogSpeichern);
		robot.clickOn("#katalogSpeichern");
		robot.lookup("#katalogSpeichern").tryQuery().isPresent();
		//FxAssert.verifyThat("Startscreen.fxml", WindowMatchers.isShowing());
		
	}
	@Test
	void pruefungStartenClickTest() {
		fail("Not yet implemented");
	}
	
	@Test
	void StatistikAnsehenClickTest() {
		fail("Not yet implemented");
	}
	
	@Test
	void katalogNameLesenTest() {
		fail("Not yet implemented");
	}

}
