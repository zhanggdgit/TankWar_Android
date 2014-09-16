package com.zgd.game_tankwar.util;

import java.util.ArrayList;
import java.util.List;

import com.zgd.game_tankwar.gameview.TankWar;
import com.zgd.game_tankwar.model_bean.Explosion;
import com.zgd.game_tankwar.model_bean.GameMapElement;

import android.graphics.Matrix;
import android.graphics.Rect;

public class Constants {
	/*********************ÿ��ͼƬ�����ű�*******************/
	public static Matrix matrix_menu;
	public static Matrix matrix_running;
	public static Matrix matrix_user_control;
	/************************��ͼ****************************/
	//��ͼ��Χ
	public static float mapTop;
	public static float mapLeft;
	public static float mapRight;
	public static float mapBottom;
	public static float midLineX;
	public static float midLineY;
	//�Ժ�שΪ׼��ÿ��Ԫ�صĿ�ȣ�����̶�ÿ��Ԫ�صĿ�ȶ����ש��ͬ
	public static float eachEleWidth;
	public static float eachEleHeight;
	//����˫����ط�Χ
	public static Rect usRect;
	public static Rect enemyRect;
	//����˫��̹�˿��Գ�û������
	public static List<Rect> usTankShowRectList;
	public static List<Rect> enemyTankShowRectList;
	
	public static Rect userShowRect;
	//��ש���򡢸�ש����boss���� ���ݵ����� ����GameMapElement�ļ��ϣ���������ػ���ͼʱ����Ϣ
	public static List<GameMapElement> redBrickRectEleList;
	public static List<GameMapElement> whiteBrickRectEletList;
	public static List<GameMapElement> bossRectEletList;
	public static List<GameMapElement> grassRectEletList;
	//Ԫ������
	public static final int type_redbric = 1;//��ש
	public static final int type_whiltebric = 2;//��ש
	public static final int type_grass = 3;//�ݵ�
	public static final int type_road = 4;//�յ�
	public static final int type_boss1 = 5;//boss1  ����Ϊ�˴�����bossͼƬ�����ĸ�Сͼ��ɵģ�Ϊ�˱�ʾ��Ӧλ����ʱ�Ǹ�Сͼ�����ĸ�����
	public static final int type_boss2 = 6;//boss2 ������һ���ô��ǣ��û�����һ���ȴ��boss��һ���֣���ͬ���ֳ��ܹ����Ĵ������Բ�ͬ
	public static final int type_boss3 = 7;//boss3
	public static final int type_boss4 = 8;//boss4
	//ÿ��Ԫ�ؿɳ��ܵĹ�����
	public static final int canBeHittedNum_redbric = 2;//��ש�ɳ��ܹ�����
	public static final int canBeHittedNum_whiltebric = 4;//��ש�ɳ��ܹ�����
	public static final int canBeHittedNum_boss = 10;//boss�ɳ��ܹ�����

	//ʹ�ö�ά�����ʾ��ͼ�е���벿��ÿ��λ�ô�ŵ�Ԫ��(ÿ����15x15)
	public static final int[][] mapModel = {
		//·��·��              ��()        ����              ��  ·��·��(�м�) ·��·��·��·(�м�)
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
	

	/************************̹��****************************/
	public static final int type_enemy = 1;//̹������_�з�
	public static final int type_us = 2;//̹������_�ҷ�
	public static final int type_user = 3;//̹������_�û�
	
	public static final int tank_group_us = 1;//̹���������ɱ�
	public static final int tank_group_enemy = 2;
	
	public static final int canBeHittedNum_enemy = 2;//�з�̹�˿ɳ��ܹ�������
	public static final int canBeHittedNum_us = 2;//�ҷ�..
	public static final int canBeHittedNum_user = 5;//�û�..

	public static final int direction_left = 1;//̹�˿ɳ���ķ���_��
	public static final int direction_right = 2;//̹�˿ɳ���ķ���_
	public static final int direction_up = 3;//̹�˿ɳ���ķ���_
	public static final int direction_down = 4;//̹�˿ɳ���ķ���_
	
	public static final int[] move_direction_us = {direction_left,direction_up,direction_up,direction_up,direction_up,direction_up,direction_up,direction_right};//�ҷ�̹�˿ɳ���ķ���,����direction_up��������������Ϊ�����ʱ�����ҷ�̹�˳��ϵĸ���
	public static final int[] move_direction_enemy = {direction_left,direction_down,direction_down,direction_down,direction_down,direction_down,direction_down,direction_right};
	public static final int[] move_direction = {direction_left,direction_down,direction_up,direction_right};
	
	public static final int state_stop = 1;//̹��״̬_ֹͣ
	public static final int state_moving = 2;//̹��״̬_�˶�
	
	public static final int[] stop_time = {(int)(18*TankWar.drawSpan),(int)(13*TankWar.drawSpan),(int)(8*TankWar.drawSpan)};//drawSpanȡֵ50ms�����ػ�һ�Σ����������1����50ms��2����100ms

	public static int each_move_distance = 2;//�̵߳���һ��̹�����ߵľ���
	public static int[] target_distance = {40,30,15};//̹����һ�������Ͽ����ߵľ���
	
	public static int fire_range = 100;//�򵥴���
	public static int fire_range_enemy = 100;//���
	public static int fire_range_us = 100;//���
	public static int fire_range_user = 150;//���
	
	public static int fire_speed = 40;//�򵥴���
	public static int fire_speed_enemy = 40;//����ٶ�
	public static int fire_speed_us = 40;//����ٶ�
	public static int fire_speed_user = 50;//����ٶ�
	
	public static final int bullet_num_enemy = 80;//�ӵ���
	public static final int bullet_num_us = 80;//�ӵ���
	public static final int bullet_num_user = 160;//�ӵ���
	
	public static final int[] fire_span = {3,5,8};//��������ʱ����(�Ի����ػ�ʱ��Ϊ��λ)
	
	/********************************��ը***********************************/
	public static final int explosion_type_tank_tank = 1;
	public static final int explosion_type_bullet_tank = 2;
	public static List<Explosion> explosionList = new ArrayList<Explosion>();
	
	/********************************��ײ***********************************/
	public static final int collison_type_brick = 1;
	public static final int collison_type_tank = 2;
	public static final int collison_type_boss = 3;
	public static final int collison_type_border = 4;
	/********************************�ӵ�***********************************/
	public static final int bullet_type_one = 1;//�ӵ�����
	public static final int bullet_type_two = 2;
	
	public static final int bullet_lethality_one = 1;//�ӵ�������
	public static final int bullet_lethality_two = 2;
	
	/*****************����̹�������� �� ��Ļ�ϳ�ʼ����̹�˵�����*****************/
	public static final int init_num_tank_us = 3;//��Ļ����ʾ������
	public static final int init_num_tank_enemy = 5;
	public static final int init_num_tank_user = 1;
	
	public static final int all_num_tank_us = 15;//�տ�ʼ��������
	public static final int all_num_tank_enemy = 20;
	public static final int all_num_tank_user = 4;
	/********************************̹�˿��Ƴ���*******************************/
	public static float radiusInner = 14;
	public static float radiusOuter = 25;
	public static float radiusDir = 9;
}
