package utility;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchFileReader {
	Pattern pattern;
	Matcher matcher;
	public ArrayList<HashMap<String, String>> getMatchOverview(){
		File dir = new File("data/matches");
		File matches[] = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return !pathname.getName().contains(".");
			}
		});
		ArrayList<HashMap<String, String>> totalData = new ArrayList<HashMap<String,String>>();
		for(File match : matches){
			totalData.add(getOverview(match));
		}
		return totalData;
	}
	
	HashMap<String, String> getOverview(File match){
		Scanner scanner = null;
		try {
			scanner = new Scanner(match);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = match.getName().split("[_]")[0] + ";" +  scanner.nextLine() + scanner.nextLine();
		scanner.close();
		String ret[] = line.split("[;]");
		return tranToMap(ret);
	}
	
	private HashMap<String, String> tranToMap(String info[]){
		HashMap<String, String> infoMap = new HashMap<String, String>();
		int i = 0;
		for(MatchProperity properity : MatchProperity.values()){
			infoMap.put(properity.toString(), info[i]);
			++i;
		}
		return infoMap;
	}
	
	
	public ArrayList<MatchData> getMatchDetail(){
		Scanner scanner = null;
		File dir = new File("data/matches");
		File matches[] = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return !pathname.getName().contains(".");
			}
		});
		
		ArrayList<MatchData> matchDetail = new ArrayList<MatchData>();
		
		for(File match : matches){
			try {
				scanner = new Scanner(match);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String teams[] = match.getName().split("[_]")[2].split("[-]");
			while (!scanner.nextLine().equals(teams[0])){}
			String line;
			while (scanner.hasNextLine()&&(scanner.nextLine().equals(teams[0]))) {}
			ArrayList<HashMap<String, String>> team = new ArrayList<HashMap<String,String>>();
			while(!(line = scanner.nextLine()).equals(teams[1])){
				team.add(transToMap(line.split("[;]")));
			}
			matchDetail.add(new MatchData(match.getName(),teams[0], team));
			
			team = new ArrayList<HashMap<String,String>>();
			while(scanner.hasNextLine()){
				line = scanner.nextLine();
				team.add(transToMap(line.split("[;]")));
			}
			matchDetail.add(new MatchData(match.getName(),teams[1], team));
			scanner.close();
		}
		return matchDetail;
	}
	
	HashMap<String, String> transToMap(String info[]){
		int i = 0;
		HashMap<String, String> infoMap = new HashMap<String, String>();
		for(MatchDetailProperity detailProperity : MatchDetailProperity.values()){
			infoMap.put(detailProperity.toString(), info[i]);
			++i;
		}
		return infoMap;
	}
}
