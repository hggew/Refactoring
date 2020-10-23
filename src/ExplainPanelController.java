import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExplainPanelController {

    private ExplainPanel _explain;

    public ExplainPanelController(){
        _explain = GameManager.getInstance().get_explain();  //explainpanel 가져오기
        //explain패널의 버튼에 액션리스너 추가
        _explain.btnNext.addActionListener(new moveExplainPageListener());
        _explain.btnPrev.addActionListener(new moveExplainPageListener());
    }


    private class moveExplainPageListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton)e.getSource();
            //버튼이 next일때 다음 페이지의 이미지 변경
            if(btn == _explain.btnNext){
                //지금 페이지가 마지막 페이지면 게임 바로시작
                if(_explain.imageIndex == 3) {
                    GameManager.getInstance().get_view().showInGame();
                    return;
                }
                _explain.imageIndex += 1;  //이미지의 인덱스 +1
                if(_explain.imageIndex == 3){ btn.setText("Start"); }  //마지막 설명페이지에서 start로 버튼 이름 변경
                _explain.btnPrev.setText("Prev Page"); //게임설명 첫페이지를 제외하고 prev버튼 이름을 prev page로 변경
            }
            //버튼이 prev 일때 이전 페이지 변경
            else if(btn == _explain.btnPrev){
                //지금 페이지가 첫번째 페이지면 게임의 메뉴로 돌아가기
                if(_explain.imageIndex == 0){
                    GameManager.getInstance().get_view().showMenu();
                }
                else _explain.imageIndex -= 1;  //이미지의 인덱스 -1
                if(_explain.imageIndex ==0) btn.setText("Menu");  //첫번째 설명페이지에서 prev 버튼을 menu로 이름 변경
                _explain.btnNext.setText("Next Page"); //첫번째 페이지를 제외하고 next버튼을 next page로 변경
            }
            _explain.lblTitle.setIcon(_explain.explains[_explain.imageIndex]); //인덱스에 따라 이미지 설정
            _explain.repaint();
        }
    }


}

