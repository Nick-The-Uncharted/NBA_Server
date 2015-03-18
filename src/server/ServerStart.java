package server;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.drda.NetworkServerControl;

import utility.DataBaseInitializer;
import utility.Utility;
public class ServerStart {
	public static void main(String[] args) {
		Utility utility = new Utility();
		try {
			NetworkServerControl serverControl=new NetworkServerControl();
			serverControl.start(new PrintWriter(System.out));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DataBaseInitializer dataBaseInitializer = new DataBaseInitializer("Nick", "19950809", "derby", "172.26.68.111",1527,"NBA_DB");
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
