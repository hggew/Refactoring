import javax.swing.*;
import java.awt.*;


public class Player {
    //필드
    public Pawn[]      pawns;
    public int         score;                                    //완주한 말의 개수 저장
    public Ability[]   abilities;
    public boolean     isMyTurn;                                //본인 차례 여부 저장
    public boolean     isNowAbility1Use, isNowAbility2Use;      //초능력 사용 여부 저장
    public int         pawnImgWidth, pawnImgHeight;

    public JLabel       imgPlayer, lblTurn;                    //플레이어 이미지와 차례를 알려주는 라벨
    public ImageIcon[] iconPlayer;                            //imgPlayer에 붙일 이미지아이콘 배열


    //생성자
    public Player(String img, int width, int height){
        //pawn 의 이미지 크기를 지정할 때 사용하려던 변수 같음. 하지만 사용하지 않음
        pawnImgWidth = width;
        pawnImgHeight = height;

        pawns = new Pawn[4];                        // pawn 생성
        for(int i=0;i<4;i++){
            pawns[i] = new Pawn(img, 0,0, width, height, i);
        }

        abilities = new Ability[2];                 //초능력 생성
        int random;                                 //초능력을 랜덤으로 부여하기 위한 임시변수
        random = (int)(Math.random() * 3);
        //movementAbility 설정
        switch (random){
            case 0:
                abilities[0] = new MovementAbility.MoOrDo();
                abilities[0].abilityName = "Mo/Do";
                break;
            case 1:
                abilities[0] = new MovementAbility.GaeOrGirl();
                abilities[0].abilityName = "Gae/Girl";
                break;

            case 2:
                abilities[0] = new MovementAbility.OnlyYut();
                abilities[0].abilityName = "OnlyYut";
        }

        //locationAbility 설정
        random = (int)(Math.random() * 3);
        switch (random){
            case 0:
                abilities[1] = new LocationAbility.Exchange();
                abilities[1].abilityName = "Swap";
                break;
            case 1:
                abilities[1] = new LocationAbility.GoHome();
                abilities[1].abilityName = "GoHome";
                break;
            case 2:
                abilities[1] = new LocationAbility.UpSideDown();
                abilities[1].abilityName = "Shuffle";
        }

        //모든 정보 초기화 및 비활성화
        score = 0;
        isMyTurn = false;
        isNowAbility1Use= false;
        isNowAbility2Use= false;

        iconPlayer = new ImageIcon[2];
        imgPlayer = new JLabel();

        //차례를 알려주는 라벨 생성 및 설정
        lblTurn= new JLabel();
        lblTurn.setBounds(21 ,10,160,80);
        lblTurn.setFont(new Font("OCR A Extended", Font.BOLD, 30));
        lblTurn.setText("My Turn!");
        lblTurn.setVisible(false);

    }

    //  methods
    //pawn을 움직이는 메소드를 만드려고 한 것 같지만 사용하지 않음.
    public void movePawn(){}

    //InGameData-InGameData_init 에서 호출
    //player 정보 초기화, 비활성화
    public void Player_init(){
        this.score=0;
        this.isMyTurn=false;
        this.isNowAbility1Use=false;
        this.isNowAbility2Use=false;
        for(Pawn p:this.pawns){
            p.Pawn_init();
        }
    }

}
