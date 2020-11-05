import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

//JPanel 상속
public class InGameView extends JPanel {
    //필드
    public UserPanel        leftUserPanel, rightUserPanel;   //플레이어 두 명의 패널
    private JLabel          gameBoard;                //윷 판
    public Yut              lblThrowing;              //윷을 던지고 결과를 보여줄 사용자정의 라벨
    private InGameData      _gameData;
    public JLabel           lblYutResult;            //윷의 결과값에 따라 텍스트이미지로 보여줄 라벨
    public JButton          leftThrowBtn, rightThrowBtn;  //각 플레이어가 가진 자신의 윷던지기 버튼

    //생성자
    public InGameView(){

        GameManager.getInstance().set_inGame(this);             //GameManager에 InGameView 설정
        _gameData = GameManager.getInstance().get_gameData();   //GameManager에서 InGameData 가져오기

        //패널 설정
        setBounds(0,0,1000, 800);
        setBackground(Color.white);
        setLayout(null);

        //ingameview에 각 플레이어가 가진 pawn 추가
        for(Pawn p:_gameData.leftPlayer.pawns) {
            this.add(p);
        }
        for(Pawn p:_gameData.rightPlayer.pawns) {
            this.add(p);
        }

        //left 유저패널 생성
        leftUserPanel = new UserPanel(_gameData.leftPlayer);
        leftUserPanel.setBounds(0,0,200,600);
        leftUserPanel.setVisible(true);
        add(leftUserPanel);
        //right 유저패널 생성
        rightUserPanel = new UserPanel(_gameData.rightPlayer);
        rightUserPanel.setBounds(800,0,200,600);
        add(rightUserPanel);

        //윷 판 생성
        //JLabel로 생성해서 윷판 이미지 붙이기
        gameBoard = new JLabel();
        gameBoard.setIcon(new ImageIcon("images/boardImage.png"));
        gameBoard.setBounds(200,0,600,600);
        //BoardPanel.setLayout(null);
        add(gameBoard);

        //왼쪽 플레이어의 윷 던지기 버튼 생성, 패널에 붙이기
        leftThrowBtn = new JButton("Throw YUT");
        leftThrowBtn.setBounds(0,599, 200,200);
        leftThrowBtn.setFont(new Font("OCR A Extended", Font.BOLD, 25));
        leftThrowBtn.setLayout(null);
        leftThrowBtn.setBorderPainted(false);  //외곽선 없애기
        leftThrowBtn.setFocusPainted(false);   //선택시 테두리 사용x
        leftThrowBtn.setBackground(new Color(225,213,191));
        add(leftThrowBtn);

        //오른쪽 플레이어의 윷 던지기 버튼 생성, 패널에 붙이기
        rightThrowBtn = new JButton("Throw Yut");
        rightThrowBtn.setBounds(800,599,200,200);
        rightThrowBtn.setFont(new Font("OCR A Extended", Font.BOLD, 25));
        rightThrowBtn.setLayout(null);
        rightThrowBtn.setBorderPainted(false);  //외곽선 없애기
        rightThrowBtn.setFocusPainted(false);   //선택시 테두리 사용x
        rightThrowBtn.setBackground(new Color(225,213,191));
        add(rightThrowBtn);


        //윷 클래스 생성, 패널에 붙이기
        lblThrowing = new Yut();
        lblThrowing.setBounds(210,600,400,200);
        lblThrowing.setVisible(true);
        add(lblThrowing);

        //윷 결과에 따라 텍스트이미지로 보여줄 라벨 생성, 패널에 붙이기
        //lblThrowing 옆에 생성
        lblYutResult= new JLabel();
        lblYutResult.setBounds(650,650,100,100);
        add(lblYutResult);


        repaint();

    }//constructor



}//YutGamePanel()
