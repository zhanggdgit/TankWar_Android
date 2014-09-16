package com.zgd.game_tankwar.model_bean;

import android.graphics.Bitmap;

public class UserControl {

    private Bitmap userControlBitmap;//目前应该显示的用户特效图片(前景还是背景)
    private float userControlX;
    private float userControlY;
    private float circleX;//圆的坐标
    private float circleY;
    private float radiusInner;//小圆的半径
    private float radiusOuter;//大圆的半径
    private boolean isMoveControlled = false;//移动控制标志：手指是否触摸到中间的小圆
	
    private float dirCircleX;//控制方向的圆的坐标及半径
    private float dirCircleY;
    private float radiusDir;
	private boolean isDirControlled = false;//控制方向标志：手指是否触摸到边上的小圆
	
    private float controlMovedX = 0;
    private float controlMovedY = 0;
    
    private float degrees = 0f;//旋转的角度
    
	
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
