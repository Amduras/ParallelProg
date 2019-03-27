package pp_assignment1;
/*
Author:
Gero 		Balsen			33462
Jan-Luca 	Pankowsky		33117
*/
public class MyLong implements CounterInterface {
	private long counter = 0;
	
	public long get() {
		return counter;
	}
	public long incrementAndGet() {		
		return counter++;
	}
	public void check(long ex) {
		if (counter != ex) {
			System.out.println("Es gab eine Abweichung vom Erwartungswert! Erwartung: "+ex+" Zählerstand: "+counter+" Verlust: "+(long)(100F-((float)counter/(float)ex)*100F)+"%");
		}
	}
}
