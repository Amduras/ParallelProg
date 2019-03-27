package pp_assignment1;

import java.util.concurrent.atomic.AtomicLong;

/*
Author:
Gero 		Balsen			33462
Jan-Luca 	Pankowsky		33117
*/
public class MyLongAtomic implements CounterInterface {
	private AtomicLong counter = new AtomicLong();
	
	public long get() {
		return counter.get();
	}
	public long incrementAndGet() {
		return counter.incrementAndGet();
	}
	public void check(long ex) {
		if (counter.get() != ex) {
			System.out.println("Es gab eine Abweichung vom Erwartungswert! Erwartung: "+ex+" Zählerstand: "+counter+" Verlust: "+(long)(100F-((float)counter.get()/(float)ex)*100F)+"%");
		}
	}
}
