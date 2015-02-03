package ademang.third.CasinoPrototype;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Games {
	public void draw(Canvas canvas);					//Do draw the game screen to Canvas of argument
	public int changeMoney();							//If there is a change of money , I will be specified in the return value variation value
	public int moveScene();								//When the screen to be drawn is switched , to specify the screen of switched earlier
	public void processTouchEvent(MotionEvent event);	//The process for the touch events
}
