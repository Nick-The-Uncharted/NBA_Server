package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utility {
	public boolean hasInitialize(){
		File staus  = new File("data/staus.ser");
		if(staus.length() == 0){
			try {
				staus.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}else{
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new FileInputStream(staus));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			boolean ret = false;
			try {
				ret = ois.readBoolean();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ret;
		}
	}
	
	public void setInitialized(boolean initialized){
		File staus  = new File("data/staus.ser");
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(staus));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			oos.writeBoolean(new Boolean(initialized));
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
