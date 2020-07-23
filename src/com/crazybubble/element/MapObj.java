package com.crazybubble.element;

import com.crazybubble.element.ElementObj;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

/**
 * @author Magic Gunner
 * @description 地图元素
 */
public class MapObj extends ElementObj {
    //地图元素生命值
    private int hp = 1;
    //地图元素类型
    private String mapType = "";
    //地图元素生命值
    private int mapHp;
    //是否不可摧毁
    private boolean isStable = false;

    //图片偏移量
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

        if (mapType.equals("block1")) {
            this.isStable = true;
        }

        if (GameLoad.mapInitMap.get(arr[0]).split(":")[0].equals("hp"))
            this.setHp(Integer.parseInt(GameLoad.mapInitMap.get(arr[0]).split(":")[1]));
        return this;
    }

    @Override
    public void crashMethod(ElementObj obj) {
//        if (this.isStable)
//            return;
//        if (obj instanceof Explode)
//            this.hp--;
//        if (this.hp <= 0)
//            this.destroy();
    }

    /**
     * @description 设置地图元素的存在状态
     */
    @Override
    public void setLive(boolean live) {
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
        if (this.isStable) {
            return;
        } else
            this.setLive(false);
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
