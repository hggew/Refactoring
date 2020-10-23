import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;



public class RankPanelController {

    private RankPanel _rank;

    public RankPanelController() {
        _rank =  GameManager.getInstance().get_rank();
        _rank.btnMenu.addActionListener(new backtoMenu());
    }


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
