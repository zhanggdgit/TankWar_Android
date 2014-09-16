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
		System.out.println("���ڻ���Ϸ��������");
		canvas.drawColor(Color.WHITE);
		paint.setColor(Color.RED);
		canvas.drawText("����Ϸ��������",TankWar.viewWidth / 2, TankWar.viewHeight / 2, paint);
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub
		System.out.println("���ڶ���Ϸ���������ͼ�����ݽ����޸�");
	}

	@Override
	public boolean onMyKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("��Ϸ��������������Ӧ��ť���� �¼������ز˵�����");
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			TankWar.game_state_current = TankWar.game_state_menu;
			break;
		case KeyEvent.KEYCODE_BACK://����Ϸ���������ϰ�Back��ʱ�����ص��˵�ҳ��
			TankWar.game_state_current = TankWar.game_state_menu;
			break;

		}
		
		return true;
	}

	@Override
	public boolean onMyKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("��Ϸ��������������Ӧ��ţ̌���¼�");
		return true;
	}

	@Override
	public boolean onMyTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("��Ϸ��������������Ӧ�����¼�");
		return true;
	}

}
