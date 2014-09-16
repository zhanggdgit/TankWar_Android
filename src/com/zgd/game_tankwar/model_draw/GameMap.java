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
	//整体地图只用画一次，只有砖块等收到攻击时，才在受攻击的地方重画
	boolean isDrawed = false;
	//一行、一列分别显示的地图元素的个数
	private int eleNumX;//每一行显示的元素的个数
	private int eleNumY;//每一列显示的元素的个数

	//将上面的地图模型mapModel封装为键值对：区域Rect---存放的元素类型
	//设置为成员变量是因为，坦克攻击地图上的砖墙后，直接修改该map变量，使得受攻击的Rect对应的类型改变，如有红砖改为空
	private Map<Rect,Integer> map = null;
	
	public GameMap(){
		isDrawed = false;//有了isDrawed时，就不能用单例模式了。
		//初始化每个地图元素的宽高
		Constants.eachEleWidth = TankWar.grass.getWidth();
		Constants.eachEleHeight = TankWar.grass.getHeight();
		//确定地图的左上角 和 右下角 的点坐标
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
		//初始化敌我阵地范围
		Constants.enemyRect = new Rect((int)Constants.mapLeft,(int)Constants.mapTop,(int)Constants.mapRight,(int)(Constants.mapTop + ((eleNumY * Constants.eachEleHeight)/2)));
		Constants.usRect = new Rect((int)Constants.mapLeft,(int)(Constants.mapTop + ((eleNumY * Constants.eachEleHeight)/2)),(int)Constants.mapRight,(int)Constants.mapBottom);
       		
		//初始化地图map
		initMap();
		//初始化画地图是所需要的资源：Bitmap[] 和  float[][]
		initDrawMapSource();
		
	}

	/**
	 * @description 
	 * ① 将二维数组mapModel封装为Map对象
     * ② 敌我双方坦克的出没区域
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
				map.put(rect, eleType);//放入map中
			
				//初始化  ‘敌我坦克出现的区域’、‘红砖区域’、‘钢转区域’
				if(eleType == Constants.type_grass){
					if(i > Constants.mapModel.length / 2){
						Constants.usTankShowRectList.add(rect);
					}else if(i > Constants.mapModel.length / 4 && j > mapRow.length / 2 && j < mapRow.length / 4 * 3){
						Constants.enemyTankShowRectList.add(rect);
					}
				}
				//对称区域
				left = (int) (Constants.mapLeft + (mapRow.length * 2 - j -1) * Constants.eachEleWidth);
				right = (int) (left + Constants.eachEleWidth);
				rect = new Rect(left,top,right,bottom);
				if(eleType == Constants.type_boss1){
					eleType = Constants.type_boss2;
				}else if(eleType == Constants.type_boss3){
					eleType = Constants.type_boss4;
				}
				//初始化敌我坦克出现的区域、红砖区域、钢转区域
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
		Constants.userShowRect = new Rect(x,y,x+ TankWar.user_up.getWidth(),y+TankWar.user_up.getHeight());//用户的坦克出现在底部中间

	}
	/**
	 * @description 
	 * ① 根据map对象，初始化画图资源：Bitmap[]和float[][]，
     * ②初始化地图的四大区域boss、grass、redBrick、whiteBrick所在的区域
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
	 * @description 向外提供的接口1：获得封装了地图上每块区域(Rect)上存放的地图元素类型
	 * @return
	 */
	public Map<Rect,Integer> getMap(){
		return map;
	}
	/**
	 * @description 向外提供的接口2：获得画地图时所需要的Bitmap[]
	 * @return
	 */
	public List<Bitmap> getMapBitmapList(){
		return mapBitmapList;
	}
	/**
	 * @description 向外提供的接口3：获得画地图时所需要的float[][]
	 * @return
	 */
	public List<float[]> getMapPostionList(){
		return positionList;
	}
	
	
}
