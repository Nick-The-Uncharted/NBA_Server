package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utility.DataBaseInitializer;
import utility.Utility;
public class ServerStart {
	public static void main(String[] args) {
		Utility utility = new Utility();
		DataBaseInitializer dataBaseInitializer = new DataBaseInitializer("Nick", "19950809", "derby", "127.0.0.1",1527,"NBA_DB");
		utility.setInitialized(false);
		if(!utility.hasInitialize()){
			dataBaseInitializer.initial();
		}
		Connection conn;
		try {
			conn = dataBaseInitializer.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT NAME,ABBV FROM TEAMS ");
			String name = null;
			if(rs.next()){
				name = rs.getString("NAME");
			}
			System.out.println(name);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
