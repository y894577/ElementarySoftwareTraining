package com.crazybubble.show;

import com.crazybubble.controller.GameThread;
import com.crazybubble.game.GameStart;
import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBeginJPanel extends JPanel {
    private ElementManager em;
    private Image img;
    private int w;
    private int h;

    public GameBeginJPanel() {
        init();
    }

    private void init() {
        this.setLayout(null);
        em = ElementManager.getManager();
        ImageIcon icon = new ImageIcon("image/start.png");
        icon.setImage(icon.getImage().getScaledInstance(GameJFrame.GameX, GameJFrame.GameY,
                Image.SCALE_DEFAULT));
        this.setImg(icon.getImage());
        JLabel jLabel = new JLabel();
        jLabel.setFocusable(false);
        jLabel.setBounds(0, 0, GameJFrame.GameX, GameJFrame.GameY);
        jLabel.setIcon(icon);

        //��ʼ��Ϸ��ť
        JButton start = new JButton("START");
        start.setLayout(null);
        ImageIcon icon1 = new ImageIcon("image/startbutton.png");
        icon1.setImage(icon1.getImage().getScaledInstance(610, 140,
                Image.SCALE_DEFAULT));
        start.setIcon(icon1);
        start.setFocusable(false);
        start.setBounds(40, 350, 570, 140);
        start.setFont(new Font("", Font.PLAIN, 24));
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //��ʼ��Ϸ
                GameStart.start();
            }
        });


        //��Ϸ����ť
        JButton rule = new JButton("RULE");
        rule.setFocusable(false);
        rule.setBounds(230, 500, 177, 50);
        rule.setFont(new Font("", Font.PLAIN, 24));
        rule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //��ʼ��Ϸ
                GameStart.rule();
            }
        });


        this.add(start);
        this.add(rule);
        this.add(jLabel);
        this.setVisible(true);
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }


}
