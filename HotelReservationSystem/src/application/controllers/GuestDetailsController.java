package application.controllers;

import application.models.RoomDetails;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class GuestDetailsController {
	
    @FXML
    private Button confirmBtn;

    @FXML
    private ChoiceBox<String> titleChoiceBox;
    
    private ObservableList<RoomDetails> roomDetails;
    
    @FXML
    void initialize() {
    	titleChoiceBox.getItems().addAll("Mr", "Mrs");
    }
    
    @FXML
    void confirmBooking(ActionEvent event) {
    	System.out.println("Confirmed");
    }
    
    void setRoomDetails(ObservableList<RoomDetails> list) {
    	roomDetails = list;
    }
}
