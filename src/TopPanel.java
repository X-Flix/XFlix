import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopPanel extends JPanel implements ActionListener {

  static ImageIcon logo;
  static ImageIcon userIcon;

  static JButton logOff = new JButton("Signout");

  private PrimaryWindow pw;

  static FlowLayout flow = new FlowLayout();
 
  TopPanel(PrimaryWindow pw) {
    super(flow);

    this.pw = pw;

    setBackground(pw.X_BACKGROUND_COLOR);

    logo = new ImageIcon("IconPics/xflix_logo.png");
    this.add(new JLabel(logo));

    logOff.setPreferredSize(new Dimension(100, 50));
    logOff.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent ae) {

    this.removeAll();
    flow.setAlignment(FlowLayout.CENTER);
    pw.refreshLogin();

  }

}
