package com.crazybubble.element;

import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;
import com.crazybubble.show.GameJFrame;
import org.w3c.dom.ls.LSException;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Magic Gunner
 * @description �����
 */
public class Player extends ElementObj {
    //������ͣ�0�������A��1�������B
    private int playerType;

    //���̼���
    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;

    //�и�ͼƬ����
    private int imgX = 0;
    private int imgY = 0;

    //ͼƬƫ����
    public static int sx1;
    public static int sy1;
    public static int sx2;
    public static int sy2;
    public static int pixel;


    //����ͼƬˢ��ʱ��
    private int imgTime = 0;

    //���﷽��
    private String fx = "";
    //����״̬
    private boolean attackType = false;
    //Ѫ��
    private int hp;
    //�ƶ��ٶ�
    private int speed;
    //������ͷ���������
    private int bubbleNum = 0;
    //���ͷ���������
    private int bubbleTotal;
    //��������
    private int bubblePower;
    //�޵�״̬
    private boolean isSuper = false;
    //��ͣ״̬
    private boolean isStop = false;
    //�ܶ�״̬
    private boolean isRun = false;
    //����״̬
    private boolean isReverse = false;
    //��ֹͬ������ͼƬ
    private int isRunPlayer;
    //���̰���״̬
    private boolean keyLeftType;
    private boolean keyRightType;
    private boolean keyUpType;
    private boolean keyDownType;

