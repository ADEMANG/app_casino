package ademang.third.CasinoPrototype;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import Baccarat.*;
import BlackJack.*;
import Select.*;

public class MainView extends View implements SceneValues{
	private Menu menu;
	private int scene;			//Screen will be drown
	private Games nowGame; 		//Screen currently drawing games

	private boolean apper_menu;	
	private long startTime,endTime;
	
	public MainView(Context context){
		super(context);
		menu = new Menu(getResources());
		nowGame = new Title(getResources());
		scene = SCENE_NONE;
		apper_menu = false;
		startTime = 0;
		endTime = 0;
	}
	
	@Override
	public void onDraw(Canvas canvas){
		nowGame.draw(canvas);
		this.printDebug(canvas);
		if(apper_menu){
			menu.draw(canvas);
		}
		
		nowGame.changeMoney();
		scene = nowGame.moveScene();
		transScene();
	}
	/**
	 * シーンの切り替え時にnowGameの参照先を付け替える
	 */
	private void transScene(){
		/*
		 * In production
		 */
		switch(scene){
		case SCENE_SELECT:
			apper_menu = true;
			nowGame = new Select(getResources());
			break;
		case SCENE_BLACKJACK:
			nowGame = new BlackJackMain(getResources());
			break;
		case SCENE_BACCARAT:
			nowGame = new Baccarat(getResources());
			break;
		default:
			break;
		}
		scene = SCENE_NONE;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		/*
		 * menu
		 */
		nowGame.processTouchEvent(event);		//Send Event to games
		return true;
	}
	
	private void printDebug(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		endTime = System.currentTimeMillis();
		canvas.drawText("scene = " + scene, 10, canvas.getHeight()-300, paint);
		canvas.drawText("Draw frame requisite time = " + (endTime - startTime), canvas.getWidth()-200, canvas.getHeight()-300, paint);
		startTime = endTime;
	}
}
