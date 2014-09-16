package com.zgd.game_tankwar.model_draw;

import com.zgd.game_tankwar.gameview.TankWar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameFinishedDraw implements GameDraw{

	Canvas canvas;
	Paint paint;
	private static GameFinishedDraw gameFinishedDraw;
	private GameFinishedDraw(){
		
	}
	public static GameFinishedDraw getInstance(){
		if(gameFinishedDraw == null){
			gameFinishedDraw = new GameFinishedDraw();
		}
		return gameFinishedDraw;
	}
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		System.out.println("正在画游戏结束界面");
		canvas.drawColor(Color.WHITE);
		paint.setColor(Color.RED);
		canvas.drawText("画游戏结束界面",TankWar.viewWidth / 2, TankWar.viewHeight / 2, paint);
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub
		System.out.println("正在对游戏结束界面的图像数据进行修改");
	}

	@Override
	public boolean onMyKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("游戏结束界面正在响应按钮按下 事件，返回菜单界面");
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			TankWar.game_state_current = TankWar.game_state_menu;
			break;
		case KeyEvent.KEYCODE_BACK://在游戏结束界面上按Back键时，返回到菜单页面
			TankWar.game_state_current = TankWar.game_state_menu;
			break;

		}
		
		return true;
	}

	@Override
	public boolean onMyKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("游戏结束界面正在响应按钮抬起事件");
		return true;
	}

	@Override
	public boolean onMyTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("游戏结束界面正在响应触屏事件");
		return true;
	}

}
