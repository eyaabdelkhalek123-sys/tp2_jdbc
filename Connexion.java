package ma.projet.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	private static final String url = "jdbc:mysql://localhost:3306/DemoJDBC";
	private static final String login = "root";
	private static final String password = "";
	
	private static Connection cn;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn=DriverManager.getConnection(url , login , password);
			System.out.println("connexion bien établie");
		}
		catch (ClassNotFoundException e) {
			System.out.println("driver introuvalbe" + e.getMessage());
		}
		catch (SQLException e) {
			System.out.println("connexion erronée" + e.getMessage());
		}
	}
	
	public static Connection getCn() {
		return cn;
	}
	
}
