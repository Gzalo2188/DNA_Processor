package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import javafx.stage.FileChooser;

public class FileHandler {
	
	public static Hashtable <String, Integer> hash;
	
	public static File fileOpenChooser() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open File");
		fc.setInitialDirectory(new File("."));
		File selectedFile = fc.showOpenDialog(null);
		
		if(selectedFile != null) {
			return selectedFile.getAbsoluteFile();
		}
		else {
			return null;
		}
	}
	
	public static File fileSaveChooser() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save File");
		fc.setInitialDirectory(new File("."));
		File selectedFile = fc.showSaveDialog(null);
		
		if(selectedFile != null) {
			return selectedFile.getAbsoluteFile();
		}
		else {
			return null;
		}
	}
	
	public static ArrayList<DnaSequence> loadData() {
		ArrayList<DnaSequence> list = new ArrayList<DnaSequence>();
		try {
			Scanner reader = new Scanner(fileOpenChooser());
			hash = new Hashtable<String, Integer>();
			while(reader.hasNextLine()) {
				String temp = reader.nextLine();
				if(hash.containsKey(temp)) {
					hash.put(temp, hash.get(temp) + 1);
				}
				else {
					hash.put(temp, 1);
				}
			}
			
			Set<String> keys = hash.keySet();
			for(String key: keys) {
				list.add(new DnaSequence(key, hash.get(key)));
			}
			
			reader.close();
			return list;
		} catch (FileNotFoundException e) {
			return list;
		}
	}
	
	public static void saveData(ArrayList<DnaSequence> list) {
		try {
			FileWriter writer = new FileWriter(fileSaveChooser().getAbsoluteFile() + ".csv");
			for(int i = 0; i < list.size(); i++) {
				if(i ==0) 
					writer.write(list.get(i).getDna() + "," + list.get(i).getCount() + "\n");
				else
					writer.append(list.get(i).getDna() + "," + list.get(i).getCount() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			
		}
	}
}
