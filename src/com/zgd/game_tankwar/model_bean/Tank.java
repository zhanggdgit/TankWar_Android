package com.zgd.game_tankwar.model_bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.zgd.game_tankwar.gameview.TankWar;
import com.zgd.game_tankwar.model_draw.GameRunningDraw;
import com.zgd.game_tankwar.util.CommonUtil;
import com.zgd.game_tankwar.util.Constants;

import android.graphics.Rect;

public class Tank {

	private int type;//坦克类型
	private int beHittedNum;//坦克目前被攻击次数
	private int direction;//坦克目前的方向
	private int distance;//坦克在目前的方向上行走的距离
	private int state;//坦克目前的状态
	private Rect rect;//坦克目前的位置
	private int stopTime;//记录坦克开始暂停的时间
    private List<Bullet> bulletList;//坦克目前拥有的子弹
    private int stopFireTime;//记录坦克从上一次开火到现在所经历的时间，用户控制坦克随机开火
    private int tankGroup;//坦克的派别
    private int showImgIndex;//首次出现时，特殊效果图的张数
	
	Rect bulletRect = null;
	int bulletDirection = -1;
	
	private Random random = new Random();
	int rectIndex;
	/**
	 * 初始化坦克
	 * @param type 坦克类型
	 * @param bulletNum 子弹数量
	 * @param bulletType 子弹类型
	 */
	public Tank(int tankGroup,int type,int bulletNum,int bulletType){
		this.distance = 0;
		this.state = Constants.state_moving;
		this.type = type;
		this.direction = Constants.direction_up;
		this.tankGroup = tankGroup;
		Rect mapRect = null;
		this.showImgIndex = 0;
		//根据坦克类型，初始化坦克刚开始的朝向、可承受攻击数、出现的位置
		if(type == Constants.type_enemy){
			this.direction = Constants.direction_down;
			this.beHittedNum = 0;
			if(Constants.enemyTankShowRectList.size() == 0){//当区域被用完时
				CommonUtil.updateTankShowRectList();
			}
			rectIndex = random.nextInt(Constants.enemyTankShowRectList.size());//从xxxxTankShowRectList中随机选一个区域作为坦克出现的区域
			mapRect = Constants.enemyTankShowRectList.get(rectIndex);
			//避免两辆坦克初始化时出现在同一个位置上
			Constants.enemyTankShowRectList.remove(rectIndex);
			
		}else if(type == Constants.type_us){
			this.beHittedNum = 0;
			if(Constants.usTankShowRectList.size() == 0){//当区域被用完时
				CommonUtil.updateTankShowRectList();
			}
			rectIndex = random.nextInt(Constants.usTankShowRectList.size());//从xxxxTankShowRectList中随机选一个区域作为坦克出现的区域
			mapRect = Constants.usTankShowRectList.get(rectIndex);
			//避免两辆坦克初始化时出现在同一个位置上
			Constants.usTankShowRectList.remove(rectIndex);
		}else if(type == Constants.type_user){
			this.beHittedNum = 0;
			mapRect = Constants.userShowRect;
		}
/**非常重要：这行代码的重要性不亚于真个游戏使用的框架的重要性**/		
		this.rect = new Rect(mapRect.left,mapRect.top,mapRect.right,mapRect.bottom);
		
		//初始化子弹
		bulletList = new ArrayList<Bullet>();
		Bullet bullet = null;
		
		for(int i=0;i<bulletNum;i++){
/**
 * **非常重要**典型错误代码，这里如果这么做的话，多个引用指向同一个实例，子弹的位置和方向变得话，坦克的位置和方向也就跟着变了。。
 * bullet = new Bullet(bulletType,this.rect,this.direction);
 */
			bullet = new Bullet(bulletType);
			bullet.setFiredTankGroup(this.getTankGroup());
			bullet.setFiredTankType(this.getType());
			bulletList.add(bullet);
		}
	}
	//自定义方法
	
	//启动坦克
	public void driveTank(){
		if(this.type == Constants.type_user){//(用户坦克不会随机移动)
			return;
		}
		if(this.state == Constants.state_stop){//如果坦克目前的状态时停止的，则根据停止的时间判断是否继续停止或开始移动
			stop();
		}
		
		if(this.state == Constants.state_moving){//如果坦克目前的状态是移动
			move();
		}
	}
	
