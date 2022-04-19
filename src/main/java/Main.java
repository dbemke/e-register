import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");
            String result = new String();
            while (rs.next()) {
                result += rs.getString(1) + "\n";
            }
            System.out.println(result);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
