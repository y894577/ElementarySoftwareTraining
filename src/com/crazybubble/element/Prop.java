package com.crazybubble.element;

import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Magic Gunner
 * @description 道具类
 */
public class Prop extends ElementObj {

    //道具类型
    private String propType;
    //玩家类型
    private String playerType;

    private int imgX = 0;
    private int imgY = 0;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getX() + this.getW(), this.getY() + this.getH(),
                0 + imgX, 0 + imgY,
                32 + imgX, 48 + imgY, null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        this.setW(30);
        this.setH(30);
        this.setPropType(split[2]);
        ImageIcon icon = GameLoad.imgMap.get(this.getPropType());
        this.setIcon(icon);
        return this;
    }

    /**
     * @description 道具和人物碰撞
     */
    @Override
    public void crashMethod(ElementObj obj) {
        this.setLive(false);
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }
}
