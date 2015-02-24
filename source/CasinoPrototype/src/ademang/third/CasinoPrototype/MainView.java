package ademang.third.CasinoPrototype;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import BlackJack.*;
import Select.*;

public class MainView extends View implements SceneValues{
	private Menu menu;
	private int scene;			//Screen will be drown
	private Games nowGame; 		//Screen currently drawing games
<<<<<<< HEAD
	private boolean apper_menu;
	
	private long startTime,endTime;
=======
>>>>>>> 6f002c3fab3d1274d4489cf7abd6724ae4612ccf
	
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
<<<<<<< HEAD
		nowGame.draw(canvas);
		this.printDebug(canvas);
		if(apper_menu){
			menu.draw(canvas);
		}
		
		nowGame.changeMoney();
		scene = nowGame.moveScene();
		transScene();
		
=======
		//ï`âÊä÷åW
		nowGame.draw(canvas);
		menu.draw(canvas);
		//Ç®ã‡ä÷åW
		nowGame.changeMoney();
		//ÉVÅ[Éìä÷åW
		scene = nowGame.moveScene();
		
		transScene();
>>>>>>> 6f002c3fab3d1274d4489cf7abd6724ae4612ccf
	}
	/**
	 * ÉVÅ[ÉìÇÃêÿÇËë÷Ç¶éûÇ…nowGameÇÃéQè∆êÊÇïtÇØë÷Ç¶ÇÈ
	 */
	private void transScene(){
		/*
		 * In production
		 */
		switch(scene){
		case SCENE_SELECT:
<<<<<<< HEAD
			apper_menu = true;
=======
>>>>>>> 6f002c3fab3d1274d4489cf7abd6724ae4612ccf
			nowGame = new Select(getResources());
			break;
		case SCENE_BLACKJACK:
			nowGame = new BlackJackMain(getResources());
			break;
		case SCENE_BACCARAT:
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
