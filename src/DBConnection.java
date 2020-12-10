import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private String userName;
	private String password;
	private String serverName;
	private String portNumber;
	private String dbName;
	private static Connection co;
	private static DBConnection instance;

	private DBConnection() {
		userName = "root";
		password = "";
		serverName = "localhost";
		portNumber = "3306";
		dbName = "testSerie";
		
	}

	public Connection getConnection() throws SQLException {
		if (co == null) {
			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			String urlDB = "jdbc:mysql://" + serverName + ":";
			urlDB += portNumber + "/" + dbName;
			Connection connect = DriverManager.getConnection(urlDB, connectionProps);
			co = connect;
			return co;
		} else {
			return co;
		}
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			DBConnection instance = new DBConnection();
			return instance;
		} else {
			return instance;
		}
	}

	public void setNomDB(String nomDB) {
		this.dbName=nomDB;
	}
}
