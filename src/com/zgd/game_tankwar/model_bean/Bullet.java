package com.zgd.game_tankwar.model_bean;

import com.zgd.game_tankwar.gameview.TankWar;
import com.zgd.game_tankwar.model_draw.GameRunningDraw;
import com.zgd.game_tankwar.util.Constants;

import android.graphics.Rect;

public class Bullet {

	private int bulletType;
	private Rect rect;
	private int direction;
	private int lethality;//������
	private int firedTankGroup;
	private int flyDistance;//Ŀǰ�ӵ����˵ľ���
	private int firedTankType;//��������ӵ���̹�˵�����

	double resultSin;
	double resultCos;
	public Bullet(int bulletType){
		this.bulletType = bulletType;
        this.flyDistance = 0;
		switch(this.bulletType){
		case Constants.bullet_type_one:
			this.lethality = Constants.bullet_lethality_one;
			break;
		case Constants.bullet_type_two:
			this.lethality = Constants.bullet_lethality_two;
			break;
			
		}
	}
	//���ӵ���
	public void flying(int flySpeed){
        //�û��ӵ������⴦��
		if(this.getFiredTankType() == Constants.type_user){
			resultSin =  flySpeed * GameRunningDraw.degreesSinValue;
			resultCos =  flySpeed * GameRunningDraw.degreesCosValue;
		    if(GameRunningDraw.userControl.getDirCircleY() < GameRunningDraw.userControl.getCircleY()){
		    	this.getRect().left -= resultSin;
		    	this.getRect().top -= resultCos;
			}else{
				this.getRect().left += resultSin;
		    	this.getRect().top += resultCos;
			}
		    this.getRect().right = this.getRect().left + TankWar.bullet_one.getWidth();
			this.getRect().bottom = this.getRect().top + TankWar.bullet_one.getHeight();

		}else{//һ���ӵ�
			switch(this.getDirection()){
			case Constants.direction_down:
				//�� �ƶ��ӵ�
				this.getRect().top += flySpeed;
				this.getRect().bottom += flySpeed;
				break;
			case Constants.direction_up:
				this.getRect().top -= flySpeed;
				this.getRect().bottom -= flySpeed;
				break;
			case Constants.direction_left:
				this.getRect().left -= flySpeed;
				this.getRect().right -= flySpeed;
				break;
			case Constants.direction_right:
				this.getRect().left += flySpeed;
				this.getRect().right += flySpeed;
				break; 
			}
		}
		//�� �����ƶ��ľ���
		this.setFlyDistance(this.getFlyDistance() + flySpeed);
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getBulletType() {
		return bulletType;
	}
	public void setBulletType(int bulletType) {
		this.bulletType = bulletType;
	}
	public Rect getRect() {
		return rect;
	}
	public void setRect(Rect rect) {
		this.rect = rect;
	}
	public int getLethality() {
		return lethality;
	}

	public void setLethality(int lethality) {
		this.lethality = lethality;
	}
	public int getFiredTankGroup() {
		return firedTankGroup;
	}

	public void setFiredTankGroup(int firedTankGroup) {
		this.firedTankGroup = firedTankGroup;
	}
	public int getFlyDistance() {
		return flyDistance;
	}

	public void setFlyDistance(int flyDistance) {
		this.flyDistance = flyDistance;
	}
	
	public int getFiredTankType() {
		return firedTankType;
	}

	public void setFiredTankType(int firedTankType) {
		this.firedTankType = firedTankType;
	}

}
