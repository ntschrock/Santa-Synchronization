import java.util.ArrayList;
import java.util.Queue;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ArrayBlockingQueue;

public class SantaScenario {
	public int numDoorElves = 0;
	public Santa santa;
	public List<Elf> elves;
	public List<Elf> inTrouble;
	public boolean isDecember;
	public Semaphore elfInTrouble;
	public Semaphore santaSemaphore;
	public Queue<Elf> semaphoreQueue;
	public boolean elvesReturned;

	public void setElfNum(){
		this.numDoorElves = numDoorElves + 1;
	}
	public void setNumElves(int num){
		this.numDoorElves = num;
	}
	public int getNumElves(){
		return numDoorElves;
	}
	public static void main(String args[]){
		SantaScenario scenario = new SantaScenario();
		scenario.isDecember = false;
		scenario.santa = new Santa(scenario);
		Thread th = new Thread(scenario.santa);
		th.start();
		scenario.elves = new ArrayList<>();
		for(int i = 0; i != 10; i++) {
			Elf elf = new Elf(i+1, scenario);
			scenario.elves.add(elf);
			th = new Thread(elf);
			th.start();
		}
		scenario.inTrouble = new ArrayList<>();
		scenario.elfInTrouble = new Semaphore(1, true);
		scenario.santaSemaphore = new Semaphore(1, true);
		scenario.semaphoreQueue = new ArrayBlockingQueue<Elf>(3);
		for(int day = 1; day < 500; day++) {
			if (day == 370){
				Santa santa = scenario.santa;

				for(Elf elf: scenario.elves) {
					elf.killThread();
				}
				santa.killThread();	
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (day > (365 - 31)) {
				scenario.isDecember = true;
			}
			if (scenario.inTrouble.size() == 3){
				try {
					scenario.santaSemaphore.acquire();
					for (Elf elf : scenario.inTrouble){
						elf.setState(Elf.ElfState.AT_SANTAS_DOOR);
					}
					scenario.santa.wokenByElves();
					scenario.santaSemaphore.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//System.out.println(scenario.getNumElves());
			System.out.println("***********  Day " + day + " *************************");
			scenario.santa.report();
			for(Elf elf: scenario.elves) {
				elf.report();
			}
		}
	}
}