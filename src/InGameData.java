import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InGameData {
    public Player  leftPlayer, rightPlayer;     //게임을 진행할 플레이어 두 명
    public Pawn    focusedPawn; //현재 선택된 말
    public int     throwResult; //마지막으로 던진 윷의 결과
    public BoardIndexData[] boardIndexer;   //게임판 위 29칸에 대한 데이터
    public Point[] leftPawnWaiting, rightPawnWaiting;   //대기칸의 좌표

    public ImageIcon[]    iconYutText;  //윷을 던지면 윷 오른쪽에 보여주는 글자 이미지들
    //public String[]  iconYutTextName = new String[7];   //윷 오른쪽에 보여줄 텍스트 이미지의 경로


    public Player activatedPlayer;  //현재 턴을 진행중인 플레이어

    public ArrayList<ThrowData> previewPawns;   //말 이동에 쓰일 "윷 던지기 결과데이터"를 저장하는 리스트
    public int throwableNCnt;   //남은 던질 수 있는 횟수




    public InGameData(){

        //move to setBoardRoadIndex
        //int x, y;

        GameManager.getInstance().set_gameData(this);   //게임매니저에 인스턴스를 등록해둔다
        //각 플레이어 생성
        makePlayer();//make player and set player Image
        /*
        leftPlayer = new Player("images/left_pawn.png", 80, 80);
        rightPlayer = new Player("images/right_pawn.png",100,75);
*/

        throwResult = 0;
        activatedPlayer = leftPlayer;   //왼쪽 플레이어부터 게임 진행 시작

        previewPawns = new ArrayList<ThrowData>();  //던지기 결과 리스트 생성

        //플레이어 움직이는 사진지정
        /*
        leftPlayer.iconPlayer[0]= new ImageIcon("images/Left_move.gif");
        leftPlayer.iconPlayer[1]= new ImageIcon("images/Left_stop.png");

        rightPlayer.iconPlayer[0] = new ImageIcon("images/Right_move.gif");
        rightPlayer.iconPlayer[1] = new ImageIcon("images/Right_stop.png");
*/
        //각 플레이어 말들의 대기 칸 좌표 미리 저장
        setPawnCoordinates();
        /*leftPawnWaiting = new Point[4];
        leftPawnWaiting[0] = new Point(10,410);
        leftPawnWaiting[1] = new Point(110,410);
        leftPawnWaiting[2] = new Point(10,510);
        leftPawnWaiting[3] = new Point(110,510);

        rightPawnWaiting = new Point[4];
        rightPawnWaiting[0] = new Point(811,410);
        rightPawnWaiting[1] = new Point(911,410);
        rightPawnWaiting[2] = new Point(811,510);
        rightPawnWaiting[3] = new Point(911,510);

        for(int i=0;i<4;i++) leftPlayer.pawns[i].setLocation(leftPawnWaiting[i]);
        for(int i=0;i<4;i++) rightPlayer.pawns[i].setLocation(rightPawnWaiting[i]);*/

        //기본적인 길 인덱스
        setBoardRoadIndex();
        /*
        boardIndexer = new BoardIndexData[30];
        boardIndexer[0] = new BoardIndexData(0,0,0,false,0);
        boardIndexer[5] = new BoardIndexData(697,32,5,true,26);
        boardIndexer[10] = new BoardIndexData(237,35,10,true,21);
        boardIndexer[15] = new BoardIndexData(237,490,15,false,0);
        boardIndexer[20] = new BoardIndexData(699,496,20,false,0);
        x = 697; y = 400;
        for(int i=1;i<=4;i++, y-=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);
        x = 601; y = 32;
        for(int i=6;i<=9;i++, x-=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);
        x = 237; y = 129;
        for(int i=11;i<=14;i++, y+=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);
        x = 332; y = 493;
        for(int i=16;i<=19;i++, x+=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);

        //지름길 인덱스 (왼쪽위 -> 오른쪽 아래, 중간 지점 인덱스 포함)
        boardIndexer[21] = new BoardIndexData(323, 123,21,false,0);
        boardIndexer[22] = new BoardIndexData(388, 188,22,false,0);
        boardIndexer[23] = new BoardIndexData(468, 263,23,true,24);
        boardIndexer[24] = new BoardIndexData(542, 343,24,false,0);
        boardIndexer[25] = new BoardIndexData(607, 408,25,false,0);
        //지름길 인덱스 (오른쪽위 -> 왼쪽아래, 중간 지점 인덱스 제외)
        boardIndexer[26] = new BoardIndexData(607, 123,26,false,0);
        boardIndexer[27] = new BoardIndexData(542, 188,27,false,0);
        boardIndexer[28] = new BoardIndexData(388, 343,28,false,0);
        boardIndexer[29] = new BoardIndexData(323, 408,29,false,0);

        //set exceptional index
        boardIndexer[0].nextIndex = 0;
        boardIndexer[1].prevIndex = 20;
        boardIndexer[21].prevIndex = 10;
        boardIndexer[23].nextIndex = 28;
        boardIndexer[26].prevIndex = 5;
        boardIndexer[25].nextIndex = 20;
        boardIndexer[27].nextIndex = 23;
        boardIndexer[28].prevIndex = 23;
        boardIndexer[29].nextIndex = 15;
        boardIndexer[20].nextIndex = 0;
*/

        //윷던지고 결과 텍스트 이미지 변경
        setYutTextImage();
        /*iconYutTextName[0]="images/imgDO.png";
        iconYutTextName[1]="images/imgGAE.png";
        iconYutTextName[2]="images/imgGIRL.png";
        iconYutTextName[3]="images/imgYUT.png";
        iconYutTextName[4]="images/imgMO.png";
        iconYutTextName[5]="images/imgBDO.png";
        iconYutTextName[6]="images/none.png";
*/
        /*iconYutText = new ImageIcon[7];
        for(int i=0; i<7; i++)
            iconYutText[i]= new ImageIcon(iconYutTextName[i]);
*/
    }//constructor

    /////////////////////////
    ///말 이동 관련 메소드///
    ////////////////////////
    public void findNextPoint(ThrowData data){  //파라미터로 받은 결과데이터를 이용해 선택된 말이 가게 될 위치를 찾는 메소드
        data.preview.setIcon(new ImageIcon(focusedPawn.ImgSource()));   //선택된 말과 같은 이미지를 보여준다
        switch(focusedPawn.getCurrentIndex()) { //산텍된 말의 위치에 따라 예외처리
            case 0: //대기칸에 있는 경우
                if(data.result == 6) {  //이동할 결과값이 빽도인 경우
                    data.preview.setVisible(false); //이동할 수 없는 상황이므로 보여주지 않는다
                    return;
                }
                data.preview.setIndex(data.result); //보여줄 말의 위치 바꿔주기
                break;
            case 5:     //오른쪽 상단 모서리
                if(data.result == 6) {//빽도인 경우 한칸 뒤에 미리보기 말을 위치시킨다.
                    data.preview.setIndex(boardIndexer[focusedPawn.getCurrentIndex()].prevIndex);
                }
                else {  //앞으로 가는 경우 지름길로 위치 설정
                    data.preview.setIndex(boardIndexer[focusedPawn.getCurrentIndex()].shortCut);
                    for (int i = 1; i < data.result; i++)
                        data.preview.setIndex(boardIndexer[data.preview.getCurrentIndex()].nextIndex);
                    break;
                }
            //23번(중앙)을 지날 때 일반적으로 왼쪽 아래방향으로 가도록  설정돼있기 때문에 23번을 오른쪽 아래방향으로 지나는 경우 인덱스를 직접 설정해준다
            //이에 해당하는 케이스가 10, 21, 22번
            case 10:    //왼쪽 상단 모서리
                if(data.result == 6)    //빽도인 경우
                    data.preview.setIndex(boardIndexer[focusedPawn.getCurrentIndex()].prevIndex);
                else    //지름길 방향으로 위치 설정
                    data.preview.setIndex(21 + data.result - 1);
                    break;
            case 21:
                if(data.result == 6)    //빽도인 경우 뒤로 한칸
                    data.preview.setIndex(boardIndexer[focusedPawn.getCurrentIndex()].prevIndex);
                else if(data.result == 5)   //5칸 이동시 20번(골인지점)으로 이동
                    data.preview.setIndex(20);
                else    //21번 위치에서 오른쪽 아래방향으로 이동하는 경우는 25번까지 인덱스 숫자가 연속되어 있기 때문에 일반화
                    data.preview.setIndex(22 + data.result - 1);
                break;
            case 22:
                if(data.result == 6)    //빽도인 경우
                    data.preview.setIndex(boardIndexer[focusedPawn.getCurrentIndex()].prevIndex);
                else if(data.result == 5)   //골인지점을 지나는 경우
                    data.preview.setIndex(0);
                else if(data.result == 4)   //골인지점에 도착하는 경우
                    data.preview.setIndex(20);
                else    //골인지점에 미달한 경우
                    data.preview.setIndex(23 + data.result - 1);
                break;
            case 23:    //중앙에 위치한 칸
                if(data.result == 6) {//빽도가 나오면 뒤로 한칸
                    data.preview.setIndex(boardIndexer[focusedPawn.getCurrentIndex()].prevIndex);
                    break;
                }
                data.preview.setIndex(boardIndexer[focusedPawn.getCurrentIndex()].shortCut);
                for(int i=1;i<data.result;i++) data.preview.setIndex(boardIndexer[data.preview.getCurrentIndex()].nextIndex);
                break;
            default:    //지름길로 이동하지 않는 경우
                if(data.result == 6) {//빽도가 나오면 뒤로 한칸
                    data.preview.setIndex((boardIndexer[focusedPawn.getCurrentIndex()].prevIndex));
                    break;
                }
                else {  //선택된 말에서부터 이동할 칸 수 만큼 앞으로  이동
                    data.preview.setIndex(boardIndexer[focusedPawn.getCurrentIndex()].nextIndex);
                    for (int i = 1; i < data.result; i++)
                        data.preview.setIndex(boardIndexer[data.preview.getCurrentIndex()].nextIndex);
                    break;
                }
        }
        if(data.preview.getCurrentIndex() == 0) {   //예상 도착지점이 골인지점을 통과한 경우 골인지점에서 이미지를 바꿔서 띄워주기
            data.preview.setIcon(new ImageIcon("images/FinishedPawn.png"));
            data.preview.setLocation(boardIndexer[20].p);
        }
        else
            data.preview.setLocation(boardIndexer[data.preview.getCurrentIndex()].p);   //위 과정에서 계산된 인덱스의 좌표로 미리보기용 말 이동
    }

    public boolean moveOnePawn(Player owner, Pawn p, int end){  //플레이어의 말 하나를 지정된 위치로 이동하는 메소드
            p.setIndex(boardIndexer[end].currentIndex);//칸 인덱스 갱신
            if (boardIndexer[end].currentIndex == 0) {  //골인지점을 지나는 경우
                goWaitingRoom(p, owner);    //대기칸으로 말을 보낸다
                p.setFinished(true);    //말의 완주 여부 설정
                activatedPlayer.score++;    //플레이어의 점수 변경
            }//완주시 대기실로 이동
            else p.setBounds(boardIndexer[boardIndexer[end].currentIndex].p.x,boardIndexer[boardIndexer[end].currentIndex].p.y,focusedPawn.getWidth(),focusedPawn.getHeight()); //인덱스에 맞춰서 좌표 이동

            return catchOpponentPawns(owner == leftPlayer ? rightPlayer : leftPlayer, boardIndexer[end]);   //도착한 위치에서 상대의 말을 잡았는지 여부 반환
    }

    public void goWaitingRoom(Pawn pawn, Player owner) {    //말을 대기칸 좌표로 이동시키는 메소드
        pawn.setLocation(owner == leftPlayer ? leftPawnWaiting[pawn.pawnNumber] : rightPawnWaiting[pawn.pawnNumber]);
    }

    public boolean catchOpponentPawns(Player opponent, BoardIndexData index) {  //특정 위치에 있는 상대 말을 집으로 보내고 성공 여부를 반환하는 메소드
        boolean result = false;
        for(Pawn pawn:opponent.pawns){  //상대방 말 중 잡히는 말이 있는지 확인 후,  있다면 그 말은 대기칸으로 보내기
            if(pawn.getCurrentIndex() == index.currentIndex){
                result = true;
                pawn.setIndex(0);
                goWaitingRoom(pawn, opponent);
            }//if
        }//for
        return result;  //결과 반환
    }//catchOpponentPawns()

    public boolean moveAllPawns(Player owner,int start , int end){  //start지점에 위치한 플레이어의 말을 모두 end지점으로 이동
        Player opponent = owner == leftPlayer ? rightPlayer : leftPlayer;   //상대 플레이어가 누군지 확인

        for(Pawn p: owner.pawns){
            if(p.getCurrentIndex() == start) {
                p.setIndex(boardIndexer[end].currentIndex);//칸 인덱스 갱신
                if(boardIndexer[end].currentIndex == 0) {   //도착지점이 골인지점을 지나는 경우라면 말을 완주처리
                    goWaitingRoom(p,owner);
                    p.setFinished(true);
                    p.setIcon(new ImageIcon("images/FinishedPawn.png"));
                    owner.score++;
                }//완주시 대기실로 이동
                else p.setLocation(boardIndexer[boardIndexer[end].currentIndex].p); //좌표 이동
            }//출발지에 있는 말들 이동
        }



        if(boardIndexer[end].currentIndex != 0) return catchOpponentPawns(opponent, boardIndexer[end]);  //이동한 자리에 있는 상대말 잡기
        return false;
    }

    /*setPawn coordinates and setlocatio method*/
    public void setPawnCoordinates()
    {
        leftPawnWaiting = new Point[4];
        leftPawnWaiting[0] = new Point(10,410);
        leftPawnWaiting[1] = new Point(110,410);
        leftPawnWaiting[2] = new Point(10,510);
        leftPawnWaiting[3] = new Point(110,510);

        rightPawnWaiting = new Point[4];
        rightPawnWaiting[0] = new Point(811,410);
        rightPawnWaiting[1] = new Point(911,410);
        rightPawnWaiting[2] = new Point(811,510);
        rightPawnWaiting[3] = new Point(911,510);

        for(int i=0;i<4;i++) leftPlayer.pawns[i].setLocation(leftPawnWaiting[i]);
        for(int i=0;i<4;i++) rightPlayer.pawns[i].setLocation(rightPawnWaiting[i]);

    }

    public void setBoardRoadIndex()
    {
        int x, y;
        boardIndexer = new BoardIndexData[30];
        boardIndexer[0] = new BoardIndexData(0,0,0,false,0);
        boardIndexer[5] = new BoardIndexData(697,32,5,true,26);
        boardIndexer[10] = new BoardIndexData(237,35,10,true,21);
        boardIndexer[15] = new BoardIndexData(237,490,15,false,0);
        boardIndexer[20] = new BoardIndexData(699,496,20,false,0);
        x = 697; y = 400;
        for(int i=1;i<=4;i++, y-=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);
        x = 601; y = 32;
        for(int i=6;i<=9;i++, x-=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);
        x = 237; y = 129;
        for(int i=11;i<=14;i++, y+=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);
        x = 332; y = 493;
        for(int i=16;i<=19;i++, x+=90) boardIndexer[i] = new BoardIndexData(x, y, i,false, 0);

        //지름길 인덱스 (왼쪽위 -> 오른쪽 아래, 중간 지점 인덱스 포함)
        boardIndexer[21] = new BoardIndexData(323, 123,21,false,0);
        boardIndexer[22] = new BoardIndexData(388, 188,22,false,0);
        boardIndexer[23] = new BoardIndexData(468, 263,23,true,24);
        boardIndexer[24] = new BoardIndexData(542, 343,24,false,0);
        boardIndexer[25] = new BoardIndexData(607, 408,25,false,0);
        //지름길 인덱스 (오른쪽위 -> 왼쪽아래, 중간 지점 인덱스 제외)
        boardIndexer[26] = new BoardIndexData(607, 123,26,false,0);
        boardIndexer[27] = new BoardIndexData(542, 188,27,false,0);
        boardIndexer[28] = new BoardIndexData(388, 343,28,false,0);
        boardIndexer[29] = new BoardIndexData(323, 408,29,false,0);

        //set exceptional index
        boardIndexer[0].nextIndex = 0;
        boardIndexer[1].prevIndex = 20;
        boardIndexer[21].prevIndex = 10;
        boardIndexer[23].nextIndex = 28;
        boardIndexer[26].prevIndex = 5;
        boardIndexer[25].nextIndex = 20;
        boardIndexer[27].nextIndex = 23;
        boardIndexer[28].prevIndex = 23;
        boardIndexer[29].nextIndex = 15;
        boardIndexer[20].nextIndex = 0;
    }

    public void makePlayer()
    {
        //make player
        leftPlayer = new Player("images/left_pawn.png", 80, 80);
        rightPlayer = new Player("images/right_pawn.png",100,75);

        //set player's Image
        leftPlayer.iconPlayer[0]= new ImageIcon("images/Left_move.gif");
        leftPlayer.iconPlayer[1]= new ImageIcon("images/Left_stop.png");

        rightPlayer.iconPlayer[0] = new ImageIcon("images/Right_move.gif");
        rightPlayer.iconPlayer[1] = new ImageIcon("images/Right_stop.png");

    }
    public void setYutTextImage()
    {
        //set Yut Text Image
        String[] iconYutTextName = new String[7];
        iconYutTextName[0]="images/imgDO.png";
        iconYutTextName[1]="images/imgGAE.png";
        iconYutTextName[2]="images/imgGIRL.png";
        iconYutTextName[3]="images/imgYUT.png";
        iconYutTextName[4]="images/imgMO.png";
        iconYutTextName[5]="images/imgBDO.png";
        iconYutTextName[6]="images/none.png";

        iconYutText = new ImageIcon[7];
        for(int i=0; i<7; i++)
            iconYutText[i]= new ImageIcon(iconYutTextName[i]);
    }
    //초기화 메소드
    public void InGameData_init(){

        //각 플레이어 초기화
        leftPlayer.Player_init();
        rightPlayer.Player_init();

        //각 플레이어의 말 사진과 위치 초기화
        for(Pawn p:leftPlayer.pawns){
            goWaitingRoom(p, leftPlayer);
            p.setPawnImg("images/left_Pawn.png");
        }
        for(Pawn p:rightPlayer.pawns){
            goWaitingRoom(p, rightPlayer);
            p.setPawnImg("images/right_Pawn.png");
        }
        //다음 게임 시작 시 왼쪽 플레이어부터 시작하도록 설정
        activatedPlayer= leftPlayer;

    }

}
