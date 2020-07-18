package com.crazybubble.element;

import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

/**
 * @author Magic Gunner
 * @description 所有元素的基类
 */
public abstract class ElementObj {
    private int x;
    private int y;
    private int w;
    private int h;

    //图片的左上角
    private int sx, sy;
    //图片的右下角
    private int dx, dy;

    private ImageIcon icon;
    //生存状态
    private boolean live = true;
    //计时器
    private static long time = 0;


    /**
     * @description 无参构造
     */
    public ElementObj() {

    }


    /**
     * @param x    左上角x坐标
     * @param y    左上角y坐标
     * @param w    w宽度
     * @param h    h高度
     * @param icon 图片
     * @description 带参构造
     */
    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }


    /**
     * @param g 画笔 用于进行绘画
     * @description 抽象方法，显示元素
     */
    public abstract void showElement(Graphics g);


    /**
     * @param bindType 点击的类型 true代表按下 false代表松开
     * @param key      代表触发键盘的code值
     * @description 键盘监听
     * @扩展 本方法是否可以分为两个方法？一个接收按下一个接收松开（扩展
     */
    public void keyClick(boolean bindType, int key) {
    }

    /**
     * @param str
     * @return
     * @description 抽象方法，使用字符串创建元素
     */
    public abstract ElementObj createElement(String str);


    /**
     * @description 模板，顺序执行
     */
    public void model(long time) {
    }

    /**
     * @description 移动方法；protected 只有子类可以重写
     */
    protected void move() {
    }


    /**
     * @description 更新图片
     */
    protected void updateImage(long time) {
        //参数可有可无
        // long ... a 不定长的数组，可以向这个方法传输N个long类型的数据
    }


    /**
     * @description 元素消亡
     */
    public void destroy() {
        //死亡方法 给子类继承的
        //死亡也是一个对象
    }

    /**
     * @return 本方法返回元素的碰撞矩形对象（实时返回）
     * @description 将图片定义成矩形，方便碰撞检测
     */
    public Rectangle getRectangle() {
        //可以将这个数据进行处理
        return new Rectangle(x, y, w, h);
    }

    /**
     * @param obj 碰撞的元素
     * @return boolean 返回true说明有碰撞，返回false说明没有碰撞
     * @description 碰撞方法
     */
    public boolean crash(ElementObj obj) {
        return this.getRectangle().intersects(obj.getRectangle());
    }

    /**
     * @return
     * @description 转换为字符串
     */
    protected String toStr() {
        return null;
    }

    /**
     * @return
     * @description 碰撞之后执行的方法
     */
    public void crashMethod(ElementObj obj) {

    }

    //只要是VO类就要为属性生成get和set方法

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
