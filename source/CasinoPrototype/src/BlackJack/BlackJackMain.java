package BlackJack;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import ademang.third.CasinoPrototype.*;

public class BlackJackMain implements SceneValues,Games{
	private Canvas canvas;
	private int scene;
	
	public BlackJackMain(Resources r){
		this.loadResource(r);
	}
	public void draw(Canvas canvas){
		this.canvas = canvas;
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		canvas.drawRect(0, 0, 100, 100, paint);
	}
	public int changeMoney(){
		return 0;
	}
	public int moveScene(){
		return SCENE_NONE;
	}
	public void processTouchEvent(MotionEvent event){
		
	}
	
	
	private void loadResource(Resources r){
		
	}
}
