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
	DBQueries db =new DBQueries();
	DBConn dbconn = new DBConn();
	
	/**
	 * Test method for {@link DB.DBQueries#frageSpeichern(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testFrageSpeichern() {
		
        try {
        	DBConn.buildConn();
        	
        	
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
                Frage frage = new Frage(frageId, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul);
                assertEquals(1, db.frageSpeichern(frage)); 
                
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
            	 //rollback operation cannot be executed, because AutoCommit=true
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
	void testAlleFrageLaden() {
		try {
        	DBConn.buildConn();
        	try {
           /* try (Statement stmt=connection.createStatement())
            {*/
        		
        		String katalog = "QM SS2019";
                // Do the call:
                ResultSet rs = db.alleFrageLaden(katalog);
                assertTrue(rs.next());
                // Database Checks:
                while(rs.next()) {
                	assertEquals("QM SS2019", rs.getString("Fragekatalog"));
                }
            }
            finally
            {
                 // Undo the testing operations:
            	 //rollback operation cannot be executed, because AutoCommit=true
                 //connection.rollback();
            }
        }
        catch (SQLException e)
        {
            fail(e.toString());
        }
	}
	/**
	 * Test method for {@link DB.DBQueries#frageLaden_niveau(int, String)}
	 */
	@Test
	void testFrageLadenNiveau() {
		fail("not yet implemented");
	}

	/**
	 * Test method for {@link DB.BDQueries#fragenLaden_gestellt)}
	 */
	@Test
	void testFragenLaden_gestellt() {
		try {
        	DBConn.buildConn();
        	try {
           /* try (Statement stmt=connection.createStatement())
            {*/

        		String katalog = "QM SS2019";
                // Do the call:
                ResultSet rs = db.fragenLaden_gestellt(katalog);
                //Database check:
                
                while(rs.next()) {
                    assertEquals("true", rs.getBoolean("gestellt"));
                }  
            }
            finally
            {
                 // Undo the testing operations:
            	 //rollback operation cannot be executed, because AutoCommit=true
                 //connection.rollback();
            }
        }
        catch (SQLException e)
        {
            fail(e.toString());
        }
	}
	
	/**
	 * Test method for {@link DB.DBQueries#frageStellen(Frage, Boolean)}
	 */
	@Test
	void testFrageStellen() {
		try {
        	DBConn.buildConn();
        	try {
           /* try (Statement stmt=connection.createStatement())
            {*/

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

                Frage frage = new Frage(frageId, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul);
                
                // Do the call:
                assertEquals(1, db.frageStellen(frage,true));
                //Database check:
                String query = "SELECT * FROM Frage WHERE idFrage = "+frageId;
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                assertEquals("true", rs.getBoolean("gestellt"));
            }
            finally
            {
                 // Undo the testing operations:
            	 //rollback operation cannot be executed, because AutoCommit=true
                 //connection.rollback();
            }
        }
        catch (SQLException e)
        {
            fail(e.toString());
        }
	}
	
	/**
	 * Test method for {@link DB.DBQueries#setAllFalse()}
	 */
	@Test
	void testSetAllFalse() {
		try {
        	DBConn.buildConn();
        	try {
           /* try (Statement stmt=connection.createStatement())
            {*/

                // Do the call:
                db.setAllFalse();
                //Database check:
                String query = "SELECT * FROM Frage";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                	assertEquals("false", rs.getBoolean("gestellt"));
                }
            }
            finally
            {
                 // Undo the testing operations:
            	 //rollback operation cannot be executed, because AutoCommit=true
                 //connection.rollback();
            }
        }
        catch (SQLException e)
        {
            fail(e.toString());
        }
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
