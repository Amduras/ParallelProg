package pp_assignment1;

import java.util.concurrent.CountDownLatch;
/*
Author:
Gero 		Balsen			33462
Jan-Luca 	Pankowsky		33117
*/
public class StrangeCounter {
	private final static int INCREMENTERS = 200;
	private final static int RUNS = 600000;
	private static CounterInterface counter = new MyLong();
	
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
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
		Thread[] Incrementers = new Thread[INCREMENTERS];
		for (int i = 0; i < INCREMENTERS; i++) {
			Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch, counter));
			Incrementers[i].start();
		}
		try {
			System.out.println("Starting with counter = "+ counter.get());
			startLatch.countDown();
			endLatch.await();
			long totalInc = RUNS * INCREMENTERS;
			System.out.println("Finished after " + totalInc+ " increments with counter = "+ counter.get());
			counter.check(totalInc);
		} catch (Exception e) {}
	}
}