package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

/**
 * @author Magic Gunner
 * @说明 本类是元素管理器，专门存储所有元素，同时提供方噶
 * 给予视图和控制获取数据
 * @问题一：存储所有元素数据，怎么存放？list map set 3大集合
 * @问题二：管理器是视图和控制要访问，管理器必须只有一个——单例模式
 */
public class ElementManager {

    /*
     * String 作为key匹配所有的元素 play-> List<Object>
     * 						  enemy->List<Object>
     * 枚举类型 当做map的key 用来区分不一样的资源，用于获取资源
     * List中元素的泛型应该是元素基类
     * 所有元素都可以存放到map集合中
     */
    private Map<GameElement, List<ElementObj>> gameElements;

    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    //添加元素
    public void addElement(ElementObj obj, GameElement ge) {
        //添加对象到集合中，按key值进行存储
        gameElements.get(ge).add(obj);
    }

    //依据key返回list集合，取出某一类元素
    public List<ElementObj> getElementsByKey(GameElement ge) {
        return gameElements.get(ge);
    }

    /**
     * 单例模式：内存中有且只有一个实例
     * 编写方式：
     * 1. 需要一个静态属性（定义一个常量）单例的引用
     * 2. 提供一个静态方法（返回这个实例）return 单例的引用
     * 3. 一般为防止其他人自己使用，私有化构造方法
     * ElementManager em=new ElementManager();
     */
    private static ElementManager EM = null;//引用

    public static synchronized ElementManager getManager() {
        //synchronized线程锁保证本方法执行中只有一个线程
        if (EM == null) {//空值判定
            EM = new ElementManager();
        }
        return EM;
    }

    public ElementManager() {//构造方法私有化
        init();//实例化方法
    }

    /**
     * 本方法是为将来可能出现的功能扩展，重写init方法准备的。
     */
    public void init() {//实例化
        gameElements = new HashMap<GameElement, List<ElementObj>>();
        //将每种元素集合都放入到map中
//        gameElements.put(GameElement.PLAY, new ArrayList<ElementObj>());
//        gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
//        gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
//        gameElements.put(GameElement.BOSS, new ArrayList<ElementObj>());
        for (GameElement ge :
                GameElement.values()) {
            gameElements.put(ge, new ArrayList<ElementObj>());
        }
        //道具，子弹，爆炸效果，死亡效果etc
    }
}
