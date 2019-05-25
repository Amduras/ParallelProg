package pp_assignment2;

public class Freimarkt {
	private final int anzWagen;
	private final int wagensitze;
	private final int besucher;
	private Achterbahn colossos;
	
	public Freimarkt(int anzWagen,int wagensitze,int besucher) {
		this.anzWagen = anzWagen;
		this.wagensitze = wagensitze;
		this.besucher = besucher;
		
		colossos = new Achterbahn(this.anzWagen,this.wagensitze);
	}
	
	public void Achterbahn÷ffnen() {
		for(int i=0;i<besucher;++i)
			new Thread(new Passagier(i+1,NamensGenerator.genName(),colossos)).start();
	}

	public static void main(String[] args) {
		Freimarkt fb = new Freimarkt(1,5,20);
		fb.Achterbahn÷ffnen();
	}
}
