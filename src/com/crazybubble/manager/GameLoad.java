package com.crazybubble.manager;

import com.crazybubble.element.ElementObj;
import com.crazybubble.element.MapObj;
import com.crazybubble.manager.ElementManager;
import com.crazybubble.manager.GameElement;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Magic Gunner
 * @说明 加载器（用于读取配置文件的工具）大多提供的是static方法
 */
public class GameLoad {
    private static ElementManager em = ElementManager.getManager();
    //地图字典
    public static Map<String, ImageIcon> imgMap = new HashMap<>();
    //用户读取文件的类
    private static Properties pro = new Properties();
    //元素字典
    private static Map<String, Class<?>> objMap = new HashMap<>();

    /**
     * @param mapID 文件编号
     * @说明 传入地图ID由加载方法依据文件规则自动生成地图文件名称加载文件
     */
    public static void MapLoad(int mapID) {
        String mapName = "com/tedu/text/" + mapID + ".map";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        if (maps == null) {
            System.out.println("error");
            return;
        }
        try {
            pro.load(maps);
            //可以直接动态的获取所有的key，有key就可以获取value
            Enumeration<?> names = pro.propertyNames();
            while (names.hasMoreElements()) {
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

    /**
     * 扩展：使用配置文件，来实例化对象，通过固定的key
     */
    public static void ObjLoad() {
        String texturl = "com/tedu/text/obj.pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        //imgMap用于存放数据
        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for (Object o :
                    set) {
                String classUrl = pro.getProperty(o.toString());
                Class<?> forName = Class.forName(classUrl);

                objMap.put(o.toString(), forName);

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void PlayLoad() {
        //将配置文件加载进map
        ObjLoad();
        //应该可以从配置文件里读取string
        String playStr = "100,100,up";

        ElementObj obj = getObj("play");
        ElementObj play = obj.createElement(playStr);

//        Class<?> class1 = objMap.get("play");
//        ElementObj obj = null;
//        try {
//            //这个对象就和new Play()等价
//            Object newInstance = class1.newInstance();
//            if (newInstance instanceof ElementObj) {
//                obj = (ElementObj) newInstance;
//            }
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        ElementObj play = obj.createElement(playStr);
        //解耦，降低代码和代码之间的耦合度，可以直接通过接口或抽象父类就可以获取到实体对象
        em.addElement(play, GameElement.PLAY);
    }

    public static ElementObj getObj(String str) {
        try {
            Class<?> class1 = objMap.get(str);
            //这个对象就和new Play()等价
            Object newInstance = class1.newInstance();
            if (newInstance instanceof ElementObj) {
                return (ElementObj) newInstance;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}

