package com.crazybubble.manager;

import com.crazybubble.element.ElementObj;
import com.crazybubble.element.MapObj;

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
    //图片字典
    public static Map<String, ImageIcon> imgMap = new HashMap<>();
    //用户读取文件的类
    private static Properties pro = new Properties();
    //元素字典
    private static Map<String, Class<?>> objMap = new HashMap<>();
    //地图字典
    public static Object[][] mapMap = new Object[100][100];

    /**
     * @param mapID 文件编号
     * @说明 传入地图ID由加载方法依据文件规则自动生成地图文件名称加载文件
     */
    public static void MapLoad(int mapID) {
        String mapName = "com/crazybubble/resource/" + mapID + ".map";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        if (maps == null) {
            System.out.println("地图资源加载失败！");
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
                for (int i = 1; i < arrs.length; i++) {
                    String[] split = arrs[i].split(",");
                    int x = Integer.parseInt(split[0]) / 10;
                    int y = Integer.parseInt(split[1]) / 10;
                    ElementObj element = new MapObj().createElement(key + "," + arrs[i]);
                    em.addElement(element, GameElement.MAPS);
                    mapMap[x][y] = element;
                }
            }
//for test
//            for (int i = 0; i < 40; i++) {
//                for (int j = 0; j < 60; j++) {
//                    if (mapMap[i][j] == null)
//                        System.out.print("   ");
//                    else
//                            System.out.print(((MapObj) mapMap[i][j]).getX() + " ");
//                }
//                System.out.println("===================================");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @说明 加载图片代码
     * 可以带参数，因为不同的类可能有不一样的图片资源
     */
    public static void ImgLoad() {
        String texturl = "com/crazybubble/resource/GameData.pro";
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
        String texturl = "com/crazybubble/resource/obj.pro";
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
        String playStr1 = "x:150,y:150,w:30,h:30,type:0";
        String playStr2 = "x:180,y:180,w:30,h:30,type:1";

        ElementObj obj = getObj("player");
        ElementObj play = obj.createElement(playStr1);
        ElementObj obj2 = getObj("player");
        ElementObj play2 = obj2.createElement(playStr2);

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
        em.addElement(play, GameElement.PLAYER);
        em.addElement(play2, GameElement.PLAYER);
    }

    public static void PropLoad() {
        ObjLoad();

        String str = "x:100,y:100,w:30,h:30,type:superpower,time:10";
        ElementObj obj = getObj("prop");
        ElementObj prop1 = obj.createElement(str);
        em.addElement(prop1, GameElement.PROP);
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

