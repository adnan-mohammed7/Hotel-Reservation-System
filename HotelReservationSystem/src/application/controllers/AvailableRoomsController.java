package application.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.database.Database;
import application.models.Reservation;
import application.models.Room;
import application.models.RoomDetails;
import application.utility.Validate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AvailableRoomsController {

	@FXML
    private DatePicker inDate;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private DatePicker outDate;

    @FXML
    private TableColumn<Room, Integer> roomIdCol;

    @FXML
    private TableColumn<Room, String> roomTypeCol;

    @FXML
    private Label roomsAvailableLabel;

    @FXML
    private TableView<Room> roomsTable;

    @FXML
    private Button searchBtn;
    
    Database db;
    ObservableList<Room> allRooms;
	    
	@FXML
	void initialize() {
		db = new Database();

		allRooms = FXCollections.observableArrayList();
		roomIdCol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("roomID"));
		roomTypeCol.setCellValueFactory(new PropertyValueFactory<Room, String>("roomType"));
		roomsTable.setItems(allRooms);
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
    void searchAvailableRooms(ActionEvent event) {
    	setUpdateRoomList();
    	roomsAvailableLabel.setText("Number of Available Rooms: " + allRooms.size());
    }
    
    void setUpdateRoomList() {
    	if(validateFields()) {
    		if(allRooms != null && allRooms.size() > 0) {
    			allRooms.clear();
    		}
    		allRooms.addAll(db.getAllRooms());
    		
        	LocalDate in = inDate.getValue();
        	LocalDate out = outDate.getValue();
        	
        	List<Reservation> exisitingReservation = db.getReservationByDate(Date.valueOf(in), Date.valueOf(out));
        	List<RoomDetails> exisitingDetails = new ArrayList<RoomDetails>();
        	
        	
        	for(Reservation e : exisitingReservation) {
        		if(e.getStatus().equals("Reserved")) {
        			exisitingDetails.addAll(e.getRoomDetails());
        		}
        	}
        	
        	for(RoomDetails e : exisitingDetails) {
        		allRooms.remove(e.getRoom());
        	}
    	}
    }
    
    boolean validateFields() {
    	if(inDate.getValue() == null) {
    		Validate.showAlert("Please select a valid check-in date");
    		return false;
    	}else {
    		if(inDate.getValue().isBefore(LocalDate.now())){
    			Validate.showAlert("Please select a check-in date not prior to today");
    			return false;
    		}
    	}
    	if(outDate.getValue() == null) {
    		Validate.showAlert("Please select a valid check-out date");
    		return false;
    	}else {
    		if(outDate.getValue().isBefore(inDate.getValue())) {
    			Validate.showAlert("Please select a check-out date not prior to check-in date");
    			return false;
    		}else if(outDate.getValue().isEqual(inDate.getValue())) {
    			Validate.showAlert("Please select a check-out date not prior to tomorrow");
    			return false;
    		}
    	}
    	return true;
    }
}