	//开炮（仅仅从bulletTest中取出一颗子弹，将其返回后，从bulletList中移除）
	public Bullet fire(){
		Bullet bullet = null;
		if(bulletList != null && bulletList.size() >0){
/**非常重要：
 * ①、根据坦克调用fire进行开火时此时坦克的位置和方向设置子弹的位置和方向，而不是坦克初始化时。。
 * ②、如果简单将坦克此时的位置作为子弹初始化的位置的话，在判断子弹相撞时，肯定是与自身坦克相撞的
 * 这里有两种处理方法，
 * 一种是将子弹初始的位置在坦克位置的基础上向前移动一个位置，
 * 另一种是在判断子弹相撞时，如果子弹已经移动了一个位置再进行碰撞检测
 * 此处用第一种
 * **/
			bulletRect = new Rect(rect.left ,rect.top,rect.left+TankWar.bullet_one.getWidth(),rect.top+TankWar.bullet_one.getHeight());
			bulletDirection = this.direction;
			//DOTO 一般子弹的方向有以下四种，对于用户坦克开炮时，将子弹放在方向圆圆心的位置
			if(this.getType() == Constants.type_user){
				/*bulletRect.left = (int) (GameRunningDraw.userControl.getDirCircleX() - TankWar.bullet_two.getWidth()/2);
				bulletRect.top = (int) (GameRunningDraw.userControl.getDirCircleY() - TankWar.bullet_two.getHeight()/2);*/
				bulletRect.left = (int) (GameRunningDraw.userControl.getDirCircleX() - 10);
				bulletRect.top = (int) (GameRunningDraw.userControl.getDirCircleY() + 10);
				bulletRect.right = bulletRect.left + TankWar.bullet_two.getWidth();
				bulletRect.bottom = bulletRect.top + TankWar.bullet_two.getHeight();
			}
			
			switch(this.direction){
			case Constants.direction_down:
				bulletRect.top += (TankWar.bullet_one.getHeight() + 1);
				bulletRect.bottom += (TankWar.bullet_one.getHeight() + 1);
				break;
			case Constants.direction_up:
				bulletRect.top -= (TankWar.bullet_one.getHeight() - 1);
				bulletRect.bottom -= (TankWar.bullet_one.getHeight() - 1);
				break;
			case Constants.direction_left:
				bulletRect.left -= (TankWar.bullet_one.getWidth() - 1);
				bulletRect.right -= (TankWar.bullet_one.getWidth() - 1);
				break;
			case Constants.direction_right:
				bulletRect.left += (TankWar.bullet_one.getWidth() + 1);
				bulletRect.right += (TankWar.bullet_one.getWidth() + 1);
				break;
			}
			
			
			
			bullet = bulletList.get(bulletList.size() -1);
			
			bullet.setRect(bulletRect);
			bullet.setDirection(bulletDirection);
			
			bulletList.remove(bulletList.size() -1);
		}
		return bullet;
	}
	
