package com.crazybubble.show;

import com.crazybubble.element.ElementObj;
import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

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
    }

    @Override
    public void run() {
        while (true){
            this.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
