package com.crazybubble.game;

import com.crazybubble.controller.GameListener;
import com.crazybubble.controller.GameMusic;
import com.crazybubble.controller.GameThread;
import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
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
    //实例化监听
    private static GameListener listener = new GameListener();
    //实例化主线程
    private static GameThread thread;

    public static void main(String[] args) {
        //加载配置文件路径
        GameLoad.ConfigLoad();

        //设置窗体大小
        String size[] = GameLoad.configMap.get("windowSize").split(",");
        GameJFrame.GameX = Integer.parseInt(size[0]);
        GameJFrame.GameY = Integer.parseInt(size[1]);
        frame = new GameJFrame(GameJFrame.GameX, GameJFrame.GameY);
        frame.setLocationRelativeTo(null);

        //面板切换
        frame.changePanel("begin");
        frame.setVisible(true);

        GameMusic gameMusic = new GameMusic();
        gameMusic.start();
    }

    public static void rule() {
        frame.setSize(GameJFrame.GameX, GameJFrame.GameY);
        frame.changePanel("rule");
    }

    public static void start() {
        frame.setSize(GameJFrame.GameX + 170, GameJFrame.GameY);
        frame.changePanel("main");
        thread = new GameThread();
        frame.setKeyListener(listener);
        frame.setThread(thread);
        frame.start();
    }

    public static void over(int type) {
        frame.setSize(GameJFrame.GameX, GameJFrame.GameY);
        if (type == 0) {
            frame.changePanel("over1");
        } else if (type == 1) {
            frame.changePanel("over0");
        }

    }

    public static void begin() {
        frame.changePanel("begin");
    }

    public static void next() {
        thread.gameOver();
        GameThread.level++;
        GameLoad.Refresh();
        ElementManager em = ElementManager.getManager();
        em.init();
        listener = new GameListener();
        thread = new GameThread();
        frame.setThread(thread);
        frame.setKeyListener(listener);
        frame.start();
    }
}