    //��̬�������������ļ���ȡ
    public static int HP;
    public static int SPEED;
    public static int BUBBLETOTAL;
    public static int BUBBLEPOWER;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(),
                this.getX() + this.getW(),
                this.getY() + this.getH(),
                sx1 + (imgX * pixel), sy1 + (imgY * pixel),
                sx2 + (imgX * pixel), sy2 + (imgY * pixel), null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        for (String s :
                split) {
            String[] split1 = s.split(":");
            switch (split1[0]) {
                case "x":
                    this.setX(Integer.parseInt(split1[1]));
                    break;
                case "y":
                    this.setY(Integer.parseInt(split1[1]));
                    break;
                case "w":
                    this.setW(Integer.parseInt(split1[1]));
                    break;
                case "h":
                    this.setH(Integer.parseInt(split1[1]));
                    break;
                case "type":
                    this.setPlayerType(Integer.parseInt(split1[1]));
                    break;
            }
        }
        ImageIcon icon = GameLoad.imgMap.get("player" + this.playerType);
        this.setIcon(icon);
        this.setHp(HP);
        this.setSpeed(SPEED);
        this.setBubbleTotal(BUBBLETOTAL);
        this.setBubblePower(BUBBLEPOWER);
        return this;
    }

    protected void addBubble() {
        if (bubbleNum < bubbleTotal) {
            if (attackType && !keyLeftType && !keyRightType && !keyUpType && !keyDownType) {
                ElementObj obj = GameLoad.getObj("bubble");
                Bubble element = (Bubble) obj.createElement(this.toStr());
                element.bubbleCrash();
                if (!element.isCrash()) {
                    this.setBubbleNum(true);
                    ElementManager.getManager().addElement(element, GameElement.BUBBLE);
                    this.attackType = false;
                } else {
                    element.setLive(false);
                }
            }
        }
    }

    /**
     * @return
     * @description ����ǰ�������Ϣת��Ϊ�ַ���
     */
    @Override
    public String toStr() {
        int x = this.getX();
        int y = this.getY();
        int w = this.getW();
        int h = this.getH();
        int playerType = this.playerType;
        return "x:" + x + ",y:" + y + ",w:" + w + ",h:" + h + ",playerType:" + playerType;
    }

    /**
     * @param time
     * @description �ܶ�ʱ����ͼƬ
     */
    @Override
    protected void updateImage(long time, ElementObj obj) {
        if (this.isRun) {
            imgTime = (int) time;
            switch (this.fx) {
                case "down":
                    imgY = 0;
                    break;
                case "left":
                    imgY = 1;
                    break;
                case "right":
                    imgY = 2;
                    break;
                case "up":
                    imgY = 3;
                    break;
            }
            imgX++;
            if (imgX > 3) {
                imgX = 0;
            }
        }
    }

    protected void move() {
        if (this.isStop)
            return;
        if (this.left)
            this.setX(this.getX() - this.speed);
        if (this.up)
            this.setY(this.getY() - this.speed);
        if (this.right)
            this.setX(this.getX() + this.speed);
        if (this.down)
            this.setY(this.getY() + speed);
    }

    /**
     * @description ģ�巽������װ���в���
     */
    @Override
    public final void model(long time, ElementObj obj) {
        if (this.isRunPlayer == this.playerType)
            updateImage(time, obj);
        move();
        addBubble();
        destroy();
    }

    /**
     * @param bindType ��������� true������ false�����ɿ�
     * @param key      ���������̵�codeֵ
     * @description ���̼���
     */
    public void keyClick(boolean bindType, int key, String type) {
        if (this.isStop)
            return;
        this.isRunPlayer = Integer.parseInt(type);

        if (bindType) {
            if (this.playerType == 0)
                switch (key) {
                    case 37:
                        this.left = true;
                        this.right = false;
                        this.up = false;
                        this.down = false;
                        this.fx = "left";
                        this.keyLeftType = true;
                        break;
                    case 38:
                        this.up = true;
                        this.down = false;
                        this.left = false;
                        this.right = false;
                        this.fx = "up";
                        this.keyUpType = true;
                        break;
                    case 39:
                        this.right = true;
                        this.left = false;
                        this.up = false;
                        this.down = false;
                        this.fx = "right";
                        this.keyRightType = true;
                        break;
                    case 40:
                        this.down = true;
                        this.up = false;
                        this.left = false;
                        this.right = false;
                        this.fx = "down";
                        this.keyDownType = true;
                        break;
                    //��������״̬
                    case 10:
                        this.attackType = true;
                        break;
                }
            else if (this.playerType == 1)
                switch (key) {
                    case 65:
                        this.left = true;
                        this.right = false;
                        this.up = false;
                        this.down = false;
                        this.fx = "left";
                        this.attackType = false;
                        this.keyLeftType = true;
                        break;
                    case 87:
                        this.up = true;
                        this.down = false;
                        this.left = false;
                        this.right = false;
                        this.fx = "up";
                        this.attackType = false;
                        this.keyUpType = true;
                        break;
                    case 68:
                        this.right = true;
                        this.left = false;
                        this.up = false;
                        this.down = false;
                        this.fx = "right";
                        this.attackType = false;
                        this.keyRightType = true;
                        break;
                    case 83:
                        this.down = true;
                        this.up = false;
                        this.left = false;
                        this.right = false;
                        this.fx = "down";
                        this.attackType = false;
                        this.keyDownType = true;
                        break;
                    //��������״̬
                    case 32:
                        this.attackType = true;
                        break;
                }
            this.isRun = true;
        } else {
            if (this.playerType == 0)
                switch (key) {
                    case 37:
                        this.left = false;
                        this.keyLeftType = false;
                        break;
                    case 38:
                        this.up = false;
                        this.keyUpType = false;
                        break;
                    case 39:
                        this.right = false;
                        this.keyRightType = false;
                        break;
                    case 40:
                        this.down = false;
                        this.keyDownType = false;
                        break;
                    //�رչ���״̬
                    case 10:
                        this.attackType = false;
                        break;
                }
            else if (this.playerType == 1)
                switch (key) {
                    case 65:
                        this.left = false;
                        this.keyLeftType = false;
                        break;
                    case 87:
                        this.up = false;
                        this.keyUpType = false;
                        break;
                    case 68:
                        this.right = false;
                        this.keyRightType = false;
                        break;
                    case 83:
                        this.down = false;
                        this.keyDownType = false;
                        break;
                    //�رչ���״̬
                    case 32:
                        this.attackType = false;
                        break;
                }
            this.isRun = false;
        }
    }

    @Override
    public void destroy() {
        if (this.hp <= 0) {
            this.setLive(false);
        }
    }

    @Override
    public void crashMethod(ElementObj obj) {
        //���֮����ײ
        if (Player.class.equals(obj.getClass())) {
            //��Ҫȡ���ƶ�
//            this.mapCrash();
        }
        //��Һ͵���֮����ײ
        else if (Prop.class.equals(obj.getClass())) {
            Prop prop = (Prop) obj;
            this.propCrash(prop.getPropType(), prop.getLastTime());
        }
        //��Һ�����֮����ײ
        else if (Bubble.class.equals(obj.getClass())) {
            Bubble bubble = (Bubble) obj;
            this.bubbleCrash(bubble);
        }
        //��Һ͵�ͼ֮����ײ
        else if (MapObj.class.equals(obj.getClass())) {
            this.attackType = false;
            this.mapCrash();
        }
    }


    /**
     * @description ��Һ͵���֮����ײ
     */
    public void propCrash(String propType, int lastTime) {
        //���ط���ֵҲ�����������ļ����ã���ʱ��д�ɶ�ֵ
        switch (propType) {
            case "SuperPower":
                this.propSuperPower(lastTime);
                break;
            case "BubbleAdd":
                this.propBubbleAdd(lastTime);
                break;
            case "RunningShoes":
                this.propRunningShoes(lastTime);
                break;
            case "CrazyDiamond":
                this.propCrazyDiamond();
                break;
            case "TheWorld":
                this.propTheWorld(lastTime);
                break;
            case "GodStatus":
                this.propGodStatus(lastTime);
        }
    }

    /**
     * @description ��Һ�����֮����ײ
     */
    public void bubbleCrash(Bubble bubble) {
        //����������������������ϱ�ը�����һ�Ҫ�ж�������ͣ�����ʱ��д
//        bubble.setCrash(true);
//        this.setHp(this.getHp() - 1);
    }

    /**
     * @description ��Һ͵�ͼ֮����ײ
     */
    public void mapCrash() {
        //��Ҫȡ���ƶ�
//        if (this.isReverse) {
//            if (this.up || this.down) {
//                this.up = true;
//                this.down = this.up ^ this.down;
//                this.up = this.up ^ this.down;
//            }
//            if (this.left || this.right) {
//                this.left = true;
//                this.right = this.left ^ this.right;
//                this.left = this.left ^ this.right;
//            }
//        }

        if (this.left)
            this.setX(this.getX() + this.speed);
        if (this.up)
            this.setY(this.getY() + this.speed);
        if (this.right)
            this.setX(this.getX() - this.speed);
        if (this.down)
            this.setY(this.getY() - speed);
    }

    /**
     * @param lastTime
     * @description BubbleAdd������������Ŀ
     */
    public void propBubbleAdd(int lastTime) {
        if (playerType == this.getPlayerType()) {
            this.setBubbleTotal(BUBBLETOTAL + 1);
            Player my = this;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    my.setBubbleTotal(BUBBLETOTAL);
                }
            };
            timer.schedule(task, lastTime * 1000);
        }
    }

    /**
     * @param lastTime
     * @description SuperPower����ɫҩˮ���������ݹ�����
     */
    public void propSuperPower(int lastTime) {
        if (playerType == this.getPlayerType()) {
            this.setBubblePower(BUBBLEPOWER + 1);
            Player my = this;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    my.setBubblePower(BUBBLEPOWER);
                }
            };
            timer.schedule(task, lastTime * 1000);

        }
    }

    /**
     * @param lastTime
     * @description Mirror����һ�ú������������ЧӦ������5s
     */
    public void propMirror(int lastTime) {
        if (playerType == this.getPlayerType()) {
            //��������
//            this.setReverse(true);
            this.setSpeed(-this.getSpeed());
            Player my = this;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    my.setSpeed(-my.getSpeed());
                }
            };
            timer.schedule(task, lastTime * 1000);
        }
    }

    public void propRunningShoes(int lastTime) {
        if (playerType == this.getPlayerType()) {
            this.setSpeed(SPEED * 2);
            Player my = this;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    my.setSpeed(SPEED);
                }
            };
            timer.schedule(task, lastTime * 1000);
        }
    }

    public void propTheWorld(int lastTime) {
        if (playerType == this.getPlayerType()) {
            //զ��³��
            //����ʱͣ
            ElementManager em = ElementManager.getManager();
            List<ElementObj> playerList = em.getElementsByKey(GameElement.PLAYER);
            for (ElementObj obj :
                    playerList) {
                Player player = (Player) obj;
                if (player.playerType != this.getPlayerType()) {
                    player.propWhiteAlbum(lastTime);
                }
            }

        }
    }

    public void propCrazyDiamond() {
        if (playerType == this.getPlayerType()) {
            this.setHp(HP + 5);
        }
    }

    public void propWhiteAlbum(int lastTime) {
        if (playerType == this.getPlayerType()) {
            //����Լ�ֹͣ
            this.setStop(true);
            Player my = this;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    my.setStop(false);
                }
            };
            timer.schedule(task, lastTime * 1000);
        }
    }

    public void propGodStatus(int lastTime) {
        if (playerType == this.getPlayerType()) {
            //�޵�
            this.setSuper(true);
            Player my = this;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    my.setSuper(false);
                }
            };
            timer.schedule(task, lastTime * 1000);
        }
    }

    public int getPlayerType() {
        return playerType;
    }

    /**
     * @param type trueΪ+1��falseΪ-1
     */
    public void setBubbleNum(boolean type) {
        if (type) {
            this.bubbleNum += 1;
        } else {
            this.bubbleNum -= 1;
        }

    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public int getImgX() {
        return imgX;
    }

    public void setImgX(int imgX) {
        this.imgX = imgX;
    }

    public int getImgY() {
        return imgY;
    }

    public void setImgY(int imgY) {
        this.imgY = imgY;
    }

    public int getImgTime() {
        return imgTime;
    }

    public void setImgTime(int imgTime) {
        this.imgTime = imgTime;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public boolean isAttackType() {
        return attackType;
    }

    public void setAttackType(boolean attackType) {
        this.attackType = attackType;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBubbleNum() {
        return bubbleNum;
    }

    public int getBubbleTotal() {
        return bubbleTotal;
    }

    public void setBubbleTotal(int bubbleTotal) {
        this.bubbleTotal = bubbleTotal;
    }

    public int getBubblePower() {
        return bubblePower;
    }

    public void setBubblePower(int bubblePower) {
        this.bubblePower = bubblePower;
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean aSuper) {
        isSuper = aSuper;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public boolean isReverse() {
        return isReverse;
    }

    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }
}
