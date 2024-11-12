package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminLoginController {

    @FXML
    private Button homeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    void startAdminPage(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/AdminPage.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (loginBtn.getScene().getWindow());
    		stage.close();
	    	stage.setScene(scene);
	    	stage.setMaximized(true);
	    	stage.setResizable(false);
	        stage.show();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void startMain(ActionEvent event) {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/Main.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (homeBtn.getScene().getWindow());
    		stage.close();
	    	stage.setScene(scene);
	    	stage.setMaximized(true);
	    	stage.setResizable(false);
	        stage.show();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}