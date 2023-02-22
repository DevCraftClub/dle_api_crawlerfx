package club.devcraft.dbcrawler.controller;

import org.controlsfx.control.Notifications;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbController {
	private String host;
	private int port;
	private String username;
	private String password;
	private String name;

	public DbController(String host, int port, String username, String password, String name) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.name = name;
	}

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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
