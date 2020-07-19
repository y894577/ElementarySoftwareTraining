package com.crazybubble.manager;

public enum GameElement {
	/**
	 * PLAY 玩家
	 * MAPS 地图
	 * ENEMY 敌人
	 * BOSS boss
	 * DIE 死亡集合（死亡了就放在里面）
	 *  子弹etc
	 *  枚举类型的顺序是声明的顺序
	 */
	MAPS,ENEMY,BOSS,PLAYFILE,DIE,BUBBLE,PLAYER,PROP,MAPOBJ,EXPLODE;
	//我们定义枚举类型，在编译的时候，虚拟机会自动帮助生成class文件
}
