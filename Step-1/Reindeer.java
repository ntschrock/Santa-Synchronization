import java.util.Random;


public class Reindeer implements Runnable {
	
	public enum ReindeerState {AT_BEACH, AT_WARMING_SHED, AT_THE_SLEIGH};
	private ReindeerState state;
	private SantaScenario scenario;
	private Random rand = new Random();
	private boolean running = true;
	private int number;
	
	public Reindeer(int number, SantaScenario scenario) {
		this.number = number;
		this.scenario = scenario;
		this.state = ReindeerState.AT_BEACH;
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
			case AT_BEACH: {
				if (scenario.isDecember) {
					if (rand.nextDouble() < 0.1) {
						state = ReindeerState.AT_WARMING_SHED;
					}
				}
				break;			
			}
			case AT_WARMING_SHED: 
				break;
			case AT_THE_SLEIGH:
				break;
			}
		}
	};
	
	public void report() {
		System.out.println("Reindeer " + number + " : " + state);
	}
}
