package com.zgd.game_tankwar.model_draw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zgd.game_tankwar.gameview.TankWar;
import com.zgd.game_tankwar.model_bean.GameMapElement;
import com.zgd.game_tankwar.util.Constants;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
/**
 * @
 * @author Administrator
 *
 */
public class GameMap{

	Paint paint;
	private List<Bitmap> mapBitmapList;
	private List<float[]> positionList;
	//�����ͼֻ�û�һ�Σ�ֻ��ש����յ�����ʱ�������ܹ����ĵط��ػ�
	boolean isDrawed = false;
	//һ�С�һ�зֱ���ʾ�ĵ�ͼԪ�صĸ���
	private int eleNumX;//ÿһ����ʾ��Ԫ�صĸ���
	private int eleNumY;//ÿһ����ʾ��Ԫ�صĸ���

	//������ĵ�ͼģ��mapModel��װΪ��ֵ�ԣ�����Rect---��ŵ�Ԫ������
	//����Ϊ��Ա��������Ϊ��̹�˹�����ͼ�ϵ�שǽ��ֱ���޸ĸ�map������ʹ���ܹ�����Rect��Ӧ�����͸ı䣬���к�ש��Ϊ��
	private Map<Rect,Integer> map = null;
	
	public GameMap(){
		isDrawed = false;//����isDrawedʱ���Ͳ����õ���ģʽ�ˡ�
		//��ʼ��ÿ����ͼԪ�صĿ��
		Constants.eachEleWidth = TankWar.grass.getWidth();
		Constants.eachEleHeight = TankWar.grass.getHeight();
		//ȷ����ͼ�����Ͻ� �� ���½� �ĵ�����
		eleNumX = (int) (TankWar.viewWidth / Constants.eachEleWidth);
		eleNumY =  (int) (TankWar.viewHeight / Constants.eachEleHeight);
		
		Constants.mapLeft = (TankWar.viewWidth - (eleNumX * Constants.eachEleWidth)) / 2;
		//Constants.mapTop = (TankWar.viewHeight - (eleNumY * Constants.eachEleHeight)) / 2;
		Constants.mapTop = 0;
		Constants.mapRight = Constants.mapLeft + (eleNumX * Constants.eachEleWidth);
		//Constants.mapBottom = Constants.mapTop + (eleNumY * Constants.eachEleHeight);
		Constants.mapBottom = TankWar.viewHeight - Constants.eachEleHeight;
		Constants.midLineX = (Constants.mapLeft + Constants.mapRight) / 2;
		Constants.midLineY = (Constants.mapBottom + Constants.mapTop) / 2;
		//��ʼ��������ط�Χ
		Constants.enemyRect = new Rect((int)Constants.mapLeft,(int)Constants.mapTop,(int)Constants.mapRight,(int)(Constants.mapTop + ((eleNumY * Constants.eachEleHeight)/2)));
		Constants.usRect = new Rect((int)Constants.mapLeft,(int)(Constants.mapTop + ((eleNumY * Constants.eachEleHeight)/2)),(int)Constants.mapRight,(int)Constants.mapBottom);
       		
		//��ʼ����ͼmap
		initMap();
		//��ʼ������ͼ������Ҫ����Դ��Bitmap[] ��  float[][]
		initDrawMapSource();
		
	}

