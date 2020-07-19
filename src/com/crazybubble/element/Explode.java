package com.crazybubble.element;

import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

/**
 * @description ÅÝÅÝ±¬Õ¨
 */
public class Explode extends ElementObj {

    private Bubble bubble;

    private int imgX = 0;
    private int imgY = 0;

    public Explode(Bubble bubble) {
        this.bubble = bubble;
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(null,
                this.getX(), this.getY(),
                this.getX() + this.getW(), this.getY() + this.getH(),
                0 + imgX, 0 + imgY,
                32 + imgX, 48 + imgY, Color.red, null);
    }

    @Override
    public ElementObj createElement(String str) {
        this.setX(bubble.getX());
        this.setY(bubble.getY());
        this.setW(bubble.getW());
        this.setH(bubble.getH());
        return this;
    }

    @Override
    public void crashMethod(ElementObj obj) {
        super.crashMethod(obj);
        //±¬Õ¨´¥¼°Íæ¼Ò
        if (obj.getClass().equals(Player.class)) {
            bubble.getScope();
        }
        //±¬Õ¨´¥¼°µØÍ¼
        else if (obj.getClass().equals(MapObj.class)) {

        }
    }

    @Override
    public void model(long time) {
        updateImage(time);
    }

    @Override
    protected void updateImage(long time) {
        super.updateImage(time);
    }
}
