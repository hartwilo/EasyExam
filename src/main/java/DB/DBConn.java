package DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;


public class DBConn {
	
	public static Connection connection = null;

	private static final Logger log;
	 
	static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log =Logger.getLogger(DBConn.class.getName());
    }
	
	public static void buildConn() {
		
		try {
		

        log.info("Connecting to the database");
        String DB_URL = "jdbc:sqlserver://easyexam.database.windows.net:1433;databaseName=EasyExam;user=hartwilo;password=easyexam1!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyexamdb?serverTimezone=UTC","root","Bachir1991");
        //connection = DriverManager.getConnection(DB_URL);
        log.info("Database connection test: " + connection.getCatalog());

        /*log.info("Create database schema");
        Scanner scanner = new Scanner(DBConn.class.getClassLoader().getResourceAsStream("schema.sql"));
        Statement statement = connection.createStatement();
        while (scanner.hasNextLine()) {
            statement.execute(scanner.nextLine());*/
     
		}
        catch (Exception e) {
        	e.printStackTrace();
        }
		
		
	}
	
	
	
}

