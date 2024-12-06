package application.utility;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class Validate {
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
    
    public static Boolean showConfirmationAlert(String msg) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		DialogPane dialog = alert.getDialogPane();
		dialog.setPrefSize(500, 240);
		dialog.setStyle("-fx-font-size: 20px");
		
		ButtonType yes = new ButtonType("Yes");
		ButtonType no = new ButtonType("No");
		
		alert.getButtonTypes().setAll(yes, no);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent()) {
			if(result.get() == yes) {
				return true;
			}
		}
		return false;
	}
    
    public static void showBookingConfirmation(String msg) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Successfull");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		DialogPane dialog = alert.getDialogPane();
		dialog.setPrefSize(500, 240);
		dialog.setStyle("-fx-font-size: 20px");
		dialog.lookupButton(ButtonType.OK).setStyle("-fx-font-size: 20px");
		
		alert.show();
	}
}
