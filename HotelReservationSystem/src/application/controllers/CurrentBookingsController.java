package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CurrentBookingsController {

    @FXML
    private Button mainMenuBtn;

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
            stage.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

}
