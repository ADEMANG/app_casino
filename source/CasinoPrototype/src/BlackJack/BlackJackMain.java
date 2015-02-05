package BlackJack;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import ademang.third.CasinoPrototype.*;

public class BlackJackMain extends View implements SceneValues,Games{
	private Canvas canvas;
	private int scene;
	
	public BlackJackMain(Context context){
		super(context);
		this.canvas = canvas;
		this.loadResource();
	}
	@Override
	public void draw(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		canvas.drawRect(0, 0, 100, 100, paint);
	}
	@Override
	public int changeMoney(){
		return 0;
	}
	@Override
	public int moveScene(){
		return SCENE_NONE;
	}
	@Override
	public void processTouchEvent(MotionEvent event){
		
	}
	
	
	private void loadResource(){
		Resources r = getResources();
//		Bitmap club = BitmapFactory.decodeResource(r, R.drawable.club1);
	}
}
