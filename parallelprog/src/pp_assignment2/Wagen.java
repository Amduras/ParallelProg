package pp_assignment2;

import java.util.Queue;

public class Wagen {

	private final int sitze;
	private int freiePlätze;
	private int wagennummer;
	private Queue<Wagen> bahnhof;
	private boolean nichtunterwegs = true;
	private Passagier[] pa;
	
	public Wagen(int sitze, int wagennummer,Queue<Wagen> bahnhof) {
		this.sitze = sitze;
		freiePlätze = sitze;
		this.wagennummer = wagennummer;
		this.bahnhof = bahnhof;
		pa = new Passagier[sitze];
	}
	public int getFreiePlätze() {
		return freiePlätze;
	}
	public void incFreiePlätze() {
		++freiePlätze;
		if (freiePlätze == sitze)
			nichtunterwegs = true;
	}
	public boolean getboolean() {
		return nichtunterwegs;
	}
	public int getNummer() {
		return wagennummer;
	}
	public String getOutputString() {
		return (sitze-freiePlätze)+"/"+sitze;
	}
	public int zusteigen(Passagier p) {
		pa[sitze-freiePlätze] = p;
		return --freiePlätze;		
	}
	
	public void abfahrt() {
		nichtunterwegs = false;
		System.out.println("Wagen "+wagennummer+" ist soeben mit "+(sitze-freiePlätze)+"/"+sitze+" Passagieren abgefahren.");			
		try {
			Thread.sleep(5000);
			for(int i=0; i<pa.length;++i)
				pa[i].incZustand();	
			ankunft();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	public void ankunft() {
		System.out.println("Wagen "+wagennummer+" hat im Bahnhof angehalten.");
		for(int i=0; i<pa.length;++i) {
			pa[i].incZustand();
			incFreiePlätze();
		}
		bahnhof.add(this);
	}
}
