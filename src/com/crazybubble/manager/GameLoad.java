package com.crazybubble.manager;

import com.crazybubble.element.*;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Magic Gunner
 * @˵�� �����������ڶ�ȡ�����ļ��Ĺ��ߣ�����ṩ����static����
 */
public class GameLoad {

    private static ElementManager em = ElementManager.getManager();
    //�����ֵ�
    public static Map<String, String> configMap = new HashMap<>();
    //ͼƬ�ֵ�
    public static Map<String, ImageIcon> imgMap = new HashMap<>();
    //�û���ȡ�ļ�����
    private static Properties pro = new Properties();
    //Ԫ���ֵ�
    private static Map<String, Class<?>> objMap = new HashMap<>();
    //��ͼ�ֵ�
    public static Object[][] mapMap = new Object[100][100];
    //�����ֵ�
    public static Map<String, String> propMap = new HashMap<>();
    //��ͼ��ʼ���ֵ�
    public static Map<String, String> mapInitMap = new HashMap<>();


    /**
     * @description ��ȡ�����ļ�
     */
    public static void ConfigLoad() {

        String filename = "com/crazybubble/resource/GameConfig.pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream config = classLoader.getResourceAsStream(filename);
        if (config == null) {
            System.out.println("�����ļ�����ʧ��");
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
     * @param mapID �ļ����
     * @description �����ͼID�ɼ��ط��������ļ������Զ����ɵ�ͼ�ļ����Ƽ����ļ�
     */
    public static void MapLoad(int mapID) {
        //��ͼԪ�س�ʼ��
        String mapInit = configMap.get("MapConfig");
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream mapConfig = classLoader.getResourceAsStream(mapInit);
        if (mapConfig == null) {
            System.out.println("��ͼ��Դ����ʧ�ܣ�");
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
        //����Ԫ�س�ʼ��
        PropInitLoad();

        //��ͼ��ʼ��
        String mapName = configMap.get("Map" + mapID);
        classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        if (maps == null) {
            System.out.println("��ͼ��Դ����ʧ�ܣ�");
            return;
        }
        pro.clear();
        try {
            pro.load(maps);
            Enumeration<?> names = pro.propertyNames();
            while (names.hasMoreElements()) {
                String key = names.nextElement().toString();
                if (key.contains("prop")) {
                    //����ǵ���Ԫ��
                    PropLoad(pro.getProperty(key), key.substring("prop".length()));
                } else {
                    //����ǵ�ͼԪ��
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


    /**
     * @˵�� ����ͼƬ����
     * ���Դ���������Ϊ��ͬ��������в�һ����ͼƬ��Դ
     */
    public static void ImgLoad() {
        String filename = configMap.get("imagePath");
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream img = classLoader.getResourceAsStream(filename);
        //imgMap���ڴ������
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
     * ��չ��ʹ�������ļ�����ʵ��������ͨ���̶���key
     */
    public static void ObjLoad() {
        ConfigLoad();
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

                //ͨ�����䶨�徲̬����
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
        //Ԫ�ص��߳�ʼ��
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
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            String string = "x:" + x + ",y:" + y + ",type:" + type + ",time:" + time;
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

    public static void Refresh() {
        //�����ֵ�
        configMap = new HashMap<>();
        //ͼƬ�ֵ�
        imgMap = new HashMap<>();
        //�û���ȡ�ļ�����
        pro = new Properties();
        //Ԫ���ֵ�
        objMap = new HashMap<>();
        //��ͼ�ֵ�
        mapMap = new Object[100][100];
        //�����ֵ�
        propMap = new HashMap<>();
        //��ͼ��ʼ���ֵ�
        mapInitMap = new HashMap<>();
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

