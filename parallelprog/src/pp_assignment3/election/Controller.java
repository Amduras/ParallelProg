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
	static boolean fin = true;
	static boolean running = false;
	static NodeInstance[] nodes;
	
	public static void start(String tree, int runs) {
		int run = 0;
		int length;
		
		if(tree.equals("complex")) {
			length = 9;
		} else {
			length = 5;
		}
		Integer[] id = createId(length);
		nodes = createTree(tree, length, id);
		
		while(run < runs) {
			if (running) break;
			++run;
			List<Integer> ini = chooseInitiators(nodes);
			System.out.println("**************************************************************\nStarting new Round("+run+") with:");
			for(int j=0; j < ini.size();++j) {
				System.out.print(nodes[ini.get(j)].name+"("+id[ini.get(j)]+") ");
			}
			System.out.println();
			for(int j=0; j < ini.size();++j) {
				final int x = ini.get(j);
				nodes[x].setInitiator(id[x]);
				new Thread(()->nodes[x].wakeup(null,id[x])).start();
			}
		}
	}
	
	private static NodeInstance[] createTree(String tree, int length, Integer[] id) {
		NodeInstance[] nodes = new NodeInstance[length];
		if(tree == "complex") {
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
				nodes[RED].setupNeighbours(nodes[BLUE],nodes[YELLOW],nodes[ORANGE],nodes[RED]);
				nodes[ORANGE].setupNeighbours(nodes[RED],nodes[BLACK],nodes[BROWN]);
				nodes[YELLOW].setupNeighbours(nodes[RED],nodes[BLACK],nodes[WHITE]);
				nodes[BLACK].setupNeighbours(nodes[ORANGE],nodes[YELLOW]);
				nodes[WHITE].setupNeighbours(nodes[YELLOW],nodes[WHITE]);
				nodes[BROWN].setupNeighbours(nodes[ORANGE],nodes[GREEN]);
			} else {
				nodes[BLUE] = new NodeInstance("blue", false, id[0]);
				nodes[MAGENTA] = new NodeInstance("magenta", false, id[1]);
				nodes[GREEN] = new NodeInstance("green", false, id[2]);
				nodes[RED] = new NodeInstance("red", false, id[3]);
				nodes[ORANGE] = new NodeInstance("orange", false, id[4]);


				nodes[BLUE].setupNeighbours(nodes[MAGENTA],nodes[GREEN],nodes[RED],nodes[ORANGE]);
				nodes[MAGENTA].setupNeighbours(nodes[BLUE],nodes[GREEN],nodes[RED],nodes[ORANGE]);
				nodes[GREEN].setupNeighbours(nodes[BLUE],nodes[MAGENTA],nodes[RED],nodes[ORANGE]);
				nodes[RED].setupNeighbours(nodes[BLUE],nodes[MAGENTA],nodes[GREEN],nodes[ORANGE]);
				nodes[ORANGE].setupNeighbours(nodes[BLUE],nodes[MAGENTA],nodes[GREEN],nodes[RED]);
			}
		return nodes;
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
		int numberOfInitiators = rng.nextInt(2)+2;
		while(ini.size() < numberOfInitiators) {
			int x = rng.nextInt(nodes.length);
			if(!ini.contains(x)) {
				ini.add(x);
			}
		}
		return ini;		
	}

	
	static void printNodeMaxId() {
		if(fin) {
			System.out.println("~fin~");
			for(NodeInstance i: nodes) {
				System.out.println(i.toString()+": "+i.maxId);
			}
		}
		running = false;
	}
}