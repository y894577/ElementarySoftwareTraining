package com.crazybubble.controller;

import com.crazybubble.element.ElementObj;
import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 监听类 用于监听用户的操作KeyListener
 *
 * @author Magic Gunner
 */
public class GameListener implements KeyListener {
    ElementManager em = ElementManager.getManager();
    Set<Integer> set = new HashSet<Integer>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //这里需要考虑双人游戏，因此要多传一个int PlayerType
    //PlayerType = 0 代表玩家A，PlayerType = 1代表玩家B，以此类推
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("press" + e.getKeyCode());
        int key = e.getKeyCode();
        //判定集合中是否已经存在，包含这个对象
        if (set.contains(key)) {
            //如果包含直接结束方法
            return;
        }
        set.add(key);
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAYER);
        for (ElementObj obj :
                play) {
            //这里需要将PlayerType传入keyClick里
            obj.keyClick(true, e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("release" + e.getKeyCode());
        int key = e.getKeyCode();
        //判定集合中是否已经存在，包含这个对象
        if (!set.contains(key)) {
            return;
        }
        //移除数据
        set.remove(e.getKeyCode());
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAYER);
        for (ElementObj obj :
                play) {
            obj.keyClick(false, e.getKeyCode());
        }
    }

}
