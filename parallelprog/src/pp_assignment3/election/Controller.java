package pp_assignment3.election;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



public class Controller {
	static int BLUE = 0, MAGENTA = 1, GREEN = 2, RED = 3,
			ORANGE = 4, YELLOW = 5, BLACK = 6, WHITE = 7,
			BROWN = 8;
	static int Min = 1, Max = 20;
	static NodeInstance[] nodes;
	static boolean fin = true;
	static boolean running = false;
	
	public static void main(String[] args) {		
		//initialisation
		int length = 9;
		int runs = 1;
		int run = 0;
		
		Integer[] id = createId(length);
		nodes = createTestTree(length, id);
		
		//main loop
		while(run < runs) {
			if (running) break;
			++run;
			List<Integer> ini = chooseInitiators(nodes);
			System.out.println("**************************************************************\nStarting new Round("+run+") with:");
			//for(int j=0; j < ini.size();++j) {
				System.out.print(nodes[2].name+"("+id[2]+") ");
				System.out.print(nodes[5].name+"("+id[5]+") ");
				System.out.print(nodes[7].name+"("+id[7]+") ");
			//}
			System.out.println();
			//for(int j=0; j < ini.size();++j) {
				nodes[2].setInitiator(id[2]);
				nodes[5].setInitiator(id[5]);
				nodes[7].setInitiator(id[7]);
				new Thread(()->nodes[2].wakeup(null,id[2])).start();
				new Thread(()->nodes[5].wakeup(null,id[5])).start();
				new Thread(()->nodes[7].wakeup(null,id[7])).start();
			//}
		}
	}
	private static Integer[] createId(int length) {
		Integer[] id  = new Integer[length];
		int i = 0;
		while(i != id.length) {
			Integer x = Min + (int)(Math.random() * ((Max - Min) + 1));
			if(!Arrays.asList(id).contains(x)) {
				id[i++] = x;
			}
		}
		return id;

	}
	private static List<Integer> chooseInitiators(NodeInstance[] nodes) {
		Random rng = new Random();
		List<Integer> ini = new ArrayList<Integer>();
		for(int i=0, numberOfInitiators = rng.nextInt(2)+2;i<numberOfInitiators;++i)
			ini.add(rng.nextInt(nodes.length));
		return ini;		
	}
	
	private static NodeInstance[] createTestTree(int length, Integer[] id) {
		NodeInstance[] nodes = new NodeInstance[length];
		nodes[BLUE] = new NodeInstance("blue", false, id[0]);
		nodes[MAGENTA] = new NodeInstance("magenta", false, id[1]);
		nodes[GREEN] = new NodeInstance("green", false, id[2]);
		nodes[RED] = new NodeInstance("red", false, id[3]);
		nodes[ORANGE] = new NodeInstance("orange", false, id[4]);
		nodes[YELLOW] = new NodeInstance("yellow", false, id[5]);
		nodes[BLACK] = new NodeInstance("black", false, id[6]);
		nodes[WHITE] = new NodeInstance("white", false, id[7]);
		nodes[BROWN] = new NodeInstance("brown", false, id[8]);
		
		nodes[BLUE].setupNeighbours(nodes[MAGENTA],nodes[RED]);
		nodes[MAGENTA].setupNeighbours(nodes[BLUE],nodes[GREEN]);
		nodes[GREEN].setupNeighbours(nodes[MAGENTA],nodes[BROWN]);
		nodes[RED].setupNeighbours(nodes[BLUE],nodes[YELLOW],nodes[ORANGE]);
		nodes[ORANGE].setupNeighbours(nodes[RED],nodes[BLACK],nodes[BROWN]);
		nodes[YELLOW].setupNeighbours(nodes[RED],nodes[BLACK],nodes[WHITE]);
		nodes[BLACK].setupNeighbours(nodes[ORANGE],nodes[YELLOW]);
		nodes[WHITE].setupNeighbours(nodes[YELLOW]);
		nodes[BROWN].setupNeighbours(nodes[ORANGE],nodes[GREEN]);
		
		return nodes;
	}
	
	static void printChildren() {
		if(fin) {
			for(NodeInstance i: nodes) {
				//System.out.print(i.toString()+": "+i.maxId);
				//System.out.print("Children from "+i.toString()+": ");
				//for(NodeInstance x: i.children) {
				//	System.out.print(x.toString()+" ");
				//}
				System.out.println();
			}
		}
	}
}