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
	private final static Integer MyLong = 1, 				//MyLong-Klasse aus der Aufgabe A+B
								 MyLongAtomic = 2,  		//MyLongAtomic-Klasse aus derAufgabe C
								 MyLongAtomicModulo = 3; 	//MyLongAtomicModulo-Klasse aus derAufgabe D
	private final static Integer cached = 1, fixed = 2,  single = 3; 
	private final static boolean executors = true;
	private final static int INCREMENTERS = 200;
	private final static int RUNS = 600000;
	private static CounterInterface counter;
	private static ExecutorService eS;					
	
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
	private static void makeCounter(int n) {
		switch(n) {
		case 1: counter = new MyLong();
		break;
		case 2: counter = new MyLongAtomic();
		break;
		case 3: counter = new MyLongAtomicModulo();
		}
	}
	
	private static void makeService(int n) {	//Wenn die Threads über einen ExecutorService starten sollen, muss eine Treadhandling Methode ausgewählt werden.
		switch(n) {
		case 1: eS = Executors.newCachedThreadPool();
		break;
		case 2: eS = Executors.newFixedThreadPool(20);
		break;
		case 3: eS = Executors.newSingleThreadExecutor();
		}
	}
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
		int counterInt = MyLongAtomic;
		int service = cached;
		

		makeCounter(counterInt);
		if(executors) {
			makeService(service);
			for (int i = 0; i < INCREMENTERS; i++) {
				eS.execute(new Incrementer(startLatch, endLatch, counter));
			}
			eS.shutdown();
		} else {
			Thread[] Incrementers = new Thread[INCREMENTERS];											//Starten der Threads ohne einen ExecutorService
			for (int i = 0; i < INCREMENTERS; i++) {
				Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch, counter));
				Incrementers[i].start();
			}
		}

				
		try {			
			System.out.println("Starting with counter = "+ counter.get());
			startLatch.countDown();
			endLatch.await();
			long totalInc = RUNS * INCREMENTERS;
			System.out.println("Finished after " + totalInc+ " increments with counter = "+ counter.get());
			counter.check(totalInc);
			System.out.println("Finished in "  + (System.currentTimeMillis()-start)+ "ms.");
		} catch (Exception e) {}
	}
}