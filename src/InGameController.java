import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InGameController {

    private InGameView _view;
    private InGameData _data;
    private PawnClickListener leftPawnListener, rightPawnListener;
    private InputInfo _inputinfo;

    /*field move to randomYutResult method*/
    //private double YutResult;

    public InGameController() {

        _view = GameManager.getInstance().get_inGame();         // InGameView 받아오기
        _data = GameManager.getInstance().get_gameData();     //   데이터 받아오기

        //각 플레이어의 폰 선택 리스터 생성
        leftPawnListener = new PawnClickListener();
        rightPawnListener = new PawnClickListener();

        //던지기 버튼에 리스너 넣기
        _view.btnThrowLeft.addActionListener(new ThrowingYut());
        _view.btnThrowRight.addActionListener(new ThrowingYut());

        //능력 버튼에 리스너 넣기
        _view.leftUserPanel.btnAbility2.addActionListener(new UseAbility(_data.leftPlayer,1));
        _view.leftUserPanel.btnAbility1.addActionListener(new UseAbility(_data.leftPlayer,0));

        _view.rightUserPanel.btnAbility1.addActionListener(new UseAbility(_data.rightPlayer,0));
        _view.rightUserPanel.btnAbility2.addActionListener(new UseAbility(_data.rightPlayer,1));

        //게임 초기화하며 시작
 
        init_Game();
        changePlayerImgnLabel();
        activeThrowBtn(_data.activatedPlayer);
 

    }

    private class PawnClickListener implements MouseListener {  //이동할 말을 선택하는 과정에서 플레이어의 말 클릭 시 이벤트 발생

        @Override
        public void mouseReleased(MouseEvent e) {
            _data.focusedPawn = (Pawn)e.getSource();    //이벤트가 발생한 말을 선택된 말로 설정

            hideAllPreviews();  //우선 말의 미리보기 가리기
            showAllPreviews();  //현재 선택된 말을 기준으로 다시 미리보기 띄우기
            _view.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private class MoveSelectedPawn implements MouseListener {   //플레이어의 말을 클릭했을 때 보여진 미리보기 말을 클락하면 발생하는 이벤트 리스너

        @Override
        public void mouseReleased(MouseEvent e) {
            Pawn p = (Pawn)e.getSource();
            boolean catched;

 
            //말 이동
            if(_data.focusedPawn.getCurrentIndex()==0) catched = _data.moveOnePawn(_data.activatedPlayer, _data.focusedPawn, p.getCurrentIndex());  //대기칸에 있는 말을 이동하는 경우 그 말만 이동
            else catched = _data.moveAllPawns(_data.activatedPlayer,_data.focusedPawn.getCurrentIndex(),p.getCurrentIndex());   //윷판 위의 말을 이동하는 경우 그 말이 있는 위치의 모든 말을 이동
            for(ThrowData data:_data.previewPawns) data.preview.setVisible(false);  //말 이동 후 미리보기 모두 보이지 않게 하기

            //78줄이랑 88줄이랑 같이 묶어서 메소드로 처리. 여기에서
            removeClickedPreview(p);
            _data.previewPawns.trimToSize();    //리스트 사이즈 갱신

            checkAllPawnFinished();
 


            //게임이 계속 진행되는 경우
            if(catched) {   //말 이동 후 상대 말을 잡으면 윷을 던질 기회 획득
                activeThrowBtn(_data.activatedPlayer);   //윷 던질 준비 시키기
                //윷을 던지는 동안에는 말 이동시키지 못하도록 리스너 제거
                deactivationPawnClick();
            }
            else if(_data.previewPawns.size()==0) { //추가 턴을 획득하지 못하고 던진 윷 결과데이터들을 모두 사용한 경우
                //말 선택 리스너 모두 제거
 
                deactivationPawnClick();
                passPlayerTurn();   //상대에게 턴 넘겨주기
            }
            else {  //아직 이동할 결과데이터가 남아있는 경우
                boolean flag = true;

                //빽도 예외처리
                for(ThrowData data:_data.previewPawns) {    //남은 결과값들 중 빽도가 아닌 것이 있는지 확인
                    if(data.result != 6) flag = false;
                }
                if(flag){   //결과 데이터들이 모두 빽도인 경우
                    for(Pawn pawn:_data.activatedPlayer.pawns){ //움직일 수 있는 말이 있는지 확인
                        if(pawn.getCurrentIndex()!=0) flag = false;
                    }
                    if(flag){   //게임판에 올라온 말이 없는 경우(빽도 이동이 가능한 말이 없는 경우)
                        //더이상 이동할 수 없으므로 턴 넘겨주기
                        deactivationPawnClick();
                        passPlayerTurn();
                        _data.previewPawns.clear();
                        _data.previewPawns.trimToSize();
                    }   //if
                }//if
            }//else
        }

        void removeClickedPreview(Pawn p){
            ThrowData clicked = null;

            for(ThrowData data:_data.previewPawns) //결과 데이터에서 이벤트가 발생한 미리보기 말 찾기
                if(data.preview == p) clicked = data;

            _data.previewPawns.remove(clicked); //결과 데이터의 리스트에서 방금 사용된 결과 데이터를 삭제

        }

        void checkAllPawnFinished(){
            //현재 플레이어의 말이 전부 완주하면 game end -> 대화상자
            if(_data.activatedPlayer.score ==4){    //말 4개가 모두 완주
                String str = "Player Win!!\nDo you want new Game?";;
                //승자에 따라 메시지 설정
                if(_data.activatedPlayer== _data.leftPlayer) str="left" + str;
                else str= "right"+str ;

                int result = JOptionPane.showConfirmDialog( _view, str,"Game End",JOptionPane.YES_NO_OPTION);   //게임을 계속 진행할 지  묻기

                makeInputinfoFrame();
                switch(result) {
                    case JOptionPane.NO_OPTION:
                        _inputinfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        break;

                    case JOptionPane.YES_OPTION:
                        deactivationPawnClick();

                        if( _data.activatedPlayer == _data.rightPlayer) passPlayerTurn();
                        ready(_data.leftPlayer);
                        _data.InGameData_init();
                        _data.previewPawns.clear();
                        _data.previewPawns.trimToSize();

                        _view.lblThrowing.setIcon(_view.lblThrowing.iconYut[0]);
                        _view.lblYutResult.setIcon(_data.iconYutText[6]);

                        GameManager.getInstance().get_view().showMenu();
                        return;
                } // switch
            }
        }


        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
        @Override
        public void mouseClicked(MouseEvent e) { }
        @Override
        public void mousePressed(MouseEvent e) { }
    }

    public void makeInputinfoFrame(){
        _inputinfo = new InputInfo();
        _inputinfo.setTitle("Input ID");
        _inputinfo.setLocation(0,0);
        _inputinfo.setSize(1000,800);
        _inputinfo.setVisible(true);
        _inputinfo.setResizable(true);
    }

    private class ThrowingYut implements ActionListener {   //윷 던지기 버튼을 누르면 발생하는 이벤트 리스너
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            //본인 턴이 아닐 때 버튼이 눌리면 무시
            if ((_data.leftPlayer.isMyTurn && btn == _view.btnThrowRight) || (_data.rightPlayer.isMyTurn && btn == _view.btnThrowLeft))
                return;

            //상태
            if(_data.activatedPlayer.isNowAbility1Use==true)    //윷 결과 조작 능력 사용
                _data.activatedPlayer.isNowAbility1Use=false;
             //상태
            else{   //윷 결과 랜덤으로 뽑기
                _data.throwResult = randomYutResult();
            }
            //모습
            _view.lblThrowing.start();  //윷 던지는 모습 보여주기
            btn.setEnabled(false);
            _view.lblThrowing.setResult(_data.throwResult); //Yut으로 결과값보내서 결과이미지 띄우기

            //상태
            if (_data.throwResult != 4 && _data.throwResult != 5)   //윷이나 모가 나온 경우
                _data.throwableNCnt--;  //던질 수 있는 횟수 -1

            //모습

            /*saveThrowResult 메서드 추출*/
            saveThrowResult();//던진 결과 저장 - 미리보기용

            //상태
            //윷 던질 기회 모두 사용한 경우
            if (_data.throwableNCnt == 0) {
                //우선 빽도 예외처리
                boolean flag = true;
                for(ThrowData d:_data.previewPawns) {   //결과 데이터들 중 빽도가 아닌 것이 있는 지 확인
                    if(d.result != 6) flag = false;
                }
 
                //결과들이 모두 빽도인 경우 말을 이동할 준비
                for(Pawn pawn:_data.activatedPlayer.pawns){ //내 말 중 이동 가능한 말이 있는 지 확인
                    if(pawn.getCurrentIndex()!=0) flag = false;
                }
                if(flag){   //윷판에 올라온 말이 없는 경우(빽도 이동이 가능한 말이 없는 경우)
                    //상대 턴으로 넘어가기
 
                    deactivationPawnClick();
                    passPlayerTurn();
                    _data.previewPawns.clear();
                    _data.previewPawns.trimToSize();
                }
                else{   //이동할 말이 있다면 말 이동을 위한 준비
                    //내 말 중 완주하지 않은 말에 말 선택 리스너를 add
                    activationPawnClick();
                }

 
            }   //if(다 던짐)
            else {  //던질 기회가 남았다면 다시 던질 준비
                activeThrowBtn(_data.activatedPlayer);
            }   //else
        }//actionPerformed()
    }

    //능력 버튼 클릭 시 발생하는 이벤트 리스너
    private class UseAbility implements ActionListener{

        Player player;
        int abilitynum;

        public UseAbility(Player p, int num) {
            player=p;
            abilitynum=num;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            //어느 플레이어가 어떤 버튼을 눌렀는지 확인 후 능력 사용
            player.abilities[abilitynum].run(player);
            _view.repaint();
        }
    }

    public void initGame(){    //게임 초기화 메소드
        //양쪽 플레이어의 말에 add된 리스너 제거
        for(Pawn p:_data.leftPlayer.pawns)
            p.removeMouseListener(leftPawnListener);
        for(Pawn p:_data.rightPlayer.pawns)
            p.removeMouseListener(rightPawnListener);

        //양 플레이어의 던지기 버튼 비활성화
        _view.btnThrowLeft.setEnabled(false);
        _view.btnThrowRight.setEnabled(false);
    }//init_Game()

  
    public void changePlayerImgnLabel(){   //진행중인 차례에 맞게 플레이어 이미지 바꾸는 메소드

        Player activedPlayer = _data.activatedPlayer == _data.leftPlayer ? _data.leftPlayer: _data.rightPlayer ;
        Player deactivedPlayer = _data.activatedPlayer == _data.leftPlayer ? _data.rightPlayer: _data.leftPlayer;

        activedPlayer.imgPlayer.setIcon(activedPlayer.iconPlayer[0]);
        activedPlayer.imgPlayer.setBounds(-25,90,250,230);
        deactivedPlayer.imgPlayer.setIcon(deactivedPlayer.iconPlayer[1]);
        deactivedPlayer.imgPlayer.setBounds(-1,90,250,230);

        activedPlayer.lblTurn.setVisible(true);
        deactivedPlayer.lblTurn.setVisible(false);
 
    }

    public void activeThrowBtn(Player player){   //던지기 버튼을 누를 수 있도록 활성화 하는 메소드
        if(player == _data.leftPlayer) GameManager.getInstance().get_inGame().btnThrowLeft.setEnabled(true);
        else if(player == _data.rightPlayer) GameManager.getInstance().get_inGame().btnThrowRight.setEnabled(true);
        _data.throwableNCnt = 1;
    }

    public void passPlayerTurn(){   //현재 진행중인 턴을 상대 차례로 넘기는 메소드

        _data.activatedPlayer.isMyTurn = false;
        _data.activatedPlayer = _data.activatedPlayer == _data.leftPlayer ? _data.rightPlayer : _data.leftPlayer;
        _data.activatedPlayer.isMyTurn = true;

        _view.lblYutResult.setIcon(_data.iconYutText[6]);   //윷 결과 지우기

        changePlayerImgnLabel();   //플레이어 이미지와 라벨 변경
 
        activeThrowBtn(_data.activatedPlayer);   //던지기 버튼 활성화
 
    }

    public void showAllPreviews(){  //선택된 말이 이동할 수 있는 경우를 모두 보여주는 메소드
        for(ThrowData data:_data.previewPawns) {
            data.preview.setVisible(true);
            if(data.result == 6 && _data.focusedPawn.getCurrentIndex() == 0)
                data.preview.setVisible(false);    //말이 이동할 곳이 없는 경우는 보이지 않도록 설정
            else _data.findNextPoint(data); //말이 결과 데이터에 따라 이동하게 될 위치 찾기
        }
        GameManager.getInstance().get_inGame().repaint();
    }

    /*sun randomYutResult method extract*/
    public int randomYutResult()
    {
        double YutResult = Math.random();
        if (YutResult <= 0.1536) return 1;
        else if (YutResult <= 0.4992) return 2;
        else if (YutResult <= 0.7584) return 3;
        else if (YutResult <= 0.8880) return 4;
        else if (YutResult <= 0.9136) return 5;
        else if (YutResult < 1) return 6;
        return 0;
    }

    public void hideAllPreviews(){
        for(ThrowData data:_data.previewPawns){ //선택된 말에 대해 이동 가능한 위치를 보여주기 위해 저장된 윷 결과 데이터에 있는 미리보기 말 초기화
            data.preview.setVisible(false); //미리보기 말들 모두 안보이게 하기
        }
    }

    public void activationPawnClick(){
        for (Pawn P : _data.activatedPlayer.pawns)
            if (P.isFinished() == false)
                P.addMouseListener(_data.activatedPlayer == _data.leftPlayer ? leftPawnListener : rightPawnListener);
    }

    public void deactivationPawnClick()
    {
        for(Pawn pawn:_data.activatedPlayer.pawns)
            pawn.removeMouseListener(_data.activatedPlayer == _data.leftPlayer ? leftPawnListener : rightPawnListener);
    }

    public void saveThrowResult()//던진 결과 저장 - 미리보기용
    {
        ThrowData data = new ThrowData(_data.throwResult);
        data.preview.addMouseListener(new MoveSelectedPawn());
        _data.previewPawns.add(data);
        _view.add(data.preview);
        _view.setComponentZOrder(data.preview, 0);  //윷판보다 위에 보이도록 설정

    }
}
//test