package com.zgd.game_tankwar.model_draw;

import com.zgd.game_tankwar.gameview.TankWar;
import com.zgd.game_tankwar.util.CommonUtil;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameMenuDraw implements GameDraw{

	boolean isDrawed = false;
	Paint paint;
	float bgX;
	float bgY;
	float buttonX;
	float buttonY;
	Rect buttonRect;
	Bitmap[] bitmapArrInit = null;
	float[][] position = null;
	Bitmap[] bitmapArrPressDown = null;
	public GameMenuDraw(float bgX,float bgY,float buttonX,float buttonY){
		this.bgX = bgX;
		this.bgY = bgY;
		this.buttonX = buttonX;
		this.buttonY = buttonY;
		isDrawed = false;
		
		buttonRect = new Rect((int)buttonX,(int)buttonY,(int)(buttonX + TankWar.bitmap_menu_start_up.getWidth()),(int)(buttonY + TankWar.bitmap_menu_start_up.getHeight()));
		
		Bitmap[] bitmapArrInit = {TankWar.bitmap_menu_bg,TankWar.bitmap_menu_start_up};
		float[][] position = {{bgX, bgX},{buttonX, buttonY}};
		Bitmap[] bitmapArrPressDown = {TankWar.bitmap_menu_bg,TankWar.bitmap_menu_start_down};
		
		this.bitmapArrInit = bitmapArrInit;
        this.position = position;
        this.bitmapArrPressDown = bitmapArrPressDown;
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		//����ͬһ��ͼƬ�����һֱ�ػ��ͻ����һֱ�����������Բ�Ҫ�����ػ�
		if(!isDrawed){
			this.paint = new Paint();
			CommonUtil.commonDraw(TankWar.holder, Color.WHITE,bitmapArrInit,position,paint);

			isDrawed = true;
		}
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub
		if(!isDrawed){
			System.out.println("���ڶԲ˵������ͼ�����ݽ����޸�");
		}
		
	}

	@Override
	public boolean onMyKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK://�ڲ˵������ϰ�Back��ʱ��ֱ���˳���Ϸ
			System.exit(0);
			break;
		}
		
		return true;
	}

	@Override
	public boolean onMyKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onMyTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN://��Ϸ��ʼ��ť����
			if(buttonRect.contains((int)event.getX(), (int)event.getY())){
				
				CommonUtil.commonDraw(TankWar.holder, Color.WHITE,bitmapArrPressDown,position,paint);		
			}
			break;
		case MotionEvent.ACTION_UP://��Ϸ��ʼ��ţ̌��
			if(buttonRect.contains((int)event.getX(), (int)event.getY())){
				
				CommonUtil.commonDraw(TankWar.holder, Color.WHITE,bitmapArrInit,position,paint);	
				TankWar.game_state_current = TankWar.game_state_running;
			}
			break;
		}
		return true;
	}

}
