package com.crazybubble.controller;

import com.crazybubble.element.ElementObj;
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
    ElementManager em = new ElementManager().getManager();

    public GameThread() {

    }

    //游戏的run方法 主线程
    @Override
    public void run() {
        //扩展 可以将true变为一个变量用于控制结束
        while (true) {
            //游戏开始前 读进度条，价值游戏资源（场景资源）
            gameLoad();
            //游戏进行时 游戏过程中
            gameRun();
            //游戏场景结束 游戏资源回收（场景资源）
            gameOver();

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 游戏的加载
     */
    private void gameLoad() {
        //可以变为变量，每一关重新加载
        //加载地图
        GameLoad.MapLoad(1);
        //加载图片
        GameLoad.ImgLoad();
        //加载主角，可以带参数（单机or双人）
        GameLoad.PlayLoad();
        //加载敌人NPC等
//        GameLoad.EnemyLoad();
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
        //预留扩展true可以变为变量，用于控制关卡结束
        while (true) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            List<ElementObj> enemy = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> file = em.getElementsByKey(GameElement.PLAYFILE);
            List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> bubble = em.getElementsByKey(GameElement.BUBBLE);
            //游戏自动化方法
            auto(all, gameTime);

            //碰撞方法
//            crash(enemy, file);
//            crash(file, map);
//            bubbleCrash(bubble,bubble);

            //唯一的时间控制
            gameTime++;
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //一个攻击，一个碰撞
    //遮挡物不可穿越需要写在这里
    //这块是重点


    //碰撞方法
    private void crash(List<ElementObj> ListA, List<ElementObj> ListB) {

        //在这里使用双层循环，做一对一判定，如果为真，就设置两个对象的死亡状态
        for (int i = 0; i < ListA.size(); i++) {
            for (int j = 0; j < ListB.size(); j++) {
                if (ListA.get(i).crash(ListB.get(j))) {
                    System.out.println("碰撞！");
                    //如果是boss，需要扣血机制
                    //将setLive方法变为一个受攻击方法，还可以传入另外一个对象的攻击力
                    //当受攻击方法里执行时，如果血量减为0再设置live为false
                    //作为扩展
                    ListA.get(i).setLive(false);
                    ListB.get(j).setLive(false);
                    break;
                }
            }
        }
    }

    //泡泡之间的碰撞检测
    private void bubbleCrash(List<ElementObj> ListA, List<ElementObj> ListB){
        for (int i = 0; i < ListA.size(); i++) {
            for (int j = 0; j < ListB.size(); j++) {
                if (ListA.get(i).crash(ListB.get(j))) {
                    System.out.println("泡泡碰撞");
                    ListB.get(i).setLive(false);
                    break;
                }
            }
        }
    }



    //游戏元素自动化方法
    public void auto(Map<GameElement, List<ElementObj>> all, int gameTime) {
        GameElement.values();
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
                obj.model(gameTime);
            }
        }
    }

    /**
     * 游戏切换关卡
     */
    private void gameOver() {
    }

}
