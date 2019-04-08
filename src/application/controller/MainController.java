package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;
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
	private TableView<DnaSequence> table;
	
	@FXML TableView<DnaSequence> mutationTable;
	
    @FXML
    private TableColumn<DnaSequence, String> dnaCol;
    
    @FXML
    private TableColumn<DnaSequence, String> mDnaCol;

    @FXML
    private TableColumn<DnaSequence, Integer> countCol;
    
    @FXML
    private TableColumn<DnaSequence, Integer> mCountCol;

    @FXML
    private Button openFile;

    @FXML
    private Button saveFile;
    
    @FXML
    private TextField textField;
    
    private Alert alert;

    private Hashtable<String, Integer> unmatchedHash;
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	this.unmatchedHash = new Hashtable<String, Integer>();
    	// Set up columns
    	this.dnaCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, String>("dna"));
    	this.mDnaCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, String>("dna"));
    	this.countCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, Integer>("count"));
    	this.mCountCol.setCellValueFactory(new PropertyValueFactory<DnaSequence, Integer>("count"));
		
    	//Alignment
    	this.countCol.setStyle("-fx-alignment: CENTER;");
      	this.mCountCol.setStyle("-fx-alignment: CENTER;");
      	
    	//Alows the table to be elitable.
		this.table.setEditable(true);		
		this.mutationTable.setEditable(true);
		this.dnaCol.setCellFactory(TextFieldTableCell.forTableColumn());
		this.mDnaCol.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	@Override
	public void handle(ActionEvent event) {
		this.mutationTable.getItems().clear();
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
        	this.mutationTable.getItems().add(searchedDna);
        	for(DnaSequence dna : list){
        		int mismatch = 0;
        		for(int i = 0; i < dna.getDna().length(); i++){
        			if(!(dna.getDna().charAt(i) == enteredString.charAt(i))){
        				mismatch++;
        			}
        		}
        		if(mismatch == 1){
        			this.mutationTable.getItems().add(dna);
        		}
        		else{
        			this.unmatchedHash.put(searchedDna.getDna(), searchedDna.getCount());
        		}
        	}
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

}
