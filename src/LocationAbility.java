import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class LocationAbility {

    public static class GoHome extends Ability{ //상대 말 하나 집으로 보내기

        Player opponent;
        SelectOpponent pawnL;

        @Override
        public void use() {
            opponent = _data.activatedPlayer == _data.leftPlayer ? _data.rightPlayer : _data.leftPlayer;//지금 실행 중인 player가 왼쪽이면 opponent는 오른쪽
            pawnL = new SelectOpponent();
            for(Pawn p:opponent.pawns) if(p.getCurrentIndex()!=0) p.addMouseListener(pawnL); //리스너를 추가해준다.
        }

        private class SelectOpponent implements MouseListener{

            @Override
            public void mouseReleased(MouseEvent e) { //눌러진 마우스 버튼이 떼어지는 순간이다.
                Pawn obj = (Pawn)e.getSource();
                for(Pawn p: opponent.pawns) if (obj.getCurrentIndex() == p.getCurrentIndex()) _data.goWaitingRoom(p, opponent);  //말을 찾아, 상대편 말을 대기칸으로 보낸다.
                for(Pawn p:opponent.pawns) if(p.getCurrentIndex()!=0) p.removeMouseListener(pawnL); //리스너를 없앤다.
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
        }
    }//GoHome class

    public static class UpSideDown extends Ability {//밥상뒤집기
        public int[] destination = new int [8]; //랜덤인 목적지 index를 저장

        int currentCount; //자리를 바꿀 모든 말의 수
        int randomIdx;

        ArrayList<Integer> leftCurrentAllPawnIdx; //왼쪽 player의 현재 모든 말들의 위치 index
        ArrayList<Integer> rightCurrentAllPawnIdx; //오른쪽 player의 현재 모든 말들의 위치 index

        @Override
        public void use() {
            leftCurrentAllPawnIdx=new ArrayList<Integer>();
            rightCurrentAllPawnIdx=new ArrayList<Integer>();
            currentCount = 0;
            shuffle();
        }

        public void shuffle(){
            int i = 0; // RandomIdx 랜덤 인덱스 뽑는 변수

            getCurrentPawnIdx(_data.leftPlayer,leftCurrentAllPawnIdx);
            getCurrentPawnIdx(_data.rightPlayer,rightCurrentAllPawnIdx);
            createRandomPawnIdx();

            Iterator<Integer> it=leftCurrentAllPawnIdx.iterator();	//leftPlayer moveAllPawns해주기
            i=0;		//Destination의 인덱스 참조하는 변수
            while(it.hasNext()) _data.moveAllPawns(_data.leftPlayer, it.next(), destination[i++]);
            it=rightCurrentAllPawnIdx.iterator();	//rightPlayer moveAllPawns해주기
            while(it.hasNext()) _data.moveAllPawns(_data.rightPlayer, it.next(), destination[i++]);

        }

        private void createRandomPawnIdx() { //중복없는 랜덤 값을 destination에 넣어주는 함수
            int i;
            HashSet<Integer> hashset=new HashSet<Integer>();	//RandomIdx중복값을 없애기 위한 HashSet
            for(i = 0 ; i < currentCount; i++) {// 왼쪽 플레이어 말의 위치를 뽑기 위한 반복문
                while (true) { // 중복된 수를 뽑지 않기 위한 무한 반복문
                    randomIdx = (int) (Math.random() * 29) + 1;
                    if(hashset.add(randomIdx)) {	//HashSet에 중복없이 add가 된다면
                        destination[i]=randomIdx;//Destination에 넣어주기
                        break;
                    }

                }
            }
        }

        private void getCurrentPawnIdx(Player player,ArrayList<Integer> currentAllPawnIdx) {//바꾸기전 원래의 말이 있던 index를 currentIdx배열에 저장하는 함수
            int flag = 0;

            for(Pawn p: player.pawns) {
                if(p.isFinished() == false && p.getCurrentIndex() != 0 ) { //만약 판에 있으면
                    Iterator<Integer> it = currentAllPawnIdx.iterator();
                    while(it.hasNext()) {
                        if(it.next()==p.getCurrentIndex()) //업힌 말이라면 자리를 바꿀 총 말의 수를 증가시켜 주지 않는다.
                            flag=1;
                    }
                    if(flag==0) {
                        currentCount++;
                        currentAllPawnIdx.add(p.getCurrentIndex()*-1); //만약에 왼쪽 사용자가 갈 위치가 오른쪽 사용자가 현재 있는 위치라면 왼쪽 사용자의 말이 오른쪽 사용자의 말을 잡는 오류가 생기므로 인덱스를 옮겨준다.
                        p.setIndex(p.getCurrentIndex()*-1);
                    }
                }
            }
        }//getCurrentPawnIdx()
    }//UpSideDown class

    public static class Exchange extends Ability { //상대 말과 자리바꾸기
        Player activatedPlayer;
        Player opponentPlayer; //상대 플레이어 변수
        SelectOpponent opponentPawnListener; //버튼리스너
        SelectActive activatedPawnListener;//버튼리스너
        private int activeIdx, opponentIdx; //activeIdx = 지금 차례인 플레이어의 선택한 말을 저장한 변수, opponenIdx = 반대편

        @Override
        public void use() {
            activatedPlayer  = _data.activatedPlayer;
            opponentPlayer = _data.activatedPlayer == _data.leftPlayer ? _data.rightPlayer : _data.leftPlayer;//지금 실행 중인 player가 왼쪽이면 opponent는 오른쪽

            opponentPawnListener = new SelectOpponent();
            activatedPawnListener = new SelectActive();

            for(Pawn p:activatedPlayer.pawns) if(p.getCurrentIndex()!=0) p.addMouseListener(activatedPawnListener);
        }

        private class SelectOpponent implements MouseListener{
            @Override
            public void mouseReleased(MouseEvent e) {
                Pawn clikedPawn = (Pawn)e.getSource();
                findClikedPawnAndGetIdx(clikedPawn);
                allPawnsRemoveMouseListener();
                swap();
            }
            private void findClikedPawnAndGetIdx(Pawn clikedPawn) {
                System.out.println(clikedPawn.pawnNumber + "번 상대 말 선택");
                for(Pawn p: opponentPlayer.pawns) if (clikedPawn.getCurrentIndex() == p.getCurrentIndex()) opponentIdx = p.getCurrentIndex();//인덱스를 가져오기
            }
            private void allPawnsRemoveMouseListener() {
                for(Pawn p:opponentPlayer.pawns) if(p.getCurrentIndex()!=0) p.removeMouseListener(opponentPawnListener);
            }
            private void swap() {
                for(int i = 0; i < 4; i++) {
                    if(activatedPlayer.pawns[i].getCurrentIndex() == activeIdx) {
                        activatedPlayer.pawns[i].setIndex(opponentIdx);
                        activatedPlayer.pawns[i].setLocation(_data.boardIndexer[opponentIdx].p);
                    }
                    if(opponentPlayer.pawns[i].getCurrentIndex() == opponentIdx) {
                        opponentPlayer.pawns[i].setIndex(activeIdx);
                        opponentPlayer.pawns[i].setLocation(_data.boardIndexer[activeIdx].p);
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
        }

        private class SelectActive implements MouseListener{
            @Override
            public void mouseReleased(MouseEvent e) {// 능력을 누른 사람의 폰 선택
                Pawn clikedPawn = (Pawn)e.getSource();
                findClikedPawnAndGetIdx(clikedPawn);
                allPawnsRemoveMouseListener();
                addOpponentMouseListener();
            }
            private void addOpponentMouseListener() {
                for(Pawn p: opponentPlayer.pawns) if(p.getCurrentIndex() != 0) p.addMouseListener(opponentPawnListener);//상대방 폰의 리스너 추가
            }
            private void findClikedPawnAndGetIdx(Pawn clikedPawn) {
                System.out.println(clikedPawn.pawnNumber + "번 폰 선택됨");
                for(Pawn p: activatedPlayer.pawns) if (clikedPawn.getCurrentIndex() == p.getCurrentIndex()) activeIdx = p.getCurrentIndex(); // 선택한 폰의 인덱스를 가져오기
            }
            private void allPawnsRemoveMouseListener() {
                for(Pawn p:activatedPlayer.pawns) if(p.getCurrentIndex()!=0) p.removeMouseListener(activatedPawnListener);//더이상 삭제 되지 못하도록 리스너 삭제
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
        }
    }
}