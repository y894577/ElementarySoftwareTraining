package com.crazybubble.element;

import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Magic Gunner
 * @description ������
 */
public class Bubble extends ElementObj {
    //�и�ͼƬ����
    private int imgX = 0;
    private int imgY = 0;
    //����ͼƬˢ��ʱ��
    private int imgTime = 0;
    //���ݱ�ը��Χ
    private int scope = 2;
    //���ݱ�ը����
    private int power = 1;
    //�������ݱ�ըʱ��
    private int bubbleExplodeTime = 0;
    //�ͷ����ݵ��������
    private int playerType;
    //�Ƿ��ͻ
    private boolean isCrash = false;
    //static�����������ݱ��
    private static int number = 0;
    //���ݱ�ţ������ж����������Ƿ�Ϊͬһ��
    private int ID;
    //��ը
    private Explode explode;

    //ͼƬƫ����
    public static int sx1;
    public static int sy1;
    public static int sx2;
    public static int sy2;
    public static int pixel;


    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), getX(), getY(),
                this.getX() + this.getW(),
                this.getY() + this.getH(),
                sx1 + imgX * pixel, sy1 + imgY * pixel,
                sx2 + imgX * pixel, sy2 + imgY * pixel, null);
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
                case "w":
                    this.setW(Integer.parseInt(split2[1]));
                    break;
                case "h":
                    this.setH(Integer.parseInt(split2[1]));
                    break;
                case "playerType":
                    this.playerType = Integer.parseInt(split2[1]);
                    break;
            }

        }
        ImageIcon icon = GameLoad.imgMap.get("bubble");
        this.setIcon(icon);
        this.setID(ID + 1);
        this.explode = new Explode(this);
        return this;
    }

    public Bubble(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    public Bubble() {
    }

    @Override
    protected void updateImage(long time, ElementObj obj) {
        if (time - imgTime > 3) {
            imgTime = (int) time;
            imgX += 1;
            if (imgX >= 3) {
                imgX = 0;
            }
        }
    }

    @Override
    public void model(long time, ElementObj obj) {
        bubbleCrash();
        updateImage(time, obj);
        destroy();
    }

    /**
     * @description ���ݱ�ը��ʧ
     */
    @Override
    public void destroy() {
        if (this.isCrash) {
            this.setLive(false);
            this.setBubbleLive(false);
        } else {
            if (bubbleExplodeTime < 10) {
                bubbleExplodeTime++;
            } else {
                //��ֹ����������setBubbleLive����
                if (this.isLive()) {
                    this.setBubbleLive(false);
                    //��ʽ��x,y,w,h,power
                    for (int i = -scope; i <= scope; i++) {
                        int x = this.getX() + this.getW() * i;
                        int y = this.getY() + this.getH() * i;
                        Explode explode1 = new Explode();
                        explode1.createElement(this.getX() + "," + y + "," + this.getW() + "," + this.getH() + "," + this.getPower());
                        Explode explode2 = new Explode();
                        explode2.createElement(x + "," + this.getY() + "," + this.getW() + "," + this.getH() + "," + this.getPower());
                        ElementManager em = ElementManager.getManager();
                        em.addElement(explode1, GameElement.EXPLODE);
                        em.addElement(explode2, GameElement.EXPLODE);
                    }
                }
            }
        }
    }

    @Override
    protected String toStr() {
        return "x:" + this.getX() + "y:" + this.getY() + "w:" + this.getW() + "h:" + this.getH();
    }

    /**
     * @description ��ײ������������״̬
     */
    public void setBubbleLive(boolean live) {
        if (!live) {
            ElementManager em = ElementManager.getManager();
            List<ElementObj> playerList = em.getElementsByKey(GameElement.PLAYER);
            for (ElementObj obj :
                    playerList) {
                Player player = (Player) obj;
                if (player.getPlayerType() == this.playerType) {
                    player.setBubbleNum(false);
                }
            }
            this.setLive(false);
        }
    }

    /**
     * @description ���������ײ
     */
    public void bubbleCrash() {
        ElementManager em = ElementManager.getManager();
        List<ElementObj> bubbleList = em.getElementsByKey(GameElement.BUBBLE);
        for (ElementObj obj :
                bubbleList) {
            Bubble bubble = (Bubble) obj;
            if (this.getID() != bubble.getID()) {
                if (crash(bubble))
                    this.isCrash = true;
                else
                    this.isCrash = false;
            }
        }
    }

    @Override
    public void crashMethod(ElementObj obj) {
        //��ը�������
        if (obj.getClass().equals(Player.class)) {


        }
        //��ը������ͼ
        else if (obj.getClass().equals(MapObj.class)) {
            Timer timer = new Timer();
            int lastTime = 5;
            Bubble my = this;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    int scope = 10;
                    int bubbleX = my.getX() / 10;
                    int bubbleY = my.getY() / 10;
                    //mathԽ���ж�
                    for (int i = (Math.max(bubbleX - scope, 0)); i < Math.min(bubbleX + scope, 100); i++) {
                        if (GameLoad.mapMap[i][bubbleY] != null) {
                            ((MapObj) GameLoad.mapMap[i][bubbleY]).setLive(false);
                            GameLoad.mapMap[i][bubbleY] = null;
                        }
                    }
                    for (int j = Math.max(bubbleY - scope, 0); j < Math.min(bubbleY + scope, 100); j++) {
                        if (GameLoad.mapMap[bubbleX][j] != null) {
                            ((MapObj) GameLoad.mapMap[bubbleX][j]).setLive(false);
                            GameLoad.mapMap[bubbleX][j] = null;
                        }
                    }
                }
            };
            timer.schedule(task, lastTime * 1000);
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = number + 1;
        number += 1;
    }

    public boolean isCrash() {
        return isCrash;
    }

    public void setCrash(boolean crash) {
        isCrash = crash;
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

    public int getPlayerType() {
        return playerType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }
}
