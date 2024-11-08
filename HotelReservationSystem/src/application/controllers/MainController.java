package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML
    private MenuItem adminLoginBtn;

    @FXML
    void startAdmin(ActionEvent event) {
    	System.out.println("Admin Started");
    }

    @FXML
    void startGuest(MouseEvent event) {
    	System.out.println("Guest Started");
    }
}
