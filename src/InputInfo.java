import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class InputInfo extends JFrame {
    static JTextField userText;
    PreparedStatement pstmt;
    ResultSet rs;

    public InputInfo()
    {
        JPanel inputInfoPanel = new JPanel();
        add(inputInfoPanel);
        inputInfoPanel.setPreferredSize(new Dimension(1000, 800));
        inputInfoPanel.setBackground(Color.white);

        GridLayout grid = new GridLayout(2, 1, 0, 0);
        inputInfoPanel.setLayout(grid);

        JPanel imagePanel = new JPanel();
        JPanel inputPanel = new JPanel();
        imagePanel.setBackground(Color.white);
        inputPanel.setBackground(Color.white);

        inputInfoPanel.add(imagePanel);
        inputInfoPanel.add(inputPanel); //inputInfoPanel은 imagePanel(안내문 이미지를 넣은 패널)과 inputPanel(사용자 입력 field, 입력 버튼)이 있는 패널

        ImageIcon guideImage = new ImageIcon("images/InputStmt.PNG");

        JLabel guideLabel = new JLabel(guideImage);
        imagePanel.add(guideLabel);

        userText = new JTextField(10);
        JButton btnInput = new JButton(new ImageIcon("images/Input.PNG"));
        btnInput.setBorderPainted(false);
        btnInput.setContentAreaFilled(false);

        inputPanel.add(userText);
        inputPanel.add(btnInput);

        btnInput.addActionListener(new ActionListenerNickNameInput()); //사용자가 textfield를 작성하고 누르는 버튼 액션리스너 추가
    }

    public class ActionListenerNickNameInput implements ActionListener { //액션 리스너

        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton)e.getSource();
            try {
                String querySelect = "select cnt_win from ranking where name = ?"; //사용자가 입력한 닉네임의 점수를 묻는 query
                excuteQueryPstmt(querySelect);

                if(rs.next()) { //점수가 존재한다면 기존 회원
                    System.out.println("You're an existing member!");
                    excuteQueryPstmt(querySelect);
                    String queryUpdate = "UPDATE ranking SET cnt_win = ? where name = ?"; //기존회원이라면 해당 회원의 점수를 Update
                    excuteUpdatePstmt(queryUpdate, 2, 1); //update문과 insert문 구별을 위해 매개변수에 숫자를 추가
                }
                else //점수가 존재하지 않는다면 신규회원
                {
                    System.out.println("You're an new member!");
                    String queryInsert = "INSERT INTO ranking VALUES (?,?)"; //신규회원이라면 입력한 닉네임과 처음 이겼으니 이긴횟수 1을 추가
                    excuteUpdatePstmt(queryInsert, 1, 2);
                }
                System.out.println("Input success");
                btn.setEnabled(false); //한번 입력하면 버튼을 누르지 못하도록
            }catch (Exception e1) {
                System.out.println("Input fail");
            }
        }

        public void excuteQueryPstmt(String queryStmt) throws SQLException, ClassNotFoundException { //select문을 실행하기 위한 함수
            try {
                pstmt = Database.con.prepareStatement(queryStmt);
                pstmt.setString(1, userText.getText());
                rs = pstmt.executeQuery();
            } catch (SQLException e) {
                System.out.println("executeQuery fail");
                throw e;
            }
        }

        public void excuteUpdatePstmt(String sqlStmt, int stringNum, int intNum) throws SQLException, ClassNotFoundException { //insert문과 update문을 실행하기 위한 함수
            try {
                pstmt = Database.con.prepareStatement(sqlStmt);
                pstmt.setString(stringNum, userText.getText());
                if (intNum == 1) pstmt.setInt(intNum, countOfWin()); //기존회원
                else pstmt.setInt(intNum, 1); //신규회원
                pstmt.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println("executeUpdate fail");
                throw e;
            }
        }

        public int countOfWin() throws SQLException, ClassNotFoundException { //기존 회원의 승리 횟수에 이번 게임 승리 횟수를 더한 후 반환
            int cnt = 0;

            try {
                while (rs.next()) cnt = rs.getInt(1);
                cnt++;
            } catch (SQLException e) {
                System.out.println("count of win fail");
                throw e;
            }

            return cnt;
        }
    }
}

