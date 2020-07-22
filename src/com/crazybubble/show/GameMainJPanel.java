package com.crazybubble.show;

import com.crazybubble.controller.GameThread;
import com.crazybubble.element.ElementObj;
import com.crazybubble.element.Player;
import com.crazybubble.game.GameStart;
import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * @author Magic Gunner
 * @说明 游戏的主要面板
 * @功能说明 主要进行元素的显示，同时进行界面的刷新（多线程）
 * java开发首先思考：继承或接口实现
 * @多线程刷新 1、本类实现线程接口
 * 2、本类中定义一个内部类来实现
 */
public class GameMainJPanel extends JPanel implements Runnable {
    //联动管理器
    private ElementManager em;

    private JTextArea t1;
    private JTextArea t2;

    public GameMainJPanel() {
        init();
    }


    public void init() {
        this.setLayout(null);
        em = ElementManager.getManager();//得到元素管理器对象

        t1 = new JTextArea();
        t1.setText("HP：");
        t1.setEditable(false);
        t1.setLineWrap(true);
        t1.setBounds(700, 270, 100, 100);

        t2 = new JTextArea();
        t2.setText("HP：");
        t2.setEditable(false);
        t2.setLineWrap(true);
        t2.setBounds(700, 570, 100, 100);

        JButton button = new JButton();
        button.setFocusable(false);
        button.setBounds(680, 50, 120, 50);
        button.setText("NEXT LEVEL");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameStart.next();
            }
        });

        this.add(t1);
        this.add(t2);
        this.add(button);
    }

    /**
     * paint方法是进行绘画元素
     * 绘画时是有固定的顺序，先绘画的图片会在底层，后绘画的图片会覆盖先绘画的
     * 约定：本方法只执行一次，想实时刷新需要使用多线程
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        for (GameElement ge :
                GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = 0; i < list.size(); i++) {
                ElementObj obj = list.get(i);
                obj.showElement(g);
            }
        }
        ImageIcon icon1 = new ImageIcon("image/Characters/p1.png");
        g.drawImage(icon1.getImage(), 700, 150, 100, 100, null);
        ImageIcon icon2 = new ImageIcon("image/Characters/p2.png");
        g.drawImage(icon2.getImage(), 700, 450, 100, 100, null);
    }

    public void flush() {
        List<ElementObj> player = em.getElementsByKey(GameElement.PLAYER);
        for (ElementObj obj :
                player) {
            if (((Player) obj).getPlayerType() == 0) {
                t1.setText("HP：" + obj.getHp()
                        + "\r\n" + "Speed：" + ((Player) obj).getSpeed()
                        + "\r\n" + "BubblePower：" + ((Player) obj).getBubblePower()
                        + "\r\n" + "BubbleTotal：" + ((Player) obj).getBubbleTotal()
                        + "\r\n" + "Super：" + ((Player) obj).isSuper());

            } else {
                t2.setText("HP：" + obj.getHp()
                        + "\r\n" + "Speed：" + ((Player) obj).getSpeed()
                        + "\r\n" + "BubblePower：" + ((Player) obj).getBubblePower()
                        + "\r\n" + "BubbleTotal：" + ((Player) obj).getBubbleTotal()
                        + "\r\n" + "Super：" + ((Player) obj).isSuper());
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            this.repaint();
            this.flush();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
