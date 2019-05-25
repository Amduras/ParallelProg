package pp_assignment2;

public class Passagier implements Runnable{
	private final int nummer;
	private final String name;
	private int zustand; // 0 = Eintritt in den Park(geburt d. Threads), 1 = anstellen ans Drehkreuz, 2 = Durch Drehkreuz gehen, 3 = anstellen im bahnhof, 4 = Einsteigen, 5 = Aussteigen/sterben
	private final Achterbahn colossos;
	
	public Passagier(int nummer,String name, Achterbahn colossos) {
		this.nummer = nummer;
		this.name = name;
		this.colossos = colossos;
		
		System.out.println("Hallo, ich bin "+name+" und Nummer "+nummer);
		incZustand();
	}
	public void incZustand(){
		switch(++zustand) {
		case 1:
			System.out.println(name+"("+nummer+") stellt sich am Bahnhof an.");
		break;
		case 2: 
			System.out.println(name+"("+nummer+") steigt in einen Wagen.");
		break;
		case 3:
			System.out.println(name+"("+nummer+") fährt los.");
		break;
		case 4:
			System.out.println(name+"("+nummer+") kommt im Bahnhof an.");
		break;
		case 5:
			System.out.println(name+"("+nummer+") steigt aus und verlässt die Achterbahn.");
		break;
		}
	}
	int getZustand() {
		return zustand;
	}

	@Override
	public void run() {
		try {					
			colossos.einsteigen(this);
			colossos.aussteigen(this);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
}