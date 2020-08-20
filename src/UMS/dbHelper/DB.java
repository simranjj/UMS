package UMS.dbHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	
	private static Connection connection;
	
	private DB() {
		
	}
	
	public static Connection getConnection()  {
		try {
			if(connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(
						"jdbc:sqlserver://LAPTOP-19P532P0:1433;databaseName=Uniform",
						"pillar", "pillar");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
