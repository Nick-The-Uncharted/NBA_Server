package test;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import utility.TeamFileReader;
import utility.TeamProperity;

public class TeamFileReadTest {

	@Test
	public void test() {
		ArrayList<HashMap<String, String>> data = new TeamFileReader().getTeamData();
		for(HashMap<String, String> map : data){
			for(TeamProperity properity : TeamProperity.values()){
				System.out.print (String.format("%-20s", map.get(properity.toString())) + "\t");
			}
			System.out.println();
		}
	}

}
