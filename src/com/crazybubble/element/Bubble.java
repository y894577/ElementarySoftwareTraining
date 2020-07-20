package com.crazybubble.element;

import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bubble extends ElementObj {
    //ÇÐ¸îÍ¼Æ¬×ø±ê
    private int imgX = 0;
    private int imgY = 0;
    //¿ØÖÆÍ¼Æ¬Ë¢ÐÂÊ±¼ä
    private int imgTime = 0;
    //ÅÝÅÝ±¬Õ¨·¶Î§
    private int scope = 2;
    //¿ØÖÆÅÝÅÝ±¬Õ¨Ê±¼ä
    private int bubbleExplodeTime = 0;
    //ÊÍ·ÅÅÝÅÝµÄÍæ¼ÒÀàÐÍ
    private int playerType;
    //ÊÇ·ñ³åÍ»
    private boolean isCrash = false;
    //staticÓÃÓÚÅÅÁÐÅÝÅÝ±àºÅ
    private static int number = 0;
    //ÅÝÅÝ±àºÅ£¬ÓÃÓÚÅÐ¶¨Á½¸öÅÝÅÝÊÇ·ñÎªÍ¬Ò»¸ö
    private int ID;
    //±¬Õ¨
    private Explode explore;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), getX(), getY(),
                this.getX() + this.getW(),
                this.getY() + this.getH(),
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
        this.explore = new Explode(this);
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
        bubbleCrash();
        updateImage(time);
        destroy();
    }

    /**
     * @description ÅÝÅÝ±¬Õ¨ÏûÊ§
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
                //·ÀÖ¹´¥·¢ÁËÁ½´ÎsetBubbleLive·½·¨
                if (this.isLive()) {
                    this.setBubbleLive(false);
                    this.explore.createElement("");
                    ElementManager em = ElementManager.getManager();
                    em.addElement(this.explore, GameElement.EXPLODE);
                }
            }
        }
    }

    @Override
    protected String toStr() {
        return "x:" + this.getX() + "y:" + this.getY() + "w:" + this.getW() + "h:" + this.getH();
    }

    /**
     * @description Åö×²¼ì²âºóÉèÖÃÅÝÅÝ×´Ì¬
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
     * @description ¼ì²âÅÝÅÝÅö×²
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
        //±¬Õ¨´¥¼°Íæ¼Ò
        if (obj.getClass().equals(Player.class)) {


        }
        //±¬Õ¨´¥¼°µØÍ¼
        else if (obj.getClass().equals(MapObj.class)) {
            Timer timer = new Timer();
            int lastTime = 1;
            Bubble my = this;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    int scope = 10;
                    int bubbleX = my.getX() / 10;
                    int bubbleY = my.getY() / 10;
                    //mathÔ½½çÅÐ¶Ï
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

}
