package com.zgd.game_tankwar.gameview;

import com.zgd.game_tankwar.R;
import com.zgd.game_tankwar.model_draw.GameDraw;
import com.zgd.game_tankwar.model_draw.GameFinishedDraw;
import com.zgd.game_tankwar.model_draw.GameMenuDraw;
import com.zgd.game_tankwar.model_draw.GameRunningDraw;
import com.zgd.game_tankwar.util.Constants;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class TankWar extends SurfaceView implements android.view.SurfaceHolder.Callback{

	//基本属性
	public static SurfaceHolder holder;
	public static float viewWidth; 
	public static float viewHeight;
	Thread drawThread;
	Resources res = this.getResources();
	//画图线程
	boolean threadFlag;
	public static final long drawSpan = 40;
	long threadStartTime;
	long threadEndTime;
	long sleepTime;
	//游戏状态
	public static int game_state_current;
	public static final int game_state_menu = 1;
	public static final int game_state_running = 2;
	public static final int game_state_finished = 3;
	//更新游戏界面的GameDraw的实例
	GameDraw gameDraw;
	//系统中用到的图片资源
	//菜单
	public static Bitmap bitmap_menu_bg;
	public static Bitmap bitmap_menu_start_up;
	public static Bitmap bitmap_menu_start_down;
	//各个地图元素的图片
	public static Bitmap brick_red;
	public static Bitmap brick_white;
	public static Bitmap road;
	public static Bitmap grass;
	public static Bitmap boss1;
	public static Bitmap boss2;
	public static Bitmap boss3;
	public static Bitmap boss4;
	//不同类型坦克的图片
	public static Bitmap enemy_left;
	public static Bitmap enemy_right;
	public static Bitmap enemy_up;
	public static Bitmap enemy_down;
	public static Bitmap us_left;
	public static Bitmap us_right;
	public static Bitmap us_up;
	public static Bitmap us_down;
	public static Bitmap user_left;
	public static Bitmap user_right;
	public static Bitmap user_up;
	public static Bitmap user_down;
	//不同类型爆炸的图片组
	public static Bitmap[] explosion_tank;
	public static Bitmap[] explosion_bullet;
	//不同类型子弹的图片组
	public static Bitmap bullet_one;
	public static Bitmap bullet_two;
	//坦克出现时的特殊效果
	public static Bitmap[] show;
	//用户坦克特效图片
	
	public static Bitmap userBgBitmap;//特效背景图片
	public static Bitmap userFgBitmap;//特效前景图片
	
	public TankWar(Context context) {
		super(context);
		
		holder = this.getHolder();
		holder.addCallback(this);
		
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}

	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//初始化
		viewWidth = this.getWidth();
		viewHeight = this.getHeight();
		threadFlag = true;
		game_state_current = TankWar.game_state_menu;
		//初始化图片
		initImgSource();
		//初始化坦克行走速度等常量
		initConstants();
		//创建画图线程
		initDrawThread();
		//启动画图线程
		drawThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		threadFlag = false;
	}

	/**
	 * 根据当前游戏所处的状态更新画图实例
	 */
	private void initDrawInstance(){
		switch(game_state_current){
		case game_state_running:
			if(!(gameDraw instanceof GameRunningDraw)){
				gameDraw = GameRunningDraw.getInstance();
			}
			break;
		case game_state_menu:
			if(!(gameDraw instanceof GameMenuDraw)){
				gameDraw = new GameMenuDraw(0f,0f,viewWidth/2-bitmap_menu_start_up.getWidth()/2,viewHeight/2-bitmap_menu_start_up.getHeight()/2);
			}
			break;
		case game_state_finished:
			if(!(gameDraw instanceof GameFinishedDraw)){
				gameDraw = GameFinishedDraw.getInstance();
			}
			break;
		}
	}
	
	/**
	 * 初始化画图线程
	 */
	private void initDrawThread(){
        drawThread = new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(threadFlag){
					//获取画布
					threadStartTime = System.currentTimeMillis();
					//根据当前游戏所处的状态更新画图实例
					initDrawInstance();
					//调用画图实例修改画布
					gameDraw.draw();
					gameDraw.logic();
						
					threadEndTime = System.currentTimeMillis();
					//画布更新时间控制
					sleepTime = drawSpan - (threadEndTime - threadStartTime);
					if(sleepTime > 0){
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		};
	}
	
	//将监听到的时间交由当前画图实例对应的事件处理方法来处理
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		gameDraw.onMyKeyDown(keyCode, event);
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		gameDraw.onMyKeyUp(keyCode, event);
		
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		gameDraw.onMyTouchEvent(event);

		return true;
	}

	/**
	 * 初始化系统用到的所有图片资源
	 */
	private void initImgSource(){
    //菜单背景图片
		Options ops = new Options();
		int imgWidth = 0;
		int imgHeight = 0;
		float scaleX = 1;
		float scaleY = 1;
		//获得图片的真实宽高
		ops.inJustDecodeBounds = true;
		bitmap_menu_bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.welcome,ops);
		imgWidth = ops.outWidth;
		imgHeight = ops.outHeight;
		//设置缩放比例
		scaleX = (viewWidth / imgWidth);
		scaleY = (viewHeight / imgHeight);
		//初始化相应的Matrix实例
		Constants.matrix_menu = new Matrix();
		Constants.matrix_menu.setScale(scaleX, scaleY);
		//真正读入图片
		ops.inJustDecodeBounds = false;
        bitmap_menu_bg = BitmapFactory.decodeResource(res, R.drawable.welcome,ops);
        //进行缩放
        bitmap_menu_bg = Bitmap.createBitmap(bitmap_menu_bg, 0, 0, imgWidth, imgHeight, Constants.matrix_menu, false);
	//菜单上的按钮图片
        bitmap_menu_start_up = this.getScaledBitmap(R.drawable.button_up, Constants.matrix_menu);
        bitmap_menu_start_down = this.getScaledBitmap(R.drawable.button_down, Constants.matrix_menu);
    //地图元素图片
        //初始化相应的Matrix实例，注：这里以每个草地图片的大小为基准，即，所有的图片都与草地图片大小一样大
        grass = BitmapFactory.decodeResource(res, R.drawable.grass);
        float xWidth = Constants.mapModel[0].length * 2 * grass.getWidth();
        float yHeight = Constants.mapModel.length * grass.getHeight();
        scaleX = viewWidth / xWidth;
        scaleY = viewHeight / yHeight;
        Constants.matrix_running = new Matrix();
        Constants.matrix_running.setScale(scaleX, scaleY);
        //初始化用户控制图片的Matrix实例(为了保证其圆的特性不受影响，这里设置x和y的缩放比例一样，取两者大的)
        Constants.matrix_user_control = new Matrix();
        if(scaleX > scaleY){
        	 Constants.matrix_user_control.setScale(scaleX, scaleX);
        	 Constants.radiusInner = Constants.radiusInner * scaleX;//根据放缩比例修改用户控制图片的所有圆的半径
        	 Constants.radiusOuter = Constants.radiusOuter * scaleX;
        	 Constants.radiusDir = Constants.radiusDir * scaleX;
        }else{
        	 Constants.matrix_user_control.setScale(scaleY, scaleY);
        	 Constants.radiusInner = Constants.radiusInner * scaleY;//根据放缩比例修改用户控制图片的所有圆的半径
        	 Constants.radiusOuter = Constants.radiusOuter * scaleY;
        	 Constants.radiusDir = Constants.radiusDir * scaleY;
        }
        
    	
        //对图片进行缩放
        brick_red = this.getScaledBitmap(R.drawable.brick_red, Constants.matrix_running); 
        brick_white = this.getScaledBitmap(R.drawable.brick_white, Constants.matrix_running); 
        grass = getScaledBitmap( R.drawable.grass,Constants.matrix_running);
        boss1 = getScaledBitmap( R.drawable.boss1,Constants.matrix_running);
        boss2 = getScaledBitmap(R.drawable.boss2,Constants.matrix_running) ;
        boss3 = getScaledBitmap( R.drawable.boss3,Constants.matrix_running);
        boss4 = getScaledBitmap( R.drawable.boss4,Constants.matrix_running);
        road = getScaledBitmap( R.drawable.road,Constants.matrix_running);
    //坦克图片
        enemy_left = getScaledBitmap( R.drawable.enemy_left,Constants.matrix_running);
        enemy_right = getScaledBitmap( R.drawable.enemy_right,Constants.matrix_running);
        enemy_up = getScaledBitmap( R.drawable.enemy_up,Constants.matrix_running);
        enemy_down = getScaledBitmap( R.drawable.enemy_down,Constants.matrix_running);
    	us_left = getScaledBitmap( R.drawable.us_left,Constants.matrix_running);
    	us_right = getScaledBitmap( R.drawable.us_right,Constants.matrix_running);
    	us_up = getScaledBitmap( R.drawable.us_up,Constants.matrix_running);
    	us_down = getScaledBitmap( R.drawable.us_down,Constants.matrix_running);
    	user_left = getScaledBitmap( R.drawable.user_left,Constants.matrix_running);
    	user_right = getScaledBitmap( R.drawable.user_right,Constants.matrix_running);
    	user_up = getScaledBitmap( R.drawable.user_up,Constants.matrix_running);
    	user_down = getScaledBitmap( R.drawable.user_down,Constants.matrix_running);
   //爆炸图片
    	explosion_tank = new Bitmap[3];
    	explosion_tank[0] = getScaledBitmap( R.drawable.explosion_tank1,Constants.matrix_running);
    	explosion_tank[1] = getScaledBitmap( R.drawable.explosion_tank2,Constants.matrix_running);
    	explosion_tank[2] = getScaledBitmap( R.drawable.explosion_tank3,Constants.matrix_running);
    	
    	explosion_bullet = new Bitmap[6];
    	explosion_bullet[0] = getScaledBitmap( R.drawable.explosion_bullet1,Constants.matrix_running);
    	explosion_bullet[1] = getScaledBitmap( R.drawable.explosion_bullet2,Constants.matrix_running);
    	explosion_bullet[2] = getScaledBitmap( R.drawable.explosion_bullet3,Constants.matrix_running);
    	explosion_bullet[3] = getScaledBitmap( R.drawable.explosion_bullet4,Constants.matrix_running);
    	explosion_bullet[4] = getScaledBitmap( R.drawable.explosion_bullet5,Constants.matrix_running);
    	explosion_bullet[5] = getScaledBitmap( R.drawable.explosion_bullet6,Constants.matrix_running);
   //子弹图片
    	bullet_one = getScaledBitmap(R.drawable.bullet_one,Constants.matrix_running);
    	bullet_two = getScaledBitmap(R.drawable.bullet_two,Constants.matrix_running);
    	
   //坦克出现时的特殊效果
    	show = new Bitmap[8];
    	show[0] = getScaledBitmap(R.drawable.show1,Constants.matrix_running);
    	show[1] = getScaledBitmap(R.drawable.show2,Constants.matrix_running);
    	show[2] = getScaledBitmap(R.drawable.show3,Constants.matrix_running);
    	show[3] = getScaledBitmap(R.drawable.show4,Constants.matrix_running);
    	show[4] = getScaledBitmap(R.drawable.show1,Constants.matrix_running);
    	show[5] = getScaledBitmap(R.drawable.show2,Constants.matrix_running);
    	show[6] = getScaledBitmap(R.drawable.show3,Constants.matrix_running);
    	show[7] = getScaledBitmap(R.drawable.show4,Constants.matrix_running);
    //用户坦克特效图片
    	userBgBitmap = getScaledBitmap(R.drawable.user_bg,Constants.matrix_user_control);
    	userFgBitmap = getScaledBitmap(R.drawable.user_fg,Constants.matrix_user_control);
    
	}
	/**
	 * 根据屏幕的宽高初始化坦克的行走速度等常量
	 */
	private void initConstants(){
		Constants.each_move_distance = (int) (viewWidth / 110);//线程调用一次坦克行走的距离
		Constants.target_distance[0] = (int) viewWidth / 15;//坦克在一个方向上可行走的距离
		Constants.target_distance[1] = (int) viewWidth / 10;
		Constants.target_distance[2] = (int) viewWidth / 8;
		
		Constants.fire_range = (int) viewWidth;//射程（简单处理）
		Constants.fire_range_enemy = (int) viewWidth;//射程
		Constants.fire_range_us = (int) viewWidth;//射程
		Constants.fire_range_user = (int) viewHeight;//射程
		
		Constants.fire_speed = (int) (viewWidth / 100);//简单处理
		Constants.fire_speed_enemy = (int) (viewWidth / 25);//射击速度
		Constants.fire_speed_us = (int) (viewWidth / 25);//射击速度
		Constants.fire_speed_user = (int) (viewWidth / 20);//射击速度

	}
	private Bitmap getScaledBitmap(int resourceId,Matrix matrix){
		Bitmap bitmap = BitmapFactory.decodeResource(res, resourceId);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		return bitmap;
	}
}
