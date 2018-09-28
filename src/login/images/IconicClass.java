package login.images;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import login.Alerts.AlertMaker;

public class IconicClass {

    public static final String ICON_IMAGE_LOC = "/login/images/lock.png";
    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_IMAGE_LOC));
    }


}
