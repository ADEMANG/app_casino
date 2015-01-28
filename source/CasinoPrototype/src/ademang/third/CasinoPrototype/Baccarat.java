package ademang.third.CasinoPrototype;



//現在は上辺だけのバカラ．
//Android API等を無視
import java.util.Random;

public class Baccarat{
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
	
	
	/*
	 * トランプをシャッフル
	 */
	public void trumpSet(){
		rand = new Random();
		Trump = new byte[trampNum];
		
		for(int i=0,tmp=0; i<trampNum; i++){
			tmp = rand.nextInt(51)+1;
			for(int j=0; j<i; j++){
				if(Trump[j] != tmp)
					Trump[i] = (byte) tmp;
			}
		}
	}
	
	/*
	 * 実行部分
	 */
	public void main(){
		trumpSet();
		distribute();
		playerTrumpNum = sumTrump(playerTrump);
		bankerTrumpNum = sumTrump(bankerTrump);
		if(natural() == true)
			win = winner();
		else{
			if(playerDraw() == true)
				playerTrump[2] = draw();
			if(bankerDraw() == true)
				bankerTrump[2] = draw();
			playerTrumpNum = sumTrump(playerTrump);
			bankerTrumpNum = sumTrump(bankerTrump);
			win = winner();
		}
		
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
	 * なお，10以上は10とみなされる
	 */
	public byte sumTrump(byte[] trumpCard){
		byte sum=0;
		byte div=0;
		for(int i=0; i<trumpCard.length; i++){
			if(trumpCard[i] > 13)
				div=4;
			else
				div=1;
				
			if(trumpCard[1]/4 >= 10)
				sum += 10;
			else
				sum += trumpCard[i]/div;
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
		for(int i=0; i<2 && trumpCount+2<52; i+=2){
			playerTrump[i] = Trump[i];
			bankerTrump[i] = Trump[i+1];
			trumpCount += 2;
		}
	}
	
	/*
	 * ナチュラル(カードの合計の1桁が"8"もしくは"9")でないかを判定
	 */
	public boolean natural(){
		boolean flag=false;
		
/*		for(int i=0; i<2; i++){
			if(playerTrump[i] > 13){
				if(playerTrump[i]/4 >= 10)
					playerTrumpNum += 10;
				else
					playerTrumpNum += playerTrump[i]/4;
			}
			else{
				if(playerTrump[i] >= 10)
					playerTrumpNum += 0;
				else
					playerTrumpNum += playerTrump[i];
			}
		}*/
		
		
		if(playerTrumpNum%10 == 8 || playerTrumpNum%10 == 9)
			flag = true;
		/*		
		for(int i=0; i<2; i++){
			if(bankerTrump[i] > 13){
				if(bankerTrump[i]/4 >= 10)
					bankerTrumpNum += 0;
				else
					bankerTrumpNum += bankerTrump[i]/4;
			}
			else{
				if(bankerTrump[i] >= 10)
					bankerTrumpNum += 0;
				else
					bankerTrumpNum += bankerTrump[i];
			}
		}
		*/
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
			//バンカーの1桁目が5以下，なおかつ7でない場合にドロー
			if(bankerTrumpNum%10 <= 5 && bankerTrumpNum != 7)
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
