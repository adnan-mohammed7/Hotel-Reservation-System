package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ReservationDetailsController {

    @FXML
    private ChoiceBox<Integer> numOfGuest;
    
    @FXML
    private Button addBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private Button rulesBtn;
    
    @FXML
    private TableView<?> listView;
    
    @FXML
    void initialize() {
    	numOfGuest.getItems().addAll(1, 2, 3, 4);
    }
    
    @FXML
    void addRoom(ActionEvent event) {
    	
    }

    @FXML
    void removeRoom(ActionEvent event) {

    }

    @FXML
    void showRules(ActionEvent event) {

    }

    @FXML
    void startGuestDetails(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/GuestDetails.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (nextBtn.getScene().getWindow());
    		stage.close();
	    	stage.setScene(scene);
	    	stage.setMaximized(true);
	    	stage.setResizable(false);
	        stage.show();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
