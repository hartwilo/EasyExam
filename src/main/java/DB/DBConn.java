package DB;


import java.sql.*;
import java.sql.Connection;
import java.util.*;
import java.util.logging.Logger;


public class DBConn {

	private static final Logger log;
	
	static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log =Logger.getLogger(DBConn.class.getName());
    }
	
	public static void buildConn() {
		
		try {
		log.info("Loading application properties");
        //Properties properties = new Properties();
        //properties.load(DBConn.class.getClassLoader().getResourceAsStream("application.properties"));
        
        //log.info(properties.toString());

        log.info("Connecting to the database");
        //Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        String DB_URL = "jdbc:sqlserver://easyexam.database.windows.net:1433;databaseName=EasyExam;user=hartwilo;password=easyexam1!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
        Connection connection = DriverManager.getConnection(DB_URL);
        log.info("Database connection test: " + connection.getCatalog());

        /*log.info("Create database schema");
        Scanner scanner = new Scanner(DBConn.class.getClassLoader().getResourceAsStream("schema.sql"));
        Statement statement = connection.createStatement();
        while (scanner.hasNextLine()) {
            statement.execute(scanner.nextLine());*/
            

        log.info("Closing database connection");
        connection.close();
        
		}
        catch (Exception e) {
        	e.printStackTrace();
        }
		
		
	}
	
	
	
}

