import javax.swing.*;
import javax.swing.border.Border;

//import application.DBconnect.DBconnect;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RankPanel extends JPanel {

    public JButton btnMenu;
    private JLabel lblTitle, lblId, lblCntWin;
    private JTextArea txtRank;
    public btnMouseEvent btnlistener;

    public RankPanel() throws ClassNotFoundException, SQLException {

        GameManager.getInstance().set_rank(this);
        btnlistener = new btnMouseEvent();

        setBounds(0,0,1000, 800);
        setBackground(Color.white);
        setLayout(null);

        btnMenu = new JButton("Menu");
        btnMenu.setBounds(730,720,220,50);
        btnMenu.setFont(new Font("OCR A Extended",Font.BOLD,30));
        btnMenu.addMouseListener(btnlistener);
        GameManager.getInstance().setBtnInit(btnMenu);
        add(btnMenu);

        lblTitle = new JLabel("Rank");
        lblTitle.setBounds(50, 30, 200, 120);
        lblTitle.setFont(new Font("OCR A Extended",Font.BOLD,50));
        lblTitle.setVisible(true);
        add(lblTitle);


        lblId = new JLabel("ID");
        lblId.setBounds(220, 130, 600, 120);
        lblId.setFont(new Font("OCR A Extended",Font.BOLD,40));
        lblId.setVisible(true);
        add(lblId);

        lblCntWin = new JLabel("Win");
        lblCntWin.setBounds(700, 130, 600, 120);
        lblCntWin.setFont(new Font("OCR A Extended",Font.BOLD,40));
        lblCntWin.setVisible(true);
        add(lblCntWin);


        txtRank = new JTextArea();
        txtRank.setBounds(100, 250, 800, 450);
        Border lineBorder = BorderFactory.createLineBorder(Color.black, 3);
        Border emptyBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);
        txtRank.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        txtRank.setFont(new Font("궁서체",Font.BOLD,40));
        txtRank.setVisible(true);
        txtRank.setEditable(false);
        add(txtRank);

        txtRank.setText(ShowRank());

    }//construct




    public static String ShowRank() throws SQLException, ClassNotFoundException {
        System.out.println("show rank");

        String selectStmt = "select * from ranking order by cnt_win DESC, name ASC limit 8;" ;
        try {
            ResultSet rsMain = Database.dbExecuteQuery(selectStmt);
            return getRankList(rsMain);
        }
        catch (SQLException e) {
            System.out.println("Show Rank ERROR " + e);
            throw e;
        }
    }




    private static String getRankList(ResultSet rs) throws SQLException, ClassNotFoundException {
        System.out.println("getRankList");

        String txtResult="";

        while (rs.next()) {
            txtResult += rs.getString(1) + "\t\t\t" + rs.getInt(2) + "\n";
        }
        return txtResult;
    }




}
