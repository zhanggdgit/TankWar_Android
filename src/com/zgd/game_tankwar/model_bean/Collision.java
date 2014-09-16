package com.zgd.game_tankwar.model_bean;

public class Collision {

	private int collisionType;
	private Object collisionInstance;
	
	public Collision(int collisionType,Object collisionInstance){
		this.collisionType = collisionType;
		this.collisionInstance = collisionInstance;
	}
	
	public int getCollisionType() {
		return collisionType;
	}
	public void setCollisionType(int collisionType) {
		this.collisionType = collisionType;
	}
	public Object getCollisionInstance() {
		return collisionInstance;
	}
	public void setCollisionInstance(Object collisionInstance) {
		this.collisionInstance = collisionInstance;
	}
	
	
}
