package application.controllers;

import application.database.Database;
import application.models.Reservation;
import application.models.RoomDetails;
import application.utility.Validate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BillServiceController {

	@FXML
    private TextField bookingId;

    @FXML
    private Button checkoutBtn;

    @FXML
    private Button cancelBtn;
    
    @FXML
    private Button discountBtn;

    @FXML
    private TextField discount;

    @FXML
    private TextField guestName;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private TextField numOfRooms;

    @FXML
    private TextField ratePerNight;

    @FXML
    private TextField discountedAmount;
    
    @FXML
    private TextField totalAmount;

    @FXML
    private TextField typesOfRooms;
    
    @FXML
    private TextField status;
    
    @FXML
    private TextField amount;
    
    @FXML
    private TextField numOfDays;
    
    @FXML
    private ChoiceBox<Integer> roomNumbers;
    
    Reservation reservation;
    double discountPercentage;
    Database db;
    
    @FXML
    void initialize(){
    	db = new Database();
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
    
    @FXML
    void checkoutBooking(ActionEvent event) {
    	if(Validate.showConfirmationAlert("Are you sure, you want to Check-out this boooking?")) {
    		reservation.setStatus("Checked-Out");
    		db.updateReservationStatus(reservation.getReservationID(), reservation.getStatus());
        	setFields();
    	}
    }

    @FXML
    void cancelBooking(ActionEvent event) {
    	if(Validate.showConfirmationAlert("Are you sure, you want to cancel this boooking?")) {
    		reservation.setStatus("Cancelled");
    		db.updateReservationStatus(reservation.getReservationID(), reservation.getStatus());
        	setFields();
    	}
    }
    
    @FXML
    void addDiscount(ActionEvent event) {
    	if(Validate.showConfirmationAlert("Are you sure, you want to add discount?") && validateFields()) {
    		reservation.getBill().setDiscount(discountPercentage);
    		db.updateBill(reservation.getBill());
    		setFields();
    	}
    }
    
    public void setReservation(Reservation reservation) {
    	this.reservation = reservation;
    	setFields();
    }
    
    void setFields() {
    	bookingId.setText(String.format("%d", reservation.getReservationID()));
    	guestName.setText(String.format("%s %s", reservation.getGuest().getFirstName(),
    			reservation.getGuest().getLastName()));
    	numOfRooms.setText(String.format("%d", reservation.getNumOfRooms()));
    	typesOfRooms.setText(reservation.getTypesOfRooms());
    	numOfDays.setText(String.format("%d", reservation.getNumOfDays()));
    	ratePerNight.setText(String.format("$ %.2f", reservation.getBill().getRatePerNight()));
    	amount.setText(String.format("$ %.2f", reservation.getBill().getTotalAmount()));
    	discount.setText(String.format("%.2f", reservation.getBill().getDiscount()));
    	discountedAmount.setText(String.format("$ %.2f", reservation.getBill().getAmountAfterDiscount()));
    	totalAmount.setText(String.format("$ %.2f", reservation.getBill().getAmountAfterTax()));
    	status.setText(reservation.getStatus());
    	
    	ObservableList<Integer> roomsBooked = FXCollections.observableArrayList();
    	for(RoomDetails e : reservation.getRoomDetails()) {
    		roomsBooked.add(e.getRoom().getRoomID());
    	}
    	
    	roomNumbers.setItems(roomsBooked);
    	roomNumbers.setValue(roomsBooked.get(0));
    }
    
    boolean validateFields() {
    	if (discount.getText().isEmpty()) {
    	    Validate.showAlert("Discount field cannot be empty");
    	    return false;
    	} 

    	try {
    	    double discountValue = Double.parseDouble(discount.getText());
    	    if (discountValue < 0.0 || discountValue > 25.00) {
    	        Validate.showAlert("Please enter a discount between 0.0 and 25.00");
    	        return false;
    	    }
    	} catch (NumberFormatException e) {
    	    Validate.showAlert("Please enter a valid numeric value for the discount");
    	    return false;
    	}
    	discountPercentage = Double.valueOf(discount.getText().toString());
    	return true;
    }
}
