package com.zgd.game_tankwar.util;

import java.util.List;

import com.zgd.game_tankwar.gameview.TankWar;
import com.zgd.game_tankwar.model_bean.Bullet;
import com.zgd.game_tankwar.model_bean.Collision;
import com.zgd.game_tankwar.model_bean.Explosion;
import com.zgd.game_tankwar.model_bean.GameMapElement;
import com.zgd.game_tankwar.model_bean.Tank;
import com.zgd.game_tankwar.model_bean.UserControl;
import com.zgd.game_tankwar.model_draw.GameRunningDraw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class CommonUtil {

	private static Canvas canvas;
	private static float x;
	private static float y;
	/**
	 * @description ������ͼ����
	 * @param holder
	 * @param bgColor,����-1ʱ������Ҫ������ɫ���Ȳ���Ҫ����
	 * @param bitmap
	 * @param x
	 * @param y
	 * @param paint
	 */
	public static void commonDraw(SurfaceHolder holder,int bgColor,Bitmap[] bitmapArr,float[][] position,Paint paint){
		canvas = holder.lockCanvas();
		if(canvas != null){
			try {
				if(bgColor != -1){
					canvas.drawColor(bgColor);
				}
				for(int i=0;i<bitmapArr.length;i++){
					x = position[i][0];
					y = position[i][1];
					if(bitmapArr[i] != null){
						canvas.drawBitmap(bitmapArr[i], x, y, paint);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				 holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	public static void commonDraw(SurfaceHolder holder,int bgColor,Bitmap[] bitmapArr1,float[][] position1,Bitmap[] bitmapArr2,float[][] position2,Paint paint){
		canvas = holder.lockCanvas();
		if(canvas != null){
			try {
				if(bgColor != -1){
					canvas.drawColor(bgColor);
				}
				for(int i=0;i<bitmapArr1.length;i++){
					x = position1[i][0];
					y = position1[i][1];
					if(bitmapArr1[i] != null){
						canvas.drawBitmap(bitmapArr1[i], x, y, paint);
					}
				}
				for(int i=0;i<bitmapArr2.length;i++){
					x = position2[i][0];
					y = position2[i][1];
					if(bitmapArr2[i] != null){
						canvas.drawBitmap(bitmapArr2[i], x, y, paint);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				 holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public static void commonDraw(SurfaceHolder holder,int bgColor,List<Bitmap> bitmapList1,List<float[]> position1,List<Bitmap> bitmapList2,List<float[]> position2,Paint paint){
		canvas = holder.lockCanvas();
		if(canvas != null){
			try {
				if(bgColor != -1){
					canvas.drawColor(bgColor);
				}
				for(int i=0;i<bitmapList1.size();i++){
					x = position1.get(i)[0];
					y = position1.get(i)[1];
					if(bitmapList1.get(i)!= null){
						canvas.drawBitmap(bitmapList1.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList2.size();i++){
					x = position2.get(i)[0];
					y = position2.get(i)[1];
					if(bitmapList2.get(i) != null){
						canvas.drawBitmap(bitmapList2.get(i), x, y, paint);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				 holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public static void commonDraw(SurfaceHolder holder,int bgColor,List<Bitmap> bitmapList1,List<float[]> position1,List<Bitmap> bitmapList2,List<float[]> position2,List<Bitmap> bitmapList3,List<float[]> position3,Paint paint){
		canvas = holder.lockCanvas();
		if(canvas != null){
			try {
				if(bgColor != -1){
					canvas.drawColor(bgColor);
				}
				for(int i=0;i<bitmapList1.size();i++){
					x = position1.get(i)[0];
					y = position1.get(i)[1];
					if(bitmapList1.get(i)!= null){
						canvas.drawBitmap(bitmapList1.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList2.size();i++){
					x = position2.get(i)[0];
					y = position2.get(i)[1];
					if(bitmapList2.get(i) != null){
						canvas.drawBitmap(bitmapList2.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList3.size();i++){
					x = position3.get(i)[0];
					y = position3.get(i)[1];
					if(bitmapList3.get(i) != null){
						canvas.drawBitmap(bitmapList3.get(i), x, y, paint);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				 holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public static void commonDraw(SurfaceHolder holder,int bgColor,List<Bitmap> bitmapList1,List<float[]> position1,List<Bitmap> bitmapList2,List<float[]> position2,List<Bitmap> bitmapList3,List<float[]> position3,List<Bitmap> bitmapList4,List<float[]> position4,Paint paint){
		canvas = holder.lockCanvas();
		if(canvas != null){
			try {
				if(bgColor != -1){
					canvas.drawColor(bgColor);
				}
				for(int i=0;i<bitmapList1.size();i++){
					x = position1.get(i)[0];
					y = position1.get(i)[1];
					if(bitmapList1.get(i)!= null){
						canvas.drawBitmap(bitmapList1.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList2.size();i++){
					x = position2.get(i)[0];
					y = position2.get(i)[1];
					if(bitmapList2.get(i) != null){
						canvas.drawBitmap(bitmapList2.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList3.size();i++){
					x = position3.get(i)[0];
					y = position3.get(i)[1];
					if(bitmapList3.get(i) != null){
						canvas.drawBitmap(bitmapList3.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList4.size();i++){
					x = position4.get(i)[0];
					y = position4.get(i)[1];
					if(bitmapList4.get(i) != null){
						canvas.drawBitmap(bitmapList4.get(i), x, y, paint);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				 holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public static void commonDraw(SurfaceHolder holder,int bgColor,List<Bitmap> bitmapList1,List<float[]> position1,List<Bitmap> bitmapList2,List<float[]> position2,List<Bitmap> bitmapList3,List<float[]> position3,UserControl userControl,List<Bitmap> bitmapList4,List<float[]> position4,Paint paint){
		canvas = holder.lockCanvas();
		if(canvas != null){
			try {
				if(bgColor != -1){
					canvas.drawColor(bgColor);
				}
				for(int i=0;i<bitmapList1.size();i++){
					x = position1.get(i)[0];
					y = position1.get(i)[1];
					if(bitmapList1.get(i)!= null){
						canvas.drawBitmap(bitmapList1.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList2.size();i++){
					x = position2.get(i)[0];
					y = position2.get(i)[1];
					if(bitmapList2.get(i) != null){
						canvas.drawBitmap(bitmapList2.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList3.size();i++){
					x = position3.get(i)[0];
					y = position3.get(i)[1];
					if(bitmapList3.get(i) != null){
						canvas.drawBitmap(bitmapList3.get(i), x, y, paint);
					}
				}
				//���û�̹����Χ����Ч
				
				canvas.drawCircle(userControl.getDirCircleX(), userControl.getDirCircleY(), userControl.getRadiusDir(), paint);
				canvas.drawBitmap(userControl.getUserControlBitmap(),userControl.getUserControlX(), userControl.getUserControlY(), paint);
				for(int i=0;i<bitmapList4.size();i++){
					x = position4.get(i)[0];
					y = position4.get(i)[1];
					if(bitmapList4.get(i) != null){
						canvas.drawBitmap(bitmapList4.get(i), x, y, paint);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				 holder.unlockCanvasAndPost(canvas);
			}
		}
	}
    /**
     * 
     * @param holder
     * @param bgColor
     * @param bitmapList1
     * @param position1
     * @param bitmapList2
     * @param position2
     * @param bitmapList3
     * @param position3
     * @param userControl
     * @param bitmapList4
     * @param position4
     * @param userBitmap
     * @param userPosition
     * @param degrees
     * @param paint
     */
	public static void commonDraw(SurfaceHolder holder,int bgColor,List<Bitmap> bitmapList1,List<float[]> position1,List<Bitmap> bitmapList2,List<float[]> position2,List<Bitmap> bitmapList3,List<float[]> position3,UserControl userControl,List<Bitmap> bitmapList4,List<float[]> position4,Bitmap userBitmap,Rect userRect,float degrees,Paint paint){
		canvas = holder.lockCanvas();
		if(canvas != null){
			try {
				if(bgColor != -1){
					canvas.drawColor(bgColor);
				}
				for(int i=0;i<bitmapList1.size();i++){
					x = position1.get(i)[0];
					y = position1.get(i)[1];
					if(bitmapList1.get(i)!= null){
						canvas.drawBitmap(bitmapList1.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList2.size();i++){
					x = position2.get(i)[0];
					y = position2.get(i)[1];
					if(bitmapList2.get(i) != null){
						canvas.drawBitmap(bitmapList2.get(i), x, y, paint);
					}
				}
				for(int i=0;i<bitmapList3.size();i++){
					x = position3.get(i)[0];
					y = position3.get(i)[1];
					if(bitmapList3.get(i) != null){
						canvas.drawBitmap(bitmapList3.get(i), x, y, paint);
					}
				}
				//���û�̹����Χ����Ч
				
				canvas.drawCircle(userControl.getDirCircleX(), userControl.getDirCircleY(), userControl.getRadiusDir(), paint);
				canvas.drawBitmap(userControl.getUserControlBitmap(),userControl.getUserControlX(), userControl.getUserControlY(), paint);
				for(int i=0;i<bitmapList4.size();i++){
					x = position4.get(i)[0];
					y = position4.get(i)[1];
					if(bitmapList4.get(i) != null){
						canvas.drawBitmap(bitmapList4.get(i), x, y, paint);
					}
				}
				//���û�̹�ˣ���Ҫ���ݽǶ���ת��
				if(userBitmap != null){
					canvas.save();
					canvas.rotate(degrees,(userRect.left+userRect.right)/2,(userRect.top+userRect.bottom)/2);//����̹�˵�������ת
					canvas.drawBitmap(userBitmap, userRect.left,userRect.top, paint);
					canvas.restore();
					//userBitmap,float[] userPosition,float degrees
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				 holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	/**
	 * @description ����������������Ƿ�����ײ
	 * @param rect1
	 * @param rect2
	 * @return
	 */
	public static boolean isCollision(Rect rect1,Rect rect2){
		boolean isCollision = true;
		if(rect1.bottom <= rect2.top){//rect1��rect2���Ϸ�
			isCollision = false;
		}else if(rect1.top >= rect2.bottom ){    //rect1��rect2���·�
			isCollision = false;
		}else if(rect1.right <= rect2.left ){    //rect1��rect2�����
			isCollision = false;
		}else if(rect1.left >= rect2.right ){    //rect1��rect2���ұ�
			isCollision = false;
		}
		return isCollision;
	}
	
	/**
	 * @description ���һ�����Ƿ���һ��Բ��������
	 * @param rect1
	 * @param rect2
	 * @return
	 */
	public static boolean isCollision(float x,float y,float circleX,float circleY,float radius){
		boolean isCollision = false;
		float a = (x - circleX) * (x - circleX) + (y - circleY) * (y - circleY);
		float b = radius * radius;
		if(a <= b){//�㵽Բ�ĵľ���С�ڰ뾶
			isCollision = true;
		}
		return isCollision;
	}
    /**
     * @description �������ĵ��Ƿ���Բ����
     * @param x �������x����
     * @param y �������y����
     * @param circleX Բ������x
     * @param circleY Բ������y
     * @param radiusInner ��Բ�뾶
     * @param radiusOuter ��Բ�뾶
     * @return
     */
	public static boolean isInRing(float x,float y,float circleX,float circleY,float radiusInner,float radiusOuter){
		boolean isIn = false;
		float distanceSquare = (x - circleX) * (x - circleX) + (y - circleY) * (y - circleY);
		float a = radiusInner * radiusInner;
		float b = radiusOuter * radiusOuter;
		if(distanceSquare > a && distanceSquare < b){
			isIn = true;
		}
		return isIn;
	}

	/**
	 * @description ���Rect�����Ƿ��롮שǽ���򡯡���boss��������ײ����������ײ�Ļ������ط�����ײ��GameMapElementʵ��
	 * @return ��û�з�����ײ���򷵻�null
	 */
	public static GameMapElement checkCollideMapRect(Rect checkedRect){
		GameMapElement gameMapElement = null;
		Rect rect = null;
		//��ש����
		for(int i=0;i<Constants.redBrickRectEleList.size();i++){
            rect = Constants.redBrickRectEleList.get(i).getRect();
            if(CommonUtil.isCollision(checkedRect, rect)){//�����ײ
            	return Constants.redBrickRectEleList.get(i);
            }
		}
		//��ש����
		for(int i=0;i<Constants.whiteBrickRectEletList.size();i++){
            rect = Constants.whiteBrickRectEletList.get(i).getRect();
            if(CommonUtil.isCollision(checkedRect, rect)){//�����ײ
            	return Constants.whiteBrickRectEletList.get(i);
            }
		}
		//boss����
		for(int i=0;i<Constants.bossRectEletList.size();i++){
			rect = Constants.bossRectEletList.get(i).getRect();
			if(CommonUtil.isCollision(checkedRect, rect)){//�����ײ
            	return Constants.bossRectEletList.get(i);
            }
		}
		return gameMapElement;
	}
	/**
	 * @description �����ײ�����ָ���������뵱ǰ��Ϸ��Ļ�е� ��ǽ�������Է�̹�ˡ���(��boss��)�����߽硯 ���ĸ�Ԫ���Ƿ���ײ
	 * �������ӵ����ɵз�̹�˷����ģ��򲻹���boss
	 * @param rect����Ҫ��������
	 * @return  ����Collisionʵ������û��ײ���򷵻�null
	 */
	public static Collision checkBulletCollision(Bullet bullet){
		Rect rect = bullet.getRect();
		int firedTankGroup = bullet.getFiredTankGroup();
		Collision collision = null;
		GameMapElement gameMapElement = null;
		Tank tank = null;
		//�Ƿ��롮ǽ������boss����ײ
		gameMapElement = CommonUtil.checkCollideMapRect(rect);
		if(gameMapElement != null){//���ǽ���ǽ��ײ
			if(gameMapElement.getElementType() == Constants.type_redbric || gameMapElement.getElementType() == Constants.type_whiltebric){
				collision = new Collision(Constants.collison_type_brick,gameMapElement);
			}else{
				if(firedTankGroup == Constants.tank_group_us){//ֻ���ҷ�̹�˵��ӵ��ſ��Թ���boss
					collision = new Collision(Constants.collison_type_boss,gameMapElement);
				}
			}
			return collision;
		}
		//�Ƿ����ͼ�߽���ײ
		if(rect.bottom > Constants.mapBottom || rect.top < Constants.mapTop || rect.left < Constants.mapLeft || rect.right > Constants.mapRight){
			collision = new Collision(Constants.collison_type_border,null);
			return collision;
		}
        //�Ƿ���Է�̹����ײ
		for(int i=0;i<GameRunningDraw.tankList.size();i++){
			tank = GameRunningDraw.tankList.get(i);
			if(firedTankGroup != tank.getTankGroup()){
				if(CommonUtil.isCollision(rect,tank.getRect())){
					collision = new Collision(Constants.collison_type_tank,tank);
					return collision;
				}
			}
		}
		
		return collision;
	}

	/**
	 * ��enemyTankShowRectList��usTankShowRectList������ʱ�����øķ�������ѡ���µĿ�ѡ����
	 */
	public static void updateTankShowRectList(){
		Rect rect = null;
		for(int i=0;i<Constants.grassRectEletList.size();i++){
			rect = Constants.grassRectEletList.get(i).getRect();
			if(rect.centerY() > Constants.midLineY ){
				Constants.usTankShowRectList.add(rect);
			}else{
				Constants.enemyTankShowRectList.add(rect);
			}
		}
	}
}
