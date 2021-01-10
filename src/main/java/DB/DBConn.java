package DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;


public class DBConn { 
	static {
    System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log =Logger.getLogger(DBConn.class.getName());}
    	
	private static final Logger log;
	public static Connection connection = null;
	
    //Bachir Local
	//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyexamdb?serverTimezone=UTC","root","Bachir1991");
	
	String DB_URL = "jdbc:sqlserver://easyexam.database.windows.net:1433;databaseName=EasyExam;user=hartwilo;password=easyexam1!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";

   	static String DbNameB = "jdbc:mysql://localhost:3306/easyexamdb?serverTimezone=UTC";
    static String DbUserB = "root";
    static String DbPassB = "Bachir1991";
	
    
	static String DbName = "jdbc:mysql://localhost:3306/easyexam";
    static String DbUser = "root";
    static String DbPass = "";
    
	public static void buildConn() {

		try {
			log.info("Connecting to the database ");

			connection = DriverManager.getConnection(DbName, DbUser, DbPass);
			// connection = DriverManager.getConnection(DB_URL);
			
			log.info("Connected to: " + connection.getCatalog());

			/*
			 * log.info("Create database schema"); Scanner scanner = new
			 * Scanner(DBConn.class.getClassLoader().getResourceAsStream("schema.sql"));
			 * Statement statement = connection.createStatement(); while
			 * (scanner.hasNextLine()) { statement.execute(scanner.nextLine());
			 */

		} catch (Exception e) {
			e.printStackTrace();
			log.warning("Connection Failed!");
		}

	}

}

