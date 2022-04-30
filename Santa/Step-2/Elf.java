import java.util.Random;

public class Elf implements Runnable {

	enum ElfState {
		WORKING, TROUBLE, AT_SANTAS_DOOR
	};

	Thread myThread = null;

	private ElfState state;
	private int number;
	private Random rand = new Random();
	private SantaScenario scenario;
	private boolean running = true;

	public Elf(int number, SantaScenario scenario) {
		this.number = number;
		this.scenario = scenario;
		this.state = ElfState.WORKING;
	}

	public ElfState getState() {
		return state;
	}
	
	public void setState(ElfState state) {
		this.state = state;
	}

	public void killThread(){
		this.running = false;
	}

	/**
	 * Santa might call this function to fix the trouble
	 * @param state
	 */
	@Override
	public void run() {
		while (running) {
  		try {
  			Thread.sleep(100);
  		} catch (InterruptedException e) {
  			e.printStackTrace();
  		}
			switch (state) {
			case WORKING: {
				if (rand.nextDouble() < 0.01) {
					this.state = ElfState.TROUBLE;
				}
				break;
			}
			case TROUBLE:
				this.state = ElfState.AT_SANTAS_DOOR;
				break;
			case AT_SANTAS_DOOR:
				scenario.santa.wokenByElves();
				break;
			}
		}
	}

	public void report() {
		System.out.println("Elf " + number + " : " + state);
	}
}
