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
 */
public class GameMainJPanel extends JPanel {
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

    @Override//用于绘画 Graphics 画笔
    public void paint(Graphics g) {
        super.paint(g);
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        Set<GameElement> set = all.keySet();
        for (GameElement ge : set) {
            List<ElementObj> list = all.get(ge);
            for (int i = 0; i < list.size(); i++) {
                ElementObj obj = list.get(i);
                //调用每个类自己的show方法完成自己的显示
                obj.showElement(g);
            }
        }
    }

}
