import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopPanel extends JPanel implements ActionListener {

  protected static ImageIcon logo;
  protected static ImageIcon userIcon;

  protected static JButton logOff = new JButton("Signout");

  protected PrimaryWindow pw;

  protected static FlowLayout flow = new FlowLayout();

  public TopPanel(PrimaryWindow pw) {
    super(flow);

    this.pw = pw;

    logo = new ImageIcon("/IconPics/xflix_logo.png");
    this.add(new JLabel(logo));

    logOff.setPreferredSize(new Dimension(100, 50));
    logOff.addActionListener(this);
  }

  public void adjustTopPanel() {

  }

  @Override
  public void actionPerformed(ActionEvent ae) {

    this.removeAll();
    this.flow.setAlignment(FlowLayout.CENTER);
    pw.refreshLogin();

  }

}
