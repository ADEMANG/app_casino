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
//implements SceneValues,Games{
	extends View{
	
	
	
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
	private String win;//勝者
	private boolean playerDrawTF;//プレイヤーが3枚目をドローを「した」/「してない」
	
	private char winnerPresage;//勝者の予想，もしくは引き分けの予想
	private final char winnerPlayer = 'p';
	private final char winnerBanker = 'b';
	private final char winnerTie = 't';
	
	
	private int sceneSerch;//現在の画面
	private final int parsagePush = 0;//予想
	private final int manyBets = 1; //ベットの金額を変更
	private final int resultGame = 2; //結果
	private final int oneMoreGame = 3;//もう一試合しますか?

	
	
	
	//以下アンドロイドAPI
	private Paint paint;
	private Bitmap[] bitmapTramp;	//トランプビットマップ
	private Bitmap[] parsageWinnerBmp; //勝者の選択画像
	
	private Rect[] parsageWinBmpDst;
	private Rect parsageWinBmpSrc;
	private Rect[] playerTrumpDst;
	private Rect[] bankerTrumpDst;
	private Rect trumpSrc;
	
	private int w;
	private int h;
	private boolean pflg;
	private boolean bflg;
	//コンストラクタ
//	public Baccarat(Canvas canvas,Resources r) {
	public Baccarat(Context context){
		super(context);
//		this.canvas = canvas;
//		this.loadResource(r);
		trumpSet();
		
		playerTrump = new byte[3];
		bankerTrump = new byte[3];
		trumpCount = 0;
		playerTrumpNum = 0;
		bankerTrumpNum = 0;
		playerDrawTF  = false;
		winnerPresage = 'n';
		sceneSerch = parsagePush;
		
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		
		w=0;
		h=0;
		pflg = false;
		bflg = false;

		bitmapTramp = new Bitmap[trampNum+1];
		parsageWinnerBmp = new Bitmap[3];

//		loadResource();
		loadResource(getResources());

		// TODO 自動生成されたコンストラクター・スタブ
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
		
		//勝者の選択
		parsageWinnerBmp[0] = BitmapFactory.decodeResource(r, R.drawable.e_button_player);		
		parsageWinnerBmp[1] = BitmapFactory.decodeResource(r, R.drawable.e_button_banker);
		parsageWinnerBmp[2] = BitmapFactory.decodeResource(r, R.drawable.e_button_tie);		
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
	public String winner(){
		if(playerTrumpNum%10 == bankerTrumpNum%10)
			return "tie";
		else if(playerTrumpNum%10 > bankerTrumpNum%10)
			return "player";
		else 
			return "banker";
		
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
		
		return 0;
	}
		
	public int moveScene() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	
	public void animation(){
		
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
	
	public Rect returnDst(int num,int w,int h, Bitmap b){
		return new Rect(0, 0, w/90/num*b.getWidth(),h/160/num*b.getHeight());
	}

	/*
	 * 実行部分
	 * drawText use Debug
	 */
	
//	public void draw(Canvas canvas){
	@Override
	public void onDraw(Canvas canvas){
		//描画領域の最大値を求める
		w = canvas.getWidth();
		h = canvas.getHeight();

		//勝者予測選択ボタンのRECT変数
		parsageWinBmpDst = new Rect[3];
		parsageWinBmpSrc = new Rect(0,0,parsageWinnerBmp[0].getWidth(),parsageWinnerBmp[0].getHeight());

		
		//予測ボタンのRECTを書き換える
		for(int i=0; i<parsageWinnerBmp.length; i++){
//			parsageWinBmpDst[i] = new Rect(0,0,parsageWinnerBmp[i].getWidth()+w/2,parsageWinnerBmp[i].getHeight()+h/12); 
			parsageWinBmpDst[i] = returnDst(2, w, h, parsageWinnerBmp[i]);
			parsageWinBmpDst[i].offsetTo((w/2)-(parsageWinBmpDst[i].width()/2)-50, h/4*(i)+50);
		}
		
		//トランプのRECTを書き換える
		playerTrumpDst = new Rect[3];
		bankerTrumpDst = new Rect[3];
		trumpSrc = new Rect(0,0,bitmapTramp[0].getWidth(),bitmapTramp[0].getHeight());

		for(int i=0; i<3; i++){
			playerTrumpDst[i] = returnDst(2, w, h, bitmapTramp[0]);
			bankerTrumpDst[i] = returnDst(2, w, h, bitmapTramp[0]);
			//トランプの描画ポジションを設定
			playerTrumpDst[i].offsetTo(20+i*100, 20);
			bankerTrumpDst[i].offsetTo(20+i*100, 400);
		}
		
		//勝者予測選択画面の表示
		if(sceneSerch == parsagePush){
			for(int i=0; i<parsageWinnerBmp.length; i++)
				canvas.drawBitmap(parsageWinnerBmp[i], parsageWinBmpSrc, parsageWinBmpDst[i],paint);
		}
		
		//掛け金の変更
		else if(sceneSerch == manyBets){
			
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
	
//	public void processTouchEvent(MotionEvent event){
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int tX = (int)event.getX();		//タッチされたところのX軸を取得
		int tY = (int)event.getY();		//		〃           Y軸を取得

		
		//もし，今現在描画しているのが勝者の予測であれば．
		if(sceneSerch == parsagePush){
			if((w/2)-(parsageWinBmpDst[0].width()/2)-50 <= tX && tX <= parsageWinBmpDst[0].width() && h/4*(0)+50 <= tY && tY <= parsageWinBmpDst[0].height()){
				winnerPresage = winnerPlayer;
				sceneSerch = resultGame;
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
				return true;
			}
		}
		
		//もし，今現在描画しているのがベットの変更であれば．
		else if(sceneSerch == manyBets){
			
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
				
		}
		return false;
	}

}



