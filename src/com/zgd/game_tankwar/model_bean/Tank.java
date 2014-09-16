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

	private int type;//̹������
	private int beHittedNum;//̹��Ŀǰ����������
	private int direction;//̹��Ŀǰ�ķ���
	private int distance;//̹����Ŀǰ�ķ��������ߵľ���
	private int state;//̹��Ŀǰ��״̬
	private Rect rect;//̹��Ŀǰ��λ��
	private int stopTime;//��¼̹�˿�ʼ��ͣ��ʱ��
    private List<Bullet> bulletList;//̹��Ŀǰӵ�е��ӵ�
    private int stopFireTime;//��¼̹�˴���һ�ο���������������ʱ�䣬�û�����̹���������
    private int tankGroup;//̹�˵��ɱ�
    private int showImgIndex;//�״γ���ʱ������Ч��ͼ������
	
	Rect bulletRect = null;
	int bulletDirection = -1;
	
	private Random random = new Random();
	int rectIndex;
	/**
	 * ��ʼ��̹��
	 * @param type ̹������
	 * @param bulletNum �ӵ�����
	 * @param bulletType �ӵ�����
	 */
	public Tank(int tankGroup,int type,int bulletNum,int bulletType){
		this.distance = 0;
		this.state = Constants.state_moving;
		this.type = type;
		this.direction = Constants.direction_up;
		this.tankGroup = tankGroup;
		Rect mapRect = null;
		this.showImgIndex = 0;
		//����̹�����ͣ���ʼ��̹�˸տ�ʼ�ĳ��򡢿ɳ��ܹ����������ֵ�λ��
		if(type == Constants.type_enemy){
			this.direction = Constants.direction_down;
			this.beHittedNum = 0;
			if(Constants.enemyTankShowRectList.size() == 0){//����������ʱ
				CommonUtil.updateTankShowRectList();
			}
			rectIndex = random.nextInt(Constants.enemyTankShowRectList.size());//��xxxxTankShowRectList�����ѡһ��������Ϊ̹�˳��ֵ�����
			mapRect = Constants.enemyTankShowRectList.get(rectIndex);
			//��������̹�˳�ʼ��ʱ������ͬһ��λ����
			Constants.enemyTankShowRectList.remove(rectIndex);
			
		}else if(type == Constants.type_us){
			this.beHittedNum = 0;
			if(Constants.usTankShowRectList.size() == 0){//����������ʱ
				CommonUtil.updateTankShowRectList();
			}
			rectIndex = random.nextInt(Constants.usTankShowRectList.size());//��xxxxTankShowRectList�����ѡһ��������Ϊ̹�˳��ֵ�����
			mapRect = Constants.usTankShowRectList.get(rectIndex);
			//��������̹�˳�ʼ��ʱ������ͬһ��λ����
			Constants.usTankShowRectList.remove(rectIndex);
		}else if(type == Constants.type_user){
			this.beHittedNum = 0;
			mapRect = Constants.userShowRect;
		}
/**�ǳ���Ҫ�����д������Ҫ�Բ����������Ϸʹ�õĿ�ܵ���Ҫ��**/		
		this.rect = new Rect(mapRect.left,mapRect.top,mapRect.right,mapRect.bottom);
		
		//��ʼ���ӵ�
		bulletList = new ArrayList<Bullet>();
		Bullet bullet = null;
		
		for(int i=0;i<bulletNum;i++){
/**
 * **�ǳ���Ҫ**���ʹ�����룬���������ô���Ļ����������ָ��ͬһ��ʵ�����ӵ���λ�úͷ����û���̹�˵�λ�úͷ���Ҳ�͸��ű��ˡ���
 * bullet = new Bullet(bulletType,this.rect,this.direction);
 */
			bullet = new Bullet(bulletType);
			bullet.setFiredTankGroup(this.getTankGroup());
			bullet.setFiredTankType(this.getType());
			bulletList.add(bullet);
		}
	}
	//�Զ��巽��
	
	//����̹��
	public void driveTank(){
		if(this.type == Constants.type_user){//(�û�̹�˲�������ƶ�)
			return;
		}
		if(this.state == Constants.state_stop){//���̹��Ŀǰ��״̬ʱֹͣ�ģ������ֹͣ��ʱ���ж��Ƿ����ֹͣ��ʼ�ƶ�
			stop();
		}
		
		if(this.state == Constants.state_moving){//���̹��Ŀǰ��״̬���ƶ�
			move();
		}
	}
	
	//���ڣ�������bulletTest��ȡ��һ���ӵ������䷵�غ󣬴�bulletList���Ƴ���
	public Bullet fire(){
		Bullet bullet = null;
		if(bulletList != null && bulletList.size() >0){
/**�ǳ���Ҫ��
 * �١�����̹�˵���fire���п���ʱ��ʱ̹�˵�λ�úͷ��������ӵ���λ�úͷ��򣬶�����̹�˳�ʼ��ʱ����
 * �ڡ�����򵥽�̹�˴�ʱ��λ����Ϊ�ӵ���ʼ����λ�õĻ������ж��ӵ���ײʱ���϶���������̹����ײ��
 * ���������ִ�������
 * һ���ǽ��ӵ���ʼ��λ����̹��λ�õĻ�������ǰ�ƶ�һ��λ�ã�
 * ��һ�������ж��ӵ���ײʱ������ӵ��Ѿ��ƶ���һ��λ���ٽ�����ײ���
 * �˴��õ�һ��
 * **/
			bulletRect = new Rect(rect.left ,rect.top,rect.left+TankWar.bullet_one.getWidth(),rect.top+TankWar.bullet_one.getHeight());
			bulletDirection = this.direction;
			//DOTO һ���ӵ��ķ������������֣������û�̹�˿���ʱ�����ӵ����ڷ���ԲԲ�ĵ�λ��
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
	
	//��̹ͣ���ƶ�
	private void stop(){
		if(this.stopTime < Constants.stop_time[random.nextInt(Constants.stop_time.length)]){//ֹͣʱ��û�дﵽ�������ֹͣ
			this.stopTime += TankWar.drawSpan;	
		}else{                  //ֹͣʱ���Ѿ��ﵽ����ñ�״̬Ϊ�ƶ���������¼ֹͣʱ���stopTime����Ϊ0���ƶ��ľ���Ϊ0
			changeState(Constants.state_moving);
		}
	}
	//�ƶ�̹��
	private void move(){
		if(this.distance < Constants.target_distance[random.nextInt(Constants.target_distance.length)]){//����ƶ��ľ���û�дﵽ
			switch(this.direction){
			case Constants.direction_down:
				//�޸�Rect������
				this.rect.top += Constants.each_move_distance;
				this.rect.bottom += Constants.each_move_distance;
				//����޸ĺ��Rect�Ƿ���שǽ������ײ ��  �򵽴�߽磬��ͨ��������ת����бܿ�
				if(CommonUtil.checkCollideMapRect(this.rect) != null || this.rect.bottom > Constants.mapBottom){//��שǽ������ײ �򵽴�߽磬��ı��ƶ�����
					back();//����һ��
					changedDirection();//�ı䷽�� 
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
				
				if(CommonUtil.checkCollideMapRect(this.rect) != null || this.rect.left < 0){//��שǽ������ײ����ı��ƶ�����
					back();
					changedDirection();
				}else{
					this.distance += Constants.each_move_distance;
				}
				break;
			case Constants.direction_right:
				this.rect.left += Constants.each_move_distance;
				this.rect.right += Constants.each_move_distance;
				if(CommonUtil.checkCollideMapRect(this.rect) != null || this.rect.right > Constants.mapRight){//��שǽ������ײ����ı��ƶ�����
					back();
					changedDirection();
				}else{
					this.distance += Constants.each_move_distance;
				}
				break;
			}

			
			
		}else{                         //�ƶ��ľ����Ѿ��ﵽ����ı�״̬Ϊֹͣ�ƶ��������ƶ��ľ����ʼ��Ϊ0
		    changeState(Constants.state_stop);
		}

	}
	//�ı�̹�˵�״̬
	private void changeState(int toState){
		this.state = toState;
		this.stopTime = 0;
		this.distance = 0;
		
		if(toState == Constants.state_moving){//���̹��״̬��Ϊ���ƶ�������Ҫȷ�����ƶ��ķ���
			if(this.type == Constants.type_enemy){
				this.direction = Constants.move_direction_enemy[random.nextInt(Constants.move_direction_enemy.length)];
			}else if(this.type == Constants.type_us){
				this.direction = Constants.move_direction_us[random.nextInt(Constants.move_direction_us.length)];
			}
		}
	}
	//�ı�̹����Եķ���
	public void changedDirection(){
		int newDire = -1;
		do{                 //��ֹ��������ԭ���Ľ���ǽ����ײ�ķ���
			newDire = Constants.move_direction[random.nextInt(Constants.move_direction.length)];
		}while(newDire == this.direction);
		this.direction = newDire;
		this.distance = 0;
	}
	//�ڸ����ķ����ϻ���һ��
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
	
	
	//get��set����
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