package dvdrental;

/**
 * From the tutorial:
 * http://www.postgresqltutorial.com/postgresql-jdbc/query/
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryActor {
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


	// Querying data with a statement that returns one r

	/**
	 * Get actors count
	 *
	 * @return int
	 */
	public int getActorsCount() {
		String SQL = "SELECT count(*) FROM actor";
		int count = 0;

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			rs.next();
			count = rs.getInt(1);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return count;
	}


	// Querying data using a statement that returns multiple rows
	/**
	 * Get all the row in the actor table
	 */
	public void getActors() {

		String SQL = "SELECT actor_id, first_name, last_name FROM actor";

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			// display the actor information
			displayActors(rs);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}


	private void displayActors(ResultSet rs) throws SQLException {
		while (rs.next()) {
			System.out.println(rs.getString("actor_id") + "\t"
					+ rs.getString("first_name") + "\t"
					+ rs.getString("last_name"));
		}
	}


	// Querying data using a statement that has parameters
	public void findActorByID (int actorID) {
		String SQL = "SELECT actor_id,first_name,last_name "
				+ "FROM actor "
				+ "WHERE actor_id = ?";

		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			pstmt.setInt(1, actorID);
			ResultSet rs = pstmt.executeQuery();
			displayActors(rs);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void main(String[] args) {
		QueryActor app = new QueryActor();
		int actorCount = app.getActorsCount();
		System.out.println(String.format("%d actors found.", actorCount));

		//app.getActors();
		app.findActorByID(33);

	}
}
