package com.crazybubble.element;

import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * @author Magic Gunner
 * @description 泡泡爆炸
 */
public class Explode extends ElementObj {

    private Bubble bubble;

    private int imgX = 0;
    private int imgY = 0;

    //爆炸时间控制
    private int explodeTime = 0;
    //爆炸范围
    private int scope;
    //爆炸威力
    private int power;
    //爆炸触发的obj列表
    private List<ElementObj> isExplodeObj = new ArrayList<>();

    //图片偏移量
    public static int sx1 = 0;
    public static int sy1 = 0;
    public static int sx2 = 0;
    public static int sy2 = 0;
    public static int pixel = 0;


    public Explode() {
    }

    public Explode(Bubble bubble) {
        this.bubble = bubble;
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(),
                this.getX() + this.getW(),
                this.getY() + this.getH(),
                sx1 + imgX, sy1 + imgY,
                sx2 + imgX, sy2 + imgY, Color.blue, null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        this.setW(Integer.parseInt(split[2]));
        this.setH(Integer.parseInt(split[3]));
        this.setPower(Integer.parseInt(split[4]));
        //确保isExplodeObj有数据
        this.isExplodeObj.add(new Player());
        ImageIcon icon = GameLoad.imgMap.get("explode");
        this.setIcon(icon);
        return this;
    }

    @Override
    public void destroy() {
        if (explodeTime < 20) {
            explodeTime++;
        } else {
            this.setLive(false);
        }

    }

    @Override
    public void model(long time, ElementObj obj) {
        updateImage(time, obj);
        destroy();
    }

    @Override
    protected void updateImage(long time, ElementObj obj) {
        super.updateImage(time, obj);
    }

    @Override
    public void crashMethod(ElementObj obj) {
        if (obj instanceof Player || obj instanceof MapObj) {
            boolean isExist = false;
            //保证一次爆炸只扣血一次
            for (ElementObj elementObj :
                    this.isExplodeObj) {
                if (elementObj.equals(obj)) {
                    isExist = true;
                }
            }
            if (!isExist) {
                this.isExplodeObj.add(obj);
                if (obj.getHp() > 0) {
                    obj.setHp(obj.getHp() - this.power);
                } else {
                    obj.destroy();
                }
            }
        }
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
