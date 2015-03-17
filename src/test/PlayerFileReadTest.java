package test;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import utility.PlayerFileReader;
import utility.PlayerProperity;

public class PlayerFileReadTest {
	@Test
	public void test() {
		PlayerFileReader playerFileReader = new PlayerFileReader();
		ArrayList<HashMap<String, String>> data = playerFileReader.getPlayerData();
		for(HashMap<String, String> map : data){
			for(PlayerProperity properity : PlayerProperity.values()){
				System.out.print (String.format("%-20s", map.get(properity.toString())) + "\t");
			}
			System.out.println();
		}
	}

}
