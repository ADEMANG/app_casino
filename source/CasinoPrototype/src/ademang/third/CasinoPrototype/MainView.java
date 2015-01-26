package ademang.third.CasinoPrototype;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View{
	private int x;
	private int y;
	private int scene;			//次に描画する画面を管理する変数
	private CasinoView nowGame; //現在描画している画面
	
	public MainView(Context context){
		super(context);
		x = 0;
		y = 0;
		nowGame = null;
	}
	
	@Override
	public void onDraw(Canvas canvas){
		/* まだ各画面ができてないのでコメントアウト
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
		 * ゲーム画面ができ次第順次追加
		 */
		switch(scene){
		default:
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		x = (int)event.getX();
		y = (int)event.getY();
		/*
		 * 全体を通してのメニュー的なのがあればここに追記
		 */
		nowGame.processTouchEvent(event);		//現在描画している画面にタッチイベントをあげる
		return true;
	}
}
