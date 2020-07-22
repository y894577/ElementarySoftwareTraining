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
 * @description 玩家类
 */
public class Player extends ElementObj {
    //玩家类型：0代表玩家A，1代表玩家B
    private int playerType;

    //键盘监听
    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;

    //切割图片坐标
    private int imgX = 0;
    private int imgY = 0;

    //图片偏移量
    public static int sx1;
    public static int sy1;
    public static int sx2;
    public static int sy2;
    public static int pixel;


    //控制图片刷新时间
    private int imgTime = 0;

    //人物方向
    private String fx = "";
    //攻击状态
    private boolean attackType = false;
    //血量
    private int hp;
    //移动速度
    private int speed;
    //玩家已释放泡泡数量
    private int bubbleNum = 0;
    //可释放泡泡总数
    private int bubbleTotal;
    //泡泡威力
    private int bubblePower;
    //无敌状态
    private boolean isSuper = false;
    //暂停状态
    private boolean isStop = false;
    //跑动状态
    private boolean isRun = false;
    //反向状态
    private boolean isReverse = false;
    //防止同步更新图片
    private int isRunPlayer;
    //键盘按下状态
    private boolean keyLeftType;
    private boolean keyRightType;
    private boolean keyUpType;
    private boolean keyDownType;

    //静态变量，从配置文件读取
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
     * @description 将当前对象的信息转换为字符串
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
     * @description 跑动时更新图片
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
     * @description 模板方法，封装所有操作
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
     * @param bindType 点击的类型 true代表按下 false代表松开
     * @param key      代表触发键盘的code值
     * @description 键盘监听
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
                    //开启攻击状态
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
                    //开启攻击状态
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
                    //关闭攻击状态
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
                    //关闭攻击状态
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
        //玩家之间碰撞
        if (Player.class.equals(obj.getClass())) {
            //需要取消移动
//            this.mapCrash();
        }
        //玩家和道具之间碰撞
        else if (Prop.class.equals(obj.getClass())) {
            Prop prop = (Prop) obj;
            this.propCrash(prop.getPropType(), prop.getLastTime());
        }
        //玩家和泡泡之间碰撞
        else if (Bubble.class.equals(obj.getClass())) {
            Bubble bubble = (Bubble) obj;
            this.bubbleCrash(bubble);
        }
        //玩家和地图之间碰撞
        else if (MapObj.class.equals(obj.getClass())) {
            this.attackType = false;
            this.mapCrash();
        }
    }


    /**
     * @description 玩家和道具之间碰撞
     */
    public void propCrash(String propType, int lastTime) {
        //这块地方数值也可以用配置文件调用，暂时先写成定值
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
     * @description 玩家和泡泡之间碰撞
     */
    public void bubbleCrash(Bubble bubble) {
        //泡泡堂里好像碰到不会马上爆炸，而且还要判断玩家类型，先暂时不写
//        bubble.setCrash(true);
//        this.setHp(this.getHp() - 1);
    }

    /**
     * @description 玩家和地图之间碰撞
     */
    public void mapCrash() {
        //需要取消移动
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
     * @description BubbleAdd：增加泡泡数目
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
     * @description SuperPower：蓝色药水，增加泡泡攻击力
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
     * @description Mirror：玩家获得后产生反向行走效应，持续5s
     */
    public void propMirror(int lastTime) {
        if (playerType == this.getPlayerType()) {
            //反向行走
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
            //咋瓦鲁多
            //对手时停
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
            //玩家自己停止
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
            //无敌
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
     * @param type true为+1，false为-1
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
