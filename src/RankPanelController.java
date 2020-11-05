import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class RankPanelController {
    //필드
    private RankPanel _rank;

    //생성자
   public RankPanelController() {
        _rank =  GameManager.getInstance().get_rank();      //GameManager에서 RankPanel 가져오기
        _rank.btnMenu.addActionListener(new backtoMenu());  //RankPanel의 menu버튼에 액션리스너 추가
   }


   //내부 클래스 액션 리스너
   //RankPanel의 menu버튼을 클릭하면 MainPanel의 Menu페이지를 보여줌
   //MainPanel의 showMenu 메소드 호출
   private class backtoMenu implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton)e.getSource();
            if(btn ==_rank.btnMenu ) {
                GameManager.getInstance().get_view().showMenu();
            }
            _rank.repaint();
        }
   }
}
