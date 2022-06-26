import java.sql.*;

public class DB {
	public static Connection connection;
	public DB() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","");
	}
}