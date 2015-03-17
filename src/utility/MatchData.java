package utility;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchData {
	ArrayList<HashMap<String, String>> playerData;
	String season;
	String date;
	String againstTeam;
	String teamName;

	public MatchData(String match,String teamName,ArrayList<HashMap<String, String>> playerData) {
		super();
		this.playerData = playerData;
		String info[] = match.split("[_]");
		this.season = info[0];
		this.date = info[1];
		this.againstTeam = info[2];
		this.teamName = teamName;
	}
	
	public ArrayList<HashMap<String, String>> getPlayerData() {
		return playerData;
	}
	public String getTeamName() {
		return teamName;
	}

	public String getSeason() {
		return season;
	}

	public String getDate() {
		return date;
	}

	public String getAgainstTeam() {
		return againstTeam;
	}
	
}
