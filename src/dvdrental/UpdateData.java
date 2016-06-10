package dvdrental;

/**
 * From the tutorial:
 * http://www.postgresqltutorial.com/postgresql-jdbc/update/
 *
 * Updating data in a PostgreSQL database.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {

	private final String url = "jdbc:postgresql://localhost/dvdrental";
	private final String user = "user12";
	private final String password = "user12";

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * Update actor's last name given an actor's id
	 *
	 * @param id
	 * @param lastName
	 * @return the number of affected ros
	 */
	
	public int updateLastName(int id, String lastName) {
		String SQL = "UPDATE actor "
				+ "SET last_name = ? "
				+ "WHERE actor_id = ?";

		int affectedrows = 0;

		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL);) {

			pstmt.setString(1, lastName);
			pstmt.setInt(2, id);

			affectedrows = pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return affectedrows;
	}

	public static void main(String[] args) {
		UpdateData app = new UpdateData();
		int num = app.updateLastName(200, "Climo");
		System.out.println(String.format("%d Rows were updated", num));
	}

}
