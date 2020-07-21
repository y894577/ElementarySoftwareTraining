package com.crazybubble.show;

import com.crazybubble.game.GameStart;
import com.crazybubble.manager.ElementManager;

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
        ImageIcon icon = new ImageIcon("image/background1.png");
        this.setImg(icon.getImage());
        JLabel jLabel = new JLabel();
        jLabel.setFocusable(false);
        jLabel.setBounds(0, 0, 800, 600);
        jLabel.setIcon(icon);


        //开始游戏按钮
        JButton start = new JButton("START");
        start.setFocusable(false);
        start.setBounds(300, 186, 177, 50);
        start.setFont(new Font("", Font.PLAIN, 24));
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //开始游戏
                GameStart.start();
            }
        });


        //游戏规则按钮
        JButton rule = new JButton("RULE");
        rule.setFocusable(false);
        rule.setBounds(300, 265, 177, 50);
        rule.setFont(new Font("", Font.PLAIN, 24));
        rule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //开始游戏
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
