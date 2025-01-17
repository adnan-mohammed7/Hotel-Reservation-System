package application.controllers;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;

import application.database.Database;
import application.models.Reservation;

public class CurrentBookingsController {

	@FXML
    private TableColumn<Reservation, Integer> bookingNumCol;

    @FXML
    private TableColumn<Reservation, LocalDate> checkInDateCol;

    @FXML
    private TableColumn<Reservation, LocalDate> checkOutDateCol;

    @FXML
    private TableColumn<Reservation, String> customerNameCol;
    
    @FXML
    private TableColumn<Reservation, String> statusCol;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private Label numOfBookingsLabel;

    @FXML
    private TableColumn<Reservation, Integer> numOfRoomsCol;

    @FXML
    private TableColumn<Reservation, String> roomTypesCol;
    
    @FXML
    private TableView<Reservation> bookingsView;
    
    Database db;
    
    @FXML
    void initialize() {
    	db = new Database();
    	ObservableList<Reservation> allReservations = FXCollections.observableArrayList(db.getAllCurrentReservations());
    	bookingNumCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("reservationID"));
    	customerNameCol.setCellValueFactory(cellData -> 
        new SimpleObjectProperty<>(cellData.getValue().getGuest().getFirstName() + 
        " " + cellData.getValue().getGuest().getLastName()));
    	numOfRoomsCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numOfRooms"));
    	roomTypesCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("typesOfRooms"));
    	checkInDateCol.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("checkInDate"));
    	checkOutDateCol.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("checkOutDate"));
    	bookingsView.setItems(allReservations);
    	statusCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("status"));
    	
    	numOfBookingsLabel.setText("Number of Current Bookings: " + allReservations.size());
    	
    	bookingsView.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 2 && bookingsView.getSelectionModel().getSelectedItem() != null) {
    	        Reservation selectedReservation = bookingsView.getSelectionModel().getSelectedItem();
    	        
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
    	        	stage.setTitle("Hotel Marma");
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

}
