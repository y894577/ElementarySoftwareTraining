package com.crazybubble.show;

import com.crazybubble.controller.GameListener;
import com.crazybubble.controller.GameThread;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Magic Gunner
 * @description ��Ϸ����
 */
public class GameJFrame extends JFrame {
    public static int GameX;
    public static int GameY;
    //������ʾ�����
    private JPanel jPanel = null;
    //���̼���
    private KeyListener keyListener = null;
    //��Ϸ���߳�
    private GameThread thread = null;
    private CardLayout card;

    private final GameBeginJPanel begin = new GameBeginJPanel();
    private final GameMainJPanel main = new GameMainJPanel();
    private final GameOverJPanel over0 = new GameOverJPanel(0);
    private final GameOverJPanel over1 = new GameOverJPanel(1);
    private final GameRuleJPanel rule = new GameRuleJPanel();

    public GameJFrame(int w, int h) {
        init(w, h);
    }

    public void init(int w, int h) {
        this.setSize(w, h);
        this.setTitle("CrazyBubble");
        this.setKeyListener(keyListener);
        //�����˳����ر�
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //��Ļ����
        this.setLocationRelativeTo(null);

        card = new CardLayout();
        jPanel = new JPanel();
        jPanel.setLayout(card);
        jPanel.add("begin", begin);
        jPanel.add("main", main);
        jPanel.add("over0", over0);
        jPanel.add("over1", over1);
        jPanel.add("rule", rule);
        this.add(jPanel);
    }

    /**
     * @description ��������
     */
    public void start() {
        if (keyListener != null) {
            this.addKeyListener(this.keyListener);
        }
        if (thread != null) {
            thread.isOver = false;
            thread.start();
        }
        //�����ˢ��
        this.setVisible(true);
        for (int i = 0; i < this.jPanel.getComponentCount(); i++) {
            if (this.jPanel.getComponent(i) instanceof Runnable) {
                new Thread((Runnable) this.jPanel.getComponent(i)).start();
            }
        }

    }

    public void changePanel(String name) {
        card.show(jPanel, name);
    }

    public void setJPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setThread(GameThread thread) {
        this.thread = thread;
    }

}
