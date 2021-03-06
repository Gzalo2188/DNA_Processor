package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Set;

import application.model.DnaSequence;
import application.model.FileHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;



public class MainController implements EventHandler<ActionEvent>, Initializable{
	
	@FXML
	private TableView<DnaSequence> table, mutationTable, unmatchedTable;
    @FXML
    private TableColumn<DnaSequence, String> dnaCol, mDnaCol, unmatchedDnaCol;
    @FXML
    private TableColumn<DnaSequence, Integer> countCol, mCountCol, unmatchedCountCol;
    @FXML
    private Button openFile;
    @FXML
    private Button saveFile;
    @FXML
    private TextField textField;
    
    private Alert alert;
    private Hashtable<String, Integer> unmatchedHash;
    private Hashtable<String, Integer> matchedHash;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	this.unmatchedHash = new Hashtable<String, Integer>();
    	this.matchedHash = new Hashtable<String, Integer>();
    	// Set up columns
    	this.dnaCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, String>("dna"));
    	this.mDnaCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, String>("dna"));
    	this.countCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, Integer>("count"));
    	this.mCountCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, Integer>("count"));
    	this.unmatchedDnaCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, String>("dna"));
    	this.unmatchedCountCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, Integer>("count"));
    	
    	//Alignment
    	this.countCol.setStyle("-fx-alignment: CENTER;");
      	this.mCountCol.setStyle("-fx-alignment: CENTER;");
     	this.unmatchedCountCol.setStyle("-fx-alignment: CENTER;");
      	
    	//Allows the table to be editable.
		this.table.setEditable(true);		
		this.mutationTable.setEditable(true);
		this.unmatchedTable.setEditable(true);
		this.dnaCol.setCellFactory(TextFieldTableCell.forTableColumn());
		this.mDnaCol.setCellFactory(TextFieldTableCell.forTableColumn());
		this.unmatchedDnaCol.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	@Override
	public void handle(ActionEvent event) {
		String enteredString = this.textField.getText();
		ArrayList<DnaSequence> list = new ArrayList<DnaSequence>(this.table.getItems());

        if(this.table.getItems().size() == 0){
    		this.alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("ERROR");
    		alert.setHeaderText(null);
    		alert.setContentText("An error has occured. Please make sure that you've loaded data.");
    		alert.showAndWait();
        }
        else if(!(FileHandler.hash.containsKey(enteredString))){
        	this.alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("ERROR");
    		alert.setHeaderText(null);
    		alert.setContentText("An error has occured. The DNA squenece you entered does not exist within this set");
    		alert.showAndWait();
        }
        else{
        	DnaSequence searchedDna = new DnaSequence(enteredString, FileHandler.hash.get(enteredString));
        	if(!(this.matchedHash.containsKey(searchedDna.getDna()))){
        		this.mutationTable.getItems().add(searchedDna);
        		this.matchedHash.put(searchedDna.getDna(), searchedDna.getCount());
        	}
        	for(DnaSequence dna : list){
        		int mismatch = 0;
        		for(int i = 0; i < dna.getDna().length(); i++){
        			if(!(dna.getDna().charAt(i) == enteredString.charAt(i))){
        				mismatch++;
        			}
        		}
        		if(mismatch == 1 && !(this.matchedHash.containsKey(dna.getDna()))){
        			this.mutationTable.getItems().add(dna);
        			this.matchedHash.put(dna.getDna(), dna.getCount());
        		}
        		else{
        			if(!(this.unmatchedHash.containsKey(dna.getDna()))){
        				this.unmatchedHash.put(dna.getDna(), dna.getCount());
        				this.unmatchedHash.remove(searchedDna.getDna());
        			}
        		}
        	}
        	
        }

        /**
         * Ensures no key in the matched set are within the unmatched set
         * This might happen because of multiple searches.
         * */
		Set<String> keys = this.matchedHash.keySet();
		for(String key : keys){
			unmatchedHash.remove(key);
		}
		this.unmatchedTable.getItems().clear();
		keys = this.unmatchedHash.keySet();
		for(String key : keys){
			this.unmatchedTable.getItems().add(new DnaSequence(key, this.unmatchedHash.get(key)));
		}
		
	}
	
    public void openFileChooser(ActionEvent event) {
    	this.table.getItems().clear(); 
    	try {
    		this.table.getItems().addAll(FileHandler.loadData());
    	}
    	catch(NullPointerException e) {
    		this.alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("ERROR");
    		alert.setHeaderText(null);
    		alert.setContentText("Please choose a file to open.");
    		alert.showAndWait();
    	}
    }
    
    public void saveFileChooser(ActionEvent event) {
    	ArrayList<DnaSequence> list = new ArrayList<DnaSequence>(this.table.getItems());
    	try {
    		FileHandler.saveData(list);
    	}
    	catch(NullPointerException e) {
    		this.alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("ERROR");
    		alert.setHeaderText(null);
    		alert.setContentText("Please choose a file location.");
    		alert.showAndWait();
    	}
    }
    
    public void mSaveFileChooser(ActionEvent event) {
    	ArrayList<DnaSequence> list = new ArrayList<DnaSequence>(this.mutationTable.getItems());
    	try {
    		FileHandler.saveData(list);
    	}
    	catch(NullPointerException e) {
    		this.alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("ERROR");
    		alert.setHeaderText(null);
    		alert.setContentText("Please choose a file location.");
    		alert.showAndWait();
    	}
    }
    public void uSaveFileChooser(ActionEvent event) {
    	ArrayList<DnaSequence> list = new ArrayList<DnaSequence>(this.unmatchedTable.getItems());
    	try {
    		FileHandler.saveData(list);
    	}
    	catch(NullPointerException e) {
    		this.alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("ERROR");
    		alert.setHeaderText(null);
    		alert.setContentText("Please choose a file location.");
    		alert.showAndWait();
    	}
    }
    public void clearTable(ActionEvent event){
    	this.mutationTable.getItems().clear();
    	this.matchedHash.clear();
    	this.unmatchedTable.getItems().clear();
    	this.unmatchedHash.clear();
    }

}
