package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
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
        load();
    }

    /**
     * 游戏进行时
     *
     * @任务说明 游戏过程中需要做的事情：
     * 1.自动化玩家的移动，碰撞，死亡
     * 2.新元素的增加（NPC死亡后出现道具）
     */
    private void gameRun(){
        //预留扩展true可以变为变量，用于控制关卡结束
        while (true) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            GameElement.values();//默认方法 返回值是一个数组，数组的顺序就是枚举顺序
            for (GameElement ge :
                    GameElement.values()) {
                List<ElementObj> list = all.get(ge);
                for (int i = 0; i < list.size(); i++) {
                    ElementObj obj = list.get(i);
                    //调用每个类自己的show方法完成自己的显示
                    obj.model();
                }
            }

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 游戏切换关卡
     */
    private void gameOver() {
    }

    private void load() {
        ImageIcon icon = new ImageIcon("image/image/tank/play1/player1_up.png");
        //实例化对象
        ElementObj obj = new Play(100, 100, 30, 30, icon);
        //将对象放入到元素管理器中
        em.addElement(obj, GameElement.PLAY);

        //添加一个敌人类，仿照Play玩家类编写，不需要实现键盘监听
        //实现敌人的显示，同时实现最简单的移动，例如：坐标100,100移动到500,100然后掉头
    }
}
