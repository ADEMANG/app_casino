package ademang.third.CasinoPrototype;



//現在は上辺だけのバカラ．
//Android API等を無視
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Baccarat extends View{
	private Random rand;
	private byte[] Trump;//シャッフル済みトランプ山札
	final private int trampNum = 52; //カードの枚数
	
	private byte[] playerTrump = new byte[3];//プレイヤーの所持しているトランプ
	private byte[] bankerTrump = new byte[3];//バンカーの所持しているトランプ
	private int trumpCount = 0;//現在，ドローされたトランプの合計
	
	private byte playerTrumpNum=0;//プレイヤーのトランプの合計
	private byte bankerTrumpNum=0;//バンカーのトランプの合計
	
	public String win;//勝者
	private boolean playerDrawTF = false;//プレイヤーが3枚目をドローを「した」/「してない」

	//以下アンドロイドAPI
	private Paint paint = new Paint();
	

	//コンストラクタ
	public Baccarat(Context context) {
		super(context);
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	
	
	/*
	 * トランプをシャッフル
	 */
	public void trumpSet(){
		rand = new Random();
		Trump = new byte[trampNum];
		
		for(int i=0,tmp=0; i<trampNum; i++){
			tmp = rand.nextInt(51) + 1;
			for(int j=0; j<=i; j++){
				if(Trump[j] != tmp)
					Trump[i] = (byte)tmp;
			}
		}
	}
	
	/*
	 * 実行部分
	 * drawText use Debug
	 */
	public void onDraw(Canvas canvas){
		trumpSet();
		distribute();
		
		playerTrumpNum = sumTrump(playerTrump);
		bankerTrumpNum = sumTrump(bankerTrump);
		if(natural())
			win = winner();
		else{
			if(playerDraw() == true){
				playerTrump[2] = draw();
				canvas.drawText(" " + playerTrump[2], 200, 50, paint);

			}
			if(bankerDraw() == true){
				bankerTrump[2] = draw();
				canvas.drawText(" " + bankerTrump[2], 200, 100, paint);
			}
			playerTrumpNum = sumTrump(playerTrump);
			bankerTrumpNum = sumTrump(bankerTrump);
			win = winner();
		}
		
		canvas.drawText("p => " + playerTrump[0], 25, 50, paint);
		canvas.drawText(" " + playerTrump[1], 125, 50, paint);
		canvas.drawText(" === > "+ playerTrumpNum, 250, 50, paint);
		
		canvas.drawText("b => " + bankerTrump[0], 25, 100, paint);
		canvas.drawText(" " + bankerTrump[1], 125, 100, paint);
		canvas.drawText(" === > " + bankerTrumpNum, 250, 100, paint);

		canvas.drawText(" TC= > " + trumpCount, 250, 150, paint);

		
	}
	
	/*
	 * 勝者を返す
	 */
	public String winner(){
		if(playerTrumpNum%10 > bankerTrumpNum%10)
			return "player";
		else 
			return "banker";
		
	}
	
	/*
	 * トランプの合計を計算し，その合計を返す
	 * なお，10以上は0とみなされる
	 */
	public byte sumTrump(byte[] trumpCard){
		byte sum=0;
		byte minus=0;
		for(int i=0; i<trumpCard.length; i++){
			if(40 <= trumpCard[i] && trumpCard[i] <= 51)
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
	
	/*
	 * トランプをプレイヤーとバンカーに配る．
	 */
	public void distribute(){
		for(int i=0,k=trumpCount,m=trumpCount+1; i<2 && trumpCount+2 < 52; i++,k+=2,m+=2){
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
			if(!(playerTrump[2]<=2 && 7>=playerTrump[2]))
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
}
