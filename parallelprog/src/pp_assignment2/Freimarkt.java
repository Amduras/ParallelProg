package pp_assignment2;

import java.util.concurrent.atomic.AtomicLong;

public class Freimarkt {
	private final int anzWagen;
	private final int wagensitze;
	private final int besucher;
	private Achterbahn colossos;
	private final boolean generieren;
	public static AtomicLong bnum = new AtomicLong(1);
	
	public Freimarkt(int anzWagen,int wagensitze,int besucher, boolean generieren) {
		this.anzWagen = anzWagen;
		this.wagensitze = wagensitze;
		this.besucher = besucher;
		this.generieren = generieren;
		
		colossos = new Achterbahn(this.anzWagen,this.wagensitze);
	}
	
	public void Achterbahn÷ffnen() {
		for(int i=0;i<besucher;++i)
			new Thread(new Passagier(bnum.getAndIncrement(),NamensGenerator.genName(),colossos,generieren)).start();
	}

	public static void main(String[] args) {
		Freimarkt fb = new Freimarkt(2,5,14,true);
		fb.Achterbahn÷ffnen();
	}
}
