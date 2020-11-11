import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//ExplainPanelController, MainPanel, RankPanelController의
// 마우스리스너, 액션리스너 부분을 통일하고 재정의해서 사용할 수 있도록 수정
//mainpanel과 explainpanel, rankpanel에서 사용할 마우스 이벤트 처리
public class btnMouseEvent implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) {  }
    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton btn = (JButton)e.getSource();
        btn.setForeground(new Color(150,100,50)); //버튼에 들어가면 색 변경
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton btn = (JButton)e.getSource();
        btn.setForeground(Color.black);  //버튼을 나오면 다시 원래 색으로 변경
    }
}