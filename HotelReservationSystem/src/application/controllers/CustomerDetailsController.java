package application.controllers;

import application.models.Guest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CustomerDetailsController {
	@FXML
    private TextField address;

    @FXML
    private Button bookingsBtn;

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private TextField phone;

    @FXML
    private TextField title;
    
    Guest guest;
    
    @FXML
    void initialize() {
    	
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
    void openCustomerBookings(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/BillSearch.fxml"));
    		BorderPane root = loader.load();
    		BillSearchController controller = loader.getController();
    		controller.setGuest(guest);
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
    
    public void setCustomer(Guest guest) {
    	this.guest = guest;
    	title.setText(guest.getTitle());
    	firstName.setText(guest.getFirstName());
    	lastName.setText(guest.getLastName());
    	address.setText(guest.getAddress());
    	phone.setText(String.valueOf(guest.getPhone()));
    	email.setText(guest.getEmail());
    }
}
