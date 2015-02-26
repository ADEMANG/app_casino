package ademang.third.CasinoPrototype;

public class Status {
	private static int money;		//Player's assets
	private static int repaid;		//Amount repaid
	private static int event;		//Events progress
	
	private static final int 
			ind1 = 10000,
			ind2 = 60000,
			ind3 = 160000;
	public static final int
			EVENT_FLAG = 0,
			EVENT_IND1 = 1,
			EVENT_CUT1 = 2,
			EVENT_IND2 = 3,
			EVENT_CUT2 = 4,
			EVENT_IND3 = 5,
			EVENT_CUT3 = 6;
	
	public static void init(int money,int repaid,int event){
		Status.money = money;
		Status.repaid = repaid;
		Status.event = event;
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
	public static void subMoney(int how){
		money -= how;
	}
	public static int getIndebtedness(){
		if(!getEventFlag(EVENT_IND1)){
			return ind1 - repaid;
		}else if(!getEventFlag(EVENT_IND2)){
			return ind2 - repaid;
		}else if(!getEventFlag(EVENT_IND3)){
			return ind3 - repaid;
		}
		return 0;
	}
	public static boolean repay(int how){
		if(how <= money){
			subMoney(how);
			repaid += how;
			return true;
		}else{
			return false;
		}
	}
	public static boolean getEventFlag(int event_num){
		int mask = event >> event_num;
		if((mask & 1) == 1)return true;
		else return false;
	}
	public static void clearEvent(int event_num){
		event += 0x00000001 << event_num;			//make a event of event_num bit
		event -= 1;									//down the EVENT_FLAG
	}
}
