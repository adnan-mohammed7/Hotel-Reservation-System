package application.controllers;

import application.models.RoomDetails;
import application.utility.Validate;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class GuestDetailsController {
	

    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phone;
	
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
    	if(validateFields()) {
    		System.out.println("Confirmed");
    	}
    }
    
    void setRoomDetails(ObservableList<RoomDetails> list) {
    	roomDetails = list;
    }
    
    boolean validateFields() {
    	if(titleChoiceBox.getValue() == null) {
    		Validate.showAlert("Select a valid title");
    		return false;
    	}
    	if(firstName.getText().isEmpty()) {
    		Validate.showAlert("Enter a valid first name");
    		return false;
    	}
    	if(lastName.getText().isEmpty()) {
    		Validate.showAlert("Enter a valid last name");
    		return false;
    	}
    	if(address.getText().isEmpty()) {
    		Validate.showAlert("Enter a valid address");
    		return false;
    	}
    	
    	if(phone.getText().isEmpty()) {
    		Validate.showAlert("Phone field cannot be empty");
    		return false;
    	} else if (!phone.getText().matches("\\d{10}")) {
    	    Validate.showAlert("Please enter a valid 10-digit phone number");
    	    return false;
    	}
    	
    	if(email.getText().isEmpty()) {
    		Validate.showAlert("Email Address cannot be empty");
    		return false;
    	} else if (!email.getText().matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,3}$")) { // Basic email regex
    	    Validate.showAlert("Please enter a valid email address");
    	    return false;
    	}
    	
    	return true;
    }
}
