package activreRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static DBConnection instance;

	private Connection connect;
	private String userName, password, serverName, portNumber, tableName, dbName;

	private DBConnection() throws SQLException {
		// variables a modifier en fonction de la base
		userName = "root";
		password = "root";
		serverName = "localhost";
		// Attention, sous MAMP, le port est 8889
		portNumber = "3306";
		tableName = "";

		// iL faut une base nommee testPersonne !
		dbName = "testpersonne";
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		String urlDB = "jdbc:mysql://" + serverName + ":";
		urlDB += portNumber + "/" + dbName;
		connect = DriverManager.getConnection(urlDB, connectionProps);

	}

	public static synchronized DBConnection getInstance() throws SQLException {
		if (instance == null)
			instance = new DBConnection();
		return instance;
	}

	public Connection getConnection() {
		return connect;
	}

	public void setNomDB(String nomDB) throws SQLException {
		connect.close();
		dbName = nomDB;
		instance = null;
		connect = getConnection();
	}
}
