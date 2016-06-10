package dvdrental;

/**
 * From the tutorial:
 * http://www.postgresqltutorial.com/postgresql-jdbc/connecting-to-postgresql-database/
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	
	private final String url = "jdbc:postgresql://localhost/dvdrental";
	private final String user = "user12";
	private final String password = "user12";
	
	/**
	 * Connect to the PostgreSQL database
	 * 
	 * @return  a Connection object
	 */
	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return conn;
	}

	public static void main(String[] args) {
		Connector cntr = new Connector();
		cntr.connect();
	}

}
