package ademang.third.CasinoPrototype;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface CasinoView {
	int SCENE_NONE = 0;
	public void draw(Canvas canvas);					//������Canvas�ɃQ�[����ʂ̕`����s��
	public int changeMoney();							//�����̕ϓ����������ꍇ�C�ϓ��l��߂�l�Ɏw�肷��
	public int moveScene();								//�`�悷���ʂ��؂�ւ��Ƃ��ɁC�؂�ւ���̉�ʂ��w�肷��
	public void processTouchEvent(MotionEvent event);	//�^�b�`�C�x���g�ɑ΂��鏈��
}
