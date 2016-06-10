package dvdrental;


/**
 * Inserting data into a database
 *
 * http://www.postgresqltutorial.com/postgresql-jdbc/insert/
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import java.sql.Types;
import dvdrental.Actor;

public class InsertData {

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

	//When we insert a row into a table that has auto generated id, we often want
	// to get the id value back for further processing.

	public long insertActor(Actor actor) {
		String SQL = "INSERT INTO actor(first_name,last_name) "
				+ "VALUES(?,?)";

		long id = 0;

		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL,
						Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, actor.getFirstName());
			pstmt.setString(2, actor.getLastName());

			int affectedRows = pstmt.executeUpdate();
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						id = rs.getLong(1);
					}
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return id;
	}


	// Inserting multiple rows into a table
	public void insertActos(List<Actor> list) {
		String SQL = "INSERT INTO actor(first_name, last_name) "
				+ "VALUES(?,?)";

		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL);) {
			int count = 0;

			for (Actor actor : list) {
				pstmt.setString(1, actor.getFirstName());
				pstmt.setString(2, actor.getLastName());

				pstmt.addBatch();
				count++;
				// execute ever 100 rows or less
				if (count % 100 == 0 || count == list.size()) {
					pstmt.executeBatch();
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}



	public static void main(String[] args) {
		InsertData app = new InsertData();
		/*
		Actor actor = new Actor("John", "Doe");

		long id = app.insertActor(actor);

		System.out.println(
				String.format("%s,%s actor has been inserted with id %d",
						actor.getFirstName(), actor.getLastName(), id));
		 */
		ArrayList<Actor> actorList = new ArrayList<Actor>();
		actorList.add(new Actor("Jown","Smith"));
		actorList.add(new Actor("Lilly","Swan"));
		app.insertActos(actorList);

	}
}
