package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controls.ServerControls;
import utils.Utils;

public class InterfaceController extends JFrame {
	private static final long serialVersionUID = 1L;
	final ServerControls serverControler = new ServerControls();
	final DefaultListModel<String> listDefaut;

	public InterfaceController() {
		setLayout(null);
		setTitle("SEO Đề Xuất");
		setSize(800, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		JLabel lbIp = new JLabel("IP address");
		JLabel lbUserName = new JLabel("UserName");
		JLabel lbPassWord = new JLabel("PassWord");

		lbIp.setBounds(20, 20, 200, 30);
		lbUserName.setBounds(20, 70, 200, 30);
		lbPassWord.setBounds(20, 120, 200, 30);

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

		final JButton btnAdd = new JButton("Add account");
		btnAdd.setBounds(175, 180, 150, 30);
		add(btnAdd);

		JLabel lbArea = new JLabel("Information");
		lbArea.setBounds(250, 240, 150, 50);
		add(lbArea);

		listDefaut = new DefaultListModel<>();
		final JList<String> jlIp = new JList<String>(listDefaut);
		jlIp.setBounds(30, 280, 550, 370);
		jlIp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(jlIp);
		scrollPane.setBounds(30, 280, 550, 370);
		add(scrollPane);

		final JButton btnLoadIP = new JButton("Load All IP");
		btnLoadIP.setBounds(620, 305, 150, 40);
		add(btnLoadIP);
		
		final JButton btnLoadIPWarning = new JButton("Load Warning IP");
		btnLoadIPWarning.setBounds(620, 360, 150, 40);
		add(btnLoadIPWarning);
		
		final JButton btnStart = new JButton("Start");
		btnStart.setBounds(620, 415, 150, 40);
		add(btnStart);

		final JButton btnStop = new JButton("Stop");
		btnStop.setBounds(620, 470, 150, 40);
		add(btnStop);
		
		final JButton btnLoadParameter = new JButton("Load Parameter");
		btnLoadParameter.setBounds(620, 525, 150, 40);
		add(btnLoadParameter);
		
		final JButton btnDelete = new JButton("Delete IP");
		btnDelete.setBounds(620, 580, 150, 40);
		add(btnDelete);

		JLabel lbMinTime = new JLabel("Min Time (s)");
		JLabel lbMaxTime = new JLabel("Max Time (s)");
		JLabel lbTargetVideos = new JLabel("My Videos");
		JLabel lbOtherVideos = new JLabel("Other Videos");
		JLabel lbComments = new JLabel("Comments");

		lbMinTime.setBounds(400, 20, 200, 30);
		lbMaxTime.setBounds(400, 50, 200, 30);
		lbTargetVideos.setBounds(400, 80, 200, 30);
		lbOtherVideos.setBounds(400, 110, 200, 30);
		lbComments.setBounds(400, 140, 200, 30);

		add(lbMinTime);
		add(lbMaxTime);
		add(lbTargetVideos);
		add(lbOtherVideos);
		add(lbComments);

		final JTextField tf_mintime = new JTextField(200);
		final JTextField tf_maxtime = new JTextField(200);
		final JTextField tf_targetvideo = new JTextField(200);
		final JTextField tf_othervideo = new JTextField(200);
		final JTextField tf_comment = new JTextField(200);

		tf_mintime.setBounds(500, 20, 270, 25);
		tf_maxtime.setBounds(500, 50, 270, 25);
		tf_targetvideo.setBounds(500, 80, 270, 25);
		tf_othervideo.setBounds(500, 110, 270, 25);
		tf_comment.setBounds(500, 140, 270, 25);

		add(tf_mintime);
		add(tf_maxtime);
		add(tf_targetvideo);
		add(tf_othervideo);
		add(tf_comment);

		final JButton btnSubmitParameter = new JButton("Submit Parameters");
		btnSubmitParameter.setBounds(550, 180, 170, 30);
		add(btnSubmitParameter);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ip = tf_ip.getText().trim();
				String username = tf_username.getText().trim();
				String password = tf_password.getText().trim();

				if (ip.length() > 0 && username.length() > 0 && password.length() > 0) {
					try {
						serverControler.insertAccount(ip, username, password);
						listDefaut.addElement(ip + " " + username);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Fail!");
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Success!");
					tf_ip.setText("");
					tf_username.setText("");
					tf_password.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Fail!");
				}
			}
		});

		btnLoadIP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					serverControler.getAllAccountFromMySQL();
					
					int listIpSize = serverControler.listIps.size();
					int index = jlIp.getLastVisibleIndex();
					if(index > listIpSize - 1)
					{
						index = listIpSize - 1;
					}
					int selectedIndex = jlIp.getSelectedIndex();
					
