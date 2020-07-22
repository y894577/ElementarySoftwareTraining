package com.crazybubble.controller;

import com.crazybubble.element.ElementObj;
import com.crazybubble.element.Player;
import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ������ ���ڼ����û��Ĳ���KeyListener
 *
 * @author Magic Gunner
 */
public class GameListener implements KeyListener {
    ElementManager em = ElementManager.getManager();
    Set<Integer> set = new HashSet<Integer>();

    int[] player1 = {37, 38, 39, 40, 10};
    int[] player2 = {65, 87, 68, 83, 32};

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //������Ҫ����˫����Ϸ�����Ҫ�ഫһ��int PlayerType
    //PlayerType = 0 �������A��PlayerType = 1�������B���Դ�����
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("press" + e.getKeyCode());
        int key = e.getKeyCode();
        //�ж��������Ƿ��Ѿ����ڣ������������
        if (set.contains(key)) {
            //�������ֱ�ӽ�������
            return;
        }
        set.add(key);
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAYER);
        for (ElementObj obj :
                play) {
            //������Ҫ��PlayerType����keyClick��
            if (obj instanceof Player) {
                if (e.getKeyCode() == 37 || e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 10)
                    obj.keyClick(true, e.getKeyCode(), "0");
                else
                    obj.keyClick(true, e.getKeyCode(), "1");
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("release" + e.getKeyCode());
        int key = e.getKeyCode();
        //�ж��������Ƿ��Ѿ����ڣ������������
        if (!set.contains(key)) {
            return;
        }
        //�Ƴ�����
        set.remove(e.getKeyCode());
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAYER);
        for (ElementObj obj :
                play) {
            if (obj instanceof Player) {
                if (e.getKeyCode() == 37 || e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 10)
                    obj.keyClick(false, e.getKeyCode(), "0");
                else
                    obj.keyClick(false, e.getKeyCode(), "1");
            }
        }
    }

}
