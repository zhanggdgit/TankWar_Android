package com.zgd.game_tankwar.model_draw;

import android.view.KeyEvent;
import android.view.MotionEvent;

public interface GameDraw {

	public void draw();
	
	public void logic();
	
	public boolean onMyKeyDown(int keyCode, KeyEvent event);
	
	public boolean onMyKeyUp(int keyCode, KeyEvent event);

	public boolean onMyTouchEvent(MotionEvent event);
	
}