package com.crazybubble.element;

import com.crazybubble.element.ElementObj;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

/**
 * @author Magic Gunner
 * @description ��ͼԪ��
 */
public class MapObj extends ElementObj {
    //��ͼԪ������ֵ
    private int hp = 1;
    //��ͼԪ������
    private String mapType = "";
    //��ͼԪ������ֵ
    private int mapHp;
    //�Ƿ񲻿ɴݻ�
    private boolean isStable;

    //ͼƬƫ����
    public static int sx1 = 0;
    public static int sy1 = 0;
    public static int sx2 = 0;
    public static int sy2 = 0;
    public static int pixel = 1;

    private int imgX = 0;
    private int imgY = 0;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getX() + 50, this.getY() + 50,
                sx1 + imgX, sy1 + imgY,
                sx2 + imgX, sy2 + imgY, null);
    }


    @Override
    public ElementObj createElement(String str) {
        String[] arr = str.split(",");
        GameLoad.configMap.get("mapPath");
        ImageIcon icon = GameLoad.imgMap.get(arr[0]);
        this.setStable(false);
        this.setMapType(arr[0]);
        this.setX(Integer.parseInt(arr[1]) * pixel);
        this.setY(Integer.parseInt(arr[2]) * pixel);
        this.setW(pixel);
        this.setH(pixel);
        this.setIcon(icon);
        if (GameLoad.mapInitMap.get(arr[0]).split(":")[0].equals("hp"))
            this.setHp(Integer.parseInt(GameLoad.mapInitMap.get(arr[0]).split(":")[1]));
        return this;
    }

    @Override
    public void crashMethod(ElementObj obj) {
        if (obj instanceof Explode)
            this.hp--;
        if (this.hp <= 0)
            this.setLive(false);
//        obj.setLive(false);
    }

    /**
     * @description ���õ�ͼԪ�صĴ���״̬
     */
    @Override
    public void setLive(boolean live) {
//        if ("IRON".equals(this.name)) {
//            this.hp--;
//            if (this.hp >= 0) {
//                super.setLive(live);
//                return;
//            }
//        }
        super.setLive(live);
    }

    @Override
    public void model(long time, ElementObj obj) {
        updateImage(time, obj);
    }

    @Override
    protected void updateImage(long time, ElementObj obj) {
        super.updateImage(time, obj);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public int getMapHp() {
        return mapHp;
    }

    public void setMapHp(int mapHp) {
        this.mapHp = mapHp;
    }

    public boolean isStable() {
        return isStable;
    }

    public void setStable(boolean stable) {
        isStable = stable;
    }
}
