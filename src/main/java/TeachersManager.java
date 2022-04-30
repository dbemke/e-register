import java.sql.*;
import java.util.Scanner;

public class TeachersManager {

    public static void main(String[] args) {
        String user = "root";
        String pass ="";
        String url="jdbc:mysql://localhost:3306";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement st = conn.createStatement();
            String sql = "USE dziennik";                  // zmiana tabeli !
            st.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS teachers (" +
                    "teacher_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(15) NOT NULL," +
                    "surname VARCHAR(30) NOT NULL," +
                    "login VARCHAR(45) NOT NULL," +
                    "password VARCHAR(10) NOT NULL," +
                    "subject_id INT," +
                    "FOREIGN KEY(subject_id) REFERENCES subjects(subject_id))";
            st.execute(sql);

            PreparedStatement pst;
            Scanner sc = new Scanner(System.in);

            while (true){
                System.out.println("Zarządzanie nauczycielami");
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
                        System.out.println("Dodawanie nowego nauczyciela");
                        String name;
                        String surname;
                        String login;
                        String password;
                        int subject_id;
                        System.out.print("Podaj imie: ");
                        name = sc.nextLine();
                        System.out.print("Podaj nazwisko: ");
                        surname = sc.nextLine();
                        System.out.print("Podaj login: ");
                        login = sc.nextLine();
                        System.out.print("Podaj haslo: ");
                        password = sc.nextLine();
                        System.out.print("Podaj id przedmiotu: ");
                        subject_id = sc.nextInt();
                        sc.nextLine();

                        /////////////////////////////////////////////////
                        // wczytywanie od uzytkownika
                        sql="INSERT INTO teachers(name, surname, login, password, subject_id) VALUES (?,?,?,?,?)";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, name);
                        pst.setString(2, surname);
                        pst.setString(3, login);
                        pst.setString(4, password);
                        pst.setInt(5, subject_id);
                        pst.execute();
                        System.out.println("Nauczyciel został dodany");
                        break;
                    case 2:
                        System.out.println("Lista nauczycieli:");
                        sql="SELECT * FROM teachers";
                        ResultSet res;
                        res = st.executeQuery(sql);
                        while(res.next()) {
                            System.out.print(res.getInt("teacher_id"));
                            System.out.print(". ");
                            System.out.print(res.getString("name"));
                            System.out.print(" ");
                            System.out.print(res.getString("surname"));
                            System.out.print(" id ");
                            System.out.print(res.getInt("subject_id"));
                            System.out.println(" ");
                            // nie wystwiela loginu i hasla
                        }
                        break;
                    case 3:
                        // wczytac id z klawiatury ktore chce usunac
                        int teacher_id;
                        System.out.println("Którego nauczyciela chcesz usunąć - podaj id");
                        teacher_id = sc.nextInt();
                        sc.nextLine();
                        sql ="DELETE FROM teachers WHERE teacher_id =?";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, teacher_id);
                        pst.execute();
                        System.out.println("Nauczyciel został usunięty");
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




