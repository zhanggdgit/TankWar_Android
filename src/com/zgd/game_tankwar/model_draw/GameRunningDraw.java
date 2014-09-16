package com.zgd.game_tankwar.model_draw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.zgd.game_tankwar.gameview.TankWar;
import com.zgd.game_tankwar.model_bean.Bullet;
import com.zgd.game_tankwar.model_bean.Collision;
import com.zgd.game_tankwar.model_bean.Explosion;
import com.zgd.game_tankwar.model_bean.GameMapElement;
import com.zgd.game_tankwar.model_bean.Tank;
import com.zgd.game_tankwar.model_bean.UserControl;
import com.zgd.game_tankwar.util.CommonUtil;
import com.zgd.game_tankwar.util.Constants;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameRunningDraw implements GameDraw{

	Canvas canvas;
	private static Paint paint;
	private static GameRunningDraw gameRunningDraw;
	Random random = new Random();
	//��ͼ��Ϣ
	private static GameMap gameMap;
	private Map<Rect,Integer> map;
	private List<Bitmap> mapBitmapList;
	private List<float[]> mapPostionList;
	//̹��Ⱥ
	public static List<Tank> tankList;
	private List<Bitmap> tankBitmapList;
	private List<float[]> tankPositionList;
	//��ǰ��Ļ�ϸ���̹�˵���������
	private int currentShowUsNum = 0;
	private int currentShowEnemyNum = 0;
	private int currentShowUserNum = 0;
	//��ǰ�ɹ�ʹ�õĸ���̹�˵���������
	private int currentCanShowUsNum = Constants.all_num_tank_us;
	private int currentCanShowEnemyNum = Constants.all_num_tank_enemy;
	private int currentCanShowUserNum = Constants.all_num_tank_user;
	//��ǰ������ײ��̹��
	private List<Tank> currentexplosionList;
	//��ǰ�ѷ�������ӵ�(δ����)
	private List<Bullet> currentBulletList;
	//��ǰ�����ӵ���ͼƬBitmap��Ϣ
	private List<Bitmap> bulletBitmapList;
	private List<float[]> bulletPositionList;
	//��ǰ������ը��ͼƬBitmap��Ϣ
	private List<Bitmap> explosionBitmapList;
	private List<float[]> explosionPositionList;
	//�û����Ƶ�̹��
	private Tank userTank = null;
	//�û����Ƶ�̹�˵���Ч
	public static UserControl userControl;
	public static double degreesSinValue = 0;
	public static double degreesCosValue = 1;
	private GameRunningDraw(){
		paint = new Paint();
		//��ʼ����ͼ
		gameMap = new GameMap();
		map = gameMap.getMap();
        mapBitmapList = gameMap.getMapBitmapList();
        mapPostionList = gameMap.getMapPostionList();
		//��ʼ��̹��Ⱥ
		initTankGroup(Constants.init_num_tank_enemy,Constants.init_num_tank_us,Constants.init_num_tank_user);
		
	}
	public static GameRunningDraw getInstance(){
		if(gameRunningDraw == null){
			gameRunningDraw = new GameRunningDraw();
		}
		return gameRunningDraw;
	}
	@Override
	public void draw() {
		//����ͼ �� ̹��Ⱥ
		if(userTank != null && userControl != null){
			CommonUtil.commonDraw(TankWar.holder, -1,mapBitmapList,mapPostionList,bulletBitmapList,bulletPositionList,explosionBitmapList,explosionPositionList,userControl,tankBitmapList, tankPositionList,TankWar.user_up,userTank.getRect(),userControl.getDegrees(),paint);
		}else{
			CommonUtil.commonDraw(TankWar.holder, -1,mapBitmapList,mapPostionList,bulletBitmapList,bulletPositionList,explosionBitmapList,explosionPositionList,tankBitmapList, tankPositionList,paint);
		}
		
	}

	@Override
	public void logic() {
		//�޸ķǿ���̹�˵�λ�ü�״̬��ʵ�ַǿ���̹�˵��ƶ�
		dravingTanks();
		//�޸ĵ�ͼ�ɱ�Ԫ�ص�λ�ü�״̬
		updateGameMapBitmap();
	}

	@Override
	public boolean onMyKeyDown(int keyCode, KeyEvent event) {
		System.out.println("��Ϸ�����н���������Ӧ��ť���� �¼������뵽��Ϸ��������");
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			TankWar.game_state_current = TankWar.game_state_finished;
			break;
		case KeyEvent.KEYCODE_BACK://����Ϸ���н����ϰ�Back��ʱ�����ص��˵�ҳ��
			TankWar.game_state_current = TankWar.game_state_menu;
			break;
		}
		
		return true;
	}

	@Override
	public boolean onMyKeyUp(int keyCode, KeyEvent event) {
		System.out.println("��Ϸ�����н���������Ӧ��ť��̧�� �¼�");
		return true;
	}
	/**
	 * ��Ϸ�����еĴ����¼����������û������û�̹�˵Ŀ���
	 */
	@Override
	public boolean onMyTouchEvent(MotionEvent event) {
		// TODO �û�����̹�˴����¼�
		if(userControl == null){
			return true;
		}
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN://���û�����ʱ���ж��Ƿ�Ϊ�û�̹�˵�λ�ã�����������̹���Ƿ��û�����
			//��ָ������СԲʱ������ͼƬ������״̬Ϊ�û�����
			if(CommonUtil.isCollision(event.getX(),event.getY(),userControl.getCircleX(),userControl.getCircleY(),userControl.getRadiusInner())){
				
				userControl.setUserControlBitmap(TankWar.userFgBitmap);
				userControl.setCircleX(event.getX());
				userControl.setCircleY(event.getY());
				userControl.setUserControlX(userControl.getCircleX() - TankWar.userFgBitmap.getWidth()/2);
				userControl.setUserControlY(userControl.getCircleY() - TankWar.userFgBitmap.getHeight()/2);
				
				userControl.setMoveControlled(true);
			}
			//��ָ�������Ա߿��Ʒ����СԲ
			if(CommonUtil.isCollision(event.getX(), event.getY(), userControl.getDirCircleX(),userControl.getDirCircleY(),userControl.getRadiusDir())){
				paint.setColor(Color.RED);
				userControl.setDirCircleX(event.getX());
				userControl.setDirCircleY(event.getY());
				userControl.setDirControlled(true);
			}
			break;
		case MotionEvent.ACTION_MOVE://�û��ƶ�ʱ����̹�˴�ʱ�ܿ��ƣ���̹�˸����ƶ�
			//��ָ�Ѿ�������СԲ�����ƶ�ʱ��ͼƬ�����ƶ�
			if(userControl.isMoveControlled()){
				//��ЧͼƬ�����ƶ�
				userControl.setControlMovedX(event.getX() - userControl.getCircleX());
				userControl.setControlMovedY(event.getY() - userControl.getCircleY());
				userControl.setCircleX(event.getX());
				userControl.setCircleY(event.getY());
				userControl.setUserControlX(userControl.getCircleX() - userControl.getUserControlBitmap().getWidth()/2);
				userControl.setUserControlY(userControl.getCircleY() - userControl.getUserControlBitmap().getHeight()/2);
				
				userControl.setDirCircleX(userControl.getDirCircleX() + userControl.getControlMovedX());//���Ʒ����СԲ�����ƶ�
				userControl.setDirCircleY(userControl.getDirCircleY() + userControl.getControlMovedY());
				//̹�˸����ƶ�
				userTank.getRect().left = (int) (userControl.getCircleX() - TankWar.user_up.getWidth()/2);
				userTank.getRect().top = (int) (userControl.getCircleY() - TankWar.user_up.getHeight()/2);
				userTank.getRect().right = (int) (userTank.getRect().left  + TankWar.user_up.getWidth());
				userTank.getRect().bottom = (int) (userTank.getRect().top  + TankWar.user_up.getHeight());
			}
			//��ָ�Ѿ��������Ա�СԲ�����ƶ�ʱ�����ƶ��ķ�Χ��У԰���ڲ�ʱ���Աߵ�СԲ�����ƶ�������¼�Ƕ�
			//ע�������ڹ��л����Ļ���������������Ϊʵ�ʲ��������Ļ����ƺ���̫���ײ����������ѻ�������������΢�����ķ�Χ����Щ
			if(userControl.isDirControlled() && CommonUtil.isInRing(event.getX(), event.getY(), userControl.getCircleX(), userControl.getCircleY(), userControl.getRadiusOuter() - userControl.getRadiusDir() - 5, userControl.getRadiusOuter() + userControl.getRadiusDir() + 8)){
				//���D��Ч�DƬ
				changeDirCicleXY(event.getX(),event.getY());
			}
			
			break;
		case MotionEvent.ACTION_UP://�û���ָ̧��ʱ��̹�˵�״̬��Ϊֹͣ
			if(userControl.isMoveControlled()){
				userControl.setCircleX(event.getX());
				userControl.setCircleY(event.getY());
				userControl.setUserControlBitmap(TankWar.userBgBitmap);
				userControl.setUserControlX(userControl.getCircleX() - TankWar.userBgBitmap.getWidth()/2);
				userControl.setUserControlY(userControl.getCircleY() - TankWar.userBgBitmap.getHeight()/2);
			}
			if(userControl.isDirControlled()){
				paint.setColor(Color.GRAY);
			}
			userControl.setMoveControlled(false);
			userControl.setDirControlled(false);
			break;
		}
		return true;
	}


	/**
	 * @description ��ʼ��̹��Ⱥ:
	 * �١�����ָ�������ĸ���̹�˲�����Щ̹�˷���tankList�У�
	 * �ڡ���ʼ����̹��ʱ����Ҫ��Bitmap[]�Ͷ�Ӧ��float[][]
	 * @param enemyNum
	 * @param usNum
	 */
	public void initTankGroup(int enemyNum,int usNum,int userNum){
		tankList = new ArrayList<Tank>();
		tankBitmapList = new ArrayList<Bitmap>(); 
		tankPositionList = new ArrayList<float[]>();
		explosionBitmapList = new ArrayList<Bitmap>(); 
		explosionPositionList = new ArrayList<float[]>();
		currentexplosionList = new ArrayList<Tank>();
		currentBulletList = new ArrayList<Bullet>();
		bulletBitmapList = new ArrayList<Bitmap>(); 
		bulletPositionList = new ArrayList<float[]>();
		Tank tank = null;
		float[] position = null;
		for(int i=0;i<enemyNum;i++){
			tank = new Tank(Constants.tank_group_enemy,Constants.type_enemy,Constants.bullet_num_enemy,Constants.bullet_type_one);
			tankList.add(tank);
			position = new float[2];
			tankBitmapList.add(TankWar.enemy_down);
			position[0] = tank.getRect().left;
			position[1] = tank.getRect().top;
			tankPositionList.add(position);

		}
		for(int i=0;i<usNum;i++){
			tank = new Tank(Constants.tank_group_us,Constants.type_us,Constants.bullet_num_us,Constants.bullet_type_one);
			tankList.add(tank);
			position = new float[2];
			tankBitmapList.add(TankWar.us_up);
			position[0] = tank.getRect().left;
			position[1] = tank.getRect().top;
			tankPositionList.add(position);
		}
		//���ܽ��û�̹�˴�tankList�ж�����������Ϊը���ı�ը����dravingTanks�����У�ʹ��tankList
		for(int i=0;i<userNum;i++){
			tank = new Tank(Constants.tank_group_us,Constants.type_user,Constants.bullet_num_user,Constants.bullet_lethality_two);
			tankList.add(tank);
			position = new float[2];
			tankBitmapList.add(TankWar.user_up);
			position[0] = tank.getRect().left;
			position[1] = tank.getRect().top;
			tankPositionList.add(position);
			
			userTank = tank;
			
			//�û�̹�˵���ЧͼƬ��ʼ��
			initUserControl(userTank.getRect().left,userTank.getRect().top);
			
		}
		this.currentShowUsNum = usNum;
		this.currentShowEnemyNum = enemyNum;
		this.currentShowUserNum = userNum;
		
		this.currentCanShowUsNum -= usNum;
		this.currentCanShowEnemyNum -= enemyNum;
		this.currentCanShowUserNum -= userNum;
	}
	/**
	 * ������ı����зǿ���̹�˵����ڵ�״̬���޸�tankList�е�̹�˶�����������
	 * �ڸ����޸ĺ��tankList�������µĻ�ͼ��Դ��
	 */
	private void dravingTanks(){
		Tank tank = null;
		boolean isCollision = false;
		int j = 0;
		tankBitmapList.clear();//���tankBitmapList��tankPositionList�����ػ�ʱ��Ҫ�������ݷ��롣��
		tankPositionList.clear();
		float[] position = null;
		Rect colliRect = null;
		int colliLeft = -1;
		int colliTop = -1;
		int canBeHittedNum = -1;
		for(int i=0;i<tankList.size();i++){
			tank = tankList.get(i);
			
			switch(tank.getType()){
			case Constants.type_us:
				canBeHittedNum = Constants.canBeHittedNum_us;
				break;
			case Constants.type_enemy:
				canBeHittedNum = Constants.canBeHittedNum_enemy;
				break;
			case Constants.type_user:
				canBeHittedNum = Constants.canBeHittedNum_user;
				break;
			}
			 if(tank.getBeHittedNum() > canBeHittedNum){  //����ʱ̹�˵ı���������Ϊ���ڿɳ��ܹ������������Ƴ�tankList�еĸ�tankʵ��
				tankList.remove(i);
		     	i --;
		     	modifyTankNum(tank.getType());//�޸ĵ�ǰ����̹�˵�����
		     	tank = null;
		     	continue;
			 }
			 
			 if(currentexplosionList.size() > 0){//��֮ǰ����ײ��̹������ܿ��Ա���������ײ
				 for(int k=0;k<currentexplosionList.size();k++){
					 currentexplosionList.get(k).back();
					 currentexplosionList.get(k).back();
					 currentexplosionList.get(k).changedDirection();
					 currentexplosionList.remove(k);
					 k --;
				 }
			 }
			isCollision = false;
			//�١��޸�̹�˵�ת���ƶ���ֹͣ��״̬
			tank.driveTank();
			
			//�ڡ��ж����޸ĵ�Tankʵ����λ���Ƿ���ǰ���Ѿ��޸ĵ�Tankʵ��λ������ײ������ͬһ����̹����ײ����ı����޸�̹�˵Ļ���ת���Աܿ������ǵ���������̹����ײ���������ը
			for(j=0;j<i;j++){
				if(CommonUtil.isCollision(tank.getRect(),tankList.get(j).getRect())){
					isCollision = true;
					break;
				}
			}
			//�ۡ�����ײ��������ײ
			if(isCollision){
				if(tank.getTankGroup() == tankList.get(j).getTankGroup()){//ͬһ��̹����ײǰ�ܿ�
					if(tank.getType() != Constants.type_user){
						tank.back();
						tank.changedDirection();
					}
					if(tankList.get(j).getType() != Constants.type_user){

						tankList.get(j).back();
						tankList.get(j).changedDirection();
					}
				}else {                   
					//��ը��Ϣ���з����û�̹����ײ��
					colliLeft = (tank.getRect().left + tankList.get(j).getRect().left) / 2;
					colliTop = (tank.getRect().top + tankList.get(j).getRect().top) / 2;
					colliRect = new Rect(colliLeft,colliTop,15,15);
					
					addExplosion(colliRect,Constants.explosion_type_tank_tank);
					
					//����������
					tank.setBeHittedNum(tank.getBeHittedNum() + 1);
					tankList.get(j).setBeHittedNum(tankList.get(j).getBeHittedNum() + 1);
					
					//�����ײ̹����Ϣ���Ա�����ײһ��֮����ܿ����û���̹�˳��⣩
					if(tank.getType() != Constants.type_user){
						currentexplosionList.add(tank);
					}
					if(tankList.get(j).getType() != Constants.type_user){
						currentexplosionList.add(tankList.get(j));
					}
				
				}
				
			}
			//�����ӿ�����Ϣ
			if(tank.getStopFireTime() > Constants.fire_span[random.nextInt(Constants.fire_span.length)]){
				//TODO �û�̹����ӿ�������⴦��
				currentBulletList.add(tank.fire());
				tank.setStopFireTime(0);
			}else{
				tank.setStopFireTime(tank.getStopFireTime() + 1);
			}
			
			//��������Ϣ
			processFire();
			
			//�����޸ĺ��̹�˵�ת��λ����������ͼ��ͼƬ��λ��
			position = new float[2];
			position[0] = tank.getRect().left;
			position[1] = tank.getRect().top;
			
			if(tank.getShowImgIndex() < TankWar.show.length){
				tankPositionList.add(position);
				tankBitmapList.add(TankWar.show[tank.getShowImgIndex()]);
				tank.setShowImgIndex(tank.getShowImgIndex() + 1);
			}
			
			if(tank.getType() == Constants.type_enemy){
				tankPositionList.add(position);
			    switch(tank.getDirection()){
			    case Constants.direction_down:
			    	tankBitmapList.add(TankWar.enemy_down);
			    	break;
			    case Constants.direction_left:
			    	tankBitmapList.add(TankWar.enemy_left);
			    	break;
			    case Constants.direction_right:
			    	tankBitmapList.add(TankWar.enemy_right);
			    	break;
			    case Constants.direction_up:
			    	tankBitmapList.add(TankWar.enemy_up);
			    	break;
			    }
			}else if(tank.getType() == Constants.type_us){
				tankPositionList.add(position);
				 switch(tank.getDirection()){
				    case Constants.direction_up:
				    	tankBitmapList.add(TankWar.us_up);
				    	break;
				    case Constants.direction_left:
				    	tankBitmapList.add(TankWar.us_left);
				    	break;
				    case Constants.direction_right:
				    	tankBitmapList.add(TankWar.us_right);
				    	break;
				    case Constants.direction_down:
				    	tankBitmapList.add(TankWar.us_down);
				    	break;
				 }
			}
			//ֻ�ǽ��û�̹�˵�ͼƬ��tankBitmapList�ж��������������޸�tankList����Ϊ�����ӵ��ı�ը�ȶ��Ǹ���tankList�����е�
			
		}
		//�������и���λ�ú��̹��֮�����ײ��ɵı�ը
		processExplosion();
		supplementTanks();  // �������̹��
		
	}
	/**
	 * @description ����������ܺ󣬵�ͼ�������ɱ����򾭹�processFire()���������
	 * redBrickRectEleList��whiteBrickRectEletList��bossRectEletList�е�Ԫ�ض������˱仯��
	 * ��Ҫ���ݱ仯�������list�����������µ�Bitmap[] �� float[][]
	 */
	private void updateGameMapBitmap(){
		//���֮ǰ��ͼƬԪ��
		this.mapBitmapList.clear();
		this.mapPostionList.clear();
		GameMapElement gameMapElement = null;
		float[] position = null;
		//���ݡ�grass���򡯣��ػ��ݵ�
		for(int i=0;i<Constants.grassRectEletList.size();i++){
			gameMapElement = Constants.grassRectEletList.get(i);
			
			position = new float[2];
			position[0] = gameMapElement.getRect().left;
			position[1] = gameMapElement.getRect().top;
			this.mapPostionList.add(position);
			
			this.mapBitmapList.add(TankWar.grass);
		}
		//����   ��boss���� Ŀǰ�����������������mapBitmapList������µ�ͼƬ����Դ
		for(int i=0;i<Constants.bossRectEletList.size();i++){
			gameMapElement = Constants.bossRectEletList.get(i);
			position = new float[2];
			position[0] = gameMapElement.getRect().left;
			position[1] = gameMapElement.getRect().top;
			this.mapPostionList.add(position);
			if(gameMapElement.getBeHittedNum() >= Constants.canBeHittedNum_boss){//���boss�ѱ����������������ޣ�����Ԫ�ص����� ��Ϊgrass�������Ƴ���������grassRectEletList
				Constants.bossRectEletList.remove(i);
				gameMapElement.setElementType(Constants.type_grass);
				Constants.grassRectEletList.add(gameMapElement);
				i--;
				continue;
			}else{  //�������ŵ�mapBitmapList���Թ���ʾ
				switch(gameMapElement.getElementType()){
				case Constants.type_boss4:
					this.mapBitmapList.add(TankWar.boss4);
					break;
				case Constants.type_boss3:
					this.mapBitmapList.add(TankWar.boss3);
					break;
				case Constants.type_boss2:
					this.mapBitmapList.add(TankWar.boss2);
					break;
				case Constants.type_boss1:
					this.mapBitmapList.add(TankWar.boss1);
					break;
				}
			}
		}
		//����   ����ש���� Ŀǰ�����������������mapBitmapList������µ�ͼƬ����Դ
		for(int i=0;i<Constants.redBrickRectEleList.size();i++){
			gameMapElement = Constants.redBrickRectEleList.get(i);
			position = new float[2];
			position[0] = gameMapElement.getRect().left;
			position[1] = gameMapElement.getRect().top;
			this.mapPostionList.add(position);
			
			if(gameMapElement.getBeHittedNum() >= Constants.canBeHittedNum_redbric){//�����ש�ѱ����������������ޣ������Ƴ�
				Constants.redBrickRectEleList.remove(i);
				gameMapElement.setElementType(Constants.type_grass);
				Constants.grassRectEletList.add(gameMapElement);
				i--;
				continue;
			}else{  //�������ŵ�mapBitmapList���Թ���ʾ
				this.mapBitmapList.add(TankWar.brick_red);
				
			}
		}
		//����   ����ת���� Ŀǰ�����������������mapBitmapList������µ�ͼƬ����Դ
		for(int i=0;i<Constants.whiteBrickRectEletList.size();i++){
			gameMapElement = Constants.whiteBrickRectEletList.get(i);
			position = new float[2];
			position[0] = gameMapElement.getRect().left;
			position[1] = gameMapElement.getRect().top;
			this.mapPostionList.add(position);
			
			if(gameMapElement.getBeHittedNum() >= Constants.canBeHittedNum_whiltebric){//�����ש�ѱ����������������ޣ������Ƴ�
				Constants.whiteBrickRectEletList.remove(i);
				gameMapElement.setElementType(Constants.type_grass);
				Constants.grassRectEletList.add(gameMapElement);
				continue;
			}else{  //�������ŵ�mapBitmapList���Թ���ʾ
				this.mapBitmapList.add(TankWar.brick_white);
			}
		}
		
	}
	/**
	 * @description ���ܱ�ը��Ϣ
	 * @param rect
	 * @param collisionType
	 */
	private void addExplosion(Rect rect,int collisionType){
		Explosion collision = new Explosion(rect,collisionType);
		
		Constants.explosionList.add(collision);
	}
	/**
	 * @description ����ը��Ϣ
	 */
	private void processExplosion(){
		
		//����������ըЧ����Bitmap[]��float[][],����bitmapIndexΪ���һ��ͼƬ��Collisionʵ���������explosionList���Ƴ�
		Explosion collision = null;
		float[] position = null;
		this.explosionBitmapList.clear();//����ػ���ըʱ����Ҫ����Դ���Դ����µ���Դ
		this.explosionPositionList.clear();
		for(int i=0;i<Constants.explosionList.size();i++){
			collision = Constants.explosionList.get(i);
			if(collision.getCollisionType() == Constants.explosion_type_tank_tank){
				
				//����collisionʵ���ı�ըͼƬ�����꣬���explosionList����collisionʵ���Ƴ�
				if(collision.getBitmapIndex() > TankWar.explosion_tank.length - 1){
					Constants.explosionList.remove(i);
					i --;
					continue;
				}
				//����collisionʵ����bitmapIndex���Ե�ֵ�������Ӧ��ըͼƬ�����е�ͼƬ������ʾ��ըͼƬ
				this.explosionBitmapList.add(TankWar.explosion_tank[collision.getBitmapIndex()]);
			}else if(collision.getCollisionType() == Constants.explosion_type_bullet_tank){
				
				if(collision.getBitmapIndex() > TankWar.explosion_bullet.length - 1){
					Constants.explosionList.remove(i);
					i --;
					continue;
				}
				this.explosionBitmapList.add(TankWar.explosion_bullet[collision.getBitmapIndex()]);
			}
			
			position = new float[2];
			position[0] = collision.getRect().left;
			position[1] = collision.getRect().top;
			this.explosionPositionList.add(position);
			
			collision.setBitmapIndex(collision.getBitmapIndex() + 1);

		}
		
	}
	/**
	 * @description ��������Ϣ��
	 * �� ����currentBulletList��ʼ��
	 * �� ������������ӵ���ǰ�ƶ�һ��λ��
	 * �� �ж��ƶ�����ӵ��Ƿ���з�̹�ˡ�שǽ����ײ���Ƿ��ѳ�����ͼ��Χ
	 * �� ����ײ�򷵻���ײ��ʵ�弰��ײλ��
	 * �� ����ײ��λ����ӱ�ը��Ϣ
	 * �� ���ڱ�ը����ӵ� �� ������ͼ��Χ���ӵ������Ƴ�����
	 * �� ���ڱ��򵹵�ʵ����д���
	 */
	private void processFire(){
		Bullet bullet = null;
		Collision collision;
		float[] position = null;
		bulletBitmapList.clear();
		bulletPositionList.clear();
		int fireRange = 0;
		int fireSpeed = 0;
		for(int i=0;i<currentBulletList.size();i++){
			bullet = currentBulletList.get(i);
			if(bullet == null){
				continue;
			}
			switch(bullet.getFiredTankType()){
			case Constants.type_enemy:
				fireRange = Constants.fire_range_enemy;
				fireSpeed = Constants.fire_speed_enemy;
				break;
			case Constants.type_us:
				fireRange = Constants.fire_range_us;
				fireSpeed = Constants.fire_speed_us;
				break;
			case Constants.type_user:
				fireRange = Constants.fire_range_user;
				fireSpeed = Constants.fire_speed_user;
				break;
			}
			//����ʱ��Ӧ��ָ������̹�˵����ͣ�Ȼ�����̹�˵����͵õ���Ӧ̹�˵���̣����ж��ӵ��Ƿ񵽴�����̣��˴��򵥴���
			if(bullet.getFlyDistance() > fireRange){
				 currentBulletList.remove(i);
				 i --;
				 continue;  
			}
			//������ӵ�ͼƬ��λ��
			if(bullet.getBulletType() == Constants.bullet_type_one){
				bulletBitmapList.add(TankWar.bullet_one);
			}else if(bullet.getBulletType() == Constants.bullet_type_two){
				bulletBitmapList.add(TankWar.bullet_two);
			}
			position = new float[2];
			position[0] = bullet.getRect().left;
			position[1] = bullet.getRect().top;
			bulletPositionList.add(position);
			//TODO ��Ҫ�����װ���ӵ��ɵķ���
			bullet.flying(fireSpeed);
			/*switch(bullet.getDirection()){
			case Constants.direction_down:
				//�� �ƶ��ӵ�
				bullet.getRect().top += fireSpeed;
				bullet.getRect().bottom += fireSpeed;
				break;
			case Constants.direction_up:
				bullet.getRect().top -= fireSpeed;
				bullet.getRect().bottom -= fireSpeed;
				break;
			case Constants.direction_left:
				bullet.getRect().left -= fireSpeed;
				bullet.getRect().right -= fireSpeed;
				break;
			case Constants.direction_right:
				bullet.getRect().left += fireSpeed;
				bullet.getRect().right += fireSpeed;
				break; 
			}
			bullet.setFlyDistance(bullet.getFlyDistance() + fireSpeed);*/
			//���ж��ƶ�����ӵ��Ƿ��롮�з�̹�ˡ�����שǽ����ײ�������߽硯����boss���� ��ײ
			collision = CommonUtil.checkBulletCollision(bullet);
			//�������ײ
			if(collision != null){
				  //�ܴ���ײʵ�塢��ӱ�ը��Ϣ
				  GameMapElement gameMapElement = null;
				  Tank tank = null;
				  switch(collision.getCollisionType()){
				  
		            case Constants.collison_type_brick:
		            	gameMapElement = (GameMapElement)collision.getCollisionInstance();
		            	gameMapElement.setBeHittedNum(gameMapElement.getBeHittedNum() + bullet.getLethality());
		            	addExplosion(gameMapElement.getRect(), Constants.explosion_type_bullet_tank);
		            	break;
		            case Constants.collison_type_tank:
		            	tank = (Tank)collision.getCollisionInstance();
		            	tank.setBeHittedNum(tank.getBeHittedNum() +  bullet.getLethality());
		            	addExplosion(tank.getRect(), Constants.explosion_type_bullet_tank);
		            	break;
		            case Constants.collison_type_border:
		            	break;
		            case Constants.collison_type_boss:
		            	gameMapElement = (GameMapElement)collision.getCollisionInstance();
		            	gameMapElement.setBeHittedNum(gameMapElement.getBeHittedNum() +  bullet.getLethality());
		            	addExplosion(gameMapElement.getRect(), Constants.explosion_type_bullet_tank);
		            	break;
		         }
			 //���Ƴ��ӵ�
			 currentBulletList.remove(i);
			 i --;
			 continue;  
		}
	  }
	
    }
	/**
	 * @description �������̹�˵�����
	 */
    private void supplementTanks(){
    	Tank tank = null;
    	if(this.currentCanShowUsNum > 0 && this.currentShowUsNum < Constants.init_num_tank_us){
    		tank = new Tank(Constants.tank_group_us,Constants.type_us,Constants.bullet_num_us,Constants.bullet_type_one);
            GameRunningDraw.tankList.add(tank);
            this.currentCanShowUsNum --;
            this.currentShowUsNum ++;
    	}
    	
    	if(this.currentCanShowEnemyNum > 0 && this.currentShowEnemyNum < Constants.init_num_tank_enemy){
    		 tank = new Tank(Constants.tank_group_enemy,Constants.type_enemy,Constants.bullet_num_enemy,Constants.bullet_type_one);		
    		 GameRunningDraw.tankList.add(tank);
             this.currentCanShowEnemyNum --;
             this.currentShowEnemyNum ++;
    	}
    	if(this.currentCanShowUserNum > 0 && this.currentShowUserNum < Constants.init_num_tank_user){
    		tank = new Tank(Constants.tank_group_us,Constants.type_user,Constants.bullet_num_user,Constants.bullet_lethality_two);
    		GameRunningDraw.tankList.add(tank);
    		
    		userTank = tank;
    		initUserControl(userTank.getRect().left,userTank.getRect().top);
    		
            this.currentCanShowUserNum --;
            this.currentShowUserNum ++;
    	}
    }
	
	private void modifyTankNum(int tankType){
		switch(tankType){
		case Constants.type_us:
			this.currentShowUsNum --;
			break;
		case Constants.type_enemy:
			this.currentShowEnemyNum --;
			break;
		case Constants.type_user:
			this.currentShowUserNum --;
			break;
		}
	}
	
	/**
	 * @description �龰���������û����Ʒ����СԲ�Ѿ��ڿ���֮�£������û�����ָ��Բ���������ƶ�ʱ��
	 * ��Ҫ�����û��ƶ�ʱ�Ĵ���λ��ȷ��СԲ��Բ�ĵ������꣬����Բ�ĵ����겻�ܼ򵥵�ֵ���������ڴ�Բ
	 * ��Բ���ϣ���СԲ��Բ�����������û����Ƶ����������Ŵ�Բ��Բ���˶�������
	 * @param eventX �û���������x
	 * @param eventY �û���������y
	 */
	private void changeDirCicleXY(float eventX,float eventY){
		float tanX = eventX - userControl.getCircleX();
		float tanY = eventY - userControl.getCircleY();
		float tan = tanX / tanY;
		double arcTangent = Math.atan(tan);
		GameRunningDraw.degreesSinValue = Math.sin(arcTangent);
		GameRunningDraw.degreesCosValue = Math.cos(arcTangent);
		
		double resultSinR = GameRunningDraw.degreesSinValue * userControl.getRadiusOuter();
		double resultCosR = GameRunningDraw.degreesCosValue * userControl.getRadiusOuter();
		if(eventY < userControl.getCircleY()){
			userControl.setDirCircleX((float)(userControl.getCircleX() - resultSinR));
			userControl.setDirCircleY((float)(userControl.getCircleY() - resultCosR));
			//��¼��ת�Ƕ� 
			userControl.setDegrees((float)(-arcTangent * 180/Math.PI));//��ӡ�-����
		}else{
			userControl.setDirCircleX((float)(userControl.getCircleX() + resultSinR));
			userControl.setDirCircleY((float)(userControl.getCircleY() + resultCosR));
			//��¼��ת�Ƕ� 
			userControl.setDegrees((float)(180 - (arcTangent * 180/Math.PI)));//ʹ��180��ȥ..
		}
	}

	private void initUserControl(int tankLeft,int tankTop){
		userControl = new UserControl();
		userControl.setRadiusInner(Constants.radiusInner);//��Բ�뾶
		userControl.setRadiusOuter(Constants.radiusOuter);//��Բ�뾶
		userControl.setRadiusDir(Constants.radiusDir);//����Բ�뾶
		userControl.setUserControlBitmap(TankWar.userBgBitmap);
		userControl.setUserControlX(tankLeft - (TankWar.userBgBitmap.getWidth() - TankWar.user_up.getWidth())/2);//ͼƬ����
		userControl.setUserControlY(tankTop - (TankWar.userBgBitmap.getHeight() - TankWar.user_up.getHeight())/2);
		userControl.setCircleX(userControl.getUserControlX() + TankWar.userBgBitmap.getWidth()/2);//��ԲԲ������
		userControl.setCircleY(userControl.getUserControlY() + TankWar.userBgBitmap.getHeight()/2);
		userControl.setDirCircleX(userControl.getCircleX());//����СԲԲ������
		userControl.setDirCircleY(userControl.getCircleY() - userControl.getRadiusOuter());
		paint.setColor(Color.GRAY);

		degreesSinValue = 0;
		degreesCosValue = 1;
	}
}
