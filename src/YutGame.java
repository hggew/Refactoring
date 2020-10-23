import javax.swing.JFrame;
import java.awt.*;
import java.sql.SQLException;

public class YutGame {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        JFrame frame = new JFrame("Yut Game");

        Database.connection();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        MainPanel Primary = new MainPanel();
        frame.getContentPane().add(Primary);

        frame.pack();
        frame.setVisible(true);
    }

}
