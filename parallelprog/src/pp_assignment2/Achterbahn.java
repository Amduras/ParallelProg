package pp_assignment2;

import java.util.LinkedList;
import java.util.Queue;

public class Achterbahn {

	//private Queue<Wagen> bahnhof = new LinkedList<Wagen>(); 
	private int anzWagen;
	private final int wagensitze;
	private int freiePlätze;
	private Wagen wagen;
	
	public Achterbahn(int anzWagen, int wagensitze) {
		this.anzWagen = anzWagen;
		this.wagensitze = wagensitze;
		wagen = new Wagen(wagensitze,1);
		
//		for(int i=0; i<this.anzWagen;++i)
//			bahnhof.offer(new Wagen(this.wagensitze,i));
	}
	
	public synchronized void Drehkreuz() throws InterruptedException{
		
	}
	
	public synchronized void einsteigen(Passagier p) throws InterruptedException{
		//System.out.println("hi");
		while(! (wagen.getFreiePlätze() > 0 && wagen.getboolean())) {
			wait();
		}
		--freiePlätze;
		int cap = wagen.zusteigen(p);
		p.incZustand();
		if(cap == 0)
			wagen.abfahrt();
		notifyAll();
	}
	
	public synchronized void aussteigen(Passagier p) throws InterruptedException{
		while(!(p.getZustand() == 4)) { 
			wait();
		}
		wagen.incFreiePlätze();
		p.incZustand();
		notifyAll();		
	}
}
