package pp_assignment1;
/*
Author:
Gero 		Balsen			33462
Jan-Luca 	Pankowsky		33117
*/
public interface CounterInterface {
	long get();
	long incrementAndGet();
	default void check(long ex) {
		if (get() != ex) {
			System.out.println("Es gab eine Abweichung vom Erwartungswert! Erwartung: "+ex+" Zählerstand: "+get()+" Verlust: "+(long)(100F-((float)get()/(float)ex)*100F)+"%");
		}
	}
}