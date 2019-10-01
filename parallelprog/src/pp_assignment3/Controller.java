package pp_assignment3;

public class Controller {
	//args[1]-echo/election, args[2] = 'anzahl der wahlen für election' args[0] = 'baumart: komplex/complete'
	public static void main(String[] args) {
		if(args.length == 0 || args.length > 3) {
			System.out.println("Faslche Parameter anzahl");
			usage();
		} else {
			String tree = null;
			if(args[1].contentEquals("complex")) {
				tree = "complex";
			} else if(args[1].contentEquals("complete")) {
				tree = "complete";
			} else {
				System.out.println("Faslche Baumart");
				usage();
			}
			if(args[0].equals("echo")) {
				pp_assignment3.echo.Controller.start(tree);
			} else if(args[0].equals("election")){
				int runs = Integer.parseInt(args[2]);
				pp_assignment3.election.Controller.start(tree, runs);
			} else {
				System.out.println("Faslcher Algorithmus");
				usage();
			}
		}
	}

	private static void usage() {
		System.out.println("usage:");
		System.out.println("arg1: algorithm: 'echo' or 'election'");
		System.out.println("arg2: tree: 'complex' or 'complete'");
		System.out.println("[if arg1 == election] runs for electionalgorithm (int)");
	}
}
