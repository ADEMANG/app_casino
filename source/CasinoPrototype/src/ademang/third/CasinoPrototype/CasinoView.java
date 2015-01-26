package ademang.third.CasinoPrototype;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface CasinoView {
	int SCENE_NONE = 0;
	public void draw(Canvas canvas);					//引数のCanvasにゲーム画面の描画を行う
	public int changeMoney();							//お金の変動があった場合，変動値を戻り値に指定する
	public int moveScene();								//描画する画面が切り替わるときに，切り替わる先の画面を指定する
	public void processTouchEvent(MotionEvent event);	//タッチイベントに対する処理
}
