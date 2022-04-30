//import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;

public class Santa implements Runnable {

	enum SantaState {SLEEPING, READY_FOR_CHRISTMAS, WOKEN_UP_BY_ELVES, WOKEN_UP_BY_REINDEER};
	private SantaState state;
	public Elf.ElfState elfState;
	SantaScenario s;
	private boolean running = true;
	
	public Santa(SantaScenario scenario) {
		this.state = SantaState.SLEEPING;
		this.s = scenario;
	}

	public void killThread(){
		running = false;
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
				for(Elf elf: s.inTrouble) {
					if (elf.getState() == Elf.ElfState.AT_SANTAS_DOOR)
						elf.setState(Elf.ElfState.WORKING);
				}
				s.inTrouble.clear();
				s.elvesReturned = false;
				this.state = SantaState.SLEEPING;
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

	public void report() {
		System.out.println("Santa : " + state);
	}
}