package application.controllers;

import application.database.Database;
import application.models.Guest;
import application.models.Reservation;
import application.utility.Validate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    private Button cancelBtn;

    @FXML
    private ChoiceBox<String> titleChoiceBox;
    
    private Reservation reservation;
    
    String title, fName, lName, location, mail;
    Long phoneNumber;
    Database db;
    
    @FXML
    void initialize() {
    	db = new Database();
    	titleChoiceBox.getItems().addAll("Mr", "Mrs");
    }
    
    @FXML
    void confirmBooking(ActionEvent event) {
    	if(validateFields()) {
    		setValues();
    		Guest guest = null;
    		if(!checkExisitingCustomer()) {
    			guest = new Guest(title, fName, lName, location, phoneNumber, mail);
    		}else {
    			if(db.getGuestsByPhone(phoneNumber)!=null) {
    				guest = db.getGuestsByPhone(phoneNumber);
    			}else if(db.getGuestsByEmail(mail) != null) {
    				guest = db.getGuestsByEmail(mail);
    			}
    		}
    		reservation.setGuest(guest);
    		
    		try {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/ReservationConfirmation.fxml"));
        		BorderPane root = loader.load();
        		
        		ReservationConfirmationController controller = loader.getController();
        		controller.setReservation(reservation);
        		
        		Scene scene = new Scene(root);
        		Stage stage = (Stage) (confirmBtn.getScene().getWindow());
        		stage.close();
    	    	stage.setScene(scene);
    	    	stage.setMaximized(true);
    	    	stage.setResizable(false);
    	    	stage.setTitle("Hotel Marma");
    	        stage.show();
        		
        	} catch(Exception e) {
        		e.printStackTrace();
        	}
    	}
    }
    
    @FXML
    void cancelBooking(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Main.fxml"));
    		BorderPane root = loader.load();
    		
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (confirmBtn.getScene().getWindow());
    		stage.close();
	    	stage.setScene(scene);
	    	stage.setMaximized(true);
	    	stage.setResizable(false);
	    	stage.setTitle("Hotel Marma");
	        stage.show();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    void setReservation(Reservation newReservation) {
    	reservation = newReservation;
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
    	} else if (!email.getText().matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,3}$")) {
    	    Validate.showAlert("Please enter a valid email address");
    	    return false;
    	}
    	
    	return true;
    }
    
    void setValues() {
    	title = titleChoiceBox.getValue();
    	fName = firstName.getText().toString();
    	lName = lastName.getText().toString();
    	location = address.getText().toString();
    	phoneNumber = Long.parseLong(phone.getText().toString());
    	mail = email.getText().toString();
    }
    
    boolean checkExisitingCustomer() {
    	if(db.getGuestsByPhone(phoneNumber) == null && db.getGuestsByEmail(mail) == null) {
    		return false;
    	}
    	return true;
    }
}
