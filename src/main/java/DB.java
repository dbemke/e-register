import java.sql.*;

public class DB {
    public boolean IsValidUser(String login,String password){
        String url = "jdbc:mariadb://localhost";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT login, haslo FROM dziennik.uzytkownicy");
            String log = new String();
            String passw = new String();
            while (rs.next()) {
                log = rs.getString(1);
                passw = rs.getString(2);
                if(login.equals(log) && password.equals(passw)){
                    return true;
                }
                System.out.println(log +" " + passw);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return false;
    }
    public String GetMarks(int userId) {
        String url = "jdbc:mariadb://localhost";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT uczen_id, ocena FROM dziennik.oceny");
            int ID;
            String marks = new String();
            while (rs.next()) {
                ID = rs.getInt(1);
                if (userId == ID) {
                    System.out.println(marks);
                }
                //System.out.println(userId);
                else {
                    System.out.println("Niepoprawna nazwa uzytkownika");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return url;
    }
}
