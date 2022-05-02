
import javax.swing.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class StudentPanel extends JFrame{

    //ramka z aplikacja
    //JFrame frame = new JFrame();
    Connection conn;
    private static JLabel mathsLabel;
    private static JTextArea mathsJTextArea;
    private static JPanel panel;

    public static void main(String[] args) {
        //utworzenie obiektu klasy    .. tworzenie JFrame
        StudentPanel frame = new StudentPanel();
        JPanel panel = new JPanel();
        frame.setSize(350,600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        mathsLabel = new JLabel("Matematyka");
        mathsLabel.setBounds(10, 50, 80,25);
        panel.add(mathsLabel);

        mathsJTextArea = new JTextArea("res");
        mathsJTextArea.setBounds(100,50,165,25);
        panel.add(mathsJTextArea);

    }

    //konstrukor do ktorego beda trafiac dane
    public StudentPanel() {
        //polaczenie z baza ktora istenieje
        String user = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/dziennik";

        try {
            conn = DriverManager.getConnection(url, user, pass);  // bylo connection conn ale ma byc globalne wiec u gory
            Statement st = conn.createStatement();
            String sql = "SELECT mark FROM marks WHERE student_id=1 and subject_id = 3";
            ResultSet res = st.executeQuery(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}