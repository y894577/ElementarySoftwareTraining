package com.crazybubble.show;

import com.crazybubble.game.GameStart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverJPanel extends JPanel {
    private Image img;

    public GameOverJPanel() {
        init();
    }

    private void init() {
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("image/map/image/level10.png");
        this.setImg(icon.getImage());
        JLabel jLabel = new JLabel();
        jLabel.setBounds(0, 0, 600, 400);
        jLabel.setIcon(icon);

        //重开按钮
        JButton again = new JButton();
        again.setFocusPainted(false);
        again.setBounds(0, 0, 100, 40);
        again.setText("AGAIN");
        again.setFocusable(false);
        again.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //开始游戏
                GameStart.begin();
            }
        });

        this.add(again);
        this.add(jLabel);
        this.setVisible(true);
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
