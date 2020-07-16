package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.nio.file.attribute.FileTime;
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
    //攻击状态 true攻击 false停止
    private boolean pkType = false;

    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);

    }

    public Play() {

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
                //开启攻击状态
                case 32:
                    this.pkType = true;
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
                //关闭攻击状态
                case 32:
                    this.pkType = false;
                    break;
            }
        }
    }

    @Override
    public ElementObj createElement(String str) {
        String[]split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        ImageIcon icon2 = GameLoad.imgMap.get(split[2]);
        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());
        this.setIcon(icon2);
        return this;
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
    protected void updateImage(long ... gameTime) {
        this.setIcon(GameLoad.imgMap.get(fx));
    }

    /**
     * @重写规则：1.重写方法的方法名称和返回值必须和父类一样 2.重写的方法的传入参数类型序列，必须和父类一样
     * 3.重写的方法访问修饰符只能比父类的更加宽泛
     * （比如：父类是protected，现在需要在非子类中调用
     * 可以直接子类继承，重写并super.父类方法，子类public）
     * 4.重写的方法抛出的异常不可以比父类更加宽泛
     */
    private long filetime=0;
    //filetime和传入的时间getTime进行比较、赋值等操作运算
    //控制子弹间隔
    @Override
    protected void add(long gameTime) {
        //构造一个类需要比较多工作的时候，可以选择一种方式：使用小工厂
        //将构造对象的多个步骤进行封装成为一个方法，返回值直接是这个对象
        //传递一个固定格式{x:3,y:5,f:up} json格式
        //会帮你返回对象的实体，并初始化数据
        if (pkType) {
            ElementObj element = new PlayFile().createElement(this.toString()); //以后的框架会碰到
            ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
        }

        //按一次发射一个子弹
        this.pkType = false;


//        //反射机制
//        try {
//            //配置文件创建对象
//            Class<?> forName = Class.forName("com.tedu.element");
//            ElementObj element = PlayFile.class.newInstance().createElement("");
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public String toString() {
        //建议自己定义一个方法
        int x = this.getX();
        int y = this.getY();
        switch (this.fx) {
            //依据不一样的方向做子弹调整
            //子弹在发射的时候就已经给了固定轨迹
            //可以加上目标，修改json格式
            //一般不会写具体数值，图片大小就是显示大小
            //使用图片大小参与运算
            case "up":
                x += getW() / 2 - 5;
                break;
            case "down":
                x += getW() / 2 - 5;
                y += getH() - 5;
                break;
            case "left":
                y += getH() / 2 - 5;
                break;
            case "right":
                x += getX()/2-5;
                y += getH()/2-5;
                break;
        }
        return "x:" + x + ",y:" + y + ",f:" + this.fx;
    }

    public int getFileX() {

        return 0;
    }

    public int getFileY() {
        return 0;
    }

    @Override
    public void die() {
        //放入死亡集合
        ElementManager em = ElementManager.getManager();
        em.addElement(this, GameElement.DIE);
    }
}
