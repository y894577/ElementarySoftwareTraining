package com.crazybubble.manager;

import com.crazybubble.element.*;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Magic Gunner
 * @说明 加载器（用于读取配置文件的工具）大多提供的是static方法
 */
public class GameLoad {

    private static ElementManager em = ElementManager.getManager();
    //配置字典
    public static Map<String, String> configMap = new HashMap<>();
    //图片字典
    public static Map<String, ImageIcon> imgMap = new HashMap<>();
    //用户读取文件的类
    private static Properties pro = new Properties();
    //元素字典
    private static Map<String, Class<?>> objMap = new HashMap<>();
    //地图字典
    public static Object[][] mapMap = new Object[100][100];
    //道具字典
    public static Map<String, String> propMap = new HashMap<>();
    //地图初始化字典
    public static Map<String, String> mapInitMap = new HashMap<>();

    private static Map<String, List<String>> gameInfoMap = new HashMap<>();//游戏信息字典

    /**
     * @description 读取配置文件
     */
    public static void ConfigLoad() {

        String filename = "com/crazybubble/resource/GameConfig.pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream config = classLoader.getResourceAsStream(filename);
        if (config == null) {
            System.out.println("配置文件加载失败");
            return;
        }
        pro.clear();
        try {
            pro.load(config);
            Set<Object> set = pro.keySet();
            for (Object o :
                    set) {
                String url = pro.getProperty(o.toString());
                configMap.put(o.toString(), url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param mapID 文件编号
     * @description 传入地图ID由加载方法依据文件规则自动生成地图文件名称加载文件
     */
    public static void MapLoad(int mapID) {
        //地图元素初始化
        String mapInit = configMap.get("MapConfig");
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream mapConfig = classLoader.getResourceAsStream(mapInit);
        if (mapConfig == null) {
            System.out.println("地图资源加载失败！");
            return;
        }
        pro.clear();
        try {
            pro.load(mapConfig);
            for (Object o : pro.keySet()) {
                String key = o.toString();
                mapInitMap.put(key, pro.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        pro.clear();
        //道具元素初始化
        PropInitLoad();

        //地图初始化
        String mapName = configMap.get("Map" + mapID);
        classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        if (maps == null) {
            System.out.println("地图资源加载失败！");
            return;
        }
        pro.clear();
        try {
            pro.load(maps);
            Enumeration<?> names = pro.propertyNames();
            while (names.hasMoreElements()) {
                String key = names.nextElement().toString();
                if (key.contains("prop")) {
                    //如果是道具元素
                    PropLoad(pro.getProperty(key), key.substring("prop".length()));
                } else {
                    //如果是地图元素
                    String[] arr = pro.getProperty(key).split(";");
                    for (int i = 0; i < arr.length; i++) {
                        String[] split = arr[i].split(",");
                        int x = Integer.parseInt(split[0]);
                        int y = Integer.parseInt(split[1]);
                        ElementObj element = new MapObj().createElement(key + "," + arr[i]);
                        em.addElement(element, GameElement.MAPS);
                        mapMap[x][y] = element;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void BubbleBoomPro() throws IOException {
        InputStream inputStream =
                GameLoad.class.getClassLoader().getResourceAsStream(gameInfoMap.get("bubblePath").get(0));
        pro.clear();
        pro.load(inputStream);
        for (Object o : pro.keySet()) {
            String info = pro.getProperty(o.toString());
            gameInfoMap.put(o.toString(), infoStringToList(info, ","));
            //此时放入Map的value是已经被，分割后的配置内容
        }
    }

    private List<String> infoStringToList(String info, String splitString) {
        return Arrays.asList(info.split(splitString));
    }


    /**
     * @说明 加载图片代码
     * 可以带参数，因为不同的类可能有不一样的图片资源
     */
    public static void ImgLoad() {
        String filename = configMap.get("imagePath");
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream img = classLoader.getResourceAsStream(filename);
        //imgMap用于存放数据
        pro.clear();
        try {
            pro.load(img);
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
        String objUrl = configMap.get("objPath");
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream obj = classLoader.getResourceAsStream(objUrl);
        pro.clear();
        try {
            pro.load(obj);
            Set<Object> set = pro.keySet();
            for (Object o :
                    set) {
                String classUrl = pro.getProperty(o.toString());
                Class<?> forName = Class.forName(classUrl);
                objMap.put(o.toString(), forName);

                //通过反射定义静态变量
                String arr2[] = configMap.get(o.toString() + "ImgConfig").split(",");
                forName.getDeclaredField("sx1").set(null, Integer.parseInt(arr2[0]));
                forName.getDeclaredField("sy1").set(null, Integer.parseInt(arr2[1]));
                forName.getDeclaredField("sx2").set(null, Integer.parseInt(arr2[2]));
                forName.getDeclaredField("sy2").set(null, Integer.parseInt(arr2[3]));
                forName.getDeclaredField("pixel").set(null, Integer.parseInt(arr2[4]));

            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void PlayLoad() {
        String playerUrl = configMap.get("playerPath");
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream play = classLoader.getResourceAsStream(playerUrl);

        String arr[] = configMap.get("playerConfig").split(",");
        for (String str :
                arr) {
            String[] split = str.split(":");
            switch (split[0]) {
                case "hp":
                    Player.HP = Integer.parseInt(split[1]);
                    break;
                case "speed":
                    Player.SPEED = Integer.parseInt(split[1]);
                    break;
                case "bubbleTotal":
                    Player.BUBBLETOTAL = Integer.parseInt(split[1]);
                    break;
                case "bubblePower":
                    Player.BUBBLEPOWER = Integer.parseInt(split[1]);
                    break;
            }
        }

        pro.clear();
        try {
            pro.load(play);
            Set<Object> set = pro.keySet();
            for (Object o :
                    set) {
                if (!o.toString().equals("config")) {
                    String playerConfig = pro.getProperty(o.toString());
                    ElementObj player = getObj("player");
                    player.createElement(playerConfig);
                    em.addElement(player, GameElement.PLAYER);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void PropLoad(String init, String type) {
        //元素道具初始化
        int time = 0;
        String arr[] = propMap.get(type).split(",");
        for (String str :
                arr) {
            String split[] = str.split(":");
            if (split[0].equals("time")) {
                time = Integer.parseInt(split[1]);
            }
        }
        String arr2[] = init.split(";");
        for (String str :
                arr2) {
            String split[] = str.split(",");
            String string = "x:" + split[0] + ",y:" + split[1] + ",w:30,h:30,type:" + type + ",time:" + time;
            ElementObj prop = getObj("prop");
            prop.createElement(string);
            em.addElement(prop, GameElement.PROP);
        }

    }

    public static void PropInitLoad() {
        if (propMap.size() == 0) {
            Properties pro = new Properties();
            String url = configMap.get("PropConfig");
            ClassLoader classLoader = GameLoad.class.getClassLoader();
            InputStream propConfig = classLoader.getResourceAsStream(url);
            pro.clear();
            try {
                pro.load(propConfig);
                for (Object o :
                        pro.keySet()) {
                    String key = o.toString();
                    propMap.put(key, pro.getProperty(key));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static ElementObj getObj(String str) {
        try {
            Class<?> class1 = objMap.get(str);
            Object newInstance = class1.newInstance();
            if (newInstance instanceof ElementObj) {
                return (ElementObj) newInstance;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}

