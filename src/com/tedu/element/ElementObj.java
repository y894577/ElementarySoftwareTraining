package com.tedu.element;

import javax.swing.*;
import java.awt.*;

/**
 * @author Magic Gunner
 * @说明 所有元素的基类
 */
public abstract class ElementObj {
    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;

    public void ElementObj() {

    }

    /**
     * @param x    左上角x坐标
     * @param y    左上角y坐标
     * @param w    w宽度
     * @param h    h高度
     * @param icon 图片
     * @说明：带参数的构造方法，可以由子类传输数据到父类
     */
    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    /**
     * 抽象方法，显示元素
     *
     * @param g 画笔 用于进行绘画
     */
    public abstract void showElement(Graphics g);

    /**
     * 使用父类定义接收键盘事件的方法
     * 只有需要实现键盘监听的子类，重写这个方法（约定）
     * 使用接口的方式需要在监听类进行类型转换
     * 约定、配置 现在大部分java框架都需要配置，约定优于配置
     *
     * @param bl  点击的类型 true代表按下 false代表松开
     * @param key 代表触发键盘的code值
     * @扩展 本方法是否可以分为两个方法？一个接收按下一个接收松开（扩展
     */
    public void keyClick(boolean bl, int key) {
        //这个方法不是强制必须实现的

    }

    /**
     * 只要是VO类就要为属性生成get和set方法
     */
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
}
