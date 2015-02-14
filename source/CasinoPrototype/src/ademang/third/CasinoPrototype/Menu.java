package ademang.third.CasinoPrototype;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Menu implements SceneValues{
	private Canvas canvas;
	
	public Menu(Resources r){
		
		
		loadResources(r);
	}
	public void draw(Canvas canvas){
		this.canvas = canvas;
		Paint paint = new Paint();
		paint.setColor(Color.MAGENTA);
		canvas.drawRect(0, canvas.getHeight()*9/10, canvas.getWidth(), canvas.getHeight(), paint);
		
	}
	public void processTouchEvent(MotionEvent e){
		int w = canvas.getWidth();
		int h = canvas.getHeight();
		
	}
	public int moveScene(){
		return SCENE_NONE;
	}
	private void loadResources(Resources r){
		
	}
}
