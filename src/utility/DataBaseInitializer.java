package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class DataBaseInitializer {
	//	initialize a basic database by reading files
	private String userName;
	private String password;
	private String dbms;
	private String serverName;
	private int portNumber;
	private String dbName;
	private Connection conn;
	private Statement stmt = null;
	public DataBaseInitializer(String userName, String password, String dbms,
			String serverName,int portNumber,String dbName) {
		super();
		this.userName = userName;
		this.password = password;
		this.dbms = dbms;
		this.serverName = serverName;
		this.portNumber = portNumber;
		this.dbName = dbName;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			System.out.println("connect failed");
			e.printStackTrace();
		}
	}

	public boolean initial(){
		try {
			createTables();
			initialTables();
			new Utility().setInitialized(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void initialTables(){
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			DataBaseUtility.printSQLException(e);
		}
		initialTeamTable();
		initialPlayerTable();
		initialMatchOverview();
		initialMatchDetail();
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initialTeamTable(){
		TeamFileReader reader = new TeamFileReader();
		ArrayList<HashMap<String,String>> teamList = reader.getTeamData();
		String query;
		for(HashMap<String, String> teamInfo : teamList){
			query = "INSERT INTO TEAMS VALUES(";
			for(TeamProperity properity : TeamProperity.values()){
				query += "'" + teamInfo.get(properity.toString()) + "',";
			}
			query = query.substring(0, query.length() - 1);
			query += ")";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				if(e.getSQLState().equals("23505")){
					System.out.println("this query lead to a data duplicate : " + query);
				}else{
					DataBaseUtility.printSQLException(e);
				}
			}
		}

	}
	
	public void initialPlayerTable(){
		PlayerFileReader reader = new PlayerFileReader();
		ArrayList<HashMap<String,String>> playerList = reader.getPlayerData();
		String query;
		String properityStr;
		for(HashMap<String, String> playerInfo : playerList){
			query = "INSERT INTO PLAYERS VALUES(";
			for(PlayerProperity properity : PlayerProperity.values()){
				properityStr = playerInfo.get(properity.toString()).replace("'", "''");
				if(properityStr.isEmpty()){
					properityStr = new String("NULL");
					query += properityStr + ",";
				}else{
					query += "'" + properityStr + "',";
				}
			}
			query = query.substring(0, query.length() - 1);
			query += ")";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				if(e.getSQLState().equals("23505")){
					System.out.println("this query lead to a data duplicate : " + query);
				}else{
					System.out.println(query);
					DataBaseUtility.printSQLException(e);
				}
			}
		}

	}

	
	private void initialMatchOverview(){
		MatchFileReader reader = new MatchFileReader();
		ArrayList<HashMap<String,String>> matchList = reader.getMatchOverview();
		String query;
		for(HashMap<String, String> matchInfo : matchList){
			query = "INSERT INTO MATCHES VALUES(";
			for(MatchProperity properity : MatchProperity.values()){
				query += "'" + matchInfo.get(properity.toString()) + "',";
			}
			query = query.substring(0, query.length() - 1);
			query += ")";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				if(e.getSQLState().equals("23505")){
					System.out.println("this query lead to a data duplicate : " + query);
				}else{
					DataBaseUtility.printSQLException(e);
				}
			}
		}
	}
	
	private void initialMatchDetail(){
		MatchFileReader matchFileReader = new MatchFileReader();
		ArrayList<MatchData> matchDatas = matchFileReader.getMatchDetail();
		for(MatchData matchData : matchDatas){
			for(HashMap<String, String> playerData : matchData.playerData){
				StringBuffer query = new StringBuffer("INSERT INTO PLAYERDATA  VALUES(");
				int i = 0;
				for(MatchDetailProperity properity : MatchDetailProperity.values()){
					String properityStr = playerData.get(properity.toString());
					if(i < 3){
						if(properityStr.isEmpty()){
							properityStr = new String("NULL");
							query.append(properityStr).append(",");
						}else{
							query.append("'").append(properityStr.replace("'","''")).append("',");
						}
					}else{
						query.append(properityStr).append(",");
					}
					++i;
				}
				query.append("'").append(matchData.getSeason()).append("',");
				query.append("'").append(matchData.getDate()).append("',");
				query.append("'").append(matchData.getAgainstTeam()).append("',");
				query.append("'").append(matchData.getTeamName()).append("'");
				query.append(")");
				try {
					stmt.executeUpdate(query.toString());
				} catch (SQLException e) {
					if(e.getSQLState().equals("23505")){
						System.out.println("this query lead to a data duplicate : " + query);
					}else{
						System.out.println(query);
						DataBaseUtility.printSQLException(e);
					}
				}
			}
		}
	}
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection(
				"jdbc:" + this.dbms + "://" +
						this.serverName +
						":" + this.portNumber + "/" +
						dbName + ";create=true",
						connectionProps);
		System.out.println("Connected to database");
		return conn;
	}

	private void createTables() throws SQLException {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			DataBaseUtility.printSQLException(e);
		}
		createTeamTable();
		createPlayerTable();
		createMatchOverview();
		createMatchTables();
		try {
			stmt.close();
		} catch (SQLException e) {
			DataBaseUtility.printSQLException(e);
		}
	}
	
	private void createTeamTable() throws SQLException {
		String teamCreateStr =
				"CREATE TABLE " +
						"TEAMS " +
						"(NAME varchar(20) NOT NULL, " +
						"ABBV varchar(6) NOT NULL, " +
						"CITY varchar(20) NOT NULL, " +
						"DIVISION varchar(8) NOT NULL, " +
						"CONFERENCE varchar(9) NOT NULL," +
						"ARENA varchar(30) NOT NULL, " +
						"FOUNDED varchar(4) NOT NULL, " +
						"PRIMARY KEY (NAME))";
		try {
			stmt.executeUpdate("DROP TABLE TEAMS");
		} catch (SQLException e) {
			if (!DataBaseUtility.tableNotExists(e)) {
				DataBaseUtility.printSQLException(e);
			}
		} 
		
		try{
			stmt.executeUpdate(teamCreateStr);
		}catch (SQLException e) {
			if (!DataBaseUtility.tableAlreadyExists(e)) {
				DataBaseUtility.printSQLException(e);
			}
		}
	}
	
	private void createPlayerTable() throws SQLException {
		String playerCreateStr = 
				"CREATE TABLE " +
						"PLAYERS " +
						"(NAME varchar(30) NOT NULL, " +
						"NUMBER varchar(6) NOT NULL, " +
						"POSITION varchar(3) NOT NULL, " +
						"HEIGHT varchar(4) NOT NULL, " +
						"WEIGHT varchar(3) NOT NULL," +
						"BIRTH varchar(15) NOT NULL, " +
						"AGE varchar(2) NOT NULL, " +
						"EXP char(3) NOT NULL," + 
						"SCHOOL varchar(40)," +
						"PRIMARY KEY (NAME))";
		try {
			stmt.executeUpdate("DROP TABLE PLAYERS");
		} catch (SQLException e) {
			if (!DataBaseUtility.tableNotExists(e)) {
				DataBaseUtility.printSQLException(e);
			}
		} 
		try{
			stmt.executeUpdate(playerCreateStr);
		}catch (SQLException e) {
			if (!DataBaseUtility.tableAlreadyExists(e)) {
				DataBaseUtility.printSQLException(e);
			}
		}
	}
	
	private void createMatchOverview() throws SQLException {
		String matchsCreateStr = 
				"CREATE TABLE " +
						"MATCHES " +
						"(SEASON varchar(5) NOT NULL, " +
						"DATE varchar(5) NOT NULL, " +
						"AGAINSTTEAMS varchar(7) NOT NULL, " +
						"SCORE varchar(7) NOT NULL, " +
						"SECTION1SCORE varchar(7) NOT NULL," +
						"SECTION2SCORE varchar(7) NOT NULL, " +
						"SECTION3SCORE varchar(7) NOT NULL, " +
						"SECTION4SCORE char(7) NOT NULL," + 
						"PRIMARY KEY (SEASON,DATE,AGAINSTTEAMS))";
		try {
			stmt.executeUpdate("DROP TABLE MATCHES");
		} catch (SQLException e) {
			if (!DataBaseUtility.tableNotExists(e)) {
				DataBaseUtility.printSQLException(e);
			}
		} 
		try{
			stmt.executeUpdate(matchsCreateStr);
		}catch (SQLException e) {
			if (!DataBaseUtility.tableAlreadyExists(e)) {
				DataBaseUtility.printSQLException(e);
			}
		}
	}
	
	private void createMatchTables() throws SQLException{
		String matchCreateStr = 
				"CREATE TABLE PLAYERDATA " +
						"(NAME varchar(30), " +
						"POSITION char(1), " +
						"MPG varchar(5), " +
						"FGM INTEGER, " +
						"FGA INTEGER," +
						"TPM INTEGER, " +
						"TPA INTEGER, " +
						"FTM INTEGER," + 
						"FTA INTEGER," +
						"OFF INTEGER," +
						"DEF INTEGER," +
						"RPG INTEGER," +
						"ASSIST INTEGER," +
						"ST INTEGER," + 
						"BLK INTEGER, " +
						"TURNOVER INTEGER, " +
						"PERSONALFOUL INTEGER," +
						"TOTAL INTEGER, " +
						"SEASON VARCHAR(5)," + 
						"DATE VARCHAR(5)," +
						"AGAINSTTEAMS VARCHAR(7)," + 
						"TEAM VARCHAR(3)," + 
						"PRIMARY KEY (NAME,SEASON,DATE,AGAINSTTEAMS))";
		try {
			stmt.executeUpdate("DROP TABLE PLAYERDATA");
		} catch (SQLException e) {
			if (!DataBaseUtility.tableNotExists(e)) {
				DataBaseUtility.printSQLException(e);
			}
		} 
		try{
			stmt.executeUpdate(matchCreateStr);
		}catch (SQLException e) {
			if (!DataBaseUtility.tableAlreadyExists(e)) {
				DataBaseUtility.printSQLException(e);
			}
		}
	}
}