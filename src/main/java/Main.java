import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
