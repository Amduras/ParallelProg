package pp_assignment1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
Author:
Gero 		Balsen			33462
Jan-Luca 	Pankowsky		33117
*/
public class StrangeCounter {
	private final static int INCREMENTERS = 200;
	private final static int RUNS = 6000000;
	//private static CounterInterface counter = new MyLong();									//MyLong-Klasse aus der Aufgabe A+B
	//private static CounterInterface counter = new MyLongAtomic();								//MyLongAtomic-Klasse aus derAufgabe C
	private static CounterInterface counter = new MyLongAtomicModulo();							//MyLongAtomicModulo-Klasse aus derAufgabe D
	
	protected static class Incrementer implements Runnable {
		private final CountDownLatch start, end;
		private CounterInterface counter;
		
		public Incrementer(CountDownLatch start,CountDownLatch end,CounterInterface counter) {
			this.start = start;
			this.end = end;
			this.counter = counter;
		}
		public void run() {
			try {
				start.await();
				for (int i = 0; i < RUNS; i++) {
					counter.incrementAndGet();
				}
				end.countDown();
			} catch (Exception e) {}
		}
	}
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
		
		/*	Testwerte				Args	Runs	Incs	ms mit MyLong	ms mit MyLongAtomic		ms mit MLAModulo - min max Werte aus 10 Versuchen.
		 * 	CachedThreadPool				6m		200		30-61			15009-24978				21650-22675
		 * 	FixedThreadPool			20		6m		200		40-79			17015-17473				22355-23315
		 * 	SingleThreadExecutor			6m		200		27-31			7590-8086				12117-12968
		 *  Thread							6m		200		32-59			18317-21145				100487-110144
		 * */
		
		//ExecutorService eS = Executors.newCachedThreadPool();										//Wenn die Threads über einen ExecutorService starten sollen, muss eine Treadhandling Methode ausgewählt werden.
		//ExecutorService eS = Executors.newFixedThreadPool(20);
		ExecutorService eS = Executors.newSingleThreadExecutor();
		
		for (int i = 0; i < INCREMENTERS; i++) {
		eS.execute(new Incrementer(startLatch, endLatch, counter));
		}		
		
//		Thread[] Incrementers = new Thread[INCREMENTERS];											//Starten der Threads ohne einen ExecutorService
//		for (int i = 0; i < INCREMENTERS; i++) {
//			Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch, counter));
//			Incrementers[i].start();
//		}
				
		try {			
			System.out.println("Starting with counter = "+ counter.get());
			startLatch.countDown();
			endLatch.await();
			
			eS.shutdown();																			//Nur mit ExecutorService ausführen
			
			long totalInc = RUNS * INCREMENTERS;
			System.out.println("Finished after " + totalInc+ " increments with counter = "+ counter.get());
			counter.check(totalInc);
			System.out.println("Finished in "  + (System.currentTimeMillis()-start)+ "ms.");
		} catch (Exception e) {}
	}
}