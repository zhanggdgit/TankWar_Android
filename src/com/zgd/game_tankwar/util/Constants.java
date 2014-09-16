package com.zgd.game_tankwar.util;

import java.util.ArrayList;
import java.util.List;

import com.zgd.game_tankwar.gameview.TankWar;
import com.zgd.game_tankwar.model_bean.Explosion;
import com.zgd.game_tankwar.model_bean.GameMapElement;

import android.graphics.Matrix;
import android.graphics.Rect;

public class Constants {
	/*********************每张图片的缩放比*******************/
	public static Matrix matrix_menu;
	public static Matrix matrix_running;
	public static Matrix matrix_user_control;
	/************************地图****************************/
	//地图范围
	public static float mapTop;
	public static float mapLeft;
	public static float mapRight;
	public static float mapBottom;
	public static float midLineX;
	public static float midLineY;
	//以红砖为准，每个元素的宽度，这里固定每个元素的宽度都与红砖相同
	public static float eachEleWidth;
	public static float eachEleHeight;
	//敌我双方阵地范围
	public static Rect usRect;
	public static Rect enemyRect;
	//敌我双方坦克可以出没的区域
	public static List<Rect> usTankShowRectList;
	public static List<Rect> enemyTankShowRectList;
	
	public static Rect userShowRect;
	//红砖区域、钢砖区域、boss区域 、草地区域 对象GameMapElement的集合，用来存放重画地图时的信息
	public static List<GameMapElement> redBrickRectEleList;
	public static List<GameMapElement> whiteBrickRectEletList;
	public static List<GameMapElement> bossRectEletList;
	public static List<GameMapElement> grassRectEletList;
	//元素类型
	public static final int type_redbric = 1;//红砖
	public static final int type_whiltebric = 2;//白砖
	public static final int type_grass = 3;//草地
	public static final int type_road = 4;//空地
	public static final int type_boss1 = 5;//boss1  这里为了处理方便boss图片是由四个小图组成的，为了表示对应位置上时那个小图用了四个变量
	public static final int type_boss2 = 6;//boss2 这样有一个好处是，用户可以一次先打掉boss的一部分，不同部分承受攻击的次数可以不同
	public static final int type_boss3 = 7;//boss3
	public static final int type_boss4 = 8;//boss4
	//每种元素可承受的攻击数
	public static final int canBeHittedNum_redbric = 2;//红砖可承受攻击数
	public static final int canBeHittedNum_whiltebric = 4;//白砖可承受攻击数
	public static final int canBeHittedNum_boss = 10;//boss可承受攻击数

