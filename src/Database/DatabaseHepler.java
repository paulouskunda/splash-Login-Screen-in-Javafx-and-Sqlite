package login.Database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//CLASS THAT WILL HANDLE THE LOGIN AND REGISTRATION
public class DatabaseHepler {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet res = null;

    public  boolean login(String sql){
        connection = DatabaseHandler.get_con();
        boolean logged_in = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            res = preparedStatement.executeQuery();

            if(res.next()){
                logged_in =true;
            } else logged_in = false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e + " during login");
        }

        finally {
            try{
                res.close();
                preparedStatement.close();
            }catch (Exception e){

            }
        }
        return logged_in;
    }

    public void onRegister(String sql){
        connection = DatabaseHandler.get_con();

        try {
            preparedStatement = connection.prepareStatement(sql);
            res = preparedStatement.executeQuery();
            System.out.println("INSERTED");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e + " during login");
        }

        finally {
            try{
                res.close();
                preparedStatement.close();
            }catch (Exception e){

            }
        }
    }
}
