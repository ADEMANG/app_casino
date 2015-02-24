package Select;

import ademang.third.CasinoPrototype.*;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Select implements Games,SceneValues{
	
	private Bitmap im_blackjack;
	private Rect src_rect_blackjack;
	private Bitmap im_baccarat;
	private Rect src_rect_baccarat;
	
	private Rect rect_background;
	private Rect rect_blackjack;
	private Rect rect_baccarat;
	
	private int scene;					//return next scene
	private int w;						//Screen width
	private int h;						//Screen height
	private boolean touch;				//Use processTouchEvent
	private Rect select_rect;			//Use processTouchEvent
	
	public Select(Resources r){
		scene = SCENE_NONE;
		touch = false;
		
		loadResources(r);
	}
	public void draw(Canvas canvas){
		//Initialization
		w = canvas.getWidth();
		h = canvas.getHeight();
		
		rect_background = new Rect(0,0,w,h);
		rect_blackjack = new Rect(w/5,h/5,w*2/5,h*3/5);
		rect_baccarat = new Rect(w*2/5,h/5,w*3/5,h*3/5);
		
		
		canvas.drawBitmap(im_blackjack, src_rect_blackjack, rect_blackjack, null);		//BlackJack
		
		canvas.drawBitmap(im_baccarat, src_rect_baccarat, rect_baccarat, null);		//Baccarat
		
		this.printDebug(canvas);
	}
	public int changeMoney(){
		return 0;
	}
	public int moveScene(){
		return scene;
	}
	public void processTouchEvent(MotionEvent event){
		Rect touchRect = new Rect((int)event.getX()-10,(int)event.getY()-10,(int)event.getX()+10,(int)event.getY()+10);
		
		if(touch == false && event.getAction() == MotionEvent.ACTION_DOWN){
			touch = true;
			if(Rect.intersects(rect_blackjack, touchRect)){                                                                                                                 
				select_rect = rect_blackjack;
			}else if(Rect.intersects(rect_baccarat, touchRect)){
				select_rect = rect_baccarat;
			}
		}else if(touch == true && event.getAction() == MotionEvent.ACTION_UP){
			touch = false;
			if(Rect.intersects(rect_blackjack, touchRect) && select_rect.equals(rect_blackjack)){
				scene = SCENE_BLACKJACK;
			}else if(Rect.intersects(rect_baccarat, touchRect) && select_rect.equals(rect_baccarat)){
				scene = SCENE_BACCARAT;
			}
		}
	}
	
	private void loadResources(Resources r){
		im_blackjack = BitmapFactory.decodeResource(r, R.drawable.select_blackjack);
		src_rect_blackjack = new Rect(0, 0, im_blackjack.getWidth(), im_blackjack.getHeight());
		im_baccarat = BitmapFactory.decodeResource(r, R.drawable.select_baccarat);
		src_rect_baccarat = new Rect(0, 0, im_baccarat.getWidth(), im_baccarat.getHeight());
		
	}
	
	public void printDebug(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawText("touch = " + touch + "\t scene = " + scene + 
						"\n select_rect = " + select_rect, 10, h-200, paint);
	}
}
