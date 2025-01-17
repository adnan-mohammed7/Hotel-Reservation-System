package application.controllers;

import java.util.List;

import application.database.Database;
import application.models.AdminUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminLoginController {

	 @FXML
	    private Button homeBtn;

	    @FXML
	    private Button loginBtn;

	    @FXML
	    private PasswordField passwordField;

	    @FXML
	    private CheckBox showCheckBox;

	    @FXML
	    private TextField usernameField;

	    @FXML
	    private TextField visiblePasswordField;
    
    private List<AdminUsers> users;
    
    @FXML
    public void initialize() {
    	users = new Database().getAdmins();
    	
    	visiblePasswordField.textProperty().bind(passwordField.textProperty());
    }

    @FXML
    void startAdminPage(ActionEvent event) {
    	if(checkUserCredentials(usernameField.getText(), passwordField.getText())) {
    		openAdminPage();
    	}else {
    		usernameField.setText("");
    		passwordField.setText("");
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
	    	stage.setTitle("Hotel Marma");
	        stage.show();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void showPassword(ActionEvent event) {
    	if(showCheckBox.isSelected()) {
            visiblePasswordField.setVisible(true);
    	}else {
            visiblePasswordField.setVisible(false);
    	}
    }

    
    void openAdminPage() {
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/AdminPage.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = (Stage) (loginBtn.getScene().getWindow());
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
    
    void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid User Credentials");
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 20px; -fx-padding: 20px;");
        
        alert.show();
    }
    
    boolean checkUserCredentials(String username, String password) {
    	for(AdminUsers a : users) {
    		if(a.getUserName().equals(username) && a.getPassword().equals(password)) {
    			return true;
    		}else if(a.getUserName().equals(username) && !(a.getPassword().equals(password))) {
    			showAlert("Invaid Password!");
    			return false;
    		}
    	}
    	showAlert("User does not exist!");
    	return false;
    }
}