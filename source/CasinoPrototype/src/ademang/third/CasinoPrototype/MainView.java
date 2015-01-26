package ademang.third.CasinoPrototype;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View{
	private int x;
	private int y;
	private int scene;			//���ɕ`�悷���ʂ��Ǘ�����ϐ�
	private CasinoView nowGame; //���ݕ`�悵�Ă�����
	
	public MainView(Context context){
		super(context);
		x = 0;
		y = 0;
		nowGame = null;
	}
	
	@Override
	public void onDraw(Canvas canvas){
		/* �܂��e��ʂ��ł��ĂȂ��̂ŃR�����g�A�E�g
		 * nowGame.draw();
		 * nowGame.changeMoney();
		 * scene = nowGame.moveScene();
		 * if(scene != SCENE_NONE){		SCENE_NONE�͉�ʑJ�ڂȂ�
		 * 		this.transScene();
		 * }
		 */
	}
	/**
	 * �V�[���̐؂�ւ�����nowGame�̎Q�Ɛ��t���ւ���
	 */
	private void transScene(){
		/*
		 * �Q�[����ʂ��ł����揇���ǉ�
		 */
		switch(scene){
		default:
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		x = (int)event.getX();
		y = (int)event.getY();
		/*
		 * �S�̂�ʂ��Ẵ��j���[�I�Ȃ̂�����΂����ɒǋL
		 */
		nowGame.processTouchEvent(event);		//���ݕ`�悵�Ă����ʂɃ^�b�`�C�x���g��������
		return true;
	}
}
