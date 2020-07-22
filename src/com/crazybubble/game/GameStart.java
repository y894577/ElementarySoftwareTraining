package com.crazybubble.game;

import com.crazybubble.controller.GameListener;
import com.crazybubble.controller.GameMusic;
import com.crazybubble.controller.GameThread;
import com.crazybubble.manager.GameLoad;
import com.crazybubble.show.GameBeginJPanel;
import com.crazybubble.show.GameJFrame;
import com.crazybubble.show.GameMainJPanel;
import com.crazybubble.show.GameOverJPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameStart {

    private static GameJFrame frame;
    //ʵ��������
    private static GameListener listener = new GameListener();
    //ʵ�������߳�
    private static GameThread thread = new GameThread();

    public static void main(String[] args) {
        //���������ļ�·��
        GameLoad.ConfigLoad();

        //���ô����С
        String size[] = GameLoad.configMap.get("windowSize").split(",");
        GameJFrame.GameX = Integer.parseInt(size[0]);
        GameJFrame.GameY = Integer.parseInt(size[1]);
        frame = new GameJFrame(GameJFrame.GameX, GameJFrame.GameY);
        frame.setLocationRelativeTo(null);

        //����л�
        frame.changePanel("begin");
        frame.setVisible(true);

//        GameMusic gameMusic = new GameMusic();
//        gameMusic.start();
    }

    public static void rule() {
        frame.changePanel("rule");
    }

    public static void start() {
        frame.setSize(GameJFrame.GameX + 170, GameJFrame.GameY);
        frame.changePanel("main");
        frame.setKeyListener(listener);
        frame.setThread(thread);
        frame.start();
    }

    public static void over(int type) {
        if (type == 0){
            frame.changePanel("over1");
        }else if(type == 1){
            frame.changePanel("over0");
        }

    }

    public static void begin() {
        frame.changePanel("begin");
    }

}

/**
 * 1.������Ϸ�������Ϸ�������ļ���ʽ���ļ���ȡ��ʽ��load��ʽ��
 * 2.�����Ϸ��ɫ��������Ϸ���󣨳�����ڻ���ļ̳У�
 * 3.����pojo�ࣨvo��
 * 4.��Ҫ�ķ������ڸ�������д��������಻֧�֣����Բ����޸ĸ��ࣩ
 * 5.������ã���ɶ����load��add��manager
 * 6.�����ײ�ȵ�ϸ�ڿ���
 */
