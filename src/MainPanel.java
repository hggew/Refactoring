import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

//JPanel 상속
public class MainPanel extends JPanel{
    //필드 -패널들과(view) 각 패널의 컨트롤러 + gamedata(model), 마우스리스너이벤트처리 클래스
    private MenuPanel Menu;
    private ExplainPanel GameExplain;
    private RankPanel Rank;
    private InGameView GameStart;
    private InGameData GameData;
    public btnMouseEvent btnlistener;

    RankPanelController r_control;
    ExplainPanelController e_control;
    InGameController g_control;

    //생성자
    //예외처리 - throws 예외객체 떠넘기기. YutGame에서 MainPanel을 생성하지만 예외처리 하는 부분이 없음
    public MainPanel() throws SQLException, ClassNotFoundException {
        GameManager.getInstance().set_view(this);                   //GameManager에 MainPanel 설정

        setPreferredSize(new Dimension(1000,800));
        setLayout(null);

        //패널, 컨트롤러, InGameData 객체 생성
        Menu = new MenuPanel();
        GameExplain = new ExplainPanel();
        e_control = new ExplainPanelController();
        GameData = new InGameData();
        GameStart = new InGameView();
        g_control = new InGameController();
        Rank = new RankPanel();
        r_control = new RankPanelController();

        //Mainpanel에 각 패널들 추가
        add(Rank);
        add(Menu);
        add(GameExplain);
        add(GameStart);

        //마우스리스너이벤트처리 클래스 생성
        btnlistener= new btnMouseEvent();

        //MenuPanel의 버튼 4개에 액션리스너(MenuSelect)와 btnMouseEvent클래스 추가
        Menu.btnStart.addActionListener(new MenuSelect());
        Menu.btnStart.addMouseListener(btnlistener);
        Menu.btnExplain.addActionListener(new MenuSelect());
        Menu.btnExplain.addMouseListener(btnlistener);
        Menu.btnExit.addActionListener(new MenuSelect());
        Menu.btnExit.addMouseListener(btnlistener);
        Menu.btnRank.addActionListener(new MenuSelect());
        Menu.btnRank.addMouseListener(btnlistener);

        //게임 시작시 메뉴패널보여주는 메소드 호출
        showMenu();
      //  showInGame();
    }

    //메소드 - 각 패널을 MainPanel에 보여주는 메소드. setVisible을 사용함.
    //메뉴패널을 보여주는 메소드. 생성자에서 사용
    public void showMenu(){
        Menu.setVisible(true);
        GameStart.setVisible(false);
        GameExplain.setVisible(false);
        Rank.setVisible(false);
    }

    //게임뷰를 보여주는 메소드.
    public void showInGame(){
        Menu.setVisible(false);
        GameStart.setVisible(true);
        GameExplain.setVisible(false);
        Rank.setVisible(false);
    }//게임시작

    //explainpanel을 보여주는 메소드
    public void showExplain(){
        Menu.setVisible(false);
        GameStart.setVisible(false);
        GameExplain.setVisible(true);
        Rank.setVisible(false);
    }//게임방법

    //rankpanel을 보여주는 메소드
    public void showRank(){
        Menu.setVisible(false);
        GameStart.setVisible(false);
        GameExplain.setVisible(false);
        Rank.setVisible(true);
    }

    //내부클래스 액션리스너
    private class MenuSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object Button = e.getSource();

            if (Menu.btnStart == Button) showInGame();
            else if (Menu.btnExplain == Button) showExplain();
            else if (Menu.btnRank== Button) showRank();
            else if (Menu.btnExit == Button) { System.exit(0);}

        }
    }



}
