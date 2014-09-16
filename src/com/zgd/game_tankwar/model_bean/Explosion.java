package com.zgd.game_tankwar.model_bean;

import android.graphics.Rect;

public class Explosion {

	private Rect rect;
	private int collisionType;
	private int bitmapIndex;
	
	public Explosion(Rect rect,int collisionType){
		this.rect = rect;
		this.collisionType = collisionType;
		this.bitmapIndex = 0;
	}
	
	public int getBitmapIndex() {
		return bitmapIndex;
	}
	public void setBitmapIndex(int bitmapIndex) {
		this.bitmapIndex = bitmapIndex;
	}
	public Rect getRect() {
		return rect;
	}
	public void setRect(Rect rect) {
		this.rect = rect;
	}
	public int getCollisionType() {
		return collisionType;
	}
	public void setCollisionType(int collisionType) {
		this.collisionType = collisionType;
	}
	
}
