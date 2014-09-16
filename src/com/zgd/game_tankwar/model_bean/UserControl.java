package com.zgd.game_tankwar.model_bean;

import android.graphics.Bitmap;

public class UserControl {

    private Bitmap userControlBitmap;//ĿǰӦ����ʾ���û���ЧͼƬ(ǰ�����Ǳ���)
    private float userControlX;
    private float userControlY;
    private float circleX;//Բ������
    private float circleY;
    private float radiusInner;//СԲ�İ뾶
    private float radiusOuter;//��Բ�İ뾶
    private boolean isMoveControlled = false;//�ƶ����Ʊ�־����ָ�Ƿ������м��СԲ
	
    private float dirCircleX;//���Ʒ����Բ�����꼰�뾶
    private float dirCircleY;
    private float radiusDir;
	private boolean isDirControlled = false;//���Ʒ����־����ָ�Ƿ��������ϵ�СԲ
	
    private float controlMovedX = 0;
    private float controlMovedY = 0;
    
    private float degrees = 0f;//��ת�ĽǶ�
    
	
	public float getDegrees() {
		return degrees;
	}
	public void setDegrees(float degrees) {
		this.degrees = degrees;
	}
	public Bitmap getUserControlBitmap() {
		return userControlBitmap;
	}
	public void setUserControlBitmap(Bitmap userControlBitmap) {
		this.userControlBitmap = userControlBitmap;
	}
	public float getUserControlX() {
		return userControlX;
	}
	public void setUserControlX(float userControlX) {
		this.userControlX = userControlX;
	}
	public float getUserControlY() {
		return userControlY;
	}
	public void setUserControlY(float userControlY) {
		this.userControlY = userControlY;
	}
	public float getCircleX() {
		return circleX;
	}
	public void setCircleX(float circleX) {
		this.circleX = circleX;
	}
	public float getCircleY() {
		return circleY;
	}
	public void setCircleY(float circleY) {
		this.circleY = circleY;
	}
	public float getRadiusInner() {
		return radiusInner;
	}
	public void setRadiusInner(float radiusInner) {
		this.radiusInner = radiusInner;
	}
	public float getRadiusOuter() {
		return radiusOuter;
	}
	public void setRadiusOuter(float radiusOuter) {
		this.radiusOuter = radiusOuter;
	}
	public boolean isMoveControlled() {
		return isMoveControlled;
	}
	public void setMoveControlled(boolean isMoveControlled) {
		this.isMoveControlled = isMoveControlled;
	}
	public float getDirCircleX() {
		return dirCircleX;
	}
	public void setDirCircleX(float dirCircleX) {
		this.dirCircleX = dirCircleX;
	}
	public float getDirCircleY() {
		return dirCircleY;
	}
	public void setDirCircleY(float dirCircleY) {
		this.dirCircleY = dirCircleY;
	}
	public boolean isDirControlled() {
		return isDirControlled;
	}
	public void setDirControlled(boolean isDirControlled) {
		this.isDirControlled = isDirControlled;
	}
	public float getControlMovedX() {
		return controlMovedX;
	}
	public void setControlMovedX(float controlMovedX) {
		this.controlMovedX = controlMovedX;
	}
	public float getControlMovedY() {
		return controlMovedY;
	}
	public void setControlMovedY(float controlMovedY) {
		this.controlMovedY = controlMovedY;
	}

	public float getRadiusDir() {
			return radiusDir;
	}
	public void setRadiusDir(float radiusDir) {
		this.radiusDir = radiusDir;
	}
}
