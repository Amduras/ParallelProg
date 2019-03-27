package pp_assignment1;
/*
Author:
Gero 		Balsen			33462
Jan-Luca 	Pankowsky		33117
*/
public interface CounterInterface {
	long get();
	long incrementAndGet();
	void check(long desired);
}