package com.tedu.manager;

import com.tedu.element.ElementObj;
import com.tedu.element.MapObj;
import com.tedu.element.Play;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Magic Gunner
 * @说明 加载器（用于读取配置文件的工具）大多提供的是static方法
 */
public class GameLoad {
    private static ElementManager em = ElementManager.getManager();

    public static Map<String, ImageIcon> imgMap = new HashMap<>();

//    static {
//        imgMap = new HashMap<>();
//        imgMap.put("left", new ImageIcon("image/image/tank/play1/player1_left.png"));
//        imgMap.put("right", new ImageIcon("image/image/tank/play1/player1_right.png"));
//        imgMap.put("up", new ImageIcon("image/image/tank/play1/player1_up.png"));
//        imgMap.put("down", new ImageIcon("image/image/tank/play1/player1_down.png"));
//    }

    //用户读取文件的类
    private static Properties pro = new Properties();

    /**
     * @param mapID 文件编号
     * @说明 传入地图ID由加载方法依据文件规则自动生成地图文件名称加载文件
     */
    public static void MapLoad(int mapID) {
        //得到了我们的文件路径
        String mapName = "com/tedu/text/" + mapID + ".map";
        //使用IO流来获取文件对象 得到类加载器
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
//        System.out.println(maps);

        if (maps == null) {
            System.out.println("error");
            return;
        }
        try {
            //以后用的是xml和json
            pro.load(maps);
            //可以直接动态的获取所有的key，有key就可以获取value
            Enumeration<?> names = pro.propertyNames();
            while (names.hasMoreElements()) {
                //这样的迭代有一个问题：一次迭代一个元素
                String key = names.nextElement().toString();
                pro.getProperty(key);
                String[] arrs = pro.getProperty(key).split(";");
                for (int i = 0; i < arrs.length; i++) {
                    ElementObj element = new MapObj().createElement(key + "," + arrs[i]);
                    em.addElement(element, GameElement.MAPS);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @说明 加载图片代码
     * 可以带参数，因为不同的官可能乣不一样的图片资源
     */
    public static void ImgLoad() {
        String texturl = "com/tedu/text/GameData.pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        //imgMap用于存放数据
        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for (Object o :
                    set) {
                String url = pro.getProperty(o.toString());
                imgMap.put(o.toString(), new ImageIcon((url)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void PlayLoad() {
        //应该可以从配置文件里读取string
        String playStr = "100,100,up";
        ElementObj play = new Play().createElement(playStr);
        em.addElement(play,GameElement.PLAY);
    }
}

