package ademang.third.CasinoPrototype;

public class Status {
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
	}
}
