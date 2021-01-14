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
import de.hftstuttgart.EasyExam.Models.Note;
import de.hftstuttgart.EasyExam.Models.Pruefer;
import de.hftstuttgart.EasyExam.Models.Pruefung;
import de.hftstuttgart.EasyExam.Models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    
    String fragestellung2 = "ab";
    String musterloesung2 = "bc";
    int niveau2 = 2;
    float punkte3 = 2;
    boolean gestellt3 = false;
    String themengebiet2 = "Informatik";
    String fragekatalog2 = "36";
    String modul2 = "Prog2";
    Frage frage3 = new Frage(fragestellung2, musterloesung2, niveau2, themengebiet2, fragekatalog2, punkte3, gestellt3, modul2, grundLageNiveau, gut, sehrGut);
    
    
	
	
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
        	    String fragekatalog="36";
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
	 * Test method for {@link DB.DBQueries#testFrageLaden_themengebiet)}
	 */
	@Test
	void testFrageLaden_themengebiet() {
		try {
			db.frageSpeichern(frage);
			ResultSet rs = db.frageLaden_themengebiet(frage.getThemengebiet(), frage.getFragekatalog());
			while(rs.next()) {
				assertEquals(frage.getThemengebiet(), rs.getString("themengebiet"));
			}
			
		}
		catch(SQLException e) {
			fail(e.toString());
		}

	}
	
	/**
	 * Test method for {@link DB.DBQueries#frageLaden_Niveau(int, String)}
	 */
	@Test
	void testFrageLaden_Niveau() {
		try {
			db.frageSpeichern(frage);
			ResultSet rs = db.frageLaden_niveau(frage.getNiveau(), frage.getFragekatalog());
			
			while(rs.next()) {
				assertEquals(frage.getNiveau(), rs.getInt("Niveau"));
			}
		}
			catch(SQLException e) {
				fail(e.toString());
			}
		
	}
	
	/**
	 * Test method for {@link DB.DBQueries#frageLaden_niveau_themengebiet(int, String, String)}
	 */
	@Test
	void testFrageLaden_Niveau_themengebiet() {
		try {
			db.frageSpeichern(frage);
			ResultSet rs = db.frageLaden_niveau_themengebiet(frage.getNiveau(),frage.getThemengebiet(), frage.getFragekatalog());
			
			while(rs.next()) {
				assertEquals(frage.getNiveau(), rs.getInt("niveau"));
				assertEquals(frage.getThemengebiet(), rs.getString("themengebiet"));
			}
	
			}
		catch(SQLException e) {
			fail(e.toString());
		}
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
		try {
			
			db.frageSpeichern(frage);
            ResultSet rs1 = db.alleFrageLaden(fragekatalog);
            while(rs1.next()) {
            	if(fragestellung.equals(rs1.getString("Fragestellung"))) {
            		frage.setID(rs1.getInt("idFrage"));
            		System.out.println(rs1.getString("Fragestellung"));
            		System.out.println(rs1.getInt("idFrage") + "   <-- ID in der Datenbank");
            	}
            }
            System.out.println(frage.getID() + "    <-- ID Frageobjekt");
			
			db.frageLoeschen(frage.getID());
			String query = "Select * From Frage";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				assertNotEquals(frage.getID(), rs.getInt("idFrage"));
				System.out.println("IDS: " + rs.getInt("idFrage"));
			}
			
		}
		catch (SQLException e){
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#katalogLoeschen(String)}
	 */
	@Test
	void testKatalogLoeschen() {
		
		try {
			db.frageSpeichern(frage);
			db.frageSpeichern(frage3);

				
			
			db.katalogLoeschen(frage.getFragekatalog());
			db.katalogLoeschen(fragekatalog);
			String query = "Select * From Frage";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()){
				assertNotEquals(frage.getFragekatalog(), rs.getString("Fragekatalog")); 
				}
			
			//assertNotEquals(frage.getFragekatalog(), rs.getString("Fragekatalog"));
		
		
		}
		catch(SQLException e) {
			fail(e.toString());
		}
		
	}

	/**
	 * Test method for {@link DB.DBQueries#themengebieteAuslesen()}.
	 */
	@Test
	void testThemengebieteAuslesen() {
		try {
			db.frageSpeichern(frage);
			ObservableList<String> list = db.themengebieteAuslesen(frage.getFragekatalog());
			assertEquals(frage.getThemengebiet(), list.get(0).toString());
			
			
		}
		catch(SQLException e) {
			fail(e.toString());
		}
		}


	/**
	 * Test method for {@link DB.DBQueries#katalogeAuslesen()}.
	 */
	@Test
	void testKatalogeAuslesen() {
		try {
			db.frageSpeichern(frage);
			ObservableList<String> list = db.katalogeAuslesen();
			assertEquals(frage.getFragekatalog(), list.get(0).toString());
			
		}
		catch(SQLException e){
			fail(e.toString());	
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#getLoginData(String)}
	 */
	@Test
	void testGetLoginData() {
		try {
			Pruefer pruefer = new Pruefer(555555,"Mosler","Christof","christof.mosler@hft-stuttgart.de", "datenschutz");
			Statement stmt = connection.createStatement();
			String query = "Insert Into Pruefer(PersNr, Nachname, Vorname, eMail, Passwort) Values(555555,'Mosler','Christof','christof.mosler@hft-stuttgart.de', 'datenschutz')";
			stmt.executeUpdate(query);
			ResultSet rs = db.getLoginData(pruefer.geteMail());
			assertEquals(pruefer.geteMail(), rs.getString("eMail"));
			assertEquals(pruefer.getPasswort(), rs.getString("Passwort"));
			
			stmt.executeUpdate("DELETE FROM Pruefer WHERE PersNr=" + "'" + pruefer.getPersNr() + "'");
		}
		catch(SQLException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#erreichte_punkte_speichern(Frage, double)}
	 */
	@Test
	void testErreichte_punkte_speichern() {
		try {
			Statement stmt = connection.createStatement();
			int frageId=54321;
    	    String fragestellung="frage";
    	    String musterloesung="loesung";
    	    int niveau=1;
    	    float punkte=3;
    	    String punkte2="3";
    	    boolean gestellt=false;
    	    String gestellt2="false";
    	    String themengebiet="Mathe";
    	    String fragekatalog="36";
    	    String modul="Mathe2";
    	    String grundLageNiveau="grundLageNiveau";
    	    String gut="gut";
    	    String sehrGut="sehrGut";
    	    Frage fragePunkte = new Frage(frageId, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul, grundLageNiveau, gut, sehrGut);
    	    db.frageSpeichern(fragePunkte);
    	   
    	    String getFrage = "SELECT idFrage FROM Frage WHERE fragestellung=" + "'" + fragestellung + "'";
    	    
    	    ResultSet rs1 = stmt.executeQuery(getFrage);
    	    while(rs1.next()) {
    	    	frageId = rs1.getInt("idFrage");
    	    }
    	    
    	    fragePunkte.setID(frageId);;
    	    
    	    double erpunkte = 2.5;
    	    db.erreichte_punkte_speichern(fragePunkte, erpunkte);
    	    
    	    
    	    
    	    String query = "SELECT Punkte_erreicht FROM Frage WHERE idFrage=" + "'" + frageId + "'";
    	    ResultSet rs = stmt.executeQuery(query);
    	    
    	    while(rs.next()) {
    	    	assertEquals(rs.getDouble("Punkte_erreicht"), erpunkte);
    	    }
		}
		catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#frage_selektieren(Frage)}
	 */
	@Test
	void testFrage_selektieren() {
		try {
			int frageId = 7843;
			String fragestellung="testFrage";
			String musterloesung="loesung";
		    int niveau=1;
		    float punkte=3;
		    String punkte2="3";
		    boolean gestellt=false;
		    String gestellt2="false";
		    String themengebiet="Mathe";
		    String fragekatalog="36";
		    String modul="Mathe2";
		    String grundLageNiveau="grundLageNiveau";
		    String gut="gut";
		    String sehrGut="sehrGut";
		    Frage fragePunkte = new Frage(fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul, grundLageNiveau, gut, sehrGut);
		    db.frageSpeichern(fragePunkte);
		    
		    Statement stmt = connection.createStatement();
		    
		    String getFrage = "SELECT idFrage FROM Frage WHERE fragestellung=" + "'" + fragestellung + "'";
    	    
    	    ResultSet rs1 = stmt.executeQuery(getFrage);
    	    while(rs1.next()) {
    	    	frageId = rs1.getInt("idFrage");
    	    }
    	    
    	    fragePunkte.setID(frageId);
    	    ResultSet rs = db.frage_selektieren(fragePunkte);
    	    while(rs.next()) {
    	    	assertEquals(rs.getString("fragestellung"), fragestellung);
    	    	assertEquals(rs.getString("musterloesung"), musterloesung);
    	    	assertEquals(rs.getInt("niveau"), niveau);
    	    	assertEquals(rs.getFloat("punkte"), punkte);
    	    	assertEquals(rs.getBoolean("gestellt"), gestellt);
    	    	assertEquals(rs.getString("themengebiet"), themengebiet);
    	    	assertEquals(rs.getString("modul"), modul);
    	    }
    	    
		}
	    catch ( SQLException e) {
	    	fail(e.toString());
	    }
	}
	
	/**
	 * Test method for {@link DB.DBQueries#studentenSpeichern(ObservableList)}
	 */
	@Test
	void testStudentenSpeichern() {
		try {
			ObservableList<Student> studenten = FXCollections.observableArrayList();
			
			Statement stmt = connection.createStatement();
			
			int matrikelnr = 123456;
			String vorname = "Hans";
			String nachname = "Müller";
			int semester =5;
			String studiengang = "WI";
			
			Student s1 = new Student(matrikelnr, vorname, nachname, semester, studiengang);
			
			int matrikelnr2 = 654321;
			String vorname2 = "Gustav";
			String nachname2 = "Maier";
			int semester2 =7;
			String studiengang2 = "Inf";
			
			Student s2 = new Student(matrikelnr2, vorname2, nachname2, semester2, studiengang2);
			
			studenten.add(s1);
			studenten.add(s2);
			
			assertEquals(1, db.studentenSpeichern(studenten));
			stmt.executeUpdate("DELETE FROM Student WHERE Matrikelnr=" + "'" + matrikelnr + "'");
			stmt.executeUpdate("DELETE FROM Student WHERE Matrikelnr=" + "'" + matrikelnr2 + "'");
		}
		catch(SQLException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#studentenLaden()}
	 */
	@Test
	void testStudentenLaden() {
		try {
			ObservableList<Student> studenten = FXCollections.observableArrayList();;

			int matrikelnr = 444444;
			String vorname = "Hans";
			String nachname = "Müller";
			int semester =5;
			String studiengang = "WI";
			
			Student s1 = new Student(matrikelnr, vorname, nachname, semester, studiengang);
			studenten.add(s1);
			
			db.studentenSpeichern(studenten);
			
			ResultSet rs = db.studentenLaden();
			
			while(rs.next()) {
				assertEquals(matrikelnr, rs.getInt("Matrikelnr"));
				assertEquals(vorname, rs.getString("Vorname"));
				assertEquals(nachname, rs.getString("Nachname"));
				assertEquals(semester, rs.getInt("Semester"));
				assertEquals(studiengang, rs.getString("Studiengang"));
			}
			
		}
		catch(SQLException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#select_erreichte_punkte(Frage)}
	 */
	@Test
	void testSelect_erreichte_punkte() {
		try {
			int frageId = 7843;
			String fragestellung="MatheFrage";
			String musterloesung="loesung";
		    int niveau=1;
		    float punkte=3;
		    String punkte2="3";
		    boolean gestellt=false;
		    String gestellt2="false";
		    String themengebiet="Mathe";
		    String fragekatalog="36";
		    String modul="Mathe2";
		    String grundLageNiveau="grundLageNiveau";
		    String gut="gut";
		    String sehrGut="sehrGut";
		    Frage fragePunkte = new Frage(fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul, grundLageNiveau, gut, sehrGut);
		    db.frageSpeichern(fragePunkte);
		    
		    Statement stmt = connection.createStatement();
		    
		    String getFrage = "SELECT idFrage FROM Frage WHERE fragestellung=" + "'" + fragestellung + "'";
    	    
    	    ResultSet rs1 = stmt.executeQuery(getFrage);
    	    while(rs1.next()) {
    	    	frageId = rs1.getInt("idFrage");
    	    }
    	    
    	    fragePunkte.setID(frageId);
    	    
    	    float erPunkte = 2;
    	    db.erreichte_punkte_speichern(fragePunkte, erPunkte);
    	    assertEquals(erPunkte, db.select_erreichte_punkte(fragePunkte));
    	    
		}
		catch(SQLException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#reset()}
	 */
	@Test
	void testReset() {
		try {
			int frageId = 7843;
			String fragestellung="MatheFrage";
			String musterloesung="loesung";
		    int niveau=1;
		    float punkte=3;
		    String punkte2="3";
		    boolean gestellt=false;
		    String gestellt2="false";
		    String themengebiet="Mathe";
		    String fragekatalog="36";
		    String modul="Mathe2";
		    String grundLageNiveau="grundLageNiveau";
		    String gut="gut";
		    String sehrGut="sehrGut";
		    Note notizen = new Note();
		    float erPunkte = 2;
		    Frage fragePunkte = new Frage(fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul, grundLageNiveau, gut, sehrGut);
		    db.frageSpeichern(fragePunkte);
		    
		    Statement stmt = connection.createStatement();
		    
		    String getFrage = "SELECT idFrage FROM Frage WHERE fragestellung=" + "'" + fragestellung + "'";
		  
		    ResultSet rs1 = stmt.executeQuery(getFrage);
		    while(rs1.next()) {
		    	frageId = rs1.getInt("idFrage");
		    }
		    
		    fragePunkte.setID(frageId);
    	    db.erreichte_punkte_speichern(fragePunkte, erPunkte);
    	    db.notizienSpeichern(notizen, fragePunkte);
    	    db.reset();
    	    String query = "SELECT gestellt, Notizien, Punkte_erreicht FROM Frage WHERE idFrage =" + "'" + frageId + "'";
    	    ResultSet rs = stmt.executeQuery(query);
    	    while(rs.next()) {
    	    	assertEquals(rs.getBoolean("gestellt"), false);
    	    	assertEquals(rs.getString("Notizien"), null);
    	    	assertEquals(rs.getFloat("Punkte_erreicht"), 0);
    	    }
		}
		catch(SQLException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#getPruefer(String)}
	 */
	@Test
	void testGetPruefer() {
		try {
			int persNr = 1234;
			String nachname ="Müller";
			String vorname = "Heinz";
			String eMail ="abc@hft.de";
			String passwort = "4321";
			
			Pruefer p = new Pruefer(persNr, nachname, vorname, eMail, passwort);
			String query = "Insert Into Pruefer(PersNr, Nachname, Vorname, eMail, Passwort) Values (1234,'Müller','Heinz','abc@hft.de','4321')";
			Statement stmt = connection.createStatement();
			stmt.execute(query);
			
			assertEquals(p.getPersNr(), db.getPruefer(eMail).getPersNr());
			
		}
		catch(SQLException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#pruefungSpeichern(de.hftstuttgart.EasyExam.Models.Pruefung)}
	 */
	@Test 
	void testPruefungSpeichern() {
		try {
			int idPruefung=3;
			String bezeichnung="abc";
			float note=2;
			float punkteGesamt=25;
			String fragekatalog="Mathe";
			int student_mtkr=123456;
			int prueferNr=1234;
			
			Pruefung pr = new Pruefung(idPruefung, bezeichnung, note, punkteGesamt, student_mtkr, prueferNr);
			
			assertEquals(1, db.pruefungSpeichern(pr));
		} catch (SQLException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Test method for {@link DB.DBQueries#allePruefung()}
	 */
	@Test
	void testAllePruefung() {
		try {
			ObservableList<Pruefung> pruefung = FXCollections.observableArrayList();
			int idPruefung=5;
			String bezeichnung="def";
			float note=2;
			float punkteGesamt=25;
			String fragekatalog="ITM";
			int student_mtkr=123456;
			int prueferNr=1234;
			
			Pruefung pr = new Pruefung(idPruefung, bezeichnung, note, punkteGesamt, student_mtkr, prueferNr);
			db.pruefungSpeichern(pr);
			
			pruefung = db.allePruefung(bezeichnung);
			for(int i = 0; i < pruefung.size(); i++) {
				if(pruefung.get(i).getIdPruefung() == idPruefung) {
					assertEquals(bezeichnung, pruefung.get(i).getBezeichnung());
					assertEquals(note, pruefung.get(i).getNote());
					assertEquals(punkteGesamt, pruefung.get(i).getPunkteGesamt());
					assertEquals(student_mtkr, pruefung.get(i).getStudent_mtkr());
					assertEquals(prueferNr, pruefung.get(i).getPrueferNr());
				}
			}
		}
		catch (SQLException e) {
			fail(e.toString());
		}
	}
}
