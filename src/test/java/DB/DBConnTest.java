/**
 * 
 */
package DB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

/**
 * @author Ruth Kallenberger
 *
 */
class DBConnTest {

	public static Connection connection = DBConn.connection;
	/**
	 * Test method for {@link DB.DBConn#buildConn()}.
	 */
	@Test
	void testBuildConn() {
		
		String url = "jdbc:sqlserver://easyexam.database.windows.net:1433;databaseName=EasyExam;user=hartwilo;password=easyexam1!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
		try {
			DBConn.buildConn(url);
			assertEquals(connection.getCatalog(), "easyexam");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
	}

}