	/**
	 * @description 
	 * �� ����ά����mapModel��װΪMap����
     * �� ����˫��̹�˵ĳ�û����
	 */
	private void initMap(){
		map = new HashMap<Rect,Integer>();
		Constants.enemyTankShowRectList = new ArrayList<Rect>();
		Constants.usTankShowRectList = new ArrayList<Rect>(); 
		int[] mapRow;
		int eleType;
		Rect rect = null;
		int left = -1;
		int top = -1;
		int right = -1;
		int bottom = -1;
		for(int i=0;i<Constants.mapModel.length;i++){
			top = (int) (Constants.mapTop + i * Constants.eachEleHeight);
			bottom = (int) (top + Constants.eachEleHeight);
			mapRow = Constants.mapModel[i];
			for(int j=0;j<mapRow.length;j++){
				left = (int) (Constants.mapLeft + j * Constants.eachEleWidth);
				right = (int) (left + Constants.eachEleWidth);
				rect = new Rect(left,top,right,bottom);
				eleType = mapRow[j];
				map.put(rect, eleType);//����map��
			
				//��ʼ��  ������̹�˳��ֵ����򡯡�����ש���򡯡�����ת����
				if(eleType == Constants.type_grass){
					if(i > Constants.mapModel.length / 2){
						Constants.usTankShowRectList.add(rect);
					}else if(i > Constants.mapModel.length / 4 && j > mapRow.length / 2 && j < mapRow.length / 4 * 3){
						Constants.enemyTankShowRectList.add(rect);
					}
				}
				//�Գ�����
				left = (int) (Constants.mapLeft + (mapRow.length * 2 - j -1) * Constants.eachEleWidth);
				right = (int) (left + Constants.eachEleWidth);
				rect = new Rect(left,top,right,bottom);
				if(eleType == Constants.type_boss1){
					eleType = Constants.type_boss2;
				}else if(eleType == Constants.type_boss3){
					eleType = Constants.type_boss4;
				}
				//��ʼ������̹�˳��ֵ����򡢺�ש���򡢸�ת����
				if(eleType == Constants.type_grass){
					if(i > Constants.mapModel.length / 2){
						Constants.usTankShowRectList.add(rect);
					}else if(i > Constants.mapModel.length / 4 && j > mapRow.length / 2 && j < mapRow.length / 4 * 3){
						Constants.enemyTankShowRectList.add(rect);
					}
				}
				
				map.put(rect, eleType);
			}
			
		}
		int x = (int)((Constants.mapLeft + Constants.mapRight - TankWar.user_up.getWidth())/2);
		int y = (int)(Constants.mapBottom - TankWar.userBgBitmap.getHeight()/2);
		Constants.userShowRect = new Rect(x,y,x+ TankWar.user_up.getWidth(),y+TankWar.user_up.getHeight());//�û���̹�˳����ڵײ��м�

	}
	/**
	 * @description 
	 * �� ����map���󣬳�ʼ����ͼ��Դ��Bitmap[]��float[][]��
     * �ڳ�ʼ����ͼ���Ĵ�����boss��grass��redBrick��whiteBrick���ڵ�����
	 */
	private void initDrawMapSource(){
		int size = map.keySet().size();
		Rect[] rectArr = map.keySet().toArray(new Rect[size]);
		mapBitmapList = new ArrayList<Bitmap>();
		positionList = new ArrayList<float[]>();
		float[] position = null;
		Rect rect = null;
		int eleType = -1;
		int x = -1;
		int y = -1;
		Constants.bossRectEletList = new ArrayList<GameMapElement>();
		Constants.grassRectEletList = new ArrayList<GameMapElement>();
		Constants.redBrickRectEleList = new ArrayList<GameMapElement>(); 
		Constants.whiteBrickRectEletList = new ArrayList<GameMapElement>(); 
		GameMapElement gameMapElement = null;
		for(int i=0;i<rectArr.length;i++){
			rect = rectArr[i];
			x  = rect.left;
			y = rect.top;
			position = new float[2];
			position[0] = x;
			position[1] = y;
			positionList.add(position);
			eleType = map.get(rect);
			switch(eleType){
			case Constants.type_road:
				mapBitmapList.add(TankWar.road);
				gameMapElement = new GameMapElement(Constants.type_road,0,rect);
				Constants.grassRectEletList.add(gameMapElement);
				break;
			case Constants.type_redbric:
				mapBitmapList.add(TankWar.brick_red);
				gameMapElement = new GameMapElement(Constants.type_redbric,0,rect);
				Constants.redBrickRectEleList.add(gameMapElement);
				break;
			case Constants.type_whiltebric:
				mapBitmapList.add(TankWar.brick_white);
				gameMapElement = new GameMapElement(Constants.type_whiltebric,0,rect);
				Constants.whiteBrickRectEletList.add(gameMapElement);
				break;
			case Constants.type_boss1:
				mapBitmapList.add(TankWar.boss1);
				gameMapElement = new GameMapElement(Constants.type_boss1,0,rect);
				Constants.bossRectEletList.add(gameMapElement);
				break;
			case Constants.type_boss2:
				mapBitmapList.add(TankWar.boss2);
				gameMapElement = new GameMapElement(Constants.type_boss2,0,rect);
				Constants.bossRectEletList.add(gameMapElement);
				break;
			case Constants.type_boss3:
				mapBitmapList.add(TankWar.boss3);
				gameMapElement = new GameMapElement(Constants.type_boss3,0,rect);
				Constants.bossRectEletList.add(gameMapElement);
				break;
			case Constants.type_boss4:
				mapBitmapList.add(TankWar.boss4);
				gameMapElement = new GameMapElement(Constants.type_boss4,0,rect);
				Constants.bossRectEletList.add(gameMapElement);
				break;
			case Constants.type_grass:
				mapBitmapList.add(TankWar.grass);
				gameMapElement = new GameMapElement(Constants.type_grass,0,rect);
				Constants.grassRectEletList.add(gameMapElement);
				break;
				
			}
		}

	}
	/**
	 * @description �����ṩ�Ľӿ�1����÷�װ�˵�ͼ��ÿ������(Rect)�ϴ�ŵĵ�ͼԪ������
	 * @return
	 */
	public Map<Rect,Integer> getMap(){
		return map;
	}
	/**
	 * @description �����ṩ�Ľӿ�2����û���ͼʱ����Ҫ��Bitmap[]
	 * @return
	 */
	public List<Bitmap> getMapBitmapList(){
		return mapBitmapList;
	}
	/**
	 * @description �����ṩ�Ľӿ�3����û���ͼʱ����Ҫ��float[][]
	 * @return
	 */
	public List<float[]> getMapPostionList(){
		return positionList;
	}
	
	
}
