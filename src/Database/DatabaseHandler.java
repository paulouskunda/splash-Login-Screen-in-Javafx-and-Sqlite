package login.Database;

/* CLASS THAT WILL CREATE THE DATABASE
* CONNECTION
* */
import java.sql.*;

public class DatabaseHandler
{

 static PreparedStatement preparedStatement = null;
 static ResultSet res = null;
 static String TABLE_NAME = "users";
 public static Connection get_con(){
    String sql = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, password VARCHAR, email VARCHAR)";

     try{
         Class.forName("org.sqlite.JDBC");
         Connection con = DriverManager.getConnection("jdbc:sqlite:src/user_db.sqlite");
          preparedStatement = con.prepareStatement(sql);
         preparedStatement.execute();


         return con;
     }catch (Exception e){
         System.out.println(e);
         return null;
     }

 }

}
