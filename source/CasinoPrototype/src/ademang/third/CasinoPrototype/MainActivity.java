package ademang.third.CasinoPrototype;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	public static MainActivity mainActivity;
	private MainView view;
	private TickHandler tickHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mainActivity = this;
		view = new MainView(this);
		tickHandler = new TickHandler();
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
		layout.addView(view);
		
	}
	@Override
	public void onResume(){
		super.onResume();
		tickHandler = new TickHandler();
		tickHandler.sleep(0);
	}
	@Override
	public void onPause(){
		super.onPause();
		tickHandler = null;
	}
	public static void exit(){
		mainActivity.finish();
	}
	
	//Periodic processing handler
	public class TickHandler extends Handler {
		//Periodic processing
		@Override
		public void handleMessage(Message msg){
			view.invalidate();
			if(tickHandler != null)tickHandler.sleep(75);
		}
		//Stop the process
		public void sleep(long how){
			removeMessages(0);
			sendMessageDelayed(obtainMessage(0),how);
		}
	}
}
