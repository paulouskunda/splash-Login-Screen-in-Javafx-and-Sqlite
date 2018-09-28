package login.Alerts;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import login.images.IconicClass;

//CLASS THAT WILL HANDLE THE ALERT BOXS
public class AlertMaker {
    public static void showSimpleAlert(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        IconicClass.setStageIcon(stage);
        alert.showAndWait();
    }
    public static void showErrorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        IconicClass.setStageIcon(stage);
        alert.showAndWait();
    }
}
