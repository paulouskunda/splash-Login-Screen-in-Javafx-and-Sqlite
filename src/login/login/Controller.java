package login.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import login.Alerts.AlertMaker;
import login.Database.DatabaseHandler;
import login.Database.DatabaseHepler;
import login.images.IconicClass;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    public AnchorPane root;
    public JFXTextField name;
    public JFXTextField email;
    public JFXPasswordField reg_password;
    public JFXPasswordField confirm_password;
    public JFXTextField username;
    public JFXPasswordField password;
    AnchorPane rootp;
    Connection con = DatabaseHandler.get_con();
    PreparedStatement preparedStatement = null;
    ResultSet res = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rootp = root;
        //MAKING SURE THE LOADING ANIMATION DOES NOT LOOP MORE THAN ONCE
        if (!Main.isLoaded){
            loadSplash();
        }
    }

    //method for login
    public void onLogin(){

        String sql = "SELECT * FROM users WHERE username = '" + username.getText() + "' AND password = '" + password.getText() + "'";


        ///CREATING TH DATABASE HELPER OBJECT
        DatabaseHepler helper = new DatabaseHepler();

        boolean check = helper.login(sql);

        if (check){
            System.out.println("LOGGED IN");
            AlertMaker.showSimpleAlert("WELCOME","LOGIN WAS A SUCCESS");
            Main.name = username.getText();
            closeStage();
            try {
                loadWindow("/login/welcome/welcome.fxml","WELCOME");
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {

            AlertMaker.showErrorMessage("","NO RECORDS");
        }

    }

    private void closeStage() {
        //GETTING THE STAGE SO THAT WE CAN CLOSE IT ON SUCCESSFUL LOGIN.
        ((Stage) username.getScene().getWindow()).close();
    }

    //method for registration
    public void onReg(){
        if (reg_password.getText().equals(confirm_password.getText())) {
            try {

                String sql = "INSERT INTO users(username,password,email) VALUES (?,?,?)";
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, name.getText());
                preparedStatement.setString(2, reg_password.getText());
                preparedStatement.setString(3, email.getText());
                preparedStatement.execute();
                Main.name = name.getText();
                System.out.println("RECORDS INSERTED");
                AlertMaker.showSimpleAlert("WELCOME","REGISTRATION WAS A SUCCESS");
                closeStage();
                loadWindow("/login/welcome/welcome.fxml","WELCOME");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("ALL WENT DATA");
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    preparedStatement.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        } else {
            AlertMaker.showErrorMessage("","PASSWORD DON'T MATCH");
        }

    }

    /*method to load the next window
    *
    * */
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

    //Method to handle the splash screen
    private void loadSplash(){
        try {
            Main.isLoaded = true;

            AnchorPane pane = FXMLLoader.load(getClass().getResource(("splashLogin.fxml")));
            root.getChildren().setAll(pane);
            pane.setPrefSize(960.0,936.0);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("login.fxml")));
                    root.getChildren().setAll(parentContent);
                    parentContent.setPrefSize(960.0,936.0);
                    //parentContent.setStyle("-fx-background-color: #000");

                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
