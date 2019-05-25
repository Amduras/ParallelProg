package pp_assignment2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Achterbahn {

	private Queue<Wagen> bahnhof = new LinkedList<Wagen>();
	private Wagen[] wagen;
	private int anzWagen;
	private final int wagensitze;
	private Random gen = new Random();
	
	public Achterbahn(int anzWagen, int wagensitze) {
		this.anzWagen = anzWagen;
		this.wagensitze = wagensitze;
		wagen = new Wagen[anzWagen];
		
		for(int i=0; i<this.anzWagen;++i) {
			wagen[i] = new Wagen(this.wagensitze,i,bahnhof);
			bahnhof.offer(wagen[i]);
		}
	}
	
	public synchronized void einsteigen(Passagier p) throws InterruptedException{
		int wagenZumZusteigen = gen.nextInt(anzWagen);
		Wagen wgn = wagen[wagenZumZusteigen];
		while(! (wgn.getFreiePlätze() > 0 && wgn.getboolean())) {
			wait();
		}
		int cap = wgn.zusteigen(p);
		p.setWagen(wgn);
		p.incZustand();
		if(cap == 0 && bahnhof.peek() == wgn) {
			wgn.abfahrt();
			bahnhof.remove();
			while(bahnhof.peek() != null && bahnhof.peek().getFreiePlätze() == 0) {
				bahnhof.poll().abfahrt();
			}
		}
		notifyAll();
	}
	
	public synchronized void aussteigen(Passagier p, Wagen w) throws InterruptedException{
		while(!(p.getZustand() == 4)) { 
			wait();
		}
		w.incFreiePlätze();
		p.incZustand();
		notifyAll();		
	}
}
