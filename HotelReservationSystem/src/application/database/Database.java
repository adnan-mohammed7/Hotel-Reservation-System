package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.models.AdminUsers;
import javafx.collections.ObservableList;

public class Database {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/project?useSSL=false";
	private static final String DB_USERNAME = "Adnan";
	private static final String DB_PASSWORD = "Seneca@123";
	
	private static final String ADMINUSERS_SELECT_ALL_QRY = "SELECT * FROM adminusers";
	
	public List<AdminUsers> getAdmins() {
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
			PreparedStatement ps = conn.prepareStatement(ADMINUSERS_SELECT_ALL_QRY);
			ResultSet res =  ps.executeQuery();
			List<AdminUsers> list = new ArrayList<AdminUsers>();
			while(res.next()) {
				list.add(new AdminUsers(res.getInt("adminID"),
						res.getString("userName"),
						res.getString("password")));
			}
			return list;
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
}