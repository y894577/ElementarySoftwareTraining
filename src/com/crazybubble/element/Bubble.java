package com.crazybubble.element;

import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Bubble extends ElementObj {
    //«–∏ÓÕº∆¨◊¯±Í
    private int imgX = 0;
    private int imgY = 0;
    //øÿ÷∆Õº∆¨À¢–¬ ±º‰
    private int imgTime = 0;
    //øÿ÷∆≈›≈›±¨’® ±º‰
    private int bubbleExploreTime = 0;
    // Õ∑≈≈›≈›µƒÕÊº“¿‡–Õ
    private int playerType;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), getX(), getY(),
                this.getX() + this.getW() * 4,
                this.getY() + this.getH() * 4,
                0 + imgX, 8 + imgY,
                31 + imgX, 45 + imgY, null);
    }

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
                case "playerType":
                    this.playerType = Integer.parseInt(split2[1]);
                    break;
            }

        }
        ImageIcon icon = GameLoad.imgMap.get("bubble");
        this.setIcon(icon);
        this.setW(10);
        this.setH(10);
        return this;
    }

    public Bubble(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    public Bubble() {
    }

    @Override
    protected void updateImage(long time) {
        if (time - imgTime > 3) {
            imgTime = (int) time;
            imgX += 33;
            if (imgX >= 99) {
                imgX = 0;
            }
        }
    }

    @Override
    public void model(long time) {
        updateImage(time);
        destroy();
    }

    /**
     * @description ≈›≈›±¨’®œ˚ ß
     */
    @Override
    public void destroy() {
        if (bubbleExploreTime < 80) {
            bubbleExploreTime++;
        } else {
            ElementManager em = ElementManager.getManager();
            List<ElementObj> playerList = em.getElementsByKey(GameElement.PLAYER);
            for (ElementObj obj :
                    playerList) {
                Player player = (Player) obj;
                player.setBubbleNum(this.playerType);
            }
            super.setLive(false);
        }
    }

    /**
     * @description ≈ˆ◊≤ºÏ≤‚∫Û…Ë÷√≈›≈›◊¥Ã¨
     */
    @Override
    public void setLive(boolean live) {
        ElementManager em = ElementManager.getManager();
        List<ElementObj> playerList = em.getElementsByKey(GameElement.PLAYER);
        for (ElementObj obj :
                playerList) {
            Player player = (Player) obj;
            player.setBubbleNum(this.playerType);
        }
        super.setLive(live);
    }

    public void bubbleCrash(){

    }

}
