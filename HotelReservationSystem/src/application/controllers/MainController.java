package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;

public class MainController {

    @FXML
    private Menu adminLoginMenu;

    @FXML
    void startAdmin(ActionEvent event) {
    	System.out.println("Done");
    }

}
