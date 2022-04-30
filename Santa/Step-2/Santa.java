//import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;

public class Santa implements Runnable {

	enum SantaState {SLEEPING, READY_FOR_CHRISTMAS, WOKEN_UP_BY_ELVES, WOKEN_UP_BY_REINDEER};
	private SantaState state;
	private boolean running = true;
	SantaScenario s;
	public Elf.ElfState elfState;
	
	public Santa(SantaScenario scenario) {
		this.state = SantaState.SLEEPING;
		this.s = scenario;
	}

	public void killThread(){
		this.running = false;
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			switch(state) {
			case SLEEPING:
				break;
			case WOKEN_UP_BY_ELVES: 
				for(Elf elf: s.elves){
					elfState = elf.getState();
					if(elfState == Elf.ElfState.AT_SANTAS_DOOR){
						elf.setState(Elf.ElfState.WORKING);
					}
				}
				sleepAgain();
				break;
			case WOKEN_UP_BY_REINDEER: 
				break;
			case READY_FOR_CHRISTMAS:
				break;
			}
		}
	}

	public void wokenByElves(){
		this.state = SantaState.WOKEN_UP_BY_ELVES;
	}

	public void sleepAgain(){
		this.state = SantaState.SLEEPING;
	}
	
	public void report() {
		System.out.println("Santa : " + state);
	}
}
