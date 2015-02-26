package ademang.third.CasinoPrototype;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Menu implements SceneValues{
	private int w,h;
	
	private Bitmap ctr_back;
	private Rect src_ctr_back;
	private Rect rect_menubar;
	private Rect rect_back_off,rect_back_on;
	
	private boolean touch;
	private Rect select_rect;
	public Menu(Resources r){
		touch = false;
		loadResources(r);
	}
	public void draw(Canvas canvas){
		w = canvas.getWidth();
		h = canvas.getHeight();
		Paint paint = new Paint();
		paint.setColor(Color.MAGENTA);
		
		rect_menubar = new Rect(0, h*9/10, w, h);
		int button_size = w/10;
		rect_back_off = new Rect(0, h-button_size, button_size, h);
		rect_back_on = new Rect(0, h-2*button_size, 2*button_size, h);
		
		canvas.drawRect(rect_menubar, paint);
		canvas.drawBitmap(ctr_back, src_ctr_back, rect_back_off, null);
		
		
	}
	public void processTouchEvent(MotionEvent event){
		Rect touchRect = new Rect((int)event.getX()-10, (int)event.getY()-10, (int)event.getX()+10, (int)event.getY()+10);
		
		if(touch == false && event.getAction() == MotionEvent.ACTION_DOWN){
			touch = true;
			if(Rect.intersects(rect_back_off, touchRect)){
				select_rect = rect_back_off;
			}
		}
		if(touch == true && event.getAction() == MotionEvent.ACTION_UP){
			touch = false;
			if(select_rect == rect_back_off && Rect.intersects(rect_back_on, touchRect)){
				//please waiting
			}
		}
	}
	public int moveScene(){
		return SCENE_NONE;
	}
	private void loadResources(Resources r){
		ctr_back = BitmapFactory.decodeResource(r, R.drawable.menu_back);
		src_ctr_back = new Rect(0, 0, ctr_back.getWidth(), ctr_back.getHeight());
	}
}
