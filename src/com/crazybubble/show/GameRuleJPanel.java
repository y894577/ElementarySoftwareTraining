package com.crazybubble.show;

import com.crazybubble.game.GameStart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.Pipe;

public class GameRuleJPanel extends JPanel {

    private Image img;

    public GameRuleJPanel() {
        init();
    }

    private void init() {
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("image/Pictures/rule.png");
        this.img = icon.getImage();
        JLabel jLabel = new JLabel();
        jLabel.setBounds(0, 0, 600, 400);
        jLabel.setIcon(icon);

        //返回按钮
        JButton back = new JButton();
        back.setFocusPainted(false);
        back.setBounds(0, 0, 100, 40);
        back.setFocusable(false);
        back.setText("BACK");
        back.setFocusable(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //开始游戏
                GameStart.begin();
            }
        });

        this.add(back);
        this.add(jLabel);
        this.setVisible(true);
    }

}
