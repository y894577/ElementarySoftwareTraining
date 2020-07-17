# ElementarySoftwareTraining
## 说明
游戏类型：泡泡堂

设计模式：MVC，工厂模式

## package说明

controller：游戏的监听，主线程的设置

element：vo类

manager：元素管理器，游戏加载器

resource：配置文件

show：view类

## 玩法规则
### 道具
1.BubbleAdd：增加泡泡数目

2.SuperPower：蓝色药水，增加泡泡攻击力

3.Mirror：玩家获得后产生反向行走效应，持续5s

4.RunningShoes：玩家获得后行走速度+1

5.TheWorld：时停，对手停止2s

6.CrazyDiamond：自己的血量恢复满格

7.WhiteAlbum：玩家自己停止1s

8.GodStatus：无敌2s（天上的卡兹不说话

### 方块
1.橙色格子：hp1

2.红色格子：hp1

3.绿色格子：hp1

4.箱子：hp1，能开出道具

5.蓝色箱子：hp2

6.淡蓝色箱子：hp2

7.障碍物：hp1

8.警戒线：hp1

9.石头：hp3

10.木箱子：hp2

房子不可以炸，草地可以隐身

### vo说明
etc

## 分工
### 1.游戏的设计

设计游戏关卡，道具摆放位置，编写pro文件和上网查找素材

### 2.vo类的编写

对于每个类的设计要求较高，需要定义好各种变量，对整体框架设计要求较高

### 3.manager的编写

需要编写元素管理器和加载器。加载器比较复杂，需要将配置文件读取到游戏中

### 4.controller的编写

需要编写键盘监听和主线程，键盘监听需要考虑双人游戏，主线程的难点在于各种碰撞方法

### 5.show的编写

这块会相对比较轻松，需要编写jpanel的代码

## 规范
### javadoc规范
@author 作者

@version 版本号

@description 描述

@param 参数说明

@return 返回值类型说明

### commit规范
init：初始化

feat：新功能

fix：修复bug

docs：修改文档

style：修改格式（不影响代码

refactor：重构代码
