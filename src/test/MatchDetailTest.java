package test;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import utility.MatchData;
import utility.MatchDetailProperity;
import utility.MatchFileReader;

public class MatchDetailTest {


	@Test
	public void test() {
		ArrayList<MatchData> dataList = new MatchFileReader().getMatchDetail();
		for(MatchData data : dataList){
			for(HashMap<String,String> map : data.getPlayerData()){
				for(MatchDetailProperity properity : MatchDetailProperity.values()){
					System.out.print (String.format("%-20s", map.get(properity.toString())) + "\t");
				}
				System.out.println();
			}
		}
	}

}
