package com.crazybubble.manager;

import com.crazybubble.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Magic Gunner
 * @说明 本类是元素管理器，专门存储所有元素，同时提供方法
 * 给予视图和控制获取数据
 */
public class ElementManager {


    private static ElementManager EM = null;//引用
    private Map<GameElement, List<ElementObj>> gameElements;

    //添加元素
    public void addElement(ElementObj obj, GameElement ge) {
        //添加对象到集合中，按key值进行存储
        gameElements.get(ge).add(obj);
    }

    //获取所有元素
    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    //依据key返回list集合，取出某一类元素
    public List<ElementObj> getElementsByKey(GameElement ge) {
        return gameElements.get(ge);
    }

    //synchronized线程锁保证本方法执行中只有一个线程
    public static synchronized ElementManager getManager() {

        if (EM == null) {
            EM = new ElementManager();
        }
        return EM;
    }


    public ElementManager() {
        init();
    }


    public void init() {
        gameElements = new HashMap<>();
        for (GameElement ge :
                GameElement.values()) {
            gameElements.put(ge, new ArrayList<ElementObj>());
        }
    }
}
