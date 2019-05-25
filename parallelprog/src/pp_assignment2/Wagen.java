package pp_assignment2;

public class Wagen {

	private final int sitze;
	private int freiePlätze;
	private int wagennummer;
	private boolean nichtunterwegs = true;
	private Passagier[] pa;
	
	public Wagen(int sitze, int wagennummer) {
		this.sitze = sitze;
		freiePlätze = sitze;
		this.wagennummer = wagennummer;
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
	public int zusteigen(Passagier p) {
		pa[sitze-freiePlätze] = p;
		return --freiePlätze;		
	}
	
	public void abfahrt() {
		nichtunterwegs = false;
		System.out.println("Wagen "+wagennummer+" ist soeben mit "+(sitze-freiePlätze)+"/"+sitze+" Passagieren abgefahren.");
		for(int i=0; i<pa.length;++i)
			pa[i].incZustand();		
		try {
			Thread.sleep(5000);
			ankunft();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	public void ankunft() {
		System.out.println("Wagen "+wagennummer+" hat im Bahnhof angehalten.");
		for(int i=0; i<pa.length;++i)
			pa[i].incZustand();
		//freiePlätze = sitze;
	}
}
