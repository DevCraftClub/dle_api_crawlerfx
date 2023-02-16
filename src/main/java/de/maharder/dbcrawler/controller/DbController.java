package de.maharder.dbcrawler;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.controlsfx.control.Notifications;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@AllArgsConstructor
public class DbController {
	private String host;
	private int port;
	private String username;
	private String password;
	private String name;

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", host,	port, name), username, password);
	}

	public boolean checkConnection() {
		try {
		    Connection con = connect();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public ResultSet query(String query) throws SQLException {
		try {
		    Connection con = connect();
			return con.createStatement().executeQuery(query);
		} catch (SQLException e) {
			Notifications.create()
					.title("Ошибка")
					.text(e.getMessage())
					.showError();
			return null;
		}
	}

}
