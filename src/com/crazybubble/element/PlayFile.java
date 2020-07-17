package com.crazybubble.element;

import com.crazybubble.element.ElementObj;

import javax.swing.*;
import java.awt.*;

/**
 * @author Magic Gunner
 * 开发步骤：
 * 1.继承与元素基类；重写show方法
 * 2.按照需求选择性重写其他方法（例如：move等
 * 3.思考并定义子类特有的属性
 * @说明 玩家子弹类，本类的实体对象由玩家对象调用和创建
 */
public class PlayFile extends ElementObj {
    //攻击力
    private int attack = 1;
    //移动速度值
    private int moveNum = 3;
    //发射方向
    private String fx;


    /**
     * @param x    左上角x坐标
     * @param y    左上角y坐标
     * @param w    w宽度
     * @param h    h高度
     * @param icon 图片
     * @说明：带参数的构造方法，可以由子类传输数据到父类
     */
    private PlayFile(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
        //一枪一个敌人
        this.attack = 1;
        //移动速度
        this.moveNum = 3;
        //发射方向
        this.fx = "";
    }

    public PlayFile() {
        super();
    }


    //对创建这个对象的过程进行封装，外界只需要传输必要的约定参数，返回值就是对象实体
    //静态方法不能重写
    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        for (String str1 : split) {
            String[] split2 = str1.split(":");
            switch (split2[0]) {
                case "x":
                    this.setX(Integer.parseInt(split2[1]));
                    break;
                case "y":
                    this.setY(Integer.parseInt(split2[1]));
                    break;
                case "f":
                    this.fx = split2[1];
                    break;
            }
        }
        this.setW(10);
        this.setH(10);
        return this;
    }

    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());

    }

//    @Override
//    protected void add(long gameTime) {
//
//    }

    @Override
    protected void move() {
        //子弹停止
        //主线程消亡子弹
        if (this.getX() < 0 || this.getX() > 500 || this.getY() < 0 || this.getY() > 300) {
            this.setLive(false);
            return;
        }
        switch (this.fx) {
            case "up":
                this.setY(this.getY() - this.moveNum);
                break;
            case "down":
                this.setY(this.getY() + this.moveNum);
                break;
            case "left":
                this.setX(this.getX() - this.moveNum);
                break;
            case "right":
                this.setX(this.getX() + this.moveNum);
                break;
        }
    }

    /**
     * 对于子弹来说：
     * 1.出边界
     * 2.碰撞
     * 3.玩家放保险
     * 处理方式：当达到死亡的条件时，只进行修改自我状态的操作
     */

    private long time = 0;

//    @Override
//    protected void updateImage(long... gameTime) {
//        //子弹变装
//
////        if (gameTime != null) {
////            if (gameTime[0] - time > 20) {
////                time = gameTime[0];
////                this.setW(this.getW() + 2);
////                this.setH(this.getH() + 2);
////            }
////        }
//
//    }
}
