package com.crazybubble.element;

import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Magic Gunner
 * @description ������
 */
public class Prop extends ElementObj {

    //��������
    private String propType;
    //�������
    private String playerType;
    //����ʱ��
    private int lastTime;
    //�ɱ���ȡ״̬
    private boolean canGet = false;

    //ͼƬƫ����
    public static int sx1;
    public static int sy1;
    public static int sx2;
    public static int sy2;
    public static int pixel;

    private int imgX = 0;
    private int imgY = 0;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getX() + this.getW(), this.getY() + this.getH(),
                sx1 + imgX, sy1 + imgY,
                sx2 + imgX, sy2 + imgY, null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        for (String s :
                split) {
            String[] split1 = s.split(":");
            switch (split1[0]) {
                case "x":
                    this.setX(Integer.parseInt(split1[1]) * pixel + 10);
                    break;
                case "y":
                    this.setY(Integer.parseInt(split1[1]) * pixel + 10);
                    break;
                case "type":
                    this.setPropType(split1[1]);
                    break;
                case "time":
                    this.setLastTime(Integer.parseInt(split1[1]));
                    break;
            }
        }
        //��С��ײ���
        this.setW(pixel / 3);
        this.setH(pixel / 3);
        ImageIcon icon = GameLoad.imgMap.get(this.getPropType());
        this.setIcon(icon);
        return this;
    }

    /**
     * @description ���ߺ�������ײ
     */
    @Override
    public void crashMethod(ElementObj obj) {
        if (this.canGet)
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

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public boolean isCanGet() {
        return canGet;
    }

    public void setCanGet(boolean canGet) {
        this.canGet = canGet;
    }
}
