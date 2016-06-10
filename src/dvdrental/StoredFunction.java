package dvdrental;

/**
 * From the tutorial:
 * http://www.postgresqltutorial.com/postgresql-jdbc/call-postgresql-stored-function/
 * Calling a stored funtion.
 */

import java.sql.CallableStatement;

/**
 * Calling stored functions
 *
 * http://www.postgresqltutorial.com/postgresql-jdbc/call-postgresql-stored-function/
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class StoredFunction {

	private final String url = "jdbc:postgresql://localhost/dvdrental";
	private final String user = "user12";
	private final String password = "user12";

	/**
	 * Connect to the PostgreSQL database
	 *
	 * @return  a Connection object
	 * @throws java.sql.SQLException
	 */
	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	/**
	 * Call a build-in function
	 *
	 * @param s
	 * @return
	 */
	public String properCase(String s) {
		String result = s;
		try (Connection conn = this.connect();
				CallableStatement properCase = conn.prepareCall("{ ? = call initcap( ? ) }")) {

			properCase.registerOutParameter(1, Types.VARCHAR);
			properCase.setString(2, s);
			properCase.execute();
			result = properCase.getString(1);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return result;
	}

	public static void main(String[] args) {
		StoredFunction app = new StoredFunction();
		String elem = new String("this is the actor list:");
		System.out.println(elem);
		System.out.println(app.properCase(elem));
	}
}

// Well, thats one way to do it.
