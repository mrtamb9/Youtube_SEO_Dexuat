package interfaces;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InterfaceController extends JFrame {
	private static final long serialVersionUID = 1L;

	public InterfaceController(String title) {
		JPanel pnFlow = new JPanel();
		pnFlow.setLayout(new FlowLayout());
		pnFlow.setBackground(Color.ORANGE);

		JButton btn1 = new JButton("Test");
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Clicked");
			}
		});

		pnFlow.add(btn1);
		Container con = getContentPane();
		con.add(pnFlow);
	}

	public static void main(String[] args) {
		InterfaceController myUI = new InterfaceController("Test Interface");
		myUI.setSize(600, 100);
		myUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myUI.setLocationRelativeTo(null);
		myUI.setVisible(true);
	}
}
