package ademang.third.CasinoPrototype;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import BlackJack.*;
import Select.*;

public class MainView extends View implements SceneValues{
	private Menu menu;
	private int scene;			//Screen will be drown
	private Games nowGame; 		//Screen currently drawing games
	
	public MainView(Context context){
		super(context);
		menu = new Menu(getResources());
		nowGame = new Title(getResources());
		scene = SCENE_NONE;
	}
	
	@Override
	public void onDraw(Canvas canvas){
		//�`��֌W
		nowGame.draw(canvas);
		menu.draw(canvas);
		//�����֌W
		nowGame.changeMoney();
		//�V�[���֌W
		scene = nowGame.moveScene();
		
		transScene();
	}
	/**
	 * �V�[���̐؂�ւ�����nowGame�̎Q�Ɛ��t���ւ���
	 */
	private void transScene(){
		/*
		 * In production
		 */
		switch(scene){
		case SCENE_SELECT:
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
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		/*
		 * menu
		 */
		nowGame.processTouchEvent(event);		//Send Event to games
		return true;
	}
}
