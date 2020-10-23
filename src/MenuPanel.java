import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;

//게임을 시작하면 맨 처음 메뉴가 나오는 패널
public class MenuPanel extends JPanel {

    private JLabel lblTitle;
    public JButton btnStart, btnExplain, btnExit;
    public JButton btnRank;

    public MenuPanel() {

        GameManager.getInstance().set_menu(this);


        setBounds(0,0,1000, 800);
        setBackground(Color.white);
        setLayout(null);

        //게임의 타이틀
        lblTitle = new JLabel(new ImageIcon("images/Title.PNG"));
        lblTitle.setBounds(220, 150, 600, 120);
        lblTitle.setVisible(true);
        add(lblTitle);

        //게임시작 버튼 세팅
        btnStart = new JButton("Start");
        btnStart.setBounds(300, 400, 400, 100);
        btnStart.setFont(new Font("OCR A Extended", Font.BOLD, 40));
        GameManager.getInstance().setBtnInit(btnStart);
        add(btnStart);

        //게임방법 버튼 세팅
        btnExplain = new JButton("How to Play");
        btnExplain.setBounds(300, 500, 400, 100);
        btnExplain.setFont(new Font("OCR A Extended", Font.BOLD, 40));
        GameManager.getInstance().setBtnInit(btnExplain);
        add(btnExplain);

        btnRank = new JButton("Rank");
        btnRank.setBounds(300, 600, 400, 100);
        btnRank.setFont(new Font("OCR A Extended", Font.BOLD, 40));
        GameManager.getInstance().setBtnInit(btnRank);
        add(btnRank);

        //게임종료 버튼 세팅
        btnExit = new JButton("Exit");
        btnExit.setBounds(300, 700, 400, 100);
        btnExit.setFont(new Font("OCR A Extended", Font.BOLD, 40));
        GameManager.getInstance().setBtnInit(btnExit);

        add(btnExit);



    }//constructor




}
