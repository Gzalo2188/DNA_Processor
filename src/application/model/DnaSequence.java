package application.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DnaSequence {
	
	private SimpleStringProperty dna;
	
	private SimpleIntegerProperty count;
	
	public DnaSequence(String dna, int count) {
		this.dna = new SimpleStringProperty(dna);
		this.count = new SimpleIntegerProperty(count);
	}

	public String getDna() {
		return dna.get();
	}

	public void setDna(String dna) {
		this.dna.set(dna);
	}

	public int getCount() {
		return count.get();
	}

	public void setCount(int count) {
		this.count.set(count);
	}
	
	
}
