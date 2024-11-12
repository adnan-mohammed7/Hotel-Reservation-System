package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class GuestDetailsController {
	
    @FXML
    private Button confirmBtn;

    @FXML
    private ChoiceBox<String> titleChoiceBox;
    
    @FXML
    void initialize() {
    	titleChoiceBox.getItems().addAll("Mr", "Mrs");
    }
    
    @FXML
    void confirmBooking(ActionEvent event) {
    	System.out.println("Confirmed");
    }

}
