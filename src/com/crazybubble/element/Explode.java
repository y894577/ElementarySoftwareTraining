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
 * @description ���ݱ�ը
 */
public class Explode extends ElementObj {

    private Bubble bubble;

    private int imgX = 0;
    private int imgY = 0;

    //��ըʱ�����
    private int explodeTime = 0;
    //��ը��Χ
    private int scope;
    //��ը����
    private int power;
    //��ը������obj�б�
    private List<ElementObj> isExplodeObj = new ArrayList<>();

    //ͼƬƫ����
    public static int sx1=0;
    public static int sy1=0;
    public static int sx2=0;
    public static int sy2=0;
    public static int pixel=0;


    public Explode() {
    }

    public Explode(Bubble bubble) {
        this.bubble = bubble;
    }

    @Override
    public void showElement(Graphics g) {
//        for (int i = -2; i <= 2; i++) {
//            g.drawImage(this.getIcon().getImage(),
//                    this.getX() + this.getW() * i, this.getY(),
//                    this.getX() + this.getW() * (i + 1), this.getY() + this.getH(),
//                    sx1 + imgX, sy1 + imgY,
//                    sx2 + imgX, sy2 + imgY, Color.blue, null);
//
//            g.drawImage(this.getIcon().getImage(),
//                    this.getX(), this.getY() + this.getH() * i,
//                    this.getX() + this.getW(), this.getY() + this.getH() * (i + 1),
//                    sx1 + imgX, sy1 + imgY,
//                    sx2 + imgX, sy2 + imgY, Color.blue, null);
//        }
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
        //ȷ��isExplodeObj������
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
            //��֤һ�α�ըֻ��Ѫһ��
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
