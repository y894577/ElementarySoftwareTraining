package com.tedu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.*;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @author Magic Gunner
 * @说明 游戏的主要面板
 * @功能说明 主要进行元素的显示，同时进行界面的刷新（多线程）
 * java开发首先思考：继承或接口实现
 *
 * @多线程刷新 1、本类实现线程接口
 *           2、本类中定义一个内部类来实现
 */
public class GameMainJPanel extends JPanel implements Runnable {
    //联动管理器
    private ElementManager em;

    public GameMainJPanel() {
        init();
        // 以下代码后面会重写
        load();
    }

    private void load() {
        ImageIcon icon = new ImageIcon("image/test.jpg");
        //实例化对象
        ElementObj obj = new Play(100, 100, 100, 100, icon);
        //将对象放入到元素管理器中
        em.addElement(obj, GameElement.PLAY);
    }

    public void init() {
        em = ElementManager.getManager();//得到元素管理器对象
    }

    /**
     * paint方法是进行绘画元素
     * 绘画时是有固定的顺序，先绘画的图片会在底层，后绘画的图片会覆盖先绘画的
     * 约定：本方法只执行一次，想实时刷新需要使用多线程
     * @param g
     */
    @Override//用于绘画 Graphics 画笔
    public void paint(Graphics g) {
        super.paint(g);
        // map  key-value key是无序不可重复
        //set 和map的key一样 无序不可重复
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        GameElement.values();//默认方法 返回值是一个数组，数组的顺序就是枚举顺序
        for (GameElement ge :
                GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = 0; i < list.size(); i++) {
                ElementObj obj = list.get(i);
                //调用每个类自己的show方法完成自己的显示
                obj.showElement(g);
            }
        }


//        Set<GameElement> set = all.keySet();
//        //迭代器
//        for (GameElement ge : set) {
//            List<ElementObj> list = all.get(ge);
//            for (int i = 0; i < list.size(); i++) {
//                ElementObj obj = list.get(i);
//                //调用每个类自己的show方法完成自己的显示
//                obj.showElement(g);
//            }
//        }
    }

    @Override
    public void run() {
        while (true){
            this.repaint();
            //一般情况下，多线程都会使用一个休眠，为了控制速度

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
