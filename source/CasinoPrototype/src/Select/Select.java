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
	private Bitmap im_background;
	private Rect src_background;
	private Bitmap im_blackjack;
	private Rect src_blackjack;
	private Bitmap im_baccarat;
	private Rect src_baccarat;
	private Bitmap im_poker;
	private Rect src_poker;
	private Bitmap im_ctr_left,im_ctr_right;
	private Rect src_ctr_left,src_ctr_right;

	private Rect rect_background;
	private Rect rect_select1,rect_select2,rect_select3;
	private Rect rect_ctr_left,rect_ctr_right;
	private int state;
	
	private int scene;					//return next scene
	private int w;						//Screen width
	private int h;						//Screen height
	private boolean touch;				//Use processTouchEvent
	private Rect select_rect;			//Use processTouchEvent

	public Select(Resources r){
		scene = SCENE_NONE;
		touch = false;
		state = 0;
		loadResources(r);
	}
	
	public void draw(Canvas canvas){
		//Initialization
		w = canvas.getWidth();
		h = canvas.getHeight();
		
		rect_background = new Rect(0,0,w,h);
		rect_select1 = new Rect(w/10,h/5,w*3/10,h*3/5);
		rect_select2 = new Rect(w*4/10,h/5,w*6/10,h*3/5);
		rect_select3 = new Rect(w*7/10,h/5,w*9/10,h*3/5);
		
		rect_ctr_left = new Rect(0,h*4/10,w/10,h*5/10);
		rect_ctr_right = new Rect(w*9/10,h*4/10,w,h*5/10);
		
		canvas.drawBitmap(im_background, src_background, rect_background, null);
		switch(state){
		case 0:
			canvas.drawBitmap(im_blackjack, src_blackjack, rect_select1, null);
			canvas.drawBitmap(im_baccarat, src_baccarat, rect_select2, null);
			canvas.drawBitmap(im_poker, src_poker, rect_select3, null);
			break;
		
		}
		canvas.drawBitmap(im_ctr_left, src_ctr_left, rect_ctr_left, null);
		canvas.drawBitmap(im_ctr_right, src_ctr_right, rect_ctr_right, null);
		
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
			if(Rect.intersects(rect_select1, touchRect)){                                                                                                                 
				select_rect = rect_select1;
			}else if(Rect.intersects(rect_select2, touchRect)){
				select_rect = rect_select2;
			}else if(Rect.intersects(rect_ctr_left, touchRect)){
				select_rect = rect_ctr_left;
			}else if(Rect.intersects(rect_ctr_right, touchRect)){
				select_rect = rect_ctr_right;
			}
		}else if(touch == true && event.getAction() == MotionEvent.ACTION_UP){
			touch = false;
			if(Rect.intersects(rect_select1, touchRect) && select_rect.equals(rect_select1)){
				switch(state){
				case 0:
					scene = SCENE_BLACKJACK;
					break;
				}
			}else if(Rect.intersects(rect_select2, touchRect) && select_rect.equals(rect_select2)){
				switch(state){
				case 0:
					scene = SCENE_BACCARAT;
					break;
				}
			}else if(Rect.intersects(rect_select3, touchRect) && select_rect.equals(rect_select3)){
				switch(state){
				case 0:
					scene = SCENE_POKER;
					break;
				}
			}else if(Rect.intersects(rect_ctr_left, touchRect) && select_rect.equals(rect_ctr_left)){
				state--;
			}else if(Rect.intersects(rect_ctr_right, touchRect) && select_rect.equals(rect_ctr_right)){
				state++;
			}
		}
	}
	
	private void loadResources(Resources r){
		im_background = BitmapFactory.decodeResource(r, R.drawable.select_background);
		src_background = new Rect(0, 0, im_background.getWidth(), im_background.getHeight());
		im_blackjack = BitmapFactory.decodeResource(r, R.drawable.select_blackjack);
		src_blackjack = new Rect(0, 0, im_blackjack.getWidth(), im_blackjack.getHeight());
		im_baccarat = BitmapFactory.decodeResource(r, R.drawable.select_baccarat);
		src_baccarat = new Rect(0, 0, im_baccarat.getWidth(), im_baccarat.getHeight());
		im_poker = BitmapFactory.decodeResource(r, R.drawable.select_poker);
		src_poker = new Rect(0, 0, im_poker.getWidth(), im_poker.getHeight());
		im_ctr_left = BitmapFactory.decodeResource(r, R.drawable.ctr_left);
		src_ctr_left = new Rect(0, 0, im_ctr_left.getWidth(), im_ctr_left.getHeight());
		im_ctr_right = BitmapFactory.decodeResource(r, R.drawable.ctr_right);
		src_ctr_right = new Rect(0, 0, im_ctr_right.getWidth(), im_ctr_right.getHeight());
	}
	
	public void printDebug(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawText("touch = " + touch +
						"\t scene = " + scene + 
						"\n select_rect = " + select_rect +
						"\n state = " + state
						, 10, h-200, paint);
	}
}
