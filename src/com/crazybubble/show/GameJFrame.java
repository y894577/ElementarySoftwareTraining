package com.crazybubble.show;

import com.crazybubble.controller.GameListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Magic Gunner
 * @说明 游戏窗体 主要实现功能：关闭，显示，最大最小化
 * @功能说明： 需要嵌入面板，启动主线程等等
 * @窗体说明 swing awt 窗体大小（记录用户上次使用软件的窗体样式）
 * @分析 1.面板绑定到窗体
 * 2.监听绑定
 * 3.游戏主线程启动
 * 4.显示窗体
 */
public class GameJFrame extends JFrame {
    public static int GameX = 800;
    public static int GameY = 600;

    private JPanel jPanel = null;//正在显示的面板
    private KeyListener keyListener = null;//键盘监听
    private Thread thread = null;//游戏主线程
    private CardLayout card;

    private final GameBeginJPanel begin = new GameBeginJPanel();
    private final GameMainJPanel main = new GameMainJPanel();
    private final GameOverJPanel over = new GameOverJPanel();
    private final GameRuleJPanel rule = new GameRuleJPanel();

    public GameJFrame() {
        init();
    }

    public void init() {
        this.setSize(GameX, GameY);//设置窗体大小
        this.setTitle("test");
        this.setKeyListener(keyListener);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置退出并关闭
        this.setLocationRelativeTo(null);//屏幕居中

        card = new CardLayout();
        jPanel = new JPanel();
        jPanel.setLayout(card);
        jPanel.add("begin", begin);
        jPanel.add("main", main);
        jPanel.add("over", over);
        jPanel.add("rule",rule);
        this.add(jPanel);
    }

    /**
     * 可扩展↓
     * 窗体布局：可以将保存数据，读档etc
     */
    public void addButton() {
//		this.setLayout(manager);//布局格式，可以添加控件
    }

    /**
     * 启动方法
     */
    public void start() {
        if (keyListener != null) {
            this.addKeyListener(this.keyListener);
        }
        if (thread != null) {
            thread.start();
        }
        //界面的刷新
        this.setVisible(true);
        for (int i = 0; i < this.jPanel.getComponentCount(); i++) {
            if (this.jPanel.getComponent(i) instanceof Runnable) {
                //已经做了类型判断，强制类型转换不会出错
                new Thread((Runnable) this.jPanel.getComponent(i)).start();
            }
        }

    }

    public void changePanel(String name) {
        card.show(jPanel,name);
    }

    /*set注入：ssm 通过set方法注入配置文件中读取的数据；
     * 将配置文件中的数据赋值给类的属性
     * 构造注入：需要配合构造方法
     * 源于spring中的ioc进行对象的自动生成，管理*/
    public void setJPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }


}
