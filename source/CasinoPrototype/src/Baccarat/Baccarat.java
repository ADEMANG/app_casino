package Baccarat;



import ademang.third.CasinoPrototype.*;
//現在は上辺だけのバカラ．
//API use debuging now
import java.util.Random;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class Baccarat 
implements SceneValues,Games{
//	extends View{
	
	
	
	private Canvas canvas;
	private int	scene;
	private Random rand;
	private byte[] Trump;//シャッフル済みトランプ山札
	final private int trampNum = 52; //カードの枚数
	
	private byte[] playerTrump;//プレイヤーの所持しているトランプ
	private byte[] bankerTrump;//バンカーの所持しているトランプ
	private int trumpCount;//現在，ドローされたトランプの合計
	
	private byte playerTrumpNum;//プレイヤーのトランプの合計
	private byte bankerTrumpNum;//バンカーのトランプの合計
	private char win;//勝者
	private boolean playerDrawTF;//プレイヤーが3枚目をドローを「した」/「してない」
	
	private char winnerPresage;//勝者の予想，もしくは引き分けの予想
	private final char winnerPlayer = 'p';
	private final char winnerBanker = 'b';
	private final char winnerTie = 't';
	
	private int bet;	//ベット
	
	private int sceneSerch;//現在の画面
	private final int parsagePush = 0;//予想
	private final int manyBets = 1; //ベットの金額を変更
	private final int resultGame = 2; //結果
	private final int oneMoreGame = 3;//もう一試合しますか?

	
	
	
	//以下アンドロイドAPI
	private Paint paint;
	private Bitmap[] bitmapTramp;	//トランプビットマップ
	private Bitmap[] parsageWinnerBmp; //勝者の選択画像
	private Bitmap[] selectNext;	//もう一度やるか，やめるかの選択画像
	private Bitmap[] betNumber;		//ベットの表示用
	private Bitmap playButton;		//プレイボタン用
	
	private Rect[] parsageWinBmpDst;//勝者の予想選択画像のRect
	private Rect parsageWinBmpSrc;
	private Rect[] playerTrumpDst;	//プレイヤートランプ画像のRect
	private Rect[] bankerTrumpDst;	//バンカートランプ画像のRect
	private Rect trumpSrc;			
	private Rect[] betBmpDst;		//ベットのRect
	private Rect betBmpSrc;
	
	private int w;					//描画領域の横長さ
	private int h;					//描画領域の縦長さ
	private boolean pflg;			//プレイヤーが3枚目をドローしたかのフラグ
	private boolean bflg;			//バンカーが3枚目をドローしたかのフラグ
	private boolean animeFlgTrump[];//トランプのアニメーションがどの程度まで済んでいるか．
	private int flameInt;			//アニメーションフレーム用
	//コンストラクタ
	public Baccarat(Resources r) {
//	public Baccarat(Context context){
//		super(context);
		this.canvas = canvas;
		trumpSet();
		
		playerTrump = new byte[3];
		bankerTrump = new byte[3];
		trumpCount = 0;
		playerTrumpNum = 0;
		bankerTrumpNum = 0;
		playerDrawTF  = false;
		winnerPresage = 'n';
		sceneSerch = parsagePush;
		bet = 0;
		
		//AndroidAPI
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		

		w=0;
		h=0;
		pflg = false;
		bflg = false;
		flameInt = 240;
		animeFlgTrump = new boolean[6];
		for(int i=0; i<animeFlgTrump.length; i++)
			animeFlgTrump[i] = false; 

		bitmapTramp = new Bitmap[trampNum+1];
		parsageWinnerBmp = new Bitmap[3];
		selectNext = new Bitmap[2];
		betNumber = new Bitmap[10];

		this.loadResource(r);
//		loadResource(getResources());

		
		//勝者予測選択ボタンのRECT変数
		parsageWinBmpDst = new Rect[3];
		parsageWinBmpSrc = new Rect(0,0,parsageWinnerBmp[0].getWidth(),parsageWinnerBmp[0].getHeight());

		//トランプのRECT変数
		playerTrumpDst = new Rect[3];
		bankerTrumpDst = new Rect[3];
		trumpSrc = new Rect(0,0,bitmapTramp[0].getWidth(),bitmapTramp[0].getHeight());
		betBmpDst = new Rect[5];
		betBmpSrc = new Rect(0,0,betNumber[0].getWidth(),betNumber[0].getHeight());
	}
	
	private void loadResource(Resources r){

		bitmapTramp[0] = BitmapFactory.decodeResource(r,R.drawable.trump1back);
		for(int i=1; i<=13; i++){
			int resId1 = r.getIdentifier("trump1spade"+String.valueOf(i),"drawable","ademang.third.CasinoPrototype");
			int resId2 = r.getIdentifier("trump1heart"+String.valueOf(i),"drawable","ademang.third.CasinoPrototype");
			int resId3 = r.getIdentifier("trump1dia"+String.valueOf(i),"drawable","ademang.third.CasinoPrototype");
			int resId4 = r.getIdentifier("trump1club"+String.valueOf(i),"drawable","ademang.third.CasinoPrototype");
			bitmapTramp[i] = BitmapFactory.decodeResource(r,resId1);
			bitmapTramp[i+13] = BitmapFactory.decodeResource(r, resId2);
			bitmapTramp[i+26] = BitmapFactory.decodeResource(r, resId3);
			bitmapTramp[i+39] = BitmapFactory.decodeResource(r, resId4);
		}
		for(int i=0; i<betNumber.length; i++){
			int resId = r.getIdentifier("number"+String.valueOf(i),"drawable","ademang.third.CasinoPrototype");
			betNumber[i] = BitmapFactory.decodeResource(r, resId); 
		}
		//勝者の選択
		parsageWinnerBmp[0] = BitmapFactory.decodeResource(r, R.drawable.e_button_player);		
		parsageWinnerBmp[1] = BitmapFactory.decodeResource(r, R.drawable.e_button_banker);
		parsageWinnerBmp[2] = BitmapFactory.decodeResource(r, R.drawable.e_button_tie);
		selectNext[0] = BitmapFactory.decodeResource(r, R.drawable.e_button_morepray);
		selectNext[1] = BitmapFactory.decodeResource(r, R.drawable.e_button_end);
		playButton = BitmapFactory.decodeResource(r, R.drawable.e_button_play);
	}
	
	public void killResource(){
		for(int i=0; i<=trampNum; i++){
			bitmapTramp[i].recycle();
			bitmapTramp[i] = null;
		}
	}

	
	
	/*
	 * トランプをシャッフル
	 */
	public void trumpSet(){
		rand = new Random();
		Trump = new byte[trampNum];
		for(int i=0; i<trampNum; i++)
			Trump[i] = (byte)(i+1);
		
		byte tmpTrump;
		for(int i=0,tmp=0,tmp1=0; i<trampNum; i++){
			tmp = rand.nextInt(25);
			tmp1 = rand.nextInt(25)+26;
			tmpTrump = Trump[tmp];
			Trump[tmp] = Trump[tmp1];
			Trump[tmp1] = tmpTrump;
			
		}
	}
	
	
	/*
	 * 勝者を返す
	 */
	public char winner(){
		if(playerTrumpNum%10 == bankerTrumpNum%10)
			return 't';
		else if(playerTrumpNum%10 > bankerTrumpNum%10)
			return 'p';
		else 
			return 'b';
		
	}
	
	/*
	 * トランプの合計を計算し，その合計を返す
	 * なお，10以上は0とみなされる
	 */
	public byte sumTrump(byte[] trumpCard,int num){
		byte sum=0;
		byte minus=0;
		for(int i=0; i<num; i++){
			if(40 <= trumpCard[i] && trumpCard[i] <= 52)
				minus=39;
			else if(27 <= trumpCard[i] && trumpCard[i] <= 39)
				minus=26;
			else if(14 <= trumpCard[i] && trumpCard[i] <= 26)
				minus=13;
			else
				minus=0;
				
			if(trumpCard[i]-minus >= 10)
				sum += 0;
			else
				sum += trumpCard[i]-minus;
		}
		return sum;
	}
	
	/*
	 * トランプを一枚ドローする．
	 */
	public byte draw(){
		++trumpCount;
		return Trump[trumpCount-1];
	}	
	
	public boolean canNextGame(){
		if(trumpCount+6 < 52)
			return true;
		else 
			return false;
	}
	
	/*
	 * トランプをプレイヤーとバンカーに配る．
	 */
	public void distribute()
	{
		for(int i=0,k=trumpCount,m=trumpCount+1; i<2; i++,k+=2,m+=2){
			playerTrump[i] = Trump[k];
			bankerTrump[i] = Trump[m];
			trumpCount += 2;
		}
	}
	
	/*
	 * ナチュラル(カードの合計の1桁が"8"もしくは"9")でないかを判定
	 */
	public boolean natural(){
		boolean flag=false;
		if(playerTrumpNum%10 == 8 || playerTrumpNum%10 == 9)
			flag = true;
		if(bankerTrumpNum%10 == 8 || bankerTrumpNum%10 == 9)
			flag = true;
	
		return flag;
								
	}

	/*
	 * プレイヤールールでみて，ドローが可能か不可能か
	 */
	public boolean playerDraw(){
		if(playerTrumpNum%10 <= 5){
			playerDrawTF = true;
			return true;
		}
		else{
			playerDrawTF = false;
			return false;
		}
	}

	/*
	 * バンカールールでみて，ドローが可能か不可能か
	 */
	public boolean bankerDraw(){
		
		
		//プレイヤーがドローしなかった場合
		if(playerDrawTF == false){
			//バンカーの1桁目が5以下の場合にドロー
			if(bankerTrumpNum%10 <= 5)
				return true;
			else
				return false;
		}			
		//バンカーのトランプ合計1桁目が2以下であれば，有無をいわさずドロー
		else if(bankerTrumpNum%10 <= 2)
			return true;
		//バンカーの〃			   3のとき，プレイヤーが引いた
		else if(bankerTrumpNum%10 == 3){
			if(playerTrump[2] == 8)
				return false;
			else 
				return true;
		}
		else if(bankerTrumpNum%10 == 4){
			if(!(playerTrump[2]<=1 && 8>=playerTrump[2]))
				return false;
			else
				return true;
		}
		else if(bankerTrumpNum%10 == 5){
			if(!(playerTrump[2]<=4 && 7>=playerTrump[2]))
				return false;
			else
				return true;
		}
		else if(bankerTrumpNum%10 == 6){
			if(playerTrump[2]!=6 && playerTrump[2]!=7)
				return false;
			else
				return true;
		}
		else if(bankerTrumpNum%10 == 7)
			return false;
		else
			return false;
		
	}
		
	public int changeMoney() {
		if(winnerPresage == win){
			if(winnerPresage == winnerPlayer)
				bet *= 2;
			else if (winnerPresage == winnerBanker)
				bet *= 1.95;
			else
				bet *= 8;
				
		}
		else
			bet *= -1;
			
		
		return bet;
	}
		
	public int moveScene() {
		
		return 0;
	}


	public void animation(){
		if(sceneSerch == resultGame){
			
		}
	}
	
	//トランプを配る
	public void trumpDistribute(){
		distribute();
		int pn = 2;
		int bn = 2;
		playerTrumpNum = 0;
		bankerTrumpNum = 0;
		playerTrumpNum = sumTrump(playerTrump,pn);
		bankerTrumpNum = sumTrump(bankerTrump,bn);
		if(natural()){
			win = winner();
			pflg = bflg = false; 
		}
		else{
			if(playerDraw() == true){
				playerTrump[2] = draw();
				pflg = true;
				pn++;
			}
			else 
				pflg = false;
			
			if(bankerDraw() == true){
				bankerTrump[2] = draw();
				bflg = true;
				bn++;
			}
			else
				bflg = false;
			playerTrumpNum = sumTrump(playerTrump,pn);
			bankerTrumpNum = sumTrump(bankerTrump,bn);
			win = winner();
		}

	}
	
	//Dstを返すだけの関数
	public Rect returnDst(double num,int w,int h, Bitmap b){
		return new Rect(0, 0, (int)(w/90/num*b.getWidth()),(int)(h/160/num*b.getHeight()));
	}
	
	/*
	 * 実行部分
	 * drawText use Debug
	 */
	
	public void draw(Canvas canvas){
//	@Override
//	public void onDraw(Canvas canvas){
		//描画領域の最大値を求める
		w = canvas.getWidth();
		h = canvas.getHeight();


		
		//予測ボタンのRECTを書き換える
		for(int i=0; i<parsageWinnerBmp.length; i++){
			parsageWinBmpDst[i] = returnDst(2.5, w, h, parsageWinnerBmp[i]);
			parsageWinBmpDst[i].offsetTo((w/2)-(parsageWinBmpDst[i].width()/2)-50, h/4*(i)+50);
		}
		
		//トランプのRECTを書き換える
		for(int i=0; i<3; i++){
			playerTrumpDst[i] = returnDst(3, w, h, bitmapTramp[0]);
			bankerTrumpDst[i] = returnDst(3, w, h, bitmapTramp[0]);
			//トランプの描画ポジションを設定
			playerTrumpDst[i].offsetTo(20+i*100, 20);
			bankerTrumpDst[i].offsetTo(20+i*100, 400);
		}
		
		//掛け金の表示RECT
		for(int i=0; i<betBmpDst.length; i++){
			betBmpDst[i] = returnDst(1.5, w, h, betNumber[0]);
			betBmpDst[i].offsetTo(w-(w/5)-(betBmpDst[i].width()*i), (h/4));
		}
		
		
		
		
//		animation();
		
		//勝者予測選択画面の表示
		if(sceneSerch == parsagePush){
			for(int i=0; i<parsageWinnerBmp.length; i++)
				canvas.drawBitmap(parsageWinnerBmp[i], parsageWinBmpSrc, parsageWinBmpDst[i],paint);
		}
		
		//掛け金の変更
		else if(sceneSerch == manyBets){
			int tmp = bet;
			for(int i=1; i<=betBmpDst.length; i++){
				canvas.drawBitmap(betNumber[tmp%10], betBmpSrc,betBmpDst[i-1], paint);
				tmp /= 10;
			}
		}
		
		//ゲームの結果の表示
		else if(sceneSerch == resultGame){
			//ゲームの結果画面．
			trumpDistribute();
			viewTrump(canvas);
			sceneSerch = oneMoreGame;
		}
		 
		//その他
		else{
			viewTrump(canvas);
			changeMoney();
			//デバッグ用
			canvas.drawText(playerTrump[0]+" "+playerTrump[1]+" "+playerTrump[2]+" = > "+ playerTrumpNum, 50, 650, paint);
			canvas.drawText(bankerTrump[0]+" "+bankerTrump[1]+" "+bankerTrump[2]+" = > " + bankerTrumpNum, 50, 675, paint);
			canvas.drawText("useTramp = > " + trumpCount, 50, 700, paint);
			canvas.drawText("Winner == " + win, 50, 725, paint);
			canvas.drawText("what ==" + pflg, 50,750, paint);			
		}
	}
	public void viewTrump(Canvas canvas){
		for(int i=0; i<2; i++){
			canvas.drawBitmap(bitmapTramp[playerTrump[i]],trumpSrc,playerTrumpDst[i],paint);
			canvas.drawBitmap(bitmapTramp[bankerTrump[i]],trumpSrc,bankerTrumpDst[i],paint);
		}
		if(pflg==true)
			canvas.drawBitmap(bitmapTramp[playerTrump[2]],trumpSrc,playerTrumpDst[2],paint);
		if(bflg==true)
			canvas.drawBitmap(bitmapTramp[bankerTrump[2]],trumpSrc,bankerTrumpDst[2],paint);


	}
	
	public void processTouchEvent(MotionEvent event){
//	@Override
//	public boolean onTouchEvent(MotionEvent event){
		int tX = (int)event.getX();		//タッチされたところのX軸を取得
		int tY = (int)event.getY();		//		〃           Y軸を取得

		
		//もし，今現在描画しているのが勝者の予測であれば．
		if(sceneSerch == parsagePush){
			if((w/2)-(parsageWinBmpDst[0].width()/2)-50 <= tX && tX <= parsageWinBmpDst[0].width() && h/4*(0)+50 <= tY && tY <= parsageWinBmpDst[0].height()){
				winnerPresage = winnerPlayer;
				sceneSerch = manyBets;
			}
			else if((w/2)-(parsageWinBmpDst[1].width()/2)-50 <= tX && tX <= parsageWinBmpDst[1].width() && h/4*(1)+50 <= tY && tY <= (h/4*(1)+50)+parsageWinBmpDst[1].height()){
				winnerPresage = winnerBanker;
				sceneSerch = manyBets;
			}
			else if((w/2)-(parsageWinBmpDst[2].width()/2)-50 <= tX && tX <= parsageWinBmpDst[2].width() && h/4*(2)+50 <= tY && tY <= (h/4*(2)+50)+parsageWinBmpDst[2].height()){
				winnerPresage = winnerTie;
				sceneSerch = manyBets;
			}
			else{
//				return true;
			}
		}
		
		//もし，今現在描画しているのがベットの変更であれば．
		else if(sceneSerch == manyBets){
			if(w-(w/5)-(betBmpDst[0].width()*0) < tX && tX < w-(w/5)+(betBmpDst[0].width())){
				if(h/4-betBmpDst[0].height() < tY && tY < h/4+betBmpDst[0].height()){
					if(bet+1 <= 99999)
						bet += 1;
					else
						bet = 99999;
				}
				else if(h/4+betBmpDst[0].height() < tY && tY < h/4+betBmpDst[0].height()*2 && bet > 0)
					bet -= 1;
			}
			else if(w-(w/5)-(betBmpDst[1].width()*1) < tX && tX < w-(w/5)+(betBmpDst[1].width())){
				if(h/4-betBmpDst[1].height() < tY && tY < h/4+betBmpDst[1].height()){
					if(bet+10 <= 99999)
						bet += 10;
					else
						bet = 99999;
				}
				else if(h/4+betBmpDst[1].height() < tY && tY < h/4+betBmpDst[1].height()*2){
					if(bet-10 >= 0)
						bet -= 10;
					else
						bet = 0;
				}
			}
			else if(w-(w/5)-(betBmpDst[2].width()*2) < tX && tX < w-(w/5)+(betBmpDst[2].width())){
				if(h/4-betBmpDst[2].height() < tY && tY < h/4+betBmpDst[2].height()){
					if(bet+100 <= 99999)
						bet += 100;
					else
						bet = 99999;
				}
				else if(h/4+betBmpDst[2].height() < tY && tY < h/4+betBmpDst[2].height()*2){
					if(bet-100 >= 0)
						bet -= 100;
					else
						bet = 0;
				}
			}
			else if(w-(w/5)-(betBmpDst[3].width()*3) < tX && tX < w-(w/5)+(betBmpDst[3].width())){
				if(h/4-betBmpDst[3].height() < tY && tY < h/4+betBmpDst[3].height()){
					if(bet+1000 <= 99999)
						bet += 1000;
					else
						bet = 99999;
				}
				else if(h/4+betBmpDst[3].height() < tY && tY < h/4+betBmpDst[3].height()*2){
					if(bet-1000 >= 0)
						bet -= 1000;
					else
						bet = 0;
				}
			}
			else if(w-(w/5)-(betBmpDst[4].width()*4) < tX && tX < w-(w/5)+(betBmpDst[4].width())){
				if(h/4-betBmpDst[4].height() < tY && tY < h/4+betBmpDst[4].height()){
					if(bet+10000 <= 99999)
						bet += 10000;
					else
						bet = 99999;
				}
				else if(h/4+betBmpDst[4].height() < tY && tY < h/4+betBmpDst[4].height()*2){
					if(bet-10000 >= 0)
						bet -= 10000;
					else
						bet = 0;
				}
			}
		}
		
		//もし，今現在描画しているのがゲームの結果であれば．
		else if(sceneSerch == resultGame){
			
		}
		
		//もし，今現在描画しているのがそれら以外(もう一度ゲームを行うかの問)であれば．
		else {
			if(tY < h/2){
				//もう一度やるけど，もうトランプがない場合．
				if(!(canNextGame())){
					trumpSet();
					trumpCount = 0;
					sceneSerch = parsagePush;
				}
				else
					sceneSerch = parsagePush;
			}
			else{
				
			}
				
		}
//		return false;
	}

}



