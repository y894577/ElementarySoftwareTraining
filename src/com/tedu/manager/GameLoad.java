package com.tedu.manager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * ¼ÓÔØÆ÷
 */
public class GameLoad {
    public static Map<String, ImageIcon> imgMap;

    static {
        imgMap = new HashMap<>();
        imgMap.put("left", new ImageIcon("image/image/tank/play1/player1_left.png"));
        imgMap.put("right", new ImageIcon("image/image/tank/play1/player1_right.png"));
        imgMap.put("up", new ImageIcon("image/image/tank/play1/player1_up.png"));
        imgMap.put("down", new ImageIcon("image/image/tank/play1/player1_down.png"));
    }

}
