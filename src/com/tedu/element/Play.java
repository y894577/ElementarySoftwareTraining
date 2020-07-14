package com.tedu.element;

import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
     *
     * @问题 1.图片要读取到内存中：加载器 临时处理方式。手动编写存储到内存中
     * 2.什么时候进行修改图片（因为图片是在父类中的属性存储）
     * 3.图片应该使用什么集合进行存储
     */

    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;

    //图片集合 使用map来进行存储
    private Map<String, ImageIcon> imgMap;
    //变量专门用来记录当前的方向，默认up
    private String fx = "up";

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
                    this.left = true;
                    this.right = false;
                    this.up = false;
                    this.down = false;
                    this.fx = "left";
                    break;
                case 38:
                    this.up = true;
                    this.down = false;
                    this.left = false;
                    this.right = false;
                    this.fx = "up";
                    break;
                case 39:
                    this.right = true;
                    this.left = false;
                    this.up = false;
                    this.down = false;
                    this.fx = "right";
                    break;
                case 40:
                    this.down = true;
                    this.up = false;
                    this.left = false;
                    this.right = false;
                    this.fx = "down";
                    break;
            }
        } else {
            switch (key) {
                case 37:
                    this.left = false;
                    break;
                case 38:
                    this.up = false;
                    break;
                case 39:
                    this.right = false;
                    break;
                case 40:
                    this.down = false;
                    break;
            }
        }
    }

    @Override
    public void move() {
        if (this.left && this.getX() > 0)
            this.setX(this.getX() - 10);
        if (this.up && this.getY() > 0)
            this.setY(this.getY() - 10);
        if (this.right && this.getX() < 600 - this.getW())
            this.setX(this.getX() + 10);
        if (this.down && this.getY() < 400 - this.getH())
            this.setY(this.getY() + 10);
    }

    @Override
    protected void updateImage() {
        this.setIcon(GameLoad.imgMap.get(fx));
    }
}
