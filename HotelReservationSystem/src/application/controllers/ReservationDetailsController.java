package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ReservationDetailsController {

    @FXML
    private ChoiceBox<String> numOfGuest;
    
    @FXML
    void initialize() {
    	numOfGuest.getItems().addAll("Adnan", "Saad", "BRocode");
    }

}
