package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	public Connection connect;
	public Statement st;
	public ResultSet rs;
	public ResultSetMetaData rsmd;
	
	public Connect() {
		try {  
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_shop", "root", "");  
            st = connect.createStatement(); 
        } catch(Exception e) {
        	e.printStackTrace();
        	System.out.println("Failed to connect the database, the system is terminated!");
        }  
	}

	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rs;
	}
	
	public void execUpdate(String query) {
    	try {
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}