					String arrayStrings[] = new String[serverControler.listInfos.size() + 1];
					arrayStrings[0] = "0.     0.0.0.0     SUPER IP ADDRESS";
					for(int i=0; i<listIpSize; i++)
					{
						String tempString = serverControler.listInfos.get(i);
						arrayStrings[i+1] = (i+1) + ".     " + tempString;
					}
					jlIp.setListData(new String [0]);
					Thread.sleep(1);
					jlIp.setListData(arrayStrings);
					
					jlIp.ensureIndexIsVisible(index);
					if(selectedIndex>=0)
					{
						jlIp.setSelectedIndex(selectedIndex);
					}
					
					JOptionPane.showMessageDialog(null, "Success!");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Fail!");
					e.printStackTrace();
				}
			}
		});
		
		btnLoadIPWarning.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					serverControler.getAllAccountFromMySQL();
					
					int listIpSize = serverControler.listWarning.size();
					int index = jlIp.getLastVisibleIndex();
					if(index > listIpSize - 1)
					{
						index = listIpSize - 1;
					}
					int selectedIndex = jlIp.getSelectedIndex();
					
					String arrayStrings[] = new String[serverControler.listWarning.size() + 1];
					arrayStrings[0] = "0.     0.0.0.0     SUPER IP ADDRESS";
					for(int i=0; i<listIpSize; i++)
					{
						String tempString = serverControler.listWarning.get(i);
						arrayStrings[i+1] = (i+1) + ".     " + tempString;
					}
					jlIp.setListData(new String [0]);
					Thread.sleep(1);
					jlIp.setListData(arrayStrings);
					
					jlIp.ensureIndexIsVisible(index);
					if(selectedIndex>=0)
					{
						jlIp.setSelectedIndex(selectedIndex);
					}
					JOptionPane.showMessageDialog(null, "Success!");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Fail!");
					e.printStackTrace();
				}
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String selection = jlIp.getSelectedValue();
					if (selection != null) {
						if(selection.split("     ").length>=2)
						{
							String ip = selection.split("     ")[1];
							int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure delete? " + ip, "Warning",
									JOptionPane.YES_NO_OPTION);
							if (dialogResult == JOptionPane.YES_OPTION) {
								serverControler.deleteAccountInMySQL(ip);
								listDefaut.removeElement(selection);
								JOptionPane.showMessageDialog(null, "Success!");
							}
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Fail!");
					e.printStackTrace();
				}
			}
		});

		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String selection = jlIp.getSelectedValue();
					if (selection != null) {
						if(selection.split("     ").length>=2)
						{
							String ip = selection.split("     ")[1];
							int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure start this? " + ip, "Warning",
									JOptionPane.YES_NO_OPTION);
							if (dialogResult == JOptionPane.YES_OPTION) {
								serverControler.setStatus(ip, 1);
								JOptionPane.showMessageDialog(null, "Success!");
							} else {
								JOptionPane.showMessageDialog(null, "Fail!");
							}
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Fail!");
					e.printStackTrace();
				}
			}
		});

		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String selection = jlIp.getSelectedValue();
					if (selection != null) {
						if(selection.split("     ").length>=2)
						{
							String ip = selection.split("     ")[1];
							int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure stop this? " + ip, "Warning",
									JOptionPane.YES_NO_OPTION);
							if (dialogResult == JOptionPane.YES_OPTION) {
								serverControler.setStatus(ip, 0);
								JOptionPane.showMessageDialog(null, "Success!");
							} else {
								JOptionPane.showMessageDialog(null, "Fail!");
							}
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Fail!");
					e.printStackTrace();
				}
			}
		});

		btnSubmitParameter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String min_time_second = tf_mintime.getText();
				String max_time_second = tf_maxtime.getText();
				String target_videos = tf_targetvideo.getText();
				String other_videos = tf_othervideo.getText();
				String comments = Utils.normalizeText(tf_comment.getText());
				if (min_time_second.length() > 0 && max_time_second.length() > 0 && target_videos.length() > 0
						&& other_videos.length() > 0 && comments.length() > 0) {
					try {
						serverControler.updateParameters(min_time_second, max_time_second, target_videos, other_videos,
								comments);
						JOptionPane.showMessageDialog(null, "Success!");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Fail!");
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Fail!");
				}
			}
		});
		
		btnLoadParameter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String [] arrayParameters = serverControler.getParameterFromMySQL();
					
					int selectedIndex = jlIp.getSelectedIndex();
					
					jlIp.setListData(new String [0]);
					Thread.sleep(1);
					jlIp.setListData(arrayParameters);
					
					if(selectedIndex>=0)
					{
						jlIp.setSelectedIndex(selectedIndex);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		tf_ip.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != '.')) {
					e.consume(); // ignore event
				}
			}
		});

		tf_mintime.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // ignore event
				}
			}
		});

		tf_maxtime.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // ignore event
				}
			}
		});
	}

	public static void main(String[] args) 
	{
		InterfaceController myUI = new InterfaceController();
		System.out.println("Interface Controller visible");
		myUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myUI.setLocationRelativeTo(null);
		myUI.setVisible(true);
	}
}
