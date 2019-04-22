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
}
