package ademang.third.CasinoPrototype;

public class Status {
<<<<<<< HEAD
	private static int money;		//Player's assets
	private static int event;		//Events progress
	
	public Status(){
		money = 0;
		event = 0;
	}
	public static boolean useMoney(int how){
		if(how < money){
			money -= how;
			return true;
		}else{
			return false;
		}
	}
	public static void addMoney(int how){
		money += how;
	}
	public static boolean getEventFlag(int event_num){
		//tmp
		
		return true;
=======
	private int money;		//Player's assets
	private int event;		//Events progress
	
	public Status(){
		
>>>>>>> 6f002c3fab3d1274d4489cf7abd6724ae4612ccf
	}
}
