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
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fc.getExtensionFilters().add(extFilter);
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
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		fc.getExtensionFilters().add(extFilter);
		fc.setInitialFileName("*.csv");
		fc.setTitle("Save File");
		fc.setInitialDirectory(new File("."));
		File selectedFile = fc.showSaveDialog(null);
		
		if(selectedFile != null) {
			if(selectedFile.getName().endsWith(".csv")) {
				return selectedFile.getAbsoluteFile();
			}
			else {
				File fixedFile = new File(selectedFile.getAbsoluteFile()+".csv");
				return fixedFile;
			}
			
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
			FileWriter writer = new FileWriter(fileSaveChooser().getAbsoluteFile());
			for(int i = 0; i < list.size(); i++) {
				String dna = "";
				String temp = list.get(i).getDna();
				for(int j = 0; j < temp.length(); j++){
					dna += temp.charAt(j) + ",";
				}
				
				if(i ==0){
					writer.write(dna + list.get(i).getCount() + "\n");
				}
				else
					writer.append(dna + list.get(i).getCount() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			
		}
	}
}
