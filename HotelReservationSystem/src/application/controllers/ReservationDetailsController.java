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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ReservationDetailsController {

	@FXML
    private Button addBtn;
	
	@FXML
    private Button cancelBtn;

    @FXML
    private ChoiceBox<Integer> availableRooms;

    @FXML
    private DatePicker checkInDate;

    @FXML
    private DatePicker checkOutDate;

    @FXML
    private TableView<RoomDetails> listView;

    @FXML
    private Button nextBtn;

    @FXML
    private ChoiceBox<Integer> numOfGuest;

    @FXML
    private TextField ratePerDay;

    @FXML
    private Button removeBtn;

    @FXML
    private ChoiceBox<String> roomType;

    @FXML
    private Button rulesBtn;

    @FXML
    private TextField totalRatePerDay;
    
    @FXML
    private TableColumn<RoomDetails, Integer> numOfGuestCol;
    
    @FXML
    private TableColumn<RoomDetails, Integer> roomNumCol;
    
    @FXML
    private TableColumn<RoomDetails, String> roomTypeCol;
    
    RoomDetails roomDetails;
    ObservableList<RoomDetails> allSelectedRooms;
    Reservation reservation;
    Database db;
    List<Room> allRooms;
    
    @FXML
    void initialize() {
    	db = new Database();
    	numOfGuest.getItems().addAll(1, 2, 3, 4);
    	
    	checkInDate.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> arg0, LocalDate arg1, LocalDate arg2) {
				if (arg2 != null) {
                    setUpdateRoomList();
                    clearFields();
                }
				
			}
		});
    	
    	checkOutDate.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> arg0, LocalDate arg1, LocalDate arg2) {
				if (arg2 != null) {
                    setUpdateRoomList();
                    clearFields();
                }
				
			}
		});
    	
    	numOfGuest.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv)->{
    		roomType.getItems().clear();
    		if(nv != null) {
    			if(nv < 3) {
        			roomType.getItems().addAll("Single Room", "Double Room", "Deluxe Room", "Pent House");
        		}else {
        			roomType.getItems().add("Double Room");
        		}
    		}
    	});
    	
    	roomType.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) ->{
    		availableRooms.getItems().clear();
    		if(nv != null) {
    			double rate = 0.0;
    			for(Room e : allRooms) {
        			if(nv.equals("Single Room") && e.getRoomType().equals("Single Room")) {
        				availableRooms.getItems().add(e.getRoomID());
        				if(rate == 0.0) {
            				rate = e.getRate();
            			}
            		}else if(nv.equals("Double Room") && e.getRoomType().equals("Double Room")) {
            			availableRooms.getItems().add(e.getRoomID());
            			if(rate == 0.0) {
            				rate = e.getRate();
            			}
            		}else if(nv.equals("Deluxe Room") && e.getRoomType().equals("Deluxe Room")) {
            			availableRooms.getItems().add(e.getRoomID());
            			if(rate == 0.0) {
            				rate = e.getRate();
            			}
            		}else if(nv.equals("Pent House") && e.getRoomType().equals("Pent House")) {
            			availableRooms.getItems().add(e.getRoomID());
            			if(rate == 0.0) {
            				rate = e.getRate();
            			}
            		}
            	}
    			ratePerDay.setText(String.format("$ %.2f", rate));
    		}
    	});
    	
    	allSelectedRooms = FXCollections.observableArrayList();
    	roomNumCol.setCellValueFactory(cellData -> 
        new SimpleObjectProperty<>(cellData.getValue().getRoom().getRoomID()));
    	roomTypeCol.setCellValueFactory(cellData -> 
        new SimpleObjectProperty<>(cellData.getValue().getRoom().getRoomType()));
    	numOfGuestCol.setCellValueFactory(new PropertyValueFactory<RoomDetails, Integer>("numOfGuest"));
    	
    	listView.setItems(allSelectedRooms);
    	
    	allSelectedRooms.addListener((ListChangeListener<RoomDetails>) e ->{
    		if (allSelectedRooms.size() > 0) {
    	        checkInDate.setDisable(true);
    	        checkOutDate.setDisable(true);
    	        nextBtn.setDisable(false);
    	    } else {
    	        checkInDate.setDisable(false);
    	        checkOutDate.setDisable(false);
    	        nextBtn.setDisable(true);
    	    }
    		double total = 0.0;
    		for(RoomDetails i : allSelectedRooms) {
    			total += i.getRoom().getRate();
    		}
    		totalRatePerDay.setText(String.format("$ %.2f", total));
    	});
    	reservation = new Reservation();
    	nextBtn.setDisable(true);
    }
    
    @FXML
    void addRoom(ActionEvent event) {
    	if(validateFields()) {
    		roomDetails = new RoomDetails();
    		roomDetails.setRoom(db.getRoomByID(availableRooms.getValue()));
    		roomDetails.setNumOfGuest(numOfGuest.getValue());
    		allRooms.remove(roomDetails.getRoom());
    		allSelectedRooms.add(roomDetails);
    		clearFields();
    	}
    }

    @FXML
    void removeRoom(ActionEvent event) {
    	if (listView.getSelectionModel().getSelectedItem() != null) {
    		allRooms.add(listView.getSelectionModel().getSelectedItem().getRoom());
    		allSelectedRooms.remove(listView.getSelectionModel().getSelectedItem());
    		clearFields();
    	}else {
    		Validate.showAlert("Please select the room to be removed");
    	}
    }

    @FXML
    void showRules(ActionEvent event) {
    	String rules = "Single room: Max 2 person's.\n"
    			+ "Double room: Max 4 person's.\n"
    			+ "Deluxe room and Pent House: Max 2 person's\n";
    	
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Rules");
		alert.setHeaderText(null);
		alert.setContentText(rules);
		DialogPane dialog = alert.getDialogPane();
		dialog.setPrefSize(500, 240);
		dialog.setStyle("-fx-font-size: 20px");
		alert.show();
    }

    @FXML
    void startGuestDetails(ActionEvent event) {
    	reservation.setRoomDetails(allSelectedRooms);
    	reservation.setCheckInDate(checkInDate.getValue());
    	reservation.setCheckOutDate(checkOutDate.getValue());
    	reservation.setBookingDate(LocalDate.now());
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/GuestDetails.fxml"));
    		BorderPane root = loader.load();
    		
    		GuestDetailsController controller = loader.getController();
    		controller.setReservation(reservation);
    		
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (nextBtn.getScene().getWindow());
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
    
    @FXML
    void cancelBooking(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Main.fxml"));
    		BorderPane root = loader.load();
    		
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (cancelBtn.getScene().getWindow());
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

    
    boolean validateFields() {
    	if(checkInDate.getValue() == null) {
    		Validate.showAlert("Please select a valid check-in date");
    		return false;
    	}else {
    		if(checkInDate.getValue().isBefore(LocalDate.now())){
    			Validate.showAlert("Please select a check-in date not prior to today");
    			return false;
    		}
    	}
    	if(checkOutDate.getValue() == null) {
    		Validate.showAlert("Please select a valid check-out date");
    		return false;
    	}else {
    		if(checkOutDate.getValue().isBefore(checkInDate.getValue())) {
    			Validate.showAlert("Please select a check-out date not prior to check-in date");
    			return false;
    		}else if(checkOutDate.getValue().isEqual(checkInDate.getValue())) {
    			Validate.showAlert("Please select a check-out date not prior to tomorrow");
    			return false;
    		}
    	}
    	if(numOfGuest.getValue() == null) {
    		Validate.showAlert("Please select a valid number of guests");
    		return false;
    	}
    	if(roomType.getValue() == null) {
    		Validate.showAlert("Please select a valid room type");
    		return false;
    	}
    	if(availableRooms.getValue() == null) {
    		Validate.showAlert("Please select a valid room");
    		return false;
    	}
    	return true;
    }
    
    void clearFields() {
    	numOfGuest.setValue(null);
    	availableRooms.setValue(null);
    	roomType.setValue(null);
    	ratePerDay.setText("$ 0.00");
    }
    
    void setUpdateRoomList() {
    	if(checkInDate.getValue() != null && checkOutDate.getValue() != null) {
    		if(allRooms != null && allRooms.size() > 0) {
    			allRooms.clear();
    		}
    		allRooms = db.getAllRooms();
    		
        	LocalDate in = checkInDate.getValue();
        	LocalDate out = checkOutDate.getValue();
        	
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
    
    void updateAvailableRoomView(String nv) {
    	availableRooms.getItems().clear();
		double rate = 0.0;
		for(Room e : allRooms) {
			if(nv.equals("Single Room") && e.getRoomType().equals("Single Room")) {
				availableRooms.getItems().add(e.getRoomID());
				if(rate == 0.0) {
    				rate = e.getRate();
    			}
    		}else if(nv.equals("Double Room") && e.getRoomType().equals("Double Room")) {
    			availableRooms.getItems().add(e.getRoomID());
    			if(rate == 0.0) {
    				rate = e.getRate();
    			}
    		}else if(nv.equals("Deluxe Room") && e.getRoomType().equals("Deluxe Room")) {
    			availableRooms.getItems().add(e.getRoomID());
    			if(rate == 0.0) {
    				rate = e.getRate();
    			}
    		}else if(nv.equals("Pent House") && e.getRoomType().equals("Pent House")) {
    			availableRooms.getItems().add(e.getRoomID());
    			if(rate == 0.0) {
    				rate = e.getRate();
    			}
    		}
		}
    }
}
