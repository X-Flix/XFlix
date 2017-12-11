import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

public class TopPanel extends JPanel {

  private static FlowLayout TOPPANEL_FLOW = new FlowLayout();

  private JLabel logoLabel = new JLabel();
  private JLabel userLabel = new JLabel();
  private JButton logOffBtn = new JButton(Xres.LOGOFF_LABEL_TEXT);;

  TopPanel() {
    super(TOPPANEL_FLOW);

    resetPanel();

    logOffBtn.setPreferredSize(new Dimension(100, 50));
  }

  void resetPanel(){
    getLogoLabel().setIcon(Xres.XFLIX_LOGO);
    add(logoLabel);
  }

  JLabel getLogoLabel() {
    return logoLabel;
  }

  JLabel getUserLabel(){ return userLabel; }

  JButton getLogOffBtn(){
    return logOffBtn;
  }

  FlowLayout getToppanelFlow() { return TOPPANEL_FLOW; }

  public void adjustTopPanel() {

  }

}
