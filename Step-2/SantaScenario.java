import java.util.ArrayList;
import java.util.List;

public class SantaScenario {

	public Santa santa;
	public List<Elf> elves;
	public boolean isDecember;
	
	public static void main(String args[]) {
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
		
		for(int day = 1; day < 500; day++) {
			if(day >= 370){
				Santa santa = scenario.santa;

				for(Elf elf: scenario.elves){
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

			System.out.println("***********  Day " + day + " *************************");
			scenario.santa.report();
			for(Elf elf: scenario.elves) {
				elf.report();
			}
		}
	}
}
