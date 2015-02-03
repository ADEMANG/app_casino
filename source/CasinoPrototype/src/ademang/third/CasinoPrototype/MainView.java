package ademang.third.CasinoPrototype;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View implements SceneValues{
	private int scene;			//Screen will be drown
	private Games nowGame; //Screen currently drawing games
	
	public MainView(Context context){
		super(context);
		scene = SCENE_NONE;
	}
	
	@Override
	public void onDraw(Canvas canvas){
		/* In production of games
		 * nowGame.draw();
		 * nowGame.changeMoney();
		 * scene = nowGame.moveScene();
		 * if(scene != SCENE_NONE){		SCENE_NONEは画面遷移なし
		 * 		this.transScene();
		 * }
		 */
	}
	/**
	 * シーンの切り替え時にnowGameの参照先を付け替える
	 */
	private void transScene(){
		/*
		 * In production
		 */
		switch(scene){
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