	//使用二维数组表示地图中的左半部分每个位置存放的元素(每个格15x15)
	public static final int[][] mapModel = {
		//路，路，              红()        ，红              ，  路，路，(中间) 路，路，路，路(中间)
		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_whiltebric,type_whiltebric,type_boss1},
		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_whiltebric,type_whiltebric,type_boss3},
		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_whiltebric,type_whiltebric,type_whiltebric},
		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_whiltebric,type_whiltebric,type_whiltebric},
		{type_whiltebric,type_whiltebric,type_redbric,type_redbric,type_whiltebric,type_grass,type_grass,type_grass},

		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_grass,type_grass,type_grass},
		{type_grass,type_grass,type_whiltebric,type_whiltebric,type_grass,type_grass,type_grass,type_grass},
		{type_grass,type_grass,type_whiltebric,type_whiltebric,type_grass,type_grass,type_grass,type_grass},
		{type_grass,type_grass,type_whiltebric,type_whiltebric,type_grass,type_grass,type_grass,type_grass},
		{type_grass,type_grass,type_whiltebric,type_whiltebric,type_grass,type_grass,type_grass,type_grass},

		{type_grass,type_grass,type_whiltebric,type_whiltebric,type_redbric,type_redbric,type_redbric,type_redbric},
		{type_grass,type_grass,type_whiltebric,type_whiltebric,type_redbric,type_redbric,type_redbric,type_redbric},
		{type_grass,type_grass,type_whiltebric,type_whiltebric,type_whiltebric,type_whiltebric,type_whiltebric,type_whiltebric},
		{type_grass,type_grass,type_whiltebric,type_whiltebric,type_grass,type_grass,type_grass,type_grass},
		{type_whiltebric,type_whiltebric,type_redbric,type_redbric,type_grass,type_grass,type_grass,type_grass},

		{type_whiltebric,type_whiltebric,type_redbric,type_redbric,type_grass,type_grass,type_grass,type_grass},
		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_grass,type_grass,type_grass},
		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_grass,type_grass,type_grass},
		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_grass,type_grass,type_grass},
		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_grass,type_grass,type_grass},
		{type_grass,type_grass,type_redbric,type_redbric,type_grass,type_grass,type_grass,type_grass}
	
	};
	

	/************************坦克****************************/
	public static final int type_enemy = 1;//坦克类型_敌方
	public static final int type_us = 2;//坦克类型_我方
	public static final int type_user = 3;//坦克类型_用户
	
	public static final int tank_group_us = 1;//坦克所属的派别
	public static final int tank_group_enemy = 2;
	
	public static final int canBeHittedNum_enemy = 2;//敌方坦克可承受攻击次数
	public static final int canBeHittedNum_us = 2;//我方..
	public static final int canBeHittedNum_user = 5;//用户..

	public static final int direction_left = 1;//坦克可朝向的方向_左
	public static final int direction_right = 2;//坦克可朝向的方向_
	public static final int direction_up = 3;//坦克可朝向的方向_
	public static final int direction_down = 4;//坦克可朝向的方向_
	
	public static final int[] move_direction_us = {direction_left,direction_up,direction_up,direction_up,direction_up,direction_up,direction_up,direction_right};//我方坦克可朝向的方向,这里direction_up连续出现三次是为了随机时增加我方坦克朝上的概率
	public static final int[] move_direction_enemy = {direction_left,direction_down,direction_down,direction_down,direction_down,direction_down,direction_down,direction_right};
	public static final int[] move_direction = {direction_left,direction_down,direction_up,direction_right};
	
	public static final int state_stop = 1;//坦克状态_停止
	public static final int state_moving = 2;//坦克状态_运动
	
	public static final int[] stop_time = {(int)(18*TankWar.drawSpan),(int)(13*TankWar.drawSpan),(int)(8*TankWar.drawSpan)};//drawSpan取值50ms，即重画一次，所以这里的1代表50ms，2代表100ms

	public static int each_move_distance = 2;//线程调用一次坦克行走的距离
	public static int[] target_distance = {40,30,15};//坦克在一个方向上可行走的距离
	
	public static int fire_range = 100;//简单处理
	public static int fire_range_enemy = 100;//射程
	public static int fire_range_us = 100;//射程
	public static int fire_range_user = 150;//射程
	
	public static int fire_speed = 40;//简单处理
	public static int fire_speed_enemy = 40;//射击速度
	public static int fire_speed_us = 40;//射击速度
	public static int fire_speed_user = 50;//射击速度
	
	public static final int bullet_num_enemy = 80;//子弹量
	public static final int bullet_num_us = 80;//子弹量
	public static final int bullet_num_user = 160;//子弹量
	
	public static final int[] fire_span = {3,5,8};//随机开火的时间间隔(以画面重画时间为单位)
	
	/********************************爆炸***********************************/
	public static final int explosion_type_tank_tank = 1;
	public static final int explosion_type_bullet_tank = 2;
	public static List<Explosion> explosionList = new ArrayList<Explosion>();
	
	/********************************碰撞***********************************/
	public static final int collison_type_brick = 1;
	public static final int collison_type_tank = 2;
	public static final int collison_type_boss = 3;
	public static final int collison_type_border = 4;
	/********************************子弹***********************************/
	public static final int bullet_type_one = 1;//子弹类型
	public static final int bullet_type_two = 2;
	
	public static final int bullet_lethality_one = 1;//子弹攻击力
	public static final int bullet_lethality_two = 2;
	
	/*****************各类坦克总数量 及 屏幕上初始化的坦克的数量*****************/
	public static final int init_num_tank_us = 3;//屏幕上显示的数量
	public static final int init_num_tank_enemy = 5;
	public static final int init_num_tank_user = 1;
	
	public static final int all_num_tank_us = 15;//刚开始的总数量
	public static final int all_num_tank_enemy = 20;
	public static final int all_num_tank_user = 4;
	/********************************坦克控制常量*******************************/
	public static float radiusInner = 14;
	public static float radiusOuter = 25;
	public static float radiusDir = 9;
}
