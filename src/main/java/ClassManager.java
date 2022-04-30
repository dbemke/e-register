import java.sql.*;
import java.util.Scanner;

public class ClassManager {

    public static void main(String[] args) {
        String user = "root";
        String pass ="";
        String url="jdbc:mysql://localhost:3306";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement st = conn.createStatement();
            String sql = "USE dziennik";                  // zmiana tabeli !
            st.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS classes("+
                    "class_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(2) NOT NULL)";
            st.execute(sql);

            PreparedStatement pst;
            Scanner sc = new Scanner(System.in);

            while (true){
                System.out.println("Zarządzanie nazwami klas");
                System.out.println("1. Dodaj klase");
                System.out.println("2. Wyśtwietl klasy");
                System.out.println("3. Usuń klase");
                System.out.println("0. Koniec");
                System.out.print("Wybierz opcje: ");
                int opt = sc.nextInt();
                sc.nextLine();

                switch (opt){
                    case 0:
                        System.out.println("Ok. Kończymy");
                        conn.close();
                        System.exit(0);
                        break;
                    case 1:
                        System.out.println("Dodawanie nowej klasy");
                        String name;
                        System.out.print("Podaj nazwę klasy: ");
                        name = sc.nextLine();

                        /////////////////////////////////////////////////
                        // wczytywanie od uzytkownika
                        sql="INSERT INTO classes (name) VALUES (?)";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, name);
                        pst.execute();
                        System.out.println("Klasa została dodana");
                        break;
                    case 2:                       // NIE DZIALA WYSWIETLANIE
                        System.out.println("Lista klas:");
                        sql="SELECT * FROM classes";
                        ResultSet res;
                        res = st.executeQuery(sql);
                        while(res.next()) {
                            System.out.print(res.getInt("class_id"));
                            System.out.println(" ");
                            System.out.print(res.getString("name"));
                            System.out.println(" ");
                        }
                        break;
                    case 3:
                        // wczytac id z klawiatury ktore chce usunac
                        int class_id;
                        System.out.println("Którą klasę chcesz usunąć - podaj id");
                        class_id = sc.nextInt();
                        sc.nextLine();
                        sql ="DELETE FROM classes WHERE class_id =?";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, class_id);
                        pst.execute();
                        System.out.println("Klasa została usunięta");
                        break;
                }

            }


        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Ups...");
            System.out.println(e.toString());
        }

    }
}

