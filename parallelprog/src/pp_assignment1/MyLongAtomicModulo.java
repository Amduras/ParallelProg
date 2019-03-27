package pp_assignment1;

import java.util.concurrent.atomic.AtomicLong;

/*
Author:
Gero 		Balsen			33462
Jan-Luca 	Pankowsky		33117
*/
public class MyLongAtomicModulo implements CounterInterface {
	private AtomicLong counter = new AtomicLong();
	public long get() {
		return counter.get();
	}
	public long incrementAndGet() {
		 for (;;) {																		//generell benutzbar
	            long current = counter.get();
	            long next = (current + 1) % 16;
	            if (counter.compareAndSet(current, next))
	                return next;
	     }
		 //return counter.getAndUpdate(value -> (value + 1) % 16);						//Ab JAVA 8 benutzbar
	}
	public void check(long ex) {
		if (counter.get() != (ex%16)) {
			System.out.println("Es gab eine Abweichung vom Erwartungswert! Erwartung: "+ex+" Zählerstand: "+counter+" Verlust: "+(long)(100F-((float)counter.get()/(float)ex)*100F)+"%");
		}
	}
}