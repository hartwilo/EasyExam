/**
 * 
 */
package DB;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.hftstuttgart.EasyExam.Models.Frage;

/**
 * @author Ruth Kallenberger
 *
 */
class DBQueriesTest {

	public static Connection connection = DBConn.connection;
	String url = "jdbc:sqlserver://easyexam.database.windows.net:1433;databaseName=EasyExam;user=hartwilo;password=easyexam1!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";

	
	
	/**
	 * Test method for {@link DB.DBQueries#frageSpeichern(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testFrageSpeichern() {
		DBQueries db =new DBQueries();
		DBConn dbconn = new DBConn();
		
        try {
        	DBConn.buildConn(url);
        	
        	
        	try {
           /* try (Statement stmt=connection.createStatement())
            {
            

                // Initial cleanup:
                stmt.executeUpdate("DELETE * FROM Frage");*/

                // Setting input parameters:
                int frageId=12345;
                String fragestellung="a";
                String musterloesung="b";
                int niveau=1;
                float punkte=3;
                String punkte2="3";
                boolean gestellt=false;
                String gestellt2="false";
                String themengebiet="Mathe";
                String fragekatalog="35";
                String modul="Mathe2";

                // Do the call:
                assertEquals(1, db.frageSpeichern(fragestellung, musterloesung, niveau, punkte2, gestellt2, themengebiet, fragekatalog, modul)); 
                Frage frage = new Frage(frageId, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul);
                
                // Javabean Checks: Check the javabean contains the expected values:
                //assertEquals(frageId, frage.getID());
                assertEquals(fragestellung, frage.getFrageStellung());
                assertEquals(musterloesung, frage.getMusterloesung());
                assertEquals(niveau, frage.getNiveau());
                assertEquals(punkte, frage.getPunkte());
                assertEquals(gestellt, frage.isGestelltbool());
                assertEquals(themengebiet, frage.getThemengebiet());
                assertEquals(fragekatalog, frage.getFragekatalog());
                assertEquals(modul, frage.getModul());
                
                // Database Checks:
                // Check the Person table contains one row with the expected values:
                Statement stmt=connection.createStatement();
                try(ResultSet rs=stmt.executeQuery("SELECT * FROM Frage"))
                {
                    assertTrue(rs.next());
                    frageId=rs.getInt("idFrage");
                    assertEquals(fragestellung, rs.getString("Fragestellung"));
                    assertEquals(musterloesung, rs.getString("Musterloesung"));
                    assertEquals(niveau, rs.getInt("Niveau"));
                    assertEquals(gestellt, rs.getBoolean("gestellt"));
                    assertEquals(themengebiet, rs.getString("Themengebiet"));
                    assertEquals(fragekatalog, rs.getString("Fragekatalog"));
                    assertEquals(punkte, rs.getFloat("Punkte"));
                    assertEquals(modul, rs.getString("Modul"));
                    assertFalse(rs.next());
                }
            }
            finally
            {
                 // Undo the testing operations:
            	 //rollback operation cannot be executed 
                 //connection.rollback();
            }
        }
        catch (SQLException e)
        {
            fail(e.toString());
        }
	}
	

	/**
	 * Test method for {@link DB.DBQueries#frageLaden()}.
	 */
	@Test
	void testFrageLaden() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link DB.DBQueries#frageLoeschen(int)}.
	 */
	@Test
	void testFrageLoeschen() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link DB.DBQueries#themengebieteAuslesen()}.
	 */
	@Test
	void testThemengebieteAuslesen() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link DB.DBQueries#katalogeAuslesen()}.
	 */
	@Test
	void testKatalogeAuslesen() {
		fail("Not yet implemented");
	}

}
