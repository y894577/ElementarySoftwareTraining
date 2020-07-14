package com.tedu.element;

import javax.swing.*;
import java.awt.*;

public class Play extends ElementObj {

    /**
     * 移动属性：
     * 1.单属性 配合 方向枚举类型使用 一次只能移动一个方向
     * 2.双属性 上下 和 左右 配合boolean值使用 例如：true代表上 false代表下
     * 需要另外一个变量来确定是否按下方向键
     * 约定 0代表不动 1代表向上 2代表向下
     * 3.四属性 上下左右都可以 boolean配合使用 true代表移动 false代表不移动
     * 同时按上和下，后按的会重置先按的
     * 说明：多状态可以使用Map<泛型,boolean>或set<判定对象>判定对象中有时间
     */

    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;

    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    //重写方法
    @Override   //反射机制为类或者方法或者属性添加的注释
    public void keyClick(boolean bl, int key) {
        if (bl) {
            switch (key) {
                case 37:
//                    this.setX(this.getX() - 10);
                    this.left = true;
                    break;
                case 38:
//                    this.setY(this.getY() - 10);
                    this.up = true;
                    break;
                case 39:
//                    this.setX(this.getX() + 10);
                    this.right = true;
                    break;
                case 40:
//                    this.setY(this.getY() + 10);
                    this.down = true;
                    break;
            }
//            System.out.println(this.getX() + ":" + this.getY());
        } else {
            switch (key) {
                case 37:
//                    this.setX(this.getX() - 10);
                    this.left = false;
                    break;
                case 38:
//                    this.setY(this.getY() - 10);
                    this.up = false;
                    break;
                case 39:
//                    this.setX(this.getX() + 10);
                    this.right = false;
                    break;
                case 40:
//                    this.setY(this.getY() + 10);
                    this.down = false;
                    break;
            }
        }

        if(this.left)
            this.setX(this.getX() - 10);
        if(this.up)
            this.setY(this.getY() - 10);
        if(this.right)
            this.setX(this.getX() + 10);
        if(this.down)
            this.setY(this.getY() + 10);

    }
}
