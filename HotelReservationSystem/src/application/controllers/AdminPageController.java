package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminPageController {

    @FXML
    private Button availableRoomsBtn;

    @FXML
    private Button billBtn;

    @FXML
    private Button bookingBtn;

    @FXML
    private Button currentBookingsBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button searchBtn;
    
    @FXML
    private BorderPane screen;
    
    Stage stage;

    @FXML
    void exitTheApplication(ActionEvent event) {
    	stage.close();
    }

    @FXML
    void showAvailableRooms(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/AvailableRooms.fxml"));
    		startStage(root);
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void showCurrentBookings(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/CurrentBookings.fxml"));
    		startStage(root);
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void startBillProcess(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/BillSearch.fxml"));
    		startStage(root);
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void startRoomBooking(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/ReservationDetails.fxml"));
    		startStage(root);
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void startSearch(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/CustomerSearch.fxml"));
    		startStage(root);
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void setStage(MouseEvent event) {
    	stage = (Stage) (exitBtn.getScene().getWindow());
    }
    
    void startStage(BorderPane pane) {
    	Scene scene = new Scene(pane);
		stage.close();
    	stage.setScene(scene);
    	stage.setMaximized(true);
    	stage.setResizable(false);
    	stage.setTitle("Hotel Marma");
        stage.show();
    }
}