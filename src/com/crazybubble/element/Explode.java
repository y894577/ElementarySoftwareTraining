package com.crazybubble.element;

import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;
import com.sun.source.tree.Scope;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @description ÅÝÅÝ±¬Õ¨
 */
public class Explode extends ElementObj {

    private Bubble bubble;

    private int imgX = 0;
    private int imgY = 0;

    //±¬Õ¨Ê±¼ä¿ØÖÆ
    private int explodeTime = 0;
    //±¬Õ¨·¶Î§
    private int scope;

    public Explode(Bubble bubble) {
        this.bubble = bubble;
    }

    @Override
    public void showElement(Graphics g) {
        for (int i = -2; i <= 2; i++) {
            g.drawImage(this.getIcon().getImage(),
                    this.getX() + this.getW() * i, this.getY(),
                    this.getX() + this.getW() * (i + 1), this.getY() + this.getH(),
                    imgX, imgY,
                    32 + imgX, 48 + imgY, Color.blue, null);
        }
        for (int i = -2; i <= 2; i++) {
            g.drawImage(this.getIcon().getImage(),
                    this.getX(), this.getY() + this.getH() * i,
                    this.getX() + this.getW(), this.getY() + this.getH() * (i + 1),
                    imgX, imgY,
                    32 + imgX, 48 + imgY, Color.blue, null);
        }

    }

    @Override
    public ElementObj createElement(String str) {
        this.setX(bubble.getX());
        this.setY(bubble.getY());
        this.setW(bubble.getW());
        this.setH(bubble.getH());

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
    public void model(long time) {
        updateImage(time);
        destroy();
    }

    @Override
    protected void updateImage(long time) {
        super.updateImage(time);
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
