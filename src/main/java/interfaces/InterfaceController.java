package interfaces;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controls.ServerControls;

public class InterfaceController extends JFrame {
	private static final long serialVersionUID = 1L;
	final ServerControls myControler = new ServerControls();

	public InterfaceController() {
		setLayout(null);
		setTitle("SEO Đề Xuất");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		JLabel lbIp = new JLabel("IP address");
		JLabel lbUserName = new JLabel("UserName");
		JLabel lbPassWord = new JLabel("PassWord");

		lbIp.setBounds(40, 20, 200, 30);
		lbUserName.setBounds(40, 70, 200, 30);
		lbPassWord.setBounds(40, 120, 200, 30);

		add(lbIp);
		add(lbUserName);
		add(lbPassWord);

		final JTextField tf_ip = new JTextField(200);
		final JTextField tf_username = new JTextField(20);
		final JTextField tf_password = new JTextField(20);

		tf_ip.setBounds(150, 20, 200, 30);
		tf_username.setBounds(150, 70, 200, 30);
		tf_password.setBounds(150, 120, 200, 30);

		add(tf_ip);
		add(tf_username);
		add(tf_password);

		JButton btnAdd = new JButton("Add account");
		btnAdd.setBounds(430, 60, 150, 50);
		add(btnAdd);

		JLabel lbArea = new JLabel("List IP address");
		lbArea.setBounds(180, 200, 150, 50);
		add(lbArea);

		DefaultListModel<String> listDefaut = new DefaultListModel<>();
		final JList<String> jlIp = new JList<String>(listDefaut);
		jlIp.setBounds(100, 250, 300, 300);
		jlIp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(jlIp);
		scrollPane.setBounds(100, 250, 300, 300);
		add(scrollPane);

		JButton btnLoadIP = new JButton("Load All IP");
		btnLoadIP.setBounds(450, 300, 150, 40);
		add(btnLoadIP);

		JButton btnDelete = new JButton("Delete IP");
		btnDelete.setBounds(450, 450, 150, 40);
		add(btnDelete);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ip = tf_ip.getText().trim();
				String username = tf_username.getText().trim();
				String password = tf_password.getText().trim();

				if (ip.length() > 0 && username.length() > 0 && password.length() > 0) {
					try {
						myControler.insertAccount(ip, username, password);
					} catch (Exception e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Success!");
				} else {
					JOptionPane.showMessageDialog(null, "Fail!");
				}
			}
		});
		
		btnLoadIP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ip = tf_ip.getText().trim();
				String username = tf_username.getText().trim();
				String password = tf_password.getText().trim();

				if (ip.length() > 0 && username.length() > 0 && password.length() > 0) {
					try {
						myControler.insertAccount(ip, username, password);
					} catch (Exception e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Success!");
				} else {
					JOptionPane.showMessageDialog(null, "Fail!");
				}
			}
		});
	}

	public static void main(String[] args) {
		InterfaceController myUI = new InterfaceController();
		myUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myUI.setLocationRelativeTo(null);
		myUI.setVisible(true);
	}
}
