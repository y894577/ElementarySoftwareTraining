package com.crazybubble.manager;

import com.crazybubble.element.ElementObj;
import com.crazybubble.element.Player;
import com.crazybubble.manager.GameElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Magic Gunner
 * @说明 本类是元素管理器，专门存储所有元素，同时提供方法
 * 给予视图和控制获取数据
 *
 */
public class ElementManager {


    private static ElementManager EM = null;//引用
    private Map<GameElement, List<ElementObj>> gameElements;
    private Map<String, Integer> prioriMap;//初始化地图图层顺序

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


    public static synchronized ElementManager getManager() {
        //synchronized线程锁保证本方法执行中只有一个线程
        if (EM == null) {
            EM = new ElementManager();
        }
        return EM;
    }

    public ElementManager() {//构造方法私有化
        init();//实例化方法
//        initPriorityMap();//图层优先级设置
    }
    
    //图层优先级比较方法
    public Comparator<String> getMapPrioComparator(){
		return new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				int p1 = prioriMap.get(o1);
				int p2 = prioriMap.get(o2);
				if(p1 > p2) {
					return 1;
				}else if(p1<p2) {
					return -1;
				}else {
					return 0;
				}
			}
			
		};
    	
    }


    public void init() {//实例化
        gameElements = new HashMap<GameElement, List<ElementObj>>();
        //将每种元素集合都放入到map中
        for (GameElement ge :
                GameElement.values()) {
            gameElements.put(ge, new ArrayList<ElementObj>());
        }
        //道具，子弹，爆炸效果，死亡效果etc
    }
}
