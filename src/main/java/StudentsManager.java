import java.sql.*;
import java.util.Scanner;

public class StudentsManager {

    public static void main(String[] args) {
        String user = "root";
        String pass ="";
        String url="jdbc:mysql://localhost:3306";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement st = conn.createStatement();
            String sql = "USE dziennik";                  // zmiana tabeli !
            st.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS students (" +
                    "student_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(15) NOT NULL," +
                    "surname VARCHAR(30) NOT NULL," +
                    "login VARCHAR(45) NOT NULL," +
                    "password VARCHAR(10) NOT NULL," +
                    "class_id INT," +
                    "FOREIGN KEY(class_id) REFERENCES classes(class_id))";   // czy klucz obcy tylko primary key
            st.execute(sql);

            PreparedStatement pst;
            Scanner sc = new Scanner(System.in);

            while (true){
                System.out.println("Zarządzanie uczniami");
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
                        int class_id;
                        System.out.print("Podaj imie: ");
                        name = sc.nextLine();
                        System.out.print("Podaj nazwisko: ");
                        surname = sc.nextLine();
                        System.out.print("Podaj login: ");
                        login = sc.nextLine();
                        System.out.print("Podaj haslo: ");
                        password = sc.nextLine();
                        System.out.print("Podaj id klasy: ");
                        class_id = sc.nextInt();
                        sc.nextLine();

                        /////////////////////////////////////////////////
                        // wczytywanie od uzytkownika
                        sql="INSERT INTO students(name, surname, login, password, class_id) VALUES (?,?,?,?,?)";
                        pst = conn.prepareStatement(sql);   // inicjalizacja obiektu zapytania
                        pst.setString(1, name);     // 1 to pierwszy ?
                        pst.setString(2, surname);
                        pst.setString(3, login);
                        pst.setString(4, password);
                        pst.setInt(5, class_id);
                        pst.execute();  // bez podawania parametru sql, execute jak enter
                        System.out.println("Uczeń został dodany");
                        break;
                    case 2:
                        System.out.println("Lista uczniów:");
                        sql="SELECT * FROM students";
                        ResultSet res;
                        res = st.executeQuery(sql);
                        while(res.next()) {
                            System.out.print(res.getInt("student_id"));
                            System.out.print(". ");
                            System.out.print(res.getString("name"));
                            System.out.print(" ");
                            System.out.print(res.getString("surname"));
                            System.out.print(" id klasy: ");
                            System.out.print(res.getInt("class_id"));
                            System.out.println(" ");
                            // nie wystwiela loginu i hasla
                        }
                        break;
                    case 3:
                        // wczytac id z klawiatury ktore chce usunac
                        int student_id;
                        System.out.println("Którego ucznia chcesz usunąć - podaj id");
                        student_id = sc.nextInt();
                        sc.nextLine();
                        sql ="DELETE FROM students WHERE student_id =?";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, student_id);
                        pst.execute();
                        System.out.println("Uczeń został usunięty");
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



