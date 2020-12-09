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

import com.itextpdf.text.log.SysoCounter;

import de.hftstuttgart.EasyExam.Models.Frage;

/**
 * @author Ruth Kallenberger
 *
 */
class DBQueriesTest {

	public static Connection connection;
	static TestDB dbc = TestDB.getInstance();
	static DBQueries db;
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
    Frage frage = new Frage(fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul, grundLageNiveau, gut, sehrGut);
    
	
	
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

                // Do the call:
        		int frageId=12345;
        	    String fragestellung="akjb";
        	    String musterloesung="badsg";
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
        	    Frage frage2 = new Frage(frageId, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul, grundLageNiveau, gut, sehrGut);
        	    
                assertEquals(1, db.frageSpeichern(frage2)); 
                
                
                
                // Javabean Checks: Check the javabean contains the expected values:
                //assertEquals(frageId, frage.getID());
                assertEquals(fragestellung, frage2.getFrageStellung());
                assertEquals(musterloesung, frage2.getMusterloesung());
                assertEquals(niveau, frage2.getNiveau());
                assertEquals(punkte, frage2.getPunkte());
                assertEquals(gestellt, frage2.isGestelltbool());
                assertEquals(themengebiet, frage2.getThemengebiet());
                assertEquals(fragekatalog, frage2.getFragekatalog());
                assertEquals(modul, frage2.getModul());
                
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
            catch(SQLException e) {
            	fail(e.toString());
            }
        	
        }
	

	/**
	 * Test method for {@link DB.DBQueries#frageLaden()}.
	 */
	@Test
	void testAlleFrageLaden() {
		try {
           /* try (Statement stmt=connection.createStatement())
            {*/
			
	           db.frageSpeichern(frage);
                // Do the call:
                ResultSet rs = db.alleFrageLaden(fragekatalog);
//                assertTrue(rs.next());
                // Database Checks:
                while(rs.next()) {
                	assertEquals(fragekatalog, rs.getString("Fragekatalog"));
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
           /* try (Statement stmt=connection.createStatement())
            {*/
        		db.frageSpeichern(frage);
        		ResultSet rs2 = db.alleFrageLaden(fragekatalog);
                while(rs2.next()) {
                	if(fragestellung.equals(rs2.getString("Fragestellung"))) {
                		frage.setID(rs2.getInt("idFrage"));
                	}
                }
        		db.frageStellen(frage, true);
        		
                // Do the call:
        		ResultSet rs1 =db.alleFrageLaden(fragekatalog);
        		while(rs1.next()) {
        			if(fragestellung.equals(rs1.getString("Fragestellung"))) {
                		System.out.println(rs1.getBoolean("gestellt"));
                	}
        		}
                ResultSet rs = db.fragenLaden_gestellt(fragekatalog);
                System.out.println(rs.getRow());
                //Database check:
                
                while(rs.next()) {
                    assertEquals(true, rs.getBoolean("gestellt"));
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
           /* try (Statement stmt=connection.createStatement())
            {*/

        		
                db.frageSpeichern(frage);
                ResultSet rs1 = db.alleFrageLaden(fragekatalog);
                while(rs1.next()) {
                	if(fragestellung.equals(rs1.getString("Fragestellung"))) {
                		frage.setID(rs1.getInt("idFrage"));
                	}
                }
                
                // Do the call:
                assertEquals(1, db.frageStellen(frage, true));
                //Database check:
                String query = "SELECT * FROM Frage WHERE idFrage = "+ "'" + frage.getID() + "'";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                assertEquals(true, rs.getBoolean("gestellt"));
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
           /* try (Statement stmt=connection.createStatement())
            {*/

                // Do the call:
                db.setAllFalse();
                //Database check:
                String query = "SELECT * FROM Frage";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                	assertEquals(false, rs.getBoolean("gestellt"));
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
