package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamFileReader {
	Pattern pattern;
	Matcher matcher;
	
	public TeamFileReader(){
		pattern = Pattern.compile("(?<properity>[\\w ()]+)((\t)|[│]|[║])");
	}
	public ArrayList<HashMap<String, String>> getTeamData(){
		File file = new File("data/teams/teams");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("teams data file not found");
		}
		
		String line;
		HashMap<String, String> teamdata;
		ArrayList<HashMap<String, String>> totalData = new ArrayList<HashMap<String,String>>();
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			teamdata = processLine(line);
			if(teamdata != null){
				totalData.add(teamdata);
			}
		}
		scanner.close();
		return totalData;
	}
	
	HashMap<String,String> processLine(String line){
		if((line == null)||(!line.startsWith("║"))){
			//if this line contains no data
			return null;
		}else{
			matcher = pattern.matcher(line);
			HashMap<String, String> teamdata = new HashMap<String, String>();
			for(TeamProperity properity : TeamProperity.values()){
				matcher.find();
				teamdata.put(properity.toString(), matcher.group("properity").trim());
			}
			return teamdata;
		}
	}
}
