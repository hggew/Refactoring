import javax.swing.*;
import java.awt.*;

//Jlabel 상속
public class Pawn extends JLabel {
    //멤버변수
    private int         currentIndex;      //말의 현재 위치
    private boolean     isFinished;        //완주 여부
    private String      pawnImg;           //말 이미지 이름
    public int          pawnNumber;        //말 번호

    //생성자
    public Pawn(){
        setBounds(0,0,90,80);
    }
    public Pawn(String img, int x, int y, int width, int height, int number){
        currentIndex = 0;
        isFinished = false;
        pawnImg = img;
        setIcon(new ImageIcon(img));
        setBounds(x,y,width,height);

        pawnNumber = number;
    }// constructor

    //  get, set 메소드
    public int getCurrentIndex(){ return currentIndex; }
    public boolean isFinished(){ return isFinished; }
    public String ImgSource(){ return pawnImg; }
    public void setIndex(int idx){ currentIndex = idx;}
    public void setFinished(boolean a){ isFinished = a; }

    //InGameData의 초기화 메소드 InGameData_init에 사용
    public void setPawnImg(String img){
        pawnImg= img;
        setIcon(new ImageIcon(img));
    }
    //InGameData- InGameData_init > Player.Player_init 에서 호출
    public void Pawn_init(){
        this.currentIndex=0;
        this.isFinished=false;
    }
}
