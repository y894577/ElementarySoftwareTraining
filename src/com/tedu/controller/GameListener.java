package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * 监听类 用于监听用户的操作KeyListener
 *
 * @author Magic Gunner
 */
public class GameListener implements KeyListener, MouseListener {
    ElementManager em = new ElementManager().getManager();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("press"+e.getKeyCode());
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for (ElementObj obj :
                play) {
            obj.keyClick(true, e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("release"+e.getKeyCode());
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for (ElementObj obj :
                play) {
            obj.keyClick(false, e.getKeyCode());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
