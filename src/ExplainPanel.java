import javax.swing.*;
import java.awt.*;


//게임 방법을 누르면 나오는 패널
public class ExplainPanel extends JPanel {

    public JLabel lblTitle;
    public JButton btnPrev, btnNext;    //게임방법을 이동할 버튼
    public ImageIcon[] explains;        //게임방법의 이미지를 보여줄 배열 선언
    public int     imageIndex;          //이미지배열에 사용할 인덱스

    public btnMouseEvent btnlistener;     //마우스리스너 선언


    public ExplainPanel(){
        GameManager.getInstance().set_explain(this);    //explainpanel 설정하기
        btnlistener = new btnMouseEvent();              //마우스리스터 객체 생성

        setBounds(0,0,1000,800);
        setBackground(Color.white);
        setLayout(null);

        imageIndex=0;               //인덱스 0으로 설정 : 처음 게임설명이미지
        //이미지 배열에 이미지 생성
        explains = new ImageIcon[4];
        for(int i=0;i<4;i++)
            explains[i] = new ImageIcon("images/rule"+(i+1)+".png");

        //게임 방법 타이틀
        lblTitle = new JLabel();
        lblTitle.setBounds(0,100,1000,562);
        lblTitle.setIcon(explains[imageIndex]);  //라벨이 이미지 붙이기
        lblTitle.setVisible(true);
        add(lblTitle);

        //이전 페이지로 넘어가게 하는 버튼
        btnPrev = new JButton("Menu");
        btnPrev.setBounds(50,720,220,50);
        btnPrev.setFont(new Font("OCR A Extended",Font.BOLD,30));
        btnPrev.addMouseListener(btnlistener);  //마우스리스너 추가
        GameManager.getInstance().setBtnInit(btnPrev);  //버튼설정 초기화
        add(btnPrev);

        //다음 페이지로 넘어가게 하는 버튼
        btnNext = new JButton("Next Page");
        btnNext.setBounds(730,720,220,50);
        btnNext.setFont(new Font("OCR A Extended",Font.BOLD,30));
        btnNext.addMouseListener(btnlistener);  //마우스리스너 추가
        GameManager.getInstance().setBtnInit(btnNext);  //버튼 설정 초기화
        add(btnNext);


    }//constructor

    /*public void paintComponent(Graphics page){

        super.paintComponent(page);


    }//paintComponent()*/

}//YutGamePanel()
