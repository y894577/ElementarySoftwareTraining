package com.crazybubble.manager;

import com.crazybubble.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Magic Gunner
 * @˵�� ������Ԫ�ع�������ר�Ŵ洢����Ԫ�أ�ͬʱ�ṩ����
 * ������ͼ�Ϳ��ƻ�ȡ����
 */
public class ElementManager {


    private static ElementManager EM = null;//����
    private Map<GameElement, List<ElementObj>> gameElements;

    //���Ԫ��
    public void addElement(ElementObj obj, GameElement ge) {
        //��Ӷ��󵽼����У���keyֵ���д洢
        gameElements.get(ge).add(obj);
    }

    //��ȡ����Ԫ��
    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    //����key����list���ϣ�ȡ��ĳһ��Ԫ��
    public List<ElementObj> getElementsByKey(GameElement ge) {
        return gameElements.get(ge);
    }

    //synchronized�߳�����֤������ִ����ֻ��һ���߳�
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
