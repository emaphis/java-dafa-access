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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TransactionApp {

	private final String url = "jdbc:postgresql://localhost/dvdrental";
	private final String user = "user12";
	private final String password = "user12";

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	/**
	 * Close an AutoCloseable object
	 *
	 * @param closeable
	 * @return itself
	 */
	private TransactionApp close(AutoCloseable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return this;
	}

	/**
	 * insert an actor and assign hin ot a specific film
	 *
	 * @param acotr
	 * @param filmId
	 */
	public void addActorAndAssighnFilm(Actor actor, int filmId) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;

		// insert an actor inot the actor table
		String SQLInsertActor = "INSERT INTO actor (first_name,last_name) "
				+ "VALUES(?,?)";

		// assign actor to a film
		String SQLAssignActor = "INSERT INTO film_actor(actor_id,film_id) "
				+ "VALUES(?,?)";

		int actorId = 0;
		try {
			// connect to the database
			conn = connect();
			conn.setAutoCommit(false);

			// add actor
			pstmt = conn.prepareStatement(SQLInsertActor,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, actor.getFirstName());
			pstmt.setString(2, actor.getLastName());

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				// get actor id
				rs = pstmt.getGeneratedKeys();

				if (rs.next()) {
					actorId = rs.getInt(1);
					if (actorId > 0) {
						pstmt2 = conn.prepareStatement(SQLAssignActor);
						pstmt2.setInt(1, actorId);
						pstmt2.setInt(2, filmId);
						pstmt2.executeUpdate();
					}
				}
			} else {
				// rollback the transaction if the insert failed
				conn.rollback();
			}

			// commit the transaction, if everything is fine
			conn.commit();

			System.out.println(
					String.format("The actor was inserted with id %d and "
							+ "assigned to the film %d", actorId, filmId));

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			// roll back the transaction
			System.out.println("Rolling back the transaction...");
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ee) {
				System.out.println(ex.getMessage());
			}
		} finally {
			this.close(rs)
				.close(pstmt)
				.close(pstmt2)
				.close(conn);
		}
	}

	public static void main(String[] args) {
		TransactionApp app = new TransactionApp();
		// OK transaction
		//app.addActorAndAssighnFilm(new Actor("Bruce","Lee"), 1);

		// Failed Transaction
		//app.addActorAndAssighnFilm(new Actor("Lilly", "Lee"), 9999);  // non-existent movie

	}

}
