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
	//地图信息
	private static GameMap gameMap;
	private Map<Rect,Integer> map;
	private List<Bitmap> mapBitmapList;
	private List<float[]> mapPostionList;
	//坦克群
	public static List<Tank> tankList;
	private List<Bitmap> tankBitmapList;
	private List<float[]> tankPositionList;
	//当前屏幕上各类坦克的数量声明
	private int currentShowUsNum = 0;
	private int currentShowEnemyNum = 0;
	private int currentShowUserNum = 0;
	//当前可供使用的各类坦克的数量声明
	private int currentCanShowUsNum = Constants.all_num_tank_us;
	private int currentCanShowEnemyNum = Constants.all_num_tank_enemy;
	private int currentCanShowUserNum = Constants.all_num_tank_user;
	//当前发生碰撞的坦克
	private List<Tank> currentexplosionList;
	//当前已发射出的子弹(未消亡)
	private List<Bullet> currentBulletList;
	//当前产生子弹的图片Bitmap信息
	private List<Bitmap> bulletBitmapList;
	private List<float[]> bulletPositionList;
	//当前产生爆炸的图片Bitmap信息
	private List<Bitmap> explosionBitmapList;
	private List<float[]> explosionPositionList;
	//用户控制的坦克
	private Tank userTank = null;
	//用户控制的坦克的特效
	public static UserControl userControl;
	public static double degreesSinValue = 0;
	public static double degreesCosValue = 1;
	private GameRunningDraw(){
		paint = new Paint();
		//初始化地图
		gameMap = new GameMap();
		map = gameMap.getMap();
        mapBitmapList = gameMap.getMapBitmapList();
        mapPostionList = gameMap.getMapPostionList();
		//初始化坦克群
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
		//画地图 和 坦克群
		if(userTank != null && userControl != null){
			CommonUtil.commonDraw(TankWar.holder, -1,mapBitmapList,mapPostionList,bulletBitmapList,bulletPositionList,explosionBitmapList,explosionPositionList,userControl,tankBitmapList, tankPositionList,TankWar.user_up,userTank.getRect(),userControl.getDegrees(),paint);
		}else{
			CommonUtil.commonDraw(TankWar.holder, -1,mapBitmapList,mapPostionList,bulletBitmapList,bulletPositionList,explosionBitmapList,explosionPositionList,tankBitmapList, tankPositionList,paint);
		}
		
	}

	@Override
	public void logic() {
		//修改非控制坦克的位置及状态，实现非控制坦克的移动
		dravingTanks();
		//修改地图可变元素的位置及状态
		updateGameMapBitmap();
	}

	@Override
	public boolean onMyKeyDown(int keyCode, KeyEvent event) {
		System.out.println("游戏进行中界面正在响应按钮按下 事件，进入到游戏结束界面");
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			TankWar.game_state_current = TankWar.game_state_finished;
			break;
		case KeyEvent.KEYCODE_BACK://在游戏运行界面上按Back键时，返回到菜单页面
			TankWar.game_state_current = TankWar.game_state_menu;
			break;
		}
		
		return true;
	}

	@Override
	public boolean onMyKeyUp(int keyCode, KeyEvent event) {
		System.out.println("游戏进行中界面正在响应按钮按抬起 事件");
		return true;
	}
	/**
	 * 游戏运行中的触屏事件用来监听用户对于用户坦克的控制
	 */
	@Override
	public boolean onMyTouchEvent(MotionEvent event) {
		// TODO 用户控制坦克触屏事件
		if(userControl == null){
			return true;
		}
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN://在用户按下时，判断是否为用户坦克的位置，进而决定该坦克是否被用户控制
			//手指触摸到小圆时，更换图片，设置状态为用户控制
			if(CommonUtil.isCollision(event.getX(),event.getY(),userControl.getCircleX(),userControl.getCircleY(),userControl.getRadiusInner())){
				
				userControl.setUserControlBitmap(TankWar.userFgBitmap);
				userControl.setCircleX(event.getX());
				userControl.setCircleY(event.getY());
				userControl.setUserControlX(userControl.getCircleX() - TankWar.userFgBitmap.getWidth()/2);
				userControl.setUserControlY(userControl.getCircleY() - TankWar.userFgBitmap.getHeight()/2);
				
				userControl.setMoveControlled(true);
			}
			//手指触摸到旁边控制方向的小圆
			if(CommonUtil.isCollision(event.getX(), event.getY(), userControl.getDirCircleX(),userControl.getDirCircleY(),userControl.getRadiusDir())){
				paint.setColor(Color.RED);
				userControl.setDirCircleX(event.getX());
				userControl.setDirCircleY(event.getY());
				userControl.setDirControlled(true);
			}
			break;
		case MotionEvent.ACTION_MOVE://用户移动时，若坦克此时受控制，则坦克跟着移动
			//手指已经触摸到小圆，并移动时，图片跟着移动
			if(userControl.isMoveControlled()){
				//特效图片跟着移动
				userControl.setControlMovedX(event.getX() - userControl.getCircleX());
				userControl.setControlMovedY(event.getY() - userControl.getCircleY());
				userControl.setCircleX(event.getX());
				userControl.setCircleY(event.getY());
				userControl.setUserControlX(userControl.getCircleX() - userControl.getUserControlBitmap().getWidth()/2);
				userControl.setUserControlY(userControl.getCircleY() - userControl.getUserControlBitmap().getHeight()/2);
				
				userControl.setDirCircleX(userControl.getDirCircleX() + userControl.getControlMovedX());//控制方向的小圆跟着移动
				userControl.setDirCircleY(userControl.getDirCircleY() + userControl.getControlMovedY());
				//坦克跟着移动
				userTank.getRect().left = (int) (userControl.getCircleX() - TankWar.user_up.getWidth()/2);
				userTank.getRect().top = (int) (userControl.getCircleY() - TankWar.user_up.getHeight()/2);
				userTank.getRect().right = (int) (userTank.getRect().left  + TankWar.user_up.getWidth());
				userTank.getRect().bottom = (int) (userTank.getRect().top  + TankWar.user_up.getHeight());
			}
			//手指已经触摸到旁边小圆，并移动时，且移动的范围在校园换内部时，旁边的小圆跟着移动，并记录角度
			//注：这里在固有环区的基础上扩大环区，因为实际操作起来的话，似乎不太容易操作，容易脱环，所以这里稍微将环的范围扩大些
			if(userControl.isDirControlled() && CommonUtil.isInRing(event.getX(), event.getY(), userControl.getCircleX(), userControl.getCircleY(), userControl.getRadiusOuter() - userControl.getRadiusDir() - 5, userControl.getRadiusOuter() + userControl.getRadiusDir() + 8)){
				//旋轉特效圖片
				changeDirCicleXY(event.getX(),event.getY());
			}
			
			break;
		case MotionEvent.ACTION_UP://用户手指抬起时，坦克的状态变为停止
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
	 * @description 初始化坦克群:
	 * ①、创建指定数量的各类坦克并将这些坦克放在tankList中；
	 * ②、初始化画坦克时所需要的Bitmap[]和对应的float[][]
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
		//不能将用户坦克从tankList中独立出来，因为炸弹的爆炸放在dravingTanks方法中，使用tankList
		for(int i=0;i<userNum;i++){
			tank = new Tank(Constants.tank_group_us,Constants.type_user,Constants.bullet_num_user,Constants.bullet_lethality_two);
			tankList.add(tank);
			position = new float[2];
			tankBitmapList.add(TankWar.user_up);
			position[0] = tank.getRect().left;
			position[1] = tank.getRect().top;
			tankPositionList.add(position);
			
			userTank = tank;
			
			//用户坦克的特效图片初始化
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
	 * ①随机改变所有非控制坦克的现在的状态：修改tankList中的坦克对象的相关属性
	 * ②根据修改后的tankList，生成新的画图资源：
	 */
	private void dravingTanks(){
		Tank tank = null;
		boolean isCollision = false;
		int j = 0;
		tankBitmapList.clear();//清空tankBitmapList和tankPositionList，将重画时需要的新数据放入。。
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
			 if(tank.getBeHittedNum() > canBeHittedNum){  //若此时坦克的被攻击次数为大于可承受攻击次数，则移除tankList中的该tank实例
				tankList.remove(i);
		     	i --;
		     	modifyTankNum(tank.getType());//修改当前各类坦克的数量
		     	tank = null;
		     	continue;
			 }
			 
			 if(currentexplosionList.size() > 0){//若之前有相撞的坦克则将其避开以避免连续相撞
				 for(int k=0;k<currentexplosionList.size();k++){
					 currentexplosionList.get(k).back();
					 currentexplosionList.get(k).back();
					 currentexplosionList.get(k).changedDirection();
					 currentexplosionList.remove(k);
					 k --;
				 }
			 }
			isCollision = false;
			//①、修改坦克的转向、移动、停止等状态
			tank.driveTank();
			
			//②、判断新修改的Tank实例的位置是否与前面已经修改的Tank实例位置相碰撞，若是同一方的坦克相撞，则改变新修改坦克的回退转向以避开，若是敌我两方的坦克相撞，则产生爆炸
			for(j=0;j<i;j++){
				if(CommonUtil.isCollision(tank.getRect(),tankList.get(j).getRect())){
					isCollision = true;
					break;
				}
			}
			//③、若相撞，则处理相撞
			if(isCollision){
				if(tank.getTankGroup() == tankList.get(j).getTankGroup()){//同一方坦克相撞前避开
					if(tank.getType() != Constants.type_user){
						tank.back();
						tank.changedDirection();
					}
					if(tankList.get(j).getType() != Constants.type_user){

						tankList.get(j).back();
						tankList.get(j).changedDirection();
					}
				}else {                   
					//添爆炸信息（敌方与用户坦克碰撞）
					colliLeft = (tank.getRect().left + tankList.get(j).getRect().left) / 2;
					colliTop = (tank.getRect().top + tankList.get(j).getRect().top) / 2;
					colliRect = new Rect(colliLeft,colliTop,15,15);
					
					addExplosion(colliRect,Constants.explosion_type_tank_tank);
					
					//降低生命力
					tank.setBeHittedNum(tank.getBeHittedNum() + 1);
					tankList.get(j).setBeHittedNum(tankList.get(j).getBeHittedNum() + 1);
					
					//添加碰撞坦克信息，以便在碰撞一次之后将其避开（用户的坦克除外）
					if(tank.getType() != Constants.type_user){
						currentexplosionList.add(tank);
					}
					if(tankList.get(j).getType() != Constants.type_user){
						currentexplosionList.add(tankList.get(j));
					}
				
				}
				
			}
			//随机添加开火信息
			if(tank.getStopFireTime() > Constants.fire_span[random.nextInt(Constants.fire_span.length)]){
				//TODO 用户坦克添加开火的特殊处理
				currentBulletList.add(tank.fire());
				tank.setStopFireTime(0);
			}else{
				tank.setStopFireTime(tank.getStopFireTime() + 1);
			}
			
			//处理开火信息
			processFire();
			
			//根据修改后的坦克的转向、位置来决定画图的图片及位置
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
			//只是将用户坦克的图片从tankBitmapList中独立出来，不用修改tankList，因为处理子弹的爆炸等都是根据tankList来进行的
			
		}
		//处理所有更新位置后的坦克之间的碰撞造成的爆炸
		processExplosion();
		supplementTanks();  // 补充各类坦克
		
	}
	/**
	 * @description 加入射击功能后，地图上三个可变区域经过processFire()方法处理后：
	 * redBrickRectEleList、whiteBrickRectEletList、bossRectEletList中的元素都发生了变化，
	 * 需要根据变化后的三个list，重新生成新的Bitmap[] 和 float[][]
	 */
	private void updateGameMapBitmap(){
		//清空之前的图片元素
		this.mapBitmapList.clear();
		this.mapPostionList.clear();
		GameMapElement gameMapElement = null;
		float[] position = null;
		//根据‘grass区域’，重画草地
		for(int i=0;i<Constants.grassRectEletList.size();i++){
			gameMapElement = Constants.grassRectEletList.get(i);
			
			position = new float[2];
			position[0] = gameMapElement.getRect().left;
			position[1] = gameMapElement.getRect().top;
			this.mapPostionList.add(position);
			
			this.mapBitmapList.add(TankWar.grass);
		}
		//根据   ‘boss区域’ 目前被攻击的情况重新在mapBitmapList中添加新的图片及资源
		for(int i=0;i<Constants.bossRectEletList.size();i++){
			gameMapElement = Constants.bossRectEletList.get(i);
			position = new float[2];
			position[0] = gameMapElement.getRect().left;
			position[1] = gameMapElement.getRect().top;
			this.mapPostionList.add(position);
			if(gameMapElement.getBeHittedNum() >= Constants.canBeHittedNum_boss){//如果boss已被攻击次数超过上限，将该元素的类型 变为grass，则将其移除，并放入grassRectEletList
				Constants.bossRectEletList.remove(i);
				gameMapElement.setElementType(Constants.type_grass);
				Constants.grassRectEletList.add(gameMapElement);
				i--;
				continue;
			}else{  //否则将其存放到mapBitmapList中以供显示
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
		//根据   ‘红砖区域’ 目前被攻击的情况重新在mapBitmapList中添加新的图片及资源
		for(int i=0;i<Constants.redBrickRectEleList.size();i++){
			gameMapElement = Constants.redBrickRectEleList.get(i);
			position = new float[2];
			position[0] = gameMapElement.getRect().left;
			position[1] = gameMapElement.getRect().top;
			this.mapPostionList.add(position);
			
			if(gameMapElement.getBeHittedNum() >= Constants.canBeHittedNum_redbric){//如果红砖已被攻击次数超过上限，则将其移除
				Constants.redBrickRectEleList.remove(i);
				gameMapElement.setElementType(Constants.type_grass);
				Constants.grassRectEletList.add(gameMapElement);
				i--;
				continue;
			}else{  //否则将其存放到mapBitmapList中以供显示
				this.mapBitmapList.add(TankWar.brick_red);
				
			}
		}
		//根据   ‘钢转区域’ 目前被攻击的情况重新在mapBitmapList中添加新的图片及资源
		for(int i=0;i<Constants.whiteBrickRectEletList.size();i++){
			gameMapElement = Constants.whiteBrickRectEletList.get(i);
			position = new float[2];
			position[0] = gameMapElement.getRect().left;
			position[1] = gameMapElement.getRect().top;
			this.mapPostionList.add(position);
			
			if(gameMapElement.getBeHittedNum() >= Constants.canBeHittedNum_whiltebric){//如果白砖已被攻击次数超过上限，则将其移除
				Constants.whiteBrickRectEletList.remove(i);
				gameMapElement.setElementType(Constants.type_grass);
				Constants.grassRectEletList.add(gameMapElement);
				continue;
			}else{  //否则将其存放到mapBitmapList中以供显示
				this.mapBitmapList.add(TankWar.brick_white);
			}
		}
		
	}
	/**
	 * @description 接受爆炸信息
	 * @param rect
	 * @param collisionType
	 */
	private void addExplosion(Rect rect,int collisionType){
		Explosion collision = new Explosion(rect,collisionType);
		
		Constants.explosionList.add(collision);
	}
	/**
	 * @description 处理爆炸信息
	 */
	private void processExplosion(){
		
		//创建产生爆炸效果的Bitmap[]和float[][],对于bitmapIndex为最后一张图片的Collision实例，将其从explosionList中移除
		Explosion collision = null;
		float[] position = null;
		this.explosionBitmapList.clear();//清楚重画爆炸时所需要的资源，以存入新的资源
		this.explosionPositionList.clear();
		for(int i=0;i<Constants.explosionList.size();i++){
			collision = Constants.explosionList.get(i);
			if(collision.getCollisionType() == Constants.explosion_type_tank_tank){
				
				//若该collision实例的爆炸图片已用完，则从explosionList将该collision实例移除
				if(collision.getBitmapIndex() > TankWar.explosion_tank.length - 1){
					Constants.explosionList.remove(i);
					i --;
					continue;
				}
				//根据collision实例中bitmapIndex属性的值，添加相应爆炸图片数组中的图片用来显示爆炸图片
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
	 * @description 处理开火信息：
	 * ① 根据currentBulletList初始化
	 * ① 将所有射出的子弹向前移动一个位置
	 * ② 判断移动后的子弹是否与敌方坦克、砖墙相碰撞、是否已超出地图范围
	 * ③ 若碰撞则返回碰撞的实体及碰撞位置
	 * ④ 在碰撞的位置添加爆炸信息
	 * ⑤ 对于爆炸后的子弹 及 超出地图范围的子弹进行移除处理
	 * ⑥ 对于被打倒的实体进行处理
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
			//开火时，应该指明开火坦克的类型，然后根据坦克的类型得到对应坦克的射程，再判断子弹是否到达了射程，此处简单处理
			if(bullet.getFlyDistance() > fireRange){
				 currentBulletList.remove(i);
				 i --;
				 continue;  
			}
			//⑥添加子弹图片及位置
			if(bullet.getBulletType() == Constants.bullet_type_one){
				bulletBitmapList.add(TankWar.bullet_one);
			}else if(bullet.getBulletType() == Constants.bullet_type_two){
				bulletBitmapList.add(TankWar.bullet_two);
			}
			position = new float[2];
			position[0] = bullet.getRect().left;
			position[1] = bullet.getRect().top;
			bulletPositionList.add(position);
			//TODO 需要单另封装让子弹飞的方法
			bullet.flying(fireSpeed);
			/*switch(bullet.getDirection()){
			case Constants.direction_down:
				//① 移动子弹
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
			//②判断移动后的子弹是否与‘敌方坦克’、‘砖墙相碰撞’、‘边界’、‘boss区域’ 碰撞
			collision = CommonUtil.checkBulletCollision(bullet);
			//③如果相撞
			if(collision != null){
				  //④处理被撞实体、添加爆炸信息
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
			 //⑤移除子弹
			 currentBulletList.remove(i);
			 i --;
			 continue;  
		}
	  }
	
    }
	/**
	 * @description 补充各类坦克的数量
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
	 * @description 情景描述：当用户控制方向的小圆已经在控制之下，并且用户的手指在圆环区域内移动时，
	 * 需要根据用户移动时的触屏位置确定小圆的圆心的新坐标，这里圆心的坐标不能简单的值，而必须在大圆
	 * 的圆周上，即小圆的圆心坐标是在用户控制的作用下沿着大圆做圆周运动。。。
	 * @param eventX 用户触屏坐标x
	 * @param eventY 用户触屏坐标y
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
			//记录旋转角度 
			userControl.setDegrees((float)(-arcTangent * 180/Math.PI));//添加‘-’号
		}else{
			userControl.setDirCircleX((float)(userControl.getCircleX() + resultSinR));
			userControl.setDirCircleY((float)(userControl.getCircleY() + resultCosR));
			//记录旋转角度 
			userControl.setDegrees((float)(180 - (arcTangent * 180/Math.PI)));//使用180减去..
		}
	}

	private void initUserControl(int tankLeft,int tankTop){
		userControl = new UserControl();
		userControl.setRadiusInner(Constants.radiusInner);//内圆半径
		userControl.setRadiusOuter(Constants.radiusOuter);//外圆半径
		userControl.setRadiusDir(Constants.radiusDir);//方向圆半径
		userControl.setUserControlBitmap(TankWar.userBgBitmap);
		userControl.setUserControlX(tankLeft - (TankWar.userBgBitmap.getWidth() - TankWar.user_up.getWidth())/2);//图片坐标
		userControl.setUserControlY(tankTop - (TankWar.userBgBitmap.getHeight() - TankWar.user_up.getHeight())/2);
		userControl.setCircleX(userControl.getUserControlX() + TankWar.userBgBitmap.getWidth()/2);//大圆圆心坐标
		userControl.setCircleY(userControl.getUserControlY() + TankWar.userBgBitmap.getHeight()/2);
		userControl.setDirCircleX(userControl.getCircleX());//方向小圆圆心坐标
		userControl.setDirCircleY(userControl.getCircleY() - userControl.getRadiusOuter());
		paint.setColor(Color.GRAY);

		degreesSinValue = 0;
		degreesCosValue = 1;
	}
}
