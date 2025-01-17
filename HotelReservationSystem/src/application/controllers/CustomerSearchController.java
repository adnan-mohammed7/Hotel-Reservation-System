package application.controllers;

import application.database.Database;
import application.models.Guest;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CustomerSearchController {

    @FXML
    private TableView<Guest> customerTable;

    @FXML
    private TableColumn<Guest, String> emailCol;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private TableColumn<Guest, String> nameCol;

    @FXML
    private Label numOfCustomersLabel;

    @FXML
    private TextField searchBox;

    @FXML
    private TableColumn<Guest, Long> phoneCol;
    
    long phoneNum = 0;
    Database db;
    ObservableList<Guest> allCustomers;
    
    @FXML
    void initialize() {
    	db = new Database();
    	allCustomers = FXCollections.observableArrayList();
    	
    	nameCol.setCellValueFactory(cellData ->
    	new SimpleObjectProperty<>(cellData.getValue().getFirstName() + 
    			" " + cellData.getValue().getLastName()));
    	phoneCol.setCellValueFactory(new PropertyValueFactory<Guest, Long>("phone"));
    	emailCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("email"));
    	customerTable.setItems(allCustomers);
    	
    	searchBox.textProperty().addListener((obs, ov, nv) ->{
    		loadCustomers();
    	});
    	
    	customerTable.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 2 && customerTable.getSelectionModel().getSelectedItem() != null) {
    	        Guest selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
    	        
    	        try {
    	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/CustomerDetails.fxml"));
    	    		BorderPane root = loader.load();
    	    		CustomerDetailsController controller = loader.getController();
    	    		controller.setCustomer(selectedCustomer);
    	    		Scene scene = new Scene(root);
    	    		Stage stage = (Stage) (mainMenuBtn.getScene().getWindow());
    	    		stage.close();
    	        	stage.setScene(scene);
    	        	stage.setMaximized(true);
    	        	stage.setResizable(false);
    	        	stage.setTitle("Hotel Marma");
    	            stage.show();
    	    	}catch(Exception e) {
    	    		e.printStackTrace();
    	    	}
    	    }
    	});

    	loadCustomers();
    }

    @FXML
    void navigateToMainMenu(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/AdminPage.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (mainMenuBtn.getScene().getWindow());
    		stage.close();
        	stage.setScene(scene);
        	stage.setMaximized(true);
        	stage.setResizable(false);
        	stage.setTitle("Hotel Marma");
            stage.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    void loadCustomers() {
    	if(allCustomers != null && allCustomers.size() > 0) {
			allCustomers.clear();
		}
		allCustomers.addAll(db.getGuestsByNameOrPhone(searchBox.getText().toString()));
		numOfCustomersLabel.setText("Number of Customers: " + allCustomers.size());
    }
}
