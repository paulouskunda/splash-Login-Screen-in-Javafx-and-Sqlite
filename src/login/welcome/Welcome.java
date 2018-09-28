package login.welcome;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Database.DatabaseHandler;
import login.images.IconicClass;
import login.login.Controller;
import login.login.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.ResourceBundle;

public class Welcome implements Initializable {

    public Label Username;
    public Label email;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadData();
    }

    //METHOD THAT WILL LOAD THE USERNAME AND EMAIL ADDRESS ON THE WELCOME SCREEN
    private void loadData() {
        String username =  Main.name;
        Connection connection = DatabaseHandler.get_con();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username ='"+username+"'");
            ResultSet set = statement.executeQuery();
            Username.setText(set.getString(2));
            email.setText(set.getString(4));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onLogOut() throws Exception{

        closeStage();
        loadWindow("/login/login/login.fxml","LOGIN");
    }
    private void closeStage() {
        //GETTING THE STAGE SO THAT WE CAN CLOSE IT ON SUCCESSFUL LOGIN.
        ((Stage) Username.getScene().getWindow()).close();
    }
    public void loadWindow(String loc, String title) throws Exception{
        Stage stage;
        Parent root;


        root = FXMLLoader.load(getClass().getResource(loc));
        stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        IconicClass.setStageIcon(stage);
        stage.show();
    }


}
