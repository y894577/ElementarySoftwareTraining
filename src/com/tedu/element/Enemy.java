package com.tedu.element;

import com.tedu.controller.GameThread;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj {
    /**
     * @param x    左上角x坐标
     * @param y    左上角y坐标
     * @param w    w宽度
     * @param h    h高度
     * @param icon 图片
     * @说明：带参数的构造方法，可以由子类传输数据到父类
     */
    public Enemy(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    private boolean left = true;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;
    private String fx = "left";

    public Enemy() {

    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public void move() {
//        if (this.left) {
//            this.setX(this.getX() - 10);
//            if (this.getX() < 100) {
//                this.left = false;
//                this.right = true;
//                this.fx = "right";
//            }
//        }
//        if (this.right)
//            this.setX(this.getX() + 10);
//        if (this.getX() > 300) {
//            this.left = true;
//            this.right = false;
//            this.fx = "left";
//        }
    }

    @Override
    protected void updateImage(long ... gameTime) {
        this.setIcon(GameLoad.imgMap.get(fx));
    }

    @Override
    protected void add(long gameTime) {

    }

    @Override
    public ElementObj createElement(String str) {
        Random ran = new Random();
        int x= ran.nextInt(800);
        int y = ran.nextInt(500);
        this.setX(x);
        this.setY(y);
        this.setW(30);
        this.setH(30);
        this.setIcon(GameLoad.imgMap.get(fx));
        return this;
    }
}
