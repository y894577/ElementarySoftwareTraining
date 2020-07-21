package com.crazybubble.game;

import com.crazybubble.controller.GameListener;
import com.crazybubble.controller.GameMusic;
import com.crazybubble.controller.GameThread;
import com.crazybubble.show.GameBeginJPanel;
import com.crazybubble.show.GameJFrame;
import com.crazybubble.show.GameMainJPanel;
import com.crazybubble.show.GameOverJPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameStart {

    private static GameJFrame frame = new GameJFrame();
    //实例化监听
    private static GameListener listener = new GameListener();
    //实例化主线程
    private static GameThread thread = new GameThread();

    public static void main(String[] args) {
        frame.changePanel("begin");
        frame.setVisible(true);
        GameJFrame gj = new GameJFrame();
        //实例化面板，注入到jFrame中
        GameMainJPanel jp = new GameMainJPanel();
        //实例化监听
        GameListener listener = new GameListener();
        //实例化主线程
        GameThread th = new GameThread();
        
        GameMusic gameMusic = new GameMusic();
        gameMusic.start();
//        gameMusic.playMusic();

        gj.setJPanel(jp);
        gj.setKeyListener(listener);
        gj.setThread(th);

        gj.start();
    }

    public static void rule(){
        frame.changePanel("rule");
    }

    public static void start() {
        frame.changePanel("main");
        frame.setKeyListener(listener);
        frame.setThread(thread);
        frame.start();
    }

    public static void over() {
        frame.changePanel("over");
    }

    public static void begin() {
        frame.changePanel("begin");
    }
}

/**
 * 1.分析游戏，设计游戏的配置文件格式，文件读取格式（load格式）
 * 2.设计游戏角色，分析游戏需求（抽象基于基类的继承）
 * 3.开发pojo类（vo）
 * 4.需要的方法就在父类中重写（如果父类不支持，可以采用修改父类）
 * 5.检查配置，完成对象的load和add到manager
 * 6.完成碰撞等等细节开发
 */
