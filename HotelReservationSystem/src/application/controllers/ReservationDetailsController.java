package application.controllers;

import java.time.LocalDate;
import java.util.Observable;

import application.models.Room;
import application.models.RoomDetails;
import application.models.Single;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    
    Room room;
    RoomDetails selectedRoom;
    ObservableList<RoomDetails> allSelectedRooms;

    
    @FXML
    void initialize() {
    	numOfGuest.getItems().addAll(1, 2, 3, 4);
    	
    	numOfGuest.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv)->{
    		roomType.getItems().clear();
    		if(nv < 3) {
    			roomType.getItems().addAll("Single Room", "Double Room", "Deluxe Room", "Pent House");
    		}else {
    			roomType.getItems().add("Double Room");
    		}
    	});
    	
    	roomType.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) ->{
    		if(nv != null && nv.equals("Single Room")) {
    			//Available Rooms and rateperday
    		}else if(nv != null && nv.equals("Double Room")) {
    			
    		}else if(nv != null && nv.equals("Deluxe Room")) {
    			
    		}else if(nv != null && nv.equals("Pent House")) {
    			
    		}
    	});
    	
    	room = new Single(312, 80.0);
    	
    	availableRooms.getItems().addAll(245, 301, 10, 312);
    	
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
    	    } else {
    	        checkInDate.setDisable(false);
    	        checkOutDate.setDisable(false);
    	    }
    	});
    }
    
    @FXML
    void addRoom(ActionEvent event) {
    	if(validateFields()) {
    		selectedRoom = new RoomDetails();
    		//room = DATABASE.GETROOM +BYID(availableRooms.getValue);
    		selectedRoom.setRoom(room); //SET ROOM HERE
    		selectedRoom.setNumOfGuest(numOfGuest.getValue());
    		allSelectedRooms.add(selectedRoom);
    	}
    }

    @FXML
    void removeRoom(ActionEvent event) {
    	if (listView.getSelectionModel().getSelectedItem() != null) {
    		allSelectedRooms.remove(listView.getSelectionModel().getSelectedItem());
    	}else {
    		showAlert("Please select the room to be removed");
    	}
    }

    @FXML
    void showRules(ActionEvent event) {

    }

    @FXML
    void startGuestDetails(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/GuestDetails.fxml"));
    		BorderPane root = loader.load();
    		
    		GuestDetailsController controller = loader.getController();
    		controller.setRoomDetails(allSelectedRooms);
    		
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (nextBtn.getScene().getWindow());
    		stage.close();
	    	stage.setScene(scene);
	    	stage.setMaximized(true);
	    	stage.setResizable(false);
	        stage.show();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    boolean validateFields() {
    	if(checkInDate.getValue() == null) {
    		showAlert("Please select a valid check-in date");
    		return false;
    	}else {
    		if(checkInDate.getValue().isBefore(LocalDate.now())){
    			showAlert("Please select a check-in date not prior to today");
    			return false;
    		}
    	}
    	if(checkOutDate.getValue() == null) {
    		showAlert("Please select a valid check-out date");
    		return false;
    	}else {
    		if(checkOutDate.getValue().isBefore(checkInDate.getValue())) {
    			showAlert("Please select a check-out date not prior to check-in date");
    			return false;
    		}else if(checkOutDate.getValue().isEqual(checkInDate.getValue())) {
    			showAlert("Please select a check-out date not prior to tomorrow");
    			return false;
    		}
    	}
    	if(numOfGuest.getValue() == null) {
    		showAlert("Please select a valid number of guests");
    		return false;
    	}
    	if(roomType.getValue() == null) {
    		showAlert("Please select a valid room type");
    		return false;
    	}
    	if(availableRooms.getValue() == null) {
    		showAlert("Please select a valid room");
    		return false;
    	}
    	return true;
    }
    
    public static void showAlert(String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		DialogPane dialog = alert.getDialogPane();
		dialog.setPrefSize(500, 240);
		dialog.setStyle("-fx-font-size: 20px");
		dialog.lookupButton(ButtonType.OK).setStyle("-fx-font-size: 20px");
		
		alert.show();
	}
}
