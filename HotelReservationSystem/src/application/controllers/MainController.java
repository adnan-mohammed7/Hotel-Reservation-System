package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private MenuItem adminLoginMenuItem;
    
    @FXML
    private VBox touchScreen;

    @FXML
    void startAdmin(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/AdminLogin.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (adminLoginMenuItem.getParentPopup().getOwnerWindow());
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
    void startGuest(MouseEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/ReservationDetails.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (touchScreen.getScene().getWindow());
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