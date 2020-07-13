package com.tedu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.tedu.manager.ElementManager;

/**
 * @author Rui
 * @说明 游戏的主要面板
 * @功能说明 主要进行元素的显示，同时进行界面的刷新（多线程）
 * java开发首先思考：继承或接口实现
 */
public class GameMainJPanel extends JPanel {
    //联动管理器
    private ElementManager em;

    public GameMainJPanel() {
        init();
    }

    public void init() {
        em = ElementManager.getManager();//得到元素管理器对象
    }

    @Override//用于绘画 Graphics 画笔
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(new Color(255, 0, 0));
        g.setFont(new Font("微软雅黑", Font.BOLD, 48));
        //一定要在绘画之前设置字体样式
        g.drawString("i love curry", 200, 200);

        g.fillOval(300, 300, 100, 100);//圆
        g.drawOval(400, 400, 100, 200);//圆圈

    }

}
