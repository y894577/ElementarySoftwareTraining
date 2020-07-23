package com.crazybubble.controller;

import com.crazybubble.element.*;
import com.crazybubble.game.GameStart;
import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;
import com.crazybubble.manager.GameLoad;

import java.util.List;
import java.util.Map;

/**
 * @author Magic Gunner
 * @说明 游戏的主线程，用于控制游戏加载，游戏关卡，
 * 游戏运行时自动化，游戏判定，游戏地图切换，资源释放和重新读取
 */
public class GameThread extends Thread {
    ElementManager em = ElementManager.getManager();

    //关卡数
    public static int level = 1;
    //控制重置地图时间，防止刷新过快
    public static boolean change = false;

    //判断游戏是否结束
    public boolean isOver = false;

    public GameThread() {

    }

    /**
     * @description 游戏的run方法 主线程
     */
    @Override
    public void run() {
        gameLoad();

        while (!isOver) {
            //游戏进行时 游戏过程中
            gameRun();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 游戏的加载
     */
    private void gameLoad() {

        //由于时间原因只做了两个关卡，因此需要重置关卡数
        if (level > 2)
            level = 1;

        //加载元素
        GameLoad.ObjLoad();
        //加载图片
        GameLoad.ImgLoad();
        //加载地图
        GameLoad.MapLoad(level);
        //加载主角
        GameLoad.PlayLoad();
    }

    /**
     * @description 游戏进行时
     */
    private void gameRun() {
        int gameTime = 0;

        while (!isOver) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> bubble = em.getElementsByKey(GameElement.BUBBLE);
            List<ElementObj> prop = em.getElementsByKey(GameElement.PROP);
            List<ElementObj> player = em.getElementsByKey(GameElement.PLAYER);
            List<ElementObj> explode = em.getElementsByKey(GameElement.EXPLODE);
            //游戏自动化方法
            auto(all, gameTime);

            crash(player, prop);

            crash(map, bubble);

            crash(player, explode);

            crash(player, map);

            crash(explode, map);

            propFlash(prop, map);

            //防止manager没加载完就刷新
            if (!change) {
                if (player.size() == 1) {
                    //如果player只剩一个，则该玩家获胜
                    isOver = true;
                    int playerType = ((Player) (player.get(0))).getPlayerType();
                    GameStart.over(playerType);
                } else if (player.size() == 0) {
                    //平局
                    System.out.println("平局");
                    GameStart.over(2);
                    isOver = true;
                }
            }

            //唯一的时间控制
            gameTime++;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param ListA
     * @param ListB
     * @description 碰撞方法
     */
    private void crash(List<ElementObj> ListA, List<ElementObj> ListB) {
        for (int i = 0; i < ListA.size(); i++) {
            for (int j = 0; j < ListB.size(); j++) {
                if (ListA.get(i).crash(ListB.get(j))) {
                    try {
                        //调用每个类各自的碰撞方法
                        ListA.get(i).crashMethod(ListB.get(j));
                        ListB.get(j).crashMethod(ListA.get(i));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    /**
     * @param prop
     * @param map
     * @description 为了防止道具被获取
     */
    private void propFlash(List<ElementObj> prop, List<ElementObj> map) {
        for (int i = 0; i < prop.size(); i++) {
            boolean record = true;
            for (int j = 0; j < map.size(); j++) {
                if (prop.get(i).crash(map.get(j))) {
                    //道具附近有地图，不开启get
                    record = false;
                }
            }
            ((Prop) prop.get(i)).setCanGet(record);
        }
    }


    /**
     * @description 游戏元素自动化方法
     */
    public void auto(Map<GameElement, List<ElementObj>> all, int gameTime) {
        for (GameElement ge :
                GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = list.size() - 1; i >= 0; i--) {
                ElementObj obj = list.get(i);
                //判断死亡状态
                if (!obj.isLive()) {
                    //启动一个死亡方法
                    obj.destroy();
                    list.remove(i);
                    continue;
                }
                //调用每个类自己的show方法完成自己的显示
                obj.model(gameTime, obj);
            }
        }
    }


    /**
     * @description 游戏结束，释放资源
     */
    public void gameOver() {
        this.isOver = true;
        System.out.println("game over!");
        em.init();
        GameLoad.Refresh();
    }

}