	//暂停坦克移动
	private void stop(){
		if(this.stopTime < Constants.stop_time[random.nextInt(Constants.stop_time.length)]){//停止时间没有达到，则继续停止
			this.stopTime += TankWar.drawSpan;	
		}else{                  //停止时间已经达到，则该变状态为移动，并将记录停止时间的stopTime设置为0，移动的距离为0
			changeState(Constants.state_moving);
		}
	}
	//移动坦克
	private void move(){
		if(this.distance < Constants.target_distance[random.nextInt(Constants.target_distance.length)]){//如果移动的距离没有达到
			switch(this.direction){
			case Constants.direction_down:
				//修改Rect的坐标
				this.rect.top += Constants.each_move_distance;
				this.rect.bottom += Constants.each_move_distance;
				//检测修改后的Rect是否与砖墙发生碰撞 或  或到达边界，并通过回退与转向进行避开
				if(CommonUtil.checkCollideMapRect(this.rect) != null || this.rect.bottom > Constants.mapBottom){//与砖墙发生碰撞 或到达边界，则改变移动方向
					back();//回退一步
					changedDirection();//改变方向 
				}else{
					this.distance += Constants.each_move_distance;
				}
				break;
			case Constants.direction_up:
				this.rect.top -= Constants.each_move_distance;
				this.rect.bottom -= Constants.each_move_distance;
				
				if(CommonUtil.checkCollideMapRect(this.rect) != null || this.rect.top < Constants.mapTop){
					back();
					changedDirection();
				}else{
					this.distance += Constants.each_move_distance;
				}
				break;
			case Constants.direction_left:
				this.rect.left -= Constants.each_move_distance;
				this.rect.right -= Constants.each_move_distance;
				
				if(CommonUtil.checkCollideMapRect(this.rect) != null || this.rect.left < 0){//与砖墙发生碰撞，则改变移动方向
					back();
					changedDirection();
				}else{
					this.distance += Constants.each_move_distance;
				}
				break;
			case Constants.direction_right:
				this.rect.left += Constants.each_move_distance;
				this.rect.right += Constants.each_move_distance;
				if(CommonUtil.checkCollideMapRect(this.rect) != null || this.rect.right > Constants.mapRight){//与砖墙发生碰撞，则改变移动方向
					back();
					changedDirection();
				}else{
					this.distance += Constants.each_move_distance;
				}
				break;
			}

			
			
		}else{                         //移动的距离已经达到，则改变状态为停止移动，并将移动的距离初始化为0
		    changeState(Constants.state_stop);
		}

	}
	//改变坦克的状态
	private void changeState(int toState){
		this.state = toState;
		this.stopTime = 0;
		this.distance = 0;
		
		if(toState == Constants.state_moving){//如果坦克状态改为是移动，则需要确定其移动的方向
			if(this.type == Constants.type_enemy){
				this.direction = Constants.move_direction_enemy[random.nextInt(Constants.move_direction_enemy.length)];
			}else if(this.type == Constants.type_us){
				this.direction = Constants.move_direction_us[random.nextInt(Constants.move_direction_us.length)];
			}
		}
	}
	//改变坦克面对的方向
	public void changedDirection(){
		int newDire = -1;
		do{                 //防止有生成了原来的将与墙体相撞的方向
			newDire = Constants.move_direction[random.nextInt(Constants.move_direction.length)];
		}while(newDire == this.direction);
		this.direction = newDire;
		this.distance = 0;
	}
	//在给定的方向上回退一步
	public void back(){
		switch(this.direction){
		case Constants.direction_down:
			this.rect.top -= Constants.each_move_distance;
			this.rect.bottom -= Constants.each_move_distance;
			break;
		case Constants.direction_up:
			this.rect.top += Constants.each_move_distance;
			this.rect.bottom += Constants.each_move_distance;
			break;
		case Constants.direction_left:
			this.rect.left += Constants.each_move_distance;
			this.rect.right += Constants.each_move_distance;
			break;
		case Constants.direction_right:
			this.rect.left -= Constants.each_move_distance;
			this.rect.right -= Constants.each_move_distance;
			break;
		}
	}
	
	
	//get、set方法
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getBeHittedNum() {
		return beHittedNum;
	}
	public void setBeHittedNum(int beHittedNum) {
		this.beHittedNum = beHittedNum;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Rect getRect() {
		return rect;
	}
	public void setRect(Rect rect) {
		this.rect = rect;
	}
	public List<Bullet> getBulletList() {
		return bulletList;
	}

	public void setBulletList(List<Bullet> bulletList) {
		this.bulletList = bulletList;
	}

	public int getStopFireTime() {
		return stopFireTime;
	}

	public void setStopFireTime(int stopFireTime) {
		this.stopFireTime = stopFireTime;
	}
	public int getTankGroup() {
		return tankGroup;
	}

	public void setTankGroup(int tankGroup) {
		this.tankGroup = tankGroup;
	}
	public int getShowImgIndex() {
		return showImgIndex;
	}

	public void setShowImgIndex(int showImgIndex) {
		this.showImgIndex = showImgIndex;
	}


}