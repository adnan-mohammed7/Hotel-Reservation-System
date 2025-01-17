package application.controllers;

import application.database.Database;
import application.models.Reservation;
import application.models.RoomDetails;
import application.utility.Validate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ReservationConfirmationController {

    @FXML
    private Button confirmBtn;
    
    @FXML
    private Button cancelBtn;

    @FXML
    private TextField guestName;

    @FXML
    private TextField inDate;

    @FXML
    private TextField numOfRooms;

    @FXML
    private TextField outDate;

    @FXML
    private TextField total;

    @FXML
    private TextField totalAfterTax;
    
    private Reservation reservation;
    Database db;
    
    @FXML
    void initialize() {
    	db = new Database();
    }

    @FXML
    void confirmBooking(ActionEvent event) {
    	db.addReservationDetails(reservation);
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/Main.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (confirmBtn.getScene().getWindow());
    		stage.close();
	    	stage.setScene(scene);
	    	stage.setMaximized(true);
	    	stage.setResizable(false);
	    	stage.setTitle("Hotel Marma");
	        stage.show();
	        String suites = "";
	        
	        for(RoomDetails e : reservation.getRoomDetails()) {
	        	suites += String.valueOf(e.getRoom().getRoomID());
	        	suites += " ";
	        }
	        
	        Validate.showBookingConfirmation("Booking Confirmed for " + reservation.getGuest().getFirstName() +
	        		" " + reservation.getGuest().getLastName() + "\n" + "Check-In Date: " + reservation.getCheckInDate() + 
	        		"\nCheck-Out Date: " + reservation.getCheckOutDate() + "\n" +
	        		"Suites: " + suites);
    	} catch(Exception e) {
    		e.printStackTrace();
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
    
    public void setReservation(Reservation res) {
    	reservation = res;
    	guestName.setText(res.getGuest().getFirstName()+ " " + res.getGuest().getLastName());
    	numOfRooms.setText(String.format("%d", res.getNumOfRooms()));
    	inDate.setText(res.getCheckInDate().toString());
    	outDate.setText(res.getCheckOutDate().toString());
    	total.setText(String.format("$ %.2f", res.getBill().getAmountAfterDiscount()));
    	totalAfterTax.setText(String.format("$ %.2f", res.getBill().getAmountAfterTax()));
    }
}
