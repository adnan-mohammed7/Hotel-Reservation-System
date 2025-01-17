package application.controllers;

import java.time.LocalDate;
import java.util.List;

import application.database.Database;
import application.models.Guest;
import application.models.Reservation;
import application.utility.Validate;
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

public class BillSearchController {

    @FXML
    private TableColumn<Reservation, Integer> bookingIdCol;

    @FXML
    private TableColumn<Reservation, String> customerNameCol;
    
    @FXML
    private TableColumn<Reservation, LocalDate> inDateCol;
    
    @FXML
    private TableColumn<Reservation, LocalDate> outDateCol;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private Label numOfBookingsLabel;

    @FXML
    private TableView<Reservation> bookingsTable;

    @FXML
    private Button searchBtn;

    @FXML
    private TableColumn<Reservation, String> statusCol;
    
    @FXML
    private TextField phone;
    
    Database db;
    Long phoneNum;
    ObservableList<Reservation> customerReservation;
    Guest guest;
    
    @FXML
    void initialize() {
    	db = new Database();
    	customerReservation = FXCollections.observableArrayList();
    	bookingIdCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("reservationID"));
    	customerNameCol.setCellValueFactory(cellData ->
    	new SimpleObjectProperty<>(cellData.getValue().getGuest().getFirstName() + 
    			" " + cellData.getValue().getGuest().getLastName()));
    	statusCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("status"));
    	inDateCol.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("checkInDate"));
    	outDateCol.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("checkOutDate"));
    	bookingsTable.setItems(customerReservation);
    	
    	bookingsTable.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 2 && bookingsTable.getSelectionModel().getSelectedItem() != null) {
    	        Reservation selectedReservation = bookingsTable.getSelectionModel().getSelectedItem();
    	        
    	        try {
    	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/BillService.fxml"));
    	    		BorderPane root = loader.load();
    	    		BillServiceController controller = loader.getController();
    	    		controller.setReservation(selectedReservation);
    	    		Scene scene = new Scene(root);
    	    		Stage stage = (Stage) (mainMenuBtn.getScene().getWindow());
    	    		stage.close();
    	        	stage.setScene(scene);
    	        	stage.setMaximized(true);
    	        	stage.setResizable(false);
    	            stage.show();
    	    	}catch(Exception e) {
    	    		e.printStackTrace();
    	    	}
    	    }
    	});

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
    void searchBookings(ActionEvent event) {
    	search();
    }
    
    void search() {
    	if(validateFields()) {
    		if(customerReservation != null && customerReservation.size() > 0) {
    			customerReservation.clear();
    		}
    		List<Reservation> allReservation = db.getAllReservations();
        	
        	for (Reservation e : allReservation) {
        		if(e.getGuest().getPhone().equals(phoneNum)) {
        			customerReservation.add(e);
        		}
        	}
        	numOfBookingsLabel.setText("Number of Bookings: " + customerReservation.size());
    	}
    }
    
    boolean validateFields() {
    	if(phone.getText().isEmpty()) {
    		Validate.showAlert("Phone field cannot be empty");
    		return false;
    	} else if (!phone.getText().matches("\\d{10}")) {
    	    Validate.showAlert("Please enter a valid 10-digit phone number");
    	    return false;
    	}
    	phoneNum = Long.valueOf(phone.getText().toString());
    	return true;
    }
    
    public void setGuest(Guest guest) {
    	this.guest = guest;
    	phone.setText(String.valueOf(guest.getPhone()));
    	search();
    }
}
