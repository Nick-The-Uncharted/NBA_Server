package utility;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerFileReader {
	Pattern pattern;
	Matcher matcher;
	
	public PlayerFileReader(){
		pattern = Pattern.compile("(?<properity>[\\w \\.,()'-]+)((\t)|[│]|[║])");
	}
	
	public ArrayList<HashMap<String, String>> getPlayerData(){
		File dir = new File("data/players/info");
		File players[] = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return !pathname.getName().contains(".");
			}
		});
		ArrayList<HashMap<String, String>> totalData = new ArrayList<HashMap<String,String>>();
		for(File player : players){
			totalData.add(processFile(player));
		}
		return totalData;
	}
	
	private HashMap<String, String> processFile(File file){
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		HashMap<String, String> playerData = new HashMap<String, String>();
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			String result[] = processLine(line);
			if(result != null){
				playerData.put(result[0].trim().toUpperCase(), result[1].trim());
			}
		}
		scanner.close();
		return playerData;
	}
	
	private String[] processLine(String line){
		if(!line.startsWith("║")){
			return null;
		}else{
			String ret[] = new String[2];
			matcher = pattern.matcher(line);
			try{
				matcher.find();
				ret[0] = matcher.group("properity");
				matcher.find();
				ret[1] = matcher.group("properity");
			}catch(Exception e){
				System.out.println(line);
			}
			return ret;
		}
	}
}
