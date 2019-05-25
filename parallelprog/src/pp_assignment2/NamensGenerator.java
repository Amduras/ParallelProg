package pp_assignment2;
import java.util.Random;

public class NamensGenerator {
	private static String[] vorname = { "Lucy",	"Lukas", "Ella", "Konstantin", "Amy", "Ben", "Emely", "Jonas", "Finja",	"Elias", "Amelie", "Niklas", "Luise", "David", "Frieda", "Oskar",	"Katharina", "Phillip",	"Romy", "Leon",	"Juna",	"Noah",
	"Theresa",	"Luis","Eva", "Paul", "Julia", "Finn", "Anna", "Felix", "Carla", "Julian", "Paulina", "Maximilian", "Elisabeth", "Henry","Rosa", "Tim", "Mia", "Karl", "Maya",	"Friedrich", "Selma", "Peter", "Edda", "Quirin", "Flora", "Liam"};

	private static String[] nachname = {"Meyer", "Müller", "Eilers", "Gerdes", "Janssen", "Schröder", "Bruns", "Ahlers", "Behrens", "Schmidt", "Albers", "Harms", "Kramer", "Macke", "Oltmanns", "Kruse", "Becker", "Cordes", "Janßen", "Frerichs",
			"Deters", "Menke", "Lange", "Schütte", "Schwarting", "Gramberg", "Meiners", "Kuhlmann", "Koopmann", "Siemer", "Olberding", "Martens", "Kröger"};
	
	private static Random gen = new Random();
	
	public static String genName() {
		return vorname[gen.nextInt(vorname.length-1)]+" "+nachname[gen.nextInt(nachname.length-1)];
	}
}
