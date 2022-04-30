import java.sql.*;
import java.util.Scanner;

public class UsersManager {

    public static void main(String[] args) {
        String user = "root";
        String pass ="";
        String url="jdbc:mysql://localhost:3306";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement st = conn.createStatement();
            String sql = "USE dziennik";                  // zmiana tabeli !
            st.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(15) NOT NULL," +
                    "surname VARCHAR(30) NOT NULL," +
                    "login VARCHAR(45) NOT NULL," +
                    "password VARCHAR(10) NOT NULL," +
                    "type_choice ENUM," +
                    "FOREIGN KEY(type_choice) REFERENCES type(type_choice)";   // czy klucz obcy tylko primary key
            st.execute(sql);

            PreparedStatement pst;
            Scanner sc = new Scanner(System.in);

            while (true){
                System.out.println("Zarządzanie użytkownika");
                System.out.println("1. Dodaj użytkownika");
                System.out.println("2. Wyświetl użytkownika");
                System.out.println("3. Usuń użytkownika");
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
                        System.out.println("Dodawanie nowej osoby");
                        String name;
                        String surname;
                        String login;
                        String password;
                        String type_choice;
                        System.out.print("Podaj imie: ");
                        name = sc.nextLine();
                        System.out.print("Podaj nazwisko: ");
                        surname = sc.nextLine();
                        System.out.print("Podaj login: ");
                        login = sc.nextLine();
                        System.out.print("Podaj haslo: ");
                        password = sc.nextLine();
                        System.out.print("Podaj typ użytkownika: Nauczyciel, Uczeń, Administrator ");
                        type_choice = sc.nextLine();

                        /////////////////////////////////////////////////
                        // wczytywanie od uzytkownika
                        sql="INSERT INTO users(name, surname, login, password, status_id) VALUES (?,?,?,?,?)";
                        pst = conn.prepareStatement(sql);   // inicjalizacja obiektu zapytania
                        pst.setString(1, name);     // 1 to pierwszy ?
                        pst.setString(2, surname);
                        pst.setString(3, login);
                        pst.setString(4, password);
                        pst.setString(5, type_choice);
                        pst.execute();  // bez podawania parametru sql, execute jak enter
                        System.out.println("Osoba zostala dodana");
                        break;
                    case 2:
                        System.out.println("Lista użytkowników:");
                        sql="SELECT * FROM users";
                        ResultSet res;
                        res = st.executeQuery(sql);
                        while(res.next()) {
                            System.out.print(res.getInt("user_id"));
                            System.out.print(". ");
                            System.out.print(res.getString("name"));
                            System.out.print(". ");
                            System.out.print(res.getString("surname"));
                            System.out.print(". ");
                            System.out.print(res.getString("status_id"));
                            // nie wystwiela loginu i hasla
                        }
                        break;
                    case 3:
                        // wczytac id z klawiatury ktore chce usunac
                        int user_id;
                        System.out.println("Którą klasę chcesz usunąć - podaj id");
                        user_id = sc.nextInt();
                        sc.nextLine();
                        sql ="DELETE FROM users WHERE user_id =?";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, user_id);
                        pst.execute();
                        System.out.println("Użytkownik został usunięty");
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


