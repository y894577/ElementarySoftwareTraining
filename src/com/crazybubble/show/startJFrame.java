package com.crazybubble.show;

import com.crazybubble.controller.GameListener;
import com.crazybubble.controller.GameThread;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;

public class startJFrame extends JFrame{
	
	private JLayeredPane layeredPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		startJFrame window = new startJFrame();
		window.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public startJFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		this.setBounds(100, 100, 800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		JButton Button1 = new JButton();
		Button1.setText("\u5F00\u59CB\u6E38\u620F");
		Button1.setBounds(300,186,177,50);
		Button1.setFont(new Font("宋体", Font.PLAIN, 24));
		getContentPane().add(Button1);
		
		JButton Button2 = new JButton("\u6E38\u620F\u8BF4\u660E");
		Button2.setBounds(300,265,177,50);
		Button2.setFont(new Font("宋体", Font.PLAIN, 24));
		getContentPane().add(Button2);
		
		JTextArea txtrctrl = new JTextArea();
		txtrctrl.setBackground(Color.ORANGE);
		txtrctrl.setLineWrap(true);
		txtrctrl.setText("\u6CE1\u6CE1\u5802\uFF0C\u662F\u4E00\u79CD\u7531\u952E\u76D8\u64CD\u4F5C\u7684\u4F11\u95F2\u5C0F\u6E38\u620F\u3002\u4E0E\u5176\u4ED6\u7F51\u7EDC\u6E38\u620F\u52A8\u8F84\u5360\u636E\u51E0\u767E\u5146\u7684\u5BB9\u91CF\u4E0D\u540C\uFF0C\u6CE1\u6CE1\u5802\u53EA\u670918\u5146\uFF0C\u51B3\u4E0D\u5360\u7528\u60A8\u5B9D\u8D35\u7684\u786C\u76D8\u7A7A\u95F4\uFF1B\u4E00\u673A\u53CC\u4EBA\u64CD\u4F5C\u66F4\u662F\u4E00\u5927\u521B\u4E3E\u3002\u8FD9\u4E2A\u72EC\u7279\u7684\u8BBE\u8BA1\uFF0C\u6EE1\u8DB3\u4E86\u5F88\u591A\u5BB6\u5EAD\u7684\u9700\u8981\uFF0C\u5728\u6E38\u620F\u7684\u540C\u65F6\u8FD8\u80FD\u589E\u8FDB\u53CC\u65B9\u7684\u611F\u60C5\uFF1B\u6E38\u620F\u63A7\u5236\u7B80\u4FBF\uFF0C\u53EA\u8981\u4F7F\u7528\u4E00\u4E2A\u65B9\u5411\u952E\u63A7\u5236\u65B9\u5411\uFF0C\u518D\u52A0\u4E0A\u4E00\u4E2A\u7A7A\u683C\u952E\u653E\u7F6E\u6CE1\u6CE1ctrl\u952E\u4F7F\u7528\u7279\u6B8A\u9053\u5177\uFF0C6\u4E2A\u6309\u952E\u5C31\u53EF\u4EE5\u8FDB\u884C\u6E38\u620F\u4E86\uFF0C\u6E38\u620F\u4E2D\u6709\u516B\u5927\u4EBA\u7269\u89D2\u8272\u3001\u4E24\u4F4D\u9690\u85CF\u4EBA\u7269\uFF0C\u5361\u901A\u7684\u4EBA\u7269\u5F62\u8C61\uFF0C\u518D\u52A0\u4E0A\u6E38\u620F\u4E2D\u4E30\u5BCC\u7684\u9053\u5177\u3001\u9970\u54C1\u548C\u8868\u60C5\uFF0C\u663E\u7136\u6210\u4E3A\u4E86\u5973\u6027\u73A9\u5BB6\u7684\u6700\u7231\u3002");
		txtrctrl.setBounds(158, 355, 482, 120);
		txtrctrl.setVisible(false);
		getContentPane().add(txtrctrl);
		
		
		ImageIcon image = new ImageIcon("image/background1.png");
		JLabel lblNewLabel = new JLabel(image);
		lblNewLabel.setBounds(0, 0, 800, 600);
		getContentPane().add(lblNewLabel);
		
		
		
		Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				GameJFrame gj = new GameJFrame();
				//实例化面板，注入到jFrame中
				GameMainJPanel jp = new GameMainJPanel();
				//实例化监听
				GameListener listener = new GameListener();
				//实例化主线程
				GameThread th = new GameThread();

				gj.setJPanel(jp);
				gj.setKeyListener(listener);
				gj.setThread(th);

				gj.start();
			}
		});
		Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrctrl.setVisible(true);
			}
		});


	}
}
