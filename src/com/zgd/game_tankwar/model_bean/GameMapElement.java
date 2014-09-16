package com.zgd.game_tankwar.model_bean;

import android.graphics.Rect;

public class GameMapElement {

	private int elementType;
	private int beHittedNum;//Ŀǰ�������Ĵ���
	private Rect rect;
	
	public GameMapElement(int elementType,int beHittedNum,Rect rect){
		this.elementType = elementType;
		this.beHittedNum = beHittedNum;
		this.rect = rect;
	}
	
	public int getElementType() {
		return elementType;
	}
	public void setElementType(int elementType) {
		this.elementType = elementType;
	}
	public int getBeHittedNum() {
		return beHittedNum;
	}
	public void setBeHittedNum(int beHittedNum) {
		this.beHittedNum = beHittedNum;
	}
	public Rect getRect() {
		return rect;
	}
	public void setRect(Rect rect) {
		this.rect = rect;
	}
	
	
}
