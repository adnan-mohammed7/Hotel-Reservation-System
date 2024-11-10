package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class GuestDetailsController {

    @FXML
    private ChoiceBox<String> titleChoiceBox;
    
    @FXML
    void initialize() {
    	titleChoiceBox.getItems().addAll("Mr", "Mrs");
    }

}
