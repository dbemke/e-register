import java.sql.*;
import java.util.Scanner;

public class SubjectsManager {

    public static void main(String[] args) {
        String user = "root";
        String pass ="";
        String url="jdbc:mysql://localhost:3306";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement st = conn.createStatement();
            String sql = "USE dziennik";                  // zmiana tabeli !
            st.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS subjects (" +
                    "subject_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "subjectName VARCHAR(30) NOT NULL)";
            st.execute(sql);

            PreparedStatement pst;
            Scanner sc = new Scanner(System.in);

            while (true){
                System.out.println("Zarządzanie przedmiotami");
                System.out.println("1. Dodaj przedmiot");
                System.out.println("2. Wyśtwietl przedmioty");
                System.out.println("3. Usuń przedmiot");
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
                        System.out.println("Dodawanie nowego przedmiotu");
                        String subjectName;
                        System.out.print("Podaj nazwę przedmiotu: ");
                        subjectName = sc.nextLine();

                        /////////////////////////////////////////////////
                        // wczytywanie od uzytkownika
                        sql="INSERT INTO subjects(subjectName) VALUES (?)";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, subjectName);
                        pst.execute();
                        System.out.println("Przedmiot został dodany");
                        break;
                    case 2:
                        System.out.println("Lista przedmiotów:");
                        sql="SELECT * FROM subjects";
                        ResultSet res;
                        res = st.executeQuery(sql);
                        while(res.next()) {
                            System.out.print(res.getInt("subject_id"));
                            System.out.print(". ");
                            System.out.print(res.getString("subjectName"));
                            System.out.println(" ");
                        }
                        break;
                    case 3:
                        // wczytac id z klawiatury ktore chce usunac
                        int subject_id;
                        System.out.println("Który przedmiot chcesz usunąć - podaj id");
                        subject_id = sc.nextInt();
                        sc.nextLine();
                        sql ="DELETE FROM subjects WHERE subject_id =?";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, subject_id);
                        pst.execute();
                        System.out.println("Przedmiot został usunięty");
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


