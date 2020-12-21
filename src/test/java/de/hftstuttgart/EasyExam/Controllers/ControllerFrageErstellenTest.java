package de.hftstuttgart.EasyExam.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Models.Frage;


class ControllerFrageErstellenTest {
	

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testThemengebieteLaden() throws SQLException {

		//Assert.assertNotNull(ControllerFrageErstellen.themengebiete);

		/*
		 * PreparedStatement pst = DBConn.connection.prepareStatement(query); ResultSet
		 * rs = pst.executeQuery();
		 */

	}

	@Test
	void testFrageSpeichern() throws SQLException {

		//ControllerFrageErstellen control = new ControllerFrageErstellen();

		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyexam","root",""))
		{
			try(Statement st = conn.createStatement())
			{
			conn.setAutoCommit(false);
			
			st.executeUpdate("DELETE FROM FRAGE");
          
			int id = 123;
			String fragestellung = "testtest";
			String musterloesung = "testantwort";
			int niveau = 2;
			float punkte = 2.5F;
			boolean gestellt = false;
			String themengebiet = "testthema";
			String fragekatalog = "testkatalog";
			String modul = "testmodul";
			
			Frage frage = new Frage(id, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul);
			String query = "Insert INTO Frage(FrageID, Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul) Values(?,?,?,?,?,?,?,?,?)";

			
			
			assertEquals(id, frage.getID());
			assertEquals(fragestellung, frage.getFrageStellung());
			assertEquals(musterloesung, frage.getMusterloesung());
			assertEquals(niveau, frage.getNiveau());
			assertEquals(punkte, frage.getPunkte());
			assertEquals(gestellt, frage.isGestelltbool());
			assertEquals(themengebiet, frage.getThemengebiet());
			assertEquals(fragekatalog, frage.getFragekatalog());
			assertEquals(modul, frage.getModul());
			
			PreparedStatement pst = DBConn.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, id);
			pst.setString(2, fragestellung);
			pst.setString(3, musterloesung);
			pst.setInt(4, niveau);
			pst.setFloat(5, punkte);
			pst.setBoolean(6, gestellt);
			pst.setString(7, themengebiet);
			pst.setString(8, fragekatalog);
			pst.setString(9, modul);
			
			ResultSet rs=pst.executeQuery();
			
			 
			
			try(ResultSet rt = st.executeQuery("SELECT * FROM Frage"))
            {
                assertTrue(rt.next());
                //personId=rs.getInt("id");
                assertEquals(id, rt.getInt("idFrage"));
                assertEquals(fragestellung, rt.getString("Fragestellung"));
                assertEquals(musterloesung, rt.getString("Musterloesung"));
                assertEquals(niveau, rt.getInt("Niveau"));
                assertEquals(punkte, rt.getFloat("Punkte"));
                assertEquals(gestellt, rt.getBoolean("gestellt"));
                assertEquals(themengebiet, rt.getString("Themengebiet"));
                assertEquals(fragekatalog, rt.getString("fragekatalog"));
                assertEquals(modul, rt.getString("modul"));
                assertFalse(rt.next());
            }

			
			
			
		
		}finally {
			conn.rollback();
        }
    }
    catch (SQLException e) {
    	
    	 fail(e.toString());
    	 
		}
		
		
		
	}
}
