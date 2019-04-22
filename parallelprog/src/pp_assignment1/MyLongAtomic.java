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
}
