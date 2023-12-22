package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection con = null;
	private static ConnectDB instance = new ConnectDB();

	public static ConnectDB getInstance() {
		return instance;
	}

	public static Connection getConnection() {
		return con;
	}

	public void connect() throws SQLException {
		String url = "jdbc:sqlserver://localhost:1433;databasename=QLBHNew;trustServerCertificate=true";
		String user = "sa";
		String pass = "sapassword";
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void disconnect() {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
