/**
 * 
 */
package DB;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

	public static Connection connection;
	static TestDB dbc = TestDB.getInstance();
	static DBQueries db;
	
	
	/**
	 * build DB Connection to TestDB
	 */
	@BeforeAll
	static void setUp() {
        dbc.initDBConnection();
        dbc.handleDB();
        connection = TestDB.connection;
        db =new DBQueries(connection);
	}
	
	/**
	 * Test method for {@link DB.DBQueries#frageSpeichern(Frage frage)}.
	 */
	@Test
	void testFrageSpeichern() {
		
        try {
        	
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
                String grundLageNiveau="grundLageNiveau";
                String gut="gut";
                String sehrGut="sehrGut";

                // Do the call:
                Frage frage = new Frage(frageId, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul, grundLageNiveau, gut, sehrGut);
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
                try(PreparedStatement stmt=connection.prepareStatement("SELECT * FROM Frage"))
                {
                	ResultSet rs = stmt.executeQuery();
                    while(rs.next()) {
                    	if(fragestellung.equals(rs.getString("Fragestellung"))) {
                    		assertEquals(fragestellung, rs.getString("Fragestellung"));
                            assertEquals(musterloesung, rs.getString("Musterloesung"));
                            assertEquals(niveau, rs.getInt("Niveau"));
                            assertEquals(gestellt, rs.getBoolean("gestellt"));
                            assertEquals(themengebiet, rs.getString("Themengebiet"));
                            assertEquals(fragekatalog, rs.getString("Fragekatalog"));
                            assertEquals(punkte, rs.getFloat("Punkte"));
                            assertEquals(modul, rs.getString("Modul"));
                            assertEquals(grundLageNiveau, rs.getString("grundLageNiveau"));
                            assertEquals(gut, rs.getString("gut"));
                            assertEquals(sehrGut, rs.getString("sehrGut"));
                    	}
                    }
                }
                catch(Exception e) {
                	e.printStackTrace();
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
                assertEquals("1", rs.getBoolean("gestellt"));
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
                	assertEquals("0", rs.getBoolean("gestellt"));
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
	
	void tearDown() throws Exception {
        
    }

}
