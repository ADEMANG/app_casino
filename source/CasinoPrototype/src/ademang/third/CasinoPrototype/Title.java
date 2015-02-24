package ademang.third.CasinoPrototype;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Title implements Games,SceneValues{
	private int scene;
	private boolean touch;			//Use processTouchEvent
	public Title(Resources r){
		scene = SCENE_NONE;
		touch = false;
		loadResources(r);
	}
	public void draw(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		canvas.drawRect(canvas.getWidth()/2-200, canvas.getHeight()/2-200, canvas.getWidth()/2+200, canvas.getHeight()/2+200, paint);
	}
	public int changeMoney(){
		return 0;
	}
	public int moveScene(){
		return scene;
	}
	public void processTouchEvent(MotionEvent event){
		if(touch == false && event.getAction() == MotionEvent.ACTION_DOWN){
			touch = true;
		}else if(touch == true && event.getAction() == MotionEvent.ACTION_UP){
			touch = false;
			scene = SCENE_SELECT;
		}
	}
	private void loadResources(Resources r){
		
	}
}
