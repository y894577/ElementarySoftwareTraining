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

    public static int level = 1;

    //判断游戏是否结束
    private static boolean isOver = false;

    public GameThread() {

    }

    //游戏的run方法 主线程
    @Override
    public void run() {
        //游戏开始前 读进度条，价值游戏资源（场景资源）
        gameLoad();

        while (!isOver) {
            //游戏进行时 游戏过程中
            gameRun();
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //游戏场景结束 游戏资源回收（场景资源）
            gameOver();
        }
    }

    /**
     * 游戏的加载
     */
    private void gameLoad() {

        //可以变为变量，每一关重新加载
        GameLoad.ObjLoad();
        //加载图片
        GameLoad.ImgLoad();
        //加载地图
        GameLoad.MapLoad(level);
        //加载主角，可以带参数（单机or双人）
        GameLoad.PlayLoad();
        //全部加载完成，游戏启动
    }

    /**
     * 游戏进行时
     *
     * @任务说明 游戏过程中需要做的事情：
     * 1.自动化玩家的移动，碰撞，死亡
     * 2.新元素的增加（NPC死亡后出现道具）
     */
    private void gameRun() {
        int gameTime = 0;

        while (!isOver) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            List<ElementObj> enemy = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> file = em.getElementsByKey(GameElement.PLAYFILE);
            List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> bubble = em.getElementsByKey(GameElement.BUBBLE);
            List<ElementObj> prop = em.getElementsByKey(GameElement.PROP);
            List<ElementObj> player = em.getElementsByKey(GameElement.PLAYER);
            List<ElementObj> explode = em.getElementsByKey(GameElement.EXPLODE);
            //游戏自动化方法
            auto(all, gameTime);

            //刷新地图
//            for (int i = 0; i < 100; i++) {
//                for (int j = 0; j < 100; j++) {
//                    if (GameLoad.mapMap[i][j] != null) {
//                        System.out.println("ok");
//                        ((MapObj)GameLoad.mapMap[i][j]).model(gameTime);
//                    }
//                }
//            }

            //碰撞方法
//            crash(enemy, file);
//            crash(file, map);

            crash(player, prop);

            crash(map, bubble);

            crash(player, explode);

            crash(player, map);

            crash(explode, map);

            propFlash(prop,map);

            if (level <= 3) {
                if (player.size() == 1) {
                    //如果player只剩一个，则该玩家获胜
                    System.out.println(((Player) (player.get(0))).getPlayerType() + "win");
                    isOver = true;
                    GameStart.over();
                    level++;
                } else if (player.size() == 0) {
                    //平局
                    System.out.println("平局");
                    isOver = true;
                    level++;
                }
            }


            //唯一的时间控制
            gameTime++;

            try {
                sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //碰撞方法
    private void crash(List<ElementObj> ListA, List<ElementObj> ListB) {
        for (int i = 0; i < ListA.size(); i++) {
            for (int j = 0; j < ListB.size(); j++) {
                if (ListA.get(i).crash(ListB.get(j))) {
                    try {
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

    //为了防止道具被获取
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


    //游戏元素自动化方法
    public void auto(Map<GameElement, List<ElementObj>> all, int gameTime) {
        //默认方法 返回值是一个数组，数组的顺序就是枚举顺序
        for (GameElement ge :
                GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            //编写这样直接操作集合数据的代码建议不要使用迭代器
            for (int i = list.size() - 1; i >= 0; i--) {
                ElementObj obj = list.get(i);
                //判断死亡状态
                if (!obj.isLive()) {
                    //启动一个死亡方法（方法中可以：死亡动画，装备掉落等）
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
     * 游戏切换关卡
     */
    private void gameOver() {
        System.out.println("game over!");
    }

}
