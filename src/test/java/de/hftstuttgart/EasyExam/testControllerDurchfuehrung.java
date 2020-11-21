/*
 * package de.hftstuttgart.EasyExam;
 * 
 * import static org.junit.Assert.assertTrue; import static
 * org.junit.jupiter.api.Assertions.*;
 * 
 * import java.sql.Connection; import java.sql.DriverManager; import
 * java.sql.PreparedStatement; import java.sql.ResultSet;
 * 
 * import org.junit.jupiter.api.AfterEach; import
 * org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * 
 * import DB.DBConn; import
 * de.hftstuttgart.EasyExam.Controllers.ControllerDurchfuehrung; import
 * javafx.scene.control.RadioButton;
 * 
 * class testControllerDurchfuehrung {
 * 
 * public static Connection connection = null; public static PreparedStatement
 * pst = null;
 * 
 * @BeforeEach void setUp() throws Exception {
 * 
 * 
 * connection =
 * DriverManager.getConnection("jdbc:mysql://localhost:3306/easyexam","root","")
 * ;
 * 
 * }
 * 
 * @AfterEach void tearDown() throws Exception { }
 * 
 * @Test void testPrepareWhereClausel() {
 * 
 * String query = "Select * From Fragen";
 * 
 * pst = DBConn.connection.prepareStatement(query); ResultSet rs =
 * pst.executeQuery();
 * 
 * while (rs.next()) { Frage frage = new Frage(rs.getString("themengebiet"),
 * rs.getString("frageStellung"), rs.getString("musterLoesung"),
 * rs.getString("niveau"), rs.getInt("punktZahl"), rs.getBoolean("gestellt"));
 * ControllerDurchfuehrung cd = new ControllerDurchfuehrung();
 * 
 * String selectedNiveau = cd.niveau.getSelectedToggle().toString();
 * System.out.println(selectedNiveau);
 * 
 * 
 * assertTrue(.equals(frage.getNiveau()));
 * 
 * 
 * 
 * }
 * 
 * 
 * }
 * 
 * @Test void testFragenLaden() { fail("Not yet implemented"); }
 * 
 * @Test void testThemengebieteLaden() { fail("Not yet implemented"); }
 * 
 * @Test void testDetailsAnzeigen() { fail("Not yet implemented"); }
 * 
 * @Test void testZueruckDurchfuehrung() { fail("Not yet implemented"); }
 * 
 * }
 */