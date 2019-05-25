package pp_assignment2;

public class Passagier implements Runnable{
	final long nummer;
	private final String name;
	private int zustand; // 0 = Eintritt in den Park(geburt d. Threads), 1  = anstellen im bahnhof, 2 = Einsteigen,3 = losfahren, 4 = Aussteigen/sterben
	private final Achterbahn colossos;
	private  Wagen wagen;
	private boolean generieren;
	private boolean erweiterteAusgabe = false;
	
	public Passagier(long nummer,String name, Achterbahn colossos,boolean generieren) {
		this.nummer = nummer;
		this.name = name;
		this.colossos = colossos;
		this.generieren = generieren;
		
		if(erweiterteAusgabe)
			System.out.println("Hallo, ich bin "+name+" und Nummer "+nummer);
		incZustand();
	}
	public void incZustand(){
		switch(++zustand) {
		case 1:
			if(erweiterteAusgabe)
				System.out.println(name+"("+nummer+") stellt sich am Bahnhof an.");
		break;
		case 2: 
			System.out.println(name+"("+nummer+") steigt in Wagen "+wagen.getNummer()+" ein. "+wagen.getOutputString());
		break;
		case 3:
			System.out.println(name+"("+nummer+") fährt los.");
		break;
		case 4:
			System.out.println(name+"("+nummer+") steigt aus und verlässt die Achterbahn.");
		break;
		}
	}
	public int getZustand() {
		return zustand;
	}
	public void setWagen(Wagen w) {
		wagen = w;
	}

	@Override
	public void run() {
		try {					
			colossos.einsteigen(this);
			//colossos.aussteigen(this,wagen);
			if(generieren)
				new Thread(new Passagier(Freimarkt.bnum.getAndIncrement(),NamensGenerator.genName(),colossos,true)).start();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
}